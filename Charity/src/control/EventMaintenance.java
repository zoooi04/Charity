package control;

/**
 *
 * @author AndrewPhengQiJinn
 */
import utility.MessageUI;
import boundary.EventMaintenanceUI;
import entity.*;
import adt.*;
import dao.EventDAO;
import dao.DAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class EventMaintenance {

    //private SortedListInterface<Event> events = new SortedLinkedList<>();
    private MapInterface<String, Event> eventsMap = new HashMap<>();
    private GraphInterface<String, Integer> eventGraph = new WeightedGraph<>();
    private MapInterface<String, Volunteer> volunteersMap = new HashMap<>();

    private StackInterface<GraphInterface<String, Integer>> graphUndoStack = new LinkedStack<>();
    private StackInterface<GraphInterface<String, Integer>> graphRedoStack = new LinkedStack<>();

    private DAO dao = new DAO();
    private EventDAO eventDAO = new EventDAO();
    private EventMaintenanceUI eventUI = new EventMaintenanceUI();

    private static final String FILENAME = "event.txt";
    private static final String FILENAME2 = "eventVolunteer.txt";
    private static final String FILENAME3 = "volunteer.txt";
    private static final String FILENAME4 = "donationHashMap.dat";
    private static final String FILENAME5 = "donor.dat";

    public EventMaintenance() {
        eventsMap = eventDAO.retrieveFromFile(FILENAME);
        eventGraph = eventDAO.createGraphOfAssignedVolunteersToEvent(FILENAME2);
        volunteersMap = eventDAO.retrieveVolunteers(FILENAME3);
        countCurrentVolunteersAndUpdateTextFile();
    }

    public void runEventMaintenance() {
        int ch = 0;
        do {
            ch = eventUI.getChoice(graphUndoStack.isEmpty(), graphRedoStack.isEmpty());
            switch (ch) {
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                case 1:
                    addEvent();
                    break;
                case 2:
                    deleteEvent();
                    break;
                case 3:
                    restoreEvent();
                    break;

                case 4:
                    searchEventMenu();
                    break;

                case 5:
                    editEvent();
                    break;

                case 6:
                    displayAllEvents();
                    break;
                case 7:
                    removeVolunteerFromEvent();
                    break;
                case 8:
                    EventVolunteerListMenu();
                    break;
                case 9:
                    //generate report
                    generateReport();
                    break;
                case 10:
                    //display availability hours
                    displayAvailabilityHours();
                    break;
                case 11:
                    // edit availability hours
                    editAvailabilityHours();
                    break;

                case 12:   //undo remove volunteer from event
                    undoRemoveVolunteerFromEvent();
                    break;
                case 13:   //redo remove volunteer from event (add them back)
                    redoRemoveVolunteerFromEvent();
                    break;

                default:
                    MessageUI.displayInvalidChoiceMessage();

            }
        } while (ch != 0);
    }

    public void countCurrentVolunteersAndUpdateTextFile() {
        ListInterface<String> events = eventGraph.getAllVertexObjects();
        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            ListInterface<String> assignedVolunteers = eventGraph.getNeighbours(events.getEntry(i + 1));

            Event event = eventsMap.get(events.getEntry(i + 1));
            if (event != null) {
                event.setCurrentVolunteer(assignedVolunteers.getNumberOfEntries());

                eventsMap.put(event.getId(), event);
            }
        }

        eventDAO.saveToFile(eventsMap, FILENAME);

    }

    public String displayAvailabilityHours() {
        String id = eventUI.getEventID("checked for volunteer availability: ");
        Event event = null;
        event = search(id);
        if (event != null) {
            displayDetails(event);
            return id;

        } else {
            MessageUI.displaySearchNotFoundMessage("Event " + id);
            return null;
        }
    }

    public void displayDetails(Event event) {
        eventUI.printVolunteerTableHeaderLine(event.getId());
        eventUI.printEventDetails(event);

        eventUI.printVolunteerTableHeader();
        ListInterface<String> assignedVolunteers = new ArrayList<>();
        assignedVolunteers = eventGraph.getNeighbours(event.getId());

        SortedListInterface<String> sortedVolunteers = new SortedLinkedList<>();
        for (int i = 0; i < assignedVolunteers.getNumberOfEntries(); i++) {
            sortedVolunteers.add(assignedVolunteers.getEntry(i + 1));
        }

        for (int i = 0; i < sortedVolunteers.getNumberOfEntries(); i++) {
            String volunteerID = sortedVolunteers.getEntry(i + 1);
            Volunteer volunteer = volunteersMap.get(volunteerID);
            if (volunteer != null) {
                Integer availabilityHalfHours = eventGraph.getEdgeWeight(event.getId(), volunteerID);

                eventUI.printVolunteerRecords(volunteer, (double) availabilityHalfHours / 2, i + 1);
            }
        }

        eventUI.printVolunteerTableFooter();
    }

    public void editAvailabilityHours() {
        String eventID = eventUI.getEventID("checked for volunteer availability for editing: ");
        Event event = search(eventID);

        if (event != null) {

            if (LocalDate.now().isAfter(event.getEndDate())) {
                System.out.println("The event has ended. You cannot edit availability hours.\n");
                return;
            }

            displayDetails(event); // Display details initially

            StackInterface<GraphInterface<String, Integer>> undoStack = new LinkedStack<>();
            StackInterface<GraphInterface<String, Integer>> redoStack = new LinkedStack<>();
            boolean continueEditing = true;

            while (continueEditing) {
                String id = eventUI.getVolunteerID("to edit availability");
                Volunteer volunteer = volunteersMap.get(id);

                if (volunteer == null) {
                    MessageUI.displaySearchNotFoundMessage("Volunteer " + id);
                } else {

                    ListInterface<String> volunteerList = eventGraph.getNeighbours(event.getId());
                    boolean found = false;
                    for (int i = 0; i < volunteerList.getNumberOfEntries(); i++) {
                        if (volunteerList.getEntry(i + 1).equals(id)) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("This volunteer is not involved in this event.\n");

                    } else {
                        // Push current state to undo stack before making changes
                        undoStack.push(deepCopyGraph(eventGraph)); // assuming eventGraph has a clone method

                        // Clear the redo stack whenever a new change is made
                        redoStack.clear();

                        System.out.println("Current availability half hours of Volunteer " + id + ": " + eventGraph.getEdgeWeight(eventID, id));
                        int halfHour = eventUI.getAvailabilityHalfHours();

                        // Edit the availability hours
                        if (eventGraph.removeUndirectedEdge(eventID, id)) {
                            eventGraph.addUndirectedEdge(eventID, id, halfHour);
                            eventDAO.saveGraphToFile(eventGraph, FILENAME2);
                            System.out.println("Edited. Volunteer " + id + " now has " + halfHour + " half hours, which is " + (double) (halfHour / 2) + " hours.");
                        }

                        displayDetails(event); // Display details after making changes
                    }

                }

                boolean validChoice = false;
                while (!validChoice) {
                    // Ask the user what they want to do next
                    String choice = eventUI.getUserChoice("Would you like to (E)dit again, (U)ndo last edit, (R)edo last undo, or (Q)uit?");

                    switch (choice.toUpperCase()) {
                        case "E":
                            validChoice = true;
                            break;

                        case "U":
                            if (!undoStack.isEmpty()) {
                                redoStack.push(deepCopyGraph(eventGraph)); // Save current state before undo
                                eventGraph = undoStack.pop();
                                eventDAO.saveGraphToFile(eventGraph, FILENAME2);
                                System.out.println("Undo successful.");
                                displayDetails(event); // Display details after undo

                            } else {
                                System.out.println("Nothing to undo.");

                            }
                            validChoice = false;

                            break;

                        case "R":
                            if (!redoStack.isEmpty()) {
                                undoStack.push(deepCopyGraph(eventGraph)); // Save current state before redo
                                eventGraph = redoStack.pop();
                                eventDAO.saveGraphToFile(eventGraph, FILENAME2);
                                System.out.println("Redo successful.");
                                displayDetails(event); // Display details after redo

                            } else {
                                System.out.println("Nothing to redo.");

                            }
                            validChoice = false;
                            break;

                        case "Q":
                            continueEditing = false; // Exit the loop
                            validChoice = true;
                            System.out.println("Changes saved.\n");
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            }
        } else {
            MessageUI.displaySearchNotFoundMessage("Event " + eventID);
        }
    }

    public void generateReport() {
        int reportChoice = 0;

        do {
            reportChoice = eventUI.getReportChoice();
            switch (reportChoice) {
                case 1:
                    generateEventPerformanceReport();
                    break;
                case 2:
                    handleTopFiveDonorReport();
                    break;
                case 3:
                    //test
                    handleTop5EventReport();
                    break;
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }

        } while (reportChoice != 0);

    }

    public void generateEventSpecificTopDonorReport() {
        String id = eventUI.getEventID("");
        Event event = eventsMap.get(id);
        if (event != null) {

            if (event.getStartDate().isAfter(LocalDate.now())) {
                System.out.println("Event has not started yet. No report available.");
                return;
            }

            DonationMaintenance dm = new DonationMaintenance();
            MapInterface<String, Donation> donationMap = (HashMap<String, Donation>) dao.retrieveFromFile(FILENAME4);
            ListInterface<Donation> allDonationList = donationMap.values();

            ListInterface<Donor> allDonor = (ListInterface<Donor>) dao.retrieveFromFile(FILENAME5);

            //create a map to store the total donations by each donor for this event
            MapInterface<String, Double> donorTotalDonations = new HashMap<>();

            for (int i = 0; i < allDonationList.getNumberOfEntries(); i++) {
                Donation donation = allDonationList.getEntry(i + 1);

                if (donation.getType().equals(Donation.DonationType.CASH)) {
                    if (donation.getEvent().getId().equals(id)) {
                        String donorID = donation.getDonor().getId();
                        if (donorTotalDonations.containsKey(donorID)) {
                            Double amt = donorTotalDonations.get(donorID);
                            donorTotalDonations.put(donorID, amt + donation.getQuantity());
                        } else {
                            donorTotalDonations.put(donorID, donation.getQuantity());
                        }
                    }
                }
            }
            //after getting all donors total donations...

            // Convert the map to a list of entries (manually iterate through the map)
            //this list is store all volunteerID, easy iteration 
            ListInterface<String> donorKeys = donorTotalDonations.keySet();

            //this will store the donor ID and the amount of cash donation paid
            ListInterface<Pair<String, Double>> donorList = new ArrayList<>();

            for (int i = 0; i < donorKeys.getNumberOfEntries(); i++) {
                String donorID = donorKeys.getEntry(i + 1);
                Double totalDonation = donorTotalDonations.get(donorID);
                donorList.add(new Pair<>(donorID, totalDonation));
            }

            // Sort the list manually using a simple sorting algorithm 
            for (int i = 0; i < donorList.getNumberOfEntries() - 1; i++) {

                for (int j = 0; j < donorList.getNumberOfEntries() - 1 - i; j++) {
                    if (donorList.getEntry(j + 1).getSecond() < donorList.getEntry(j + 2).getSecond()) {
                        // Swap the entries
                        Pair<String, Double> temp = donorList.getEntry(j + 1);
                        donorList.replace(j + 1, donorList.getEntry(j + 2));
                        donorList.replace(j + 2, temp);
                    }
                }
            }

            eventUI.generateReportHeader("Top Five Donors of Event " + id);

            // Display the sorted results
            eventUI.printSingleEventDetailsInReport(event);

            for (int i = 0; i < donorList.getNumberOfEntries(); i++) {
                if (i == 5) {
                    break;
                }
                Pair<String, Double> entry = donorList.getEntry(i + 1);
                Donor donor = null;
                for (int j = 0; j < allDonor.getNumberOfEntries(); j++) {
                    if (allDonor.getEntry(j + 1).getId().equals(entry.getFirst())) {
                        donor = allDonor.getEntry(j + 1);
                    }
                }

                eventUI.printSingleDonorDetailsInReport(donor, entry.getSecond(), i + 1, event.getCurrentAmount());

            }

            eventUI.PrintSingleEventDetailsReportFooter();

        } else {
            MessageUI.displaySearchNotFoundMessage("Event " + id);
        }

    }

    public void generateMonthlyTopDonorReport() {
        LocalDate start = eventUI.getMonthAndYearFromUser("Enter start month/year (e.g., 01/2024): ");
        LocalDate end = eventUI.getEndMonthAndYearFromUser(start);

        String combinedEventString = "";

        DonationMaintenance dm = new DonationMaintenance();
        MapInterface<String, Donation> donationMap = (HashMap<String, Donation>) dao.retrieveFromFile(FILENAME4);
        ListInterface<Donation> allDonationList = donationMap.values();
        ListInterface<Donor> allDonor = (ListInterface<Donor>) dao.retrieveFromFile(FILENAME5);

        MapInterface<String, DonorReportData> donorReportDataMap = new HashMap<>();

        int eventCount = 0;
        ListInterface<Event> eventList = eventsMap.values();
        for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
            Event event = eventList.getEntry(i + 1);
            if (!event.getIsDeleted()) {
                if (!event.getEndDate().isBefore(start) && !event.getStartDate().isAfter(end)) {
                    if (combinedEventString.length() > 0) {
                        combinedEventString += ", ";
                    }
                    combinedEventString += event.getId();
                    eventCount++;

                    //a set to track which donors have supported this event
                    SetInterface<String> donorsWhoSupportedThisEvent = new ArraySet<>();

                    for (int j = 0; j < allDonationList.getNumberOfEntries(); j++) {
                        Donation donation = allDonationList.getEntry(j + 1);
                        if (donation.getEvent().getId().equals(event.getId()) && donation.getType().equals(Donation.DonationType.CASH)) {
                            String donorID = donation.getDonor().getId();
                            DonorReportData donorData = donorReportDataMap.get(donorID);

                            if (donorData == null) {
                                donorData = new DonorReportData(donorID);
                            }

                            donorData.totalContribution += donation.getQuantity();
                            // If the donor hasn't been counted for this event yet, increment the event count
                            if (!donorsWhoSupportedThisEvent.contains(donorID)) {
                                donorData.numberOfEventsSupported += 1;
                                donorsWhoSupportedThisEvent.add(donorID);
                            }
                            donorData.averageContributionPerEvent = donorData.totalContribution / donorData.numberOfEventsSupported;

                            donorReportDataMap.put(donorID, donorData);
                        }
                    }
                }
            }
        }
        if (eventCount == 0) {
            System.out.println("No events within specified range.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        // Format start and end dates
        String startFormatted = start.format(formatter);
        String endFormatted = end.format(formatter);

        eventUI.generateReport2Header("Top Five Donors of Events From " + startFormatted + " to " + endFormatted);
        eventUI.generateReport2Body1(eventCount, combinedEventString);

        ListInterface<DonorReportData> donorList = donorReportDataMap.values();

        int n = donorList.getNumberOfEntries();

        // Perform Bubble Sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                DonorReportData d1 = donorList.getEntry(j + 1);
                DonorReportData d2 = donorList.getEntry(j + 2);

                // Compare total contributions
                if (d1.totalContribution < d2.totalContribution) {
                    // Swap the entries
                    donorList.replace(j + 1, d2);
                    donorList.replace(j + 2, d1);
                }
            }
        }

        for (int i = 0; i < Math.min(donorList.getNumberOfEntries(), 5); i++) {
            DonorReportData donorData = donorList.getEntry(i + 1);
            Donor donor = findDonorById(donorData.donorID, allDonor);
            eventUI.printSingleDonorDetailsInReport2(donor, donorData.totalContribution, donorData.numberOfEventsSupported, donorData.averageContributionPerEvent, i + 1);
        }

        eventUI.PrintTopDonorMonthReportFooter();
    }

    public void generateYearlyTopDonorReport() {
        int year = eventUI.getYearFromUser("Enter the year (e.g., 2023): ");

        //get current date
        LocalDate now = LocalDate.now();

        //determine start and end date
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end;

        if (year == now.getYear()) {
            // If the year is the current year, set the end date to the current month
            end = LocalDate.of(year, now.getMonthValue(), now.lengthOfMonth());
        } else {
            // If the year is in the past, set the end date to December 31st of that year
            end = LocalDate.of(year, 12, 31);
        }

        String combinedEventString = "";
        DonationMaintenance dm = new DonationMaintenance();
        MapInterface<String, Donation> donationMap = (HashMap<String, Donation>) dao.retrieveFromFile(FILENAME4);
        ListInterface<Donation> allDonationList = donationMap.values();
        ListInterface<Donor> allDonor = (ListInterface<Donor>) dao.retrieveFromFile(FILENAME5);

        MapInterface<String, DonorReportData> donorReportDataMap = new HashMap<>();

        int eventCount = 0;

        ListInterface<Event> eventList = eventsMap.values();
        for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
            Event event = eventList.getEntry(i + 1);
            if (!event.getIsDeleted()) {
                if (!event.getEndDate().isBefore(start) && !event.getStartDate().isAfter(end)) {
                    if (combinedEventString.length() > 0) {
                        combinedEventString += ", ";
                    }
                    combinedEventString += event.getId();
                    eventCount++;
                    //a set to track which donors have supported this event
                    SetInterface<String> donorsWhoSupportedThisEvent = new ArraySet<>();

                    for (int j = 0; j < allDonationList.getNumberOfEntries(); j++) {
                        Donation donation = allDonationList.getEntry(j + 1);
                        if (donation.getEvent().getId().equals(event.getId()) && donation.getType().equals(Donation.DonationType.CASH)) {
                            String donorID = donation.getDonor().getId();
                            DonorReportData donorData = donorReportDataMap.get(donorID);

                            if (donorData == null) {
                                donorData = new DonorReportData(donorID);
                            }

                            donorData.totalContribution += donation.getQuantity();
                            // If the donor hasn't been counted for this event yet, increment the event count

                            if (!donorsWhoSupportedThisEvent.contains(donorID)) {
                                donorData.numberOfEventsSupported += 1;
                                donorsWhoSupportedThisEvent.add(donorID);
                            }
                            donorData.averageContributionPerEvent = donorData.totalContribution / donorData.numberOfEventsSupported;

                            donorReportDataMap.put(donorID, donorData);
                        }
                    }
                }
            }
        }
        if (eventCount == 0) {
            System.out.println("No events within the specified year.");
            return;
        }

        eventUI.generateReport2Header("Top Five Donors of Events in Year " + year);
        eventUI.generateReport2Body1(eventCount, combinedEventString);

        ListInterface<DonorReportData> donorList = donorReportDataMap.values();
        int n = donorList.getNumberOfEntries();

        // Perform Bubble Sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                DonorReportData d1 = donorList.getEntry(j + 1);
                DonorReportData d2 = donorList.getEntry(j + 2);

                if (d1.totalContribution < d2.totalContribution) {
                    donorList.replace(j + 1, d2);
                    donorList.replace(j + 2, d1);
                }
            }
        }

        for (int i = 0; i < Math.min(donorList.getNumberOfEntries(), 5); i++) {
            DonorReportData donorData = donorList.getEntry(i + 1);
            Donor donor = findDonorById(donorData.donorID, allDonor);
            eventUI.printSingleDonorDetailsInReport2(donor, donorData.totalContribution, donorData.numberOfEventsSupported, donorData.averageContributionPerEvent, i + 1);
        }

        eventUI.PrintTopDonorMonthReportFooter();
    }

    public void generateAllTimeTopDonorReport() {
        DonationMaintenance dm = new DonationMaintenance();
        MapInterface<String, Donation> donationMap = (HashMap<String, Donation>) dao.retrieveFromFile(FILENAME4);
        ListInterface<Donation> allDonationList = donationMap.values();
        ListInterface<Donor> allDonor = (ListInterface<Donor>) dao.retrieveFromFile(FILENAME5);

        MapInterface<String, DonorReportData> donorReportDataMap = new HashMap<>();
        int eventCount = 0;

        ListInterface<Event> eventList = eventsMap.values();
        for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
            Event event = eventList.getEntry(i + 1);

            // Check if the event is not deleted and has already started
            if (!event.getIsDeleted() && !event.getStartDate().isAfter(LocalDate.now())) {

                eventCount++;

                // A set to track which donors have supported this event
                SetInterface<String> donorsWhoSupportedThisEvent = new ArraySet<>();

                for (int j = 0; j < allDonationList.getNumberOfEntries(); j++) {
                    Donation donation = allDonationList.getEntry(j + 1);
                    if (donation.getEvent().getId().equals(event.getId()) && donation.getType().equals(Donation.DonationType.CASH)) {
                        String donorID = donation.getDonor().getId();
                        DonorReportData donorData = donorReportDataMap.get(donorID);

                        if (donorData == null) {
                            donorData = new DonorReportData(donorID);
                        }

                        // Update total contribution for the donor
                        donorData.totalContribution += donation.getQuantity();

                        // If the donor hasn't been counted for this event yet, increment the event count
                        if (!donorsWhoSupportedThisEvent.contains(donorID)) {
                            donorData.numberOfEventsSupported += 1;
                            donorsWhoSupportedThisEvent.add(donorID);
                        }

                        // Update average contribution per event
                        donorData.averageContributionPerEvent = donorData.totalContribution / donorData.numberOfEventsSupported;

                        donorReportDataMap.put(donorID, donorData);
                    }
                }
            }
        }

        if (eventCount == 0) {
            System.out.println("No events available.");
            return;
        }

        eventUI.generateReport2Header("Top Five Donors of All Time");
        eventUI.generateReport2Body2(eventCount);

        ListInterface<DonorReportData> donorList = donorReportDataMap.values();
        int n = donorList.getNumberOfEntries();

        // Perform Bubble Sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                DonorReportData d1 = donorList.getEntry(j + 1);
                DonorReportData d2 = donorList.getEntry(j + 2);

                if (d1.totalContribution < d2.totalContribution) {
                    donorList.replace(j + 1, d2);
                    donorList.replace(j + 2, d1);
                }
            }
        }

        for (int i = 0; i < Math.min(donorList.getNumberOfEntries(), 5); i++) {
            DonorReportData donorData = donorList.getEntry(i + 1);
            Donor donor = findDonorById(donorData.donorID, allDonor);
            eventUI.printSingleDonorDetailsInReport2(donor, donorData.totalContribution, donorData.numberOfEventsSupported, donorData.averageContributionPerEvent, i + 1);
        }

        eventUI.PrintTopDonorMonthReportFooter();
    }

    public void generateEventPerformanceReport() {
        // Prompt user for start and end dates
        LocalDate start = eventUI.getMonthAndYearFromUser("Enter start month/year (e.g., 01/2024): ");
        LocalDate end = eventUI.getEndMonthAndYearFromUser(start);

        int eventCount = 0;
        int achievedCount = 0;
        double totalAmountRaised = 0;
        double totalTargetAmount = 0;
        double totalExcessAmount = 0;

        ListInterface<Event> eventList = eventsMap.values();

        // Iterate through the list of events
        for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
            Event event = eventList.getEntry(i + 1);

            // Check if the event falls within the specified date range and is not deleted
            if (!event.getIsDeleted() && !event.getEndDate().isBefore(start) && !event.getStartDate().isAfter(end)) {
                // Convert startDate and endDate to String format
                eventCount++;

                // Check if the event achieved its target
                if (event.getCurrentAmount() >= event.getGoalAmount()) {
                    achievedCount++;
                }
            }
        }

        if (eventCount == 0) {
            System.out.println("No events within the specified range.");
            return;
        }

        // Format start and end dates
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
        String startFormatted = start.format(monthYearFormatter);
        String endFormatted = end.format(monthYearFormatter);

        // Generate the report header
        eventUI.generateReport3Header("Event Performance Report From " + startFormatted + " to " + endFormatted);
        eventUI.generateReport3Body(eventCount);

        int eventNumber = 0;
        // Iterate through the list of events and print details
        for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
            Event event = eventList.getEntry(i + 1);
            // Check if the event falls within the specified date range and is not deleted
            if (!event.getIsDeleted() && !event.getEndDate().isBefore(start) && !event.getStartDate().isAfter(end)) {
                eventUI.printEventDetailInEventPerformanceReport(event, eventNumber + 1);
                totalTargetAmount += event.getGoalAmount();
                totalAmountRaised += event.getCurrentAmount();
                totalExcessAmount += (event.getCurrentAmount() - event.getGoalAmount());

                eventNumber++;
            }
        }

        eventUI.generateReport3Footer(totalTargetAmount, totalAmountRaised, totalExcessAmount, achievedCount, eventCount);

    }

    public void generateTop5EventReport() {
        // Prompt user for start and end dates
        LocalDate start = eventUI.getMonthAndYearFromUser("Enter start month/year (e.g., 01/2024): ");
        LocalDate end = eventUI.getEndMonthAndYearFromUser(start);

        // Create a list to store events within the specified date range
        ListInterface<Event> eventList = new ArrayList<>();

        // Retrieve all events and filter them based on the date range
        ListInterface<Event> allEvents = eventsMap.values();
        for (int i = 0; i < allEvents.getNumberOfEntries(); i++) {
            Event event = allEvents.getEntry(i + 1);
            if (!event.getIsDeleted() && !event.getEndDate().isBefore(start) && !event.getStartDate().isAfter(end)) {
                eventList.add(event);
            }
        }

        // Check if there are no events in the specified date range
        if (eventList.getNumberOfEntries() == 0) {
            System.out.println("No events found within the specified range.");
            return;
        }

        // Bubble Sort events based on the amount raised (in descending order)
        for (int i = 0; i < eventList.getNumberOfEntries() - 1; i++) {
            for (int j = 0; j < eventList.getNumberOfEntries() - 1 - i; j++) {
                Event e1 = eventList.getEntry(j + 1);
                Event e2 = eventList.getEntry(j + 2);
                if (e1.getCurrentAmount() < e2.getCurrentAmount()) {
                    // Swap events
                    eventList.replace(j + 1, e2);
                    eventList.replace(j + 2, e1);
                }
            }
        }

        // Determine the number of events to display
        int numEventsToDisplay = Math.min(5, eventList.getNumberOfEntries());

        // Get the top events
        ListInterface<Event> topEvents = new ArrayList<>();
        for (int i = 0; i < numEventsToDisplay; i++) {
            topEvents.add(eventList.getEntry(i + 1));
        }

        // Format start and end dates
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
        String startFormatted = start.format(monthYearFormatter);
        String endFormatted = end.format(monthYearFormatter);

        eventUI.generateReport3Header("Top 5 Events From " + startFormatted + " to " + endFormatted);

        eventUI.generateReport3Body(eventList.getNumberOfEntries());

        for (int i = 0; i < topEvents.getNumberOfEntries(); i++) {
            Event event = topEvents.getEntry(i + 1);

            // Print the event details (customize this as needed)
            eventUI.printEventDetailInEventPerformanceReport(event, i + 1);
        }

        eventUI.generateReport4Footer();
    }

    public void generateTop5EventReportAllTime() {
        // Create a list to store all events
        ListInterface<Event> eventList = new ArrayList<>();

        // Retrieve all events from the events map
        ListInterface<Event> allEvents = eventsMap.values();
        for (int i = 0; i < allEvents.getNumberOfEntries(); i++) {
            Event event = allEvents.getEntry(i + 1);
            if (!event.getIsDeleted()) {
                eventList.add(event);
            }
        }

        // Check if there are no events
        if (eventList.getNumberOfEntries() == 0) {
            System.out.println("No events found.");
            return;
        }

        // Bubble Sort events based on the amount raised (in descending order)
        for (int i = 0; i < eventList.getNumberOfEntries() - 1; i++) {
            for (int j = 0; j < eventList.getNumberOfEntries() - 1 - i; j++) {
                Event e1 = eventList.getEntry(j + 1);
                Event e2 = eventList.getEntry(j + 2);
                if (e1.getCurrentAmount() < e2.getCurrentAmount()) {
                    // Swap events
                    eventList.replace(j + 1, e2);
                    eventList.replace(j + 2, e1);
                }
            }
        }

        // Determine the number of events to display
        int numEventsToDisplay = Math.min(5, eventList.getNumberOfEntries());

        // Get the top events
        ListInterface<Event> topEvents = new ArrayList<>();
        for (int i = 0; i < numEventsToDisplay; i++) {
            topEvents.add(eventList.getEntry(i + 1));
        }

        // Generate the report header
        eventUI.generateReport3Header("Top 5 Events of All Time");

        // Generate the report body
        eventUI.generateReport3Body(eventList.getNumberOfEntries());

        // Print details for each of the top events
        for (int i = 0; i < topEvents.getNumberOfEntries(); i++) {
            Event event = topEvents.getEntry(i + 1);

            // Print the event details
            eventUI.printEventDetailInEventPerformanceReport(event, i + 1);
        }

        // Generate the report footer
        eventUI.generateReport4Footer();
    }

    private Donor findDonorById(String donorID, ListInterface<Donor> allDonor) {
        for (int i = 0; i < allDonor.getNumberOfEntries(); i++) {
            Donor donor = allDonor.getEntry(i + 1);
            if (donor.getId().equals(donorID)) {
                return donor;
            }
        }
        return null;
    }

    public void handleTop5EventReport() {
        int choice = 0;
        do {
            choice = eventUI.getTop5ReportChoice();
            switch (choice) {
                case 1:
                    generateTop5EventReport();
                    break;
                case 2:
                    generateTop5EventReportAllTime();
                    break;
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }

        } while (choice != 0);
    }

    public void handleTopFiveDonorReport() {
        int choice = 0;
        do {
            choice = eventUI.getTopFiveDonorReportChoice();
            switch (choice) {
                case 1:
                    generateEventSpecificTopDonorReport();
                    break;
                case 2:
                    generateMonthlyTopDonorReport();
                    break;
                case 3:
                    generateYearlyTopDonorReport();
                    break;
                case 4:
                    generateAllTimeTopDonorReport();
                    break;
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }

        } while (choice != 0);
    }

    public void undoRemoveVolunteerFromEvent() {
        if (!graphUndoStack.isEmpty()) {
            GraphInterface<String, Integer> undoneGraph = graphUndoStack.pop();

            graphRedoStack.push(deepCopyGraph(eventGraph));
            eventGraph = undoneGraph;

            eventDAO.saveGraphToFile(eventGraph, FILENAME2);
            System.out.println("Action Undone. Volunteers have been readded back.");

        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void redoRemoveVolunteerFromEvent() {
        if (!graphRedoStack.isEmpty()) {
            GraphInterface<String, Integer> redoneGraph = graphRedoStack.pop();

            graphUndoStack.push(deepCopyGraph(eventGraph));

            eventGraph = redoneGraph;

            eventDAO.saveGraphToFile(eventGraph, FILENAME2);
            System.out.println("Action Redone. Volunteers have been removed again.");
        } else {
            System.out.println("Nothing to redo.");
        }
    }

    private GraphInterface<String, Integer> deepCopyGraph(GraphInterface<String, Integer> originalGraph) {
        GraphInterface<String, Integer> copyGraph = new WeightedGraph<>();

        ListInterface<String> allVertices = originalGraph.getAllVertexObjects();

        for (int i = 0; i < allVertices.getNumberOfEntries(); i++) {
            copyGraph.addVertex(allVertices.getEntry(i + 1));
        }

        for (int j = 0; j < allVertices.getNumberOfEntries(); j++) {

            if (allVertices.getEntry(j + 1).startsWith("E")) {
                ListInterface<String> neighbours = originalGraph.getNeighbours(allVertices.getEntry(j + 1));
                for (int k = 0; k < neighbours.getNumberOfEntries(); k++) {

                    copyGraph.addUndirectedEdge(allVertices.getEntry(j + 1), neighbours.getEntry(k + 1), originalGraph.getEdgeWeight(allVertices.getEntry(j + 1), neighbours.getEntry(k + 1)));
                }
            }
        }

        return copyGraph;
    }

    public void clearGraphUndoAndRedoStacks() {
        graphUndoStack.clear();
        graphRedoStack.clear();
    }

    public void addEvent() {
        Event newEvent = eventUI.AddNewEventUI(generateEventID());

        if (newEvent != null) {
            eventsMap.put(newEvent.getId(), newEvent);
            System.out.println("\nEvent added successfully.\n");
            eventDAO.saveToFile(eventsMap, FILENAME);
            clearGraphUndoAndRedoStacks();
        } else {
            System.out.println("\nEvent creation was canceled.\n");
        }
    }

    //calling single delete function
    public void deleteEvent() {
        String id = eventUI.getEventID("deleted");
        Event event = null;
        event = search(id);
        if (event != null) {
            if (event.getCurrentAmount() > 0) {
                System.out.println("Event contains donations. Unable to delete.\n");
                return;
            }

            eventUI.printEventDetails(event);
            char confirm = eventUI.getDeleteConfirmation();
            if (confirm == 'Y') {
                event.setIsDeleted(true); // Mark it as deleted

                // Update the hashmap value of the key
                eventsMap.put(id, event);
                eventDAO.saveToFile(eventsMap, "event.txt");
                System.out.println("Event " + id + " deleted.\n");
                clearGraphUndoAndRedoStacks();
            } else {
                System.out.println("Event " + id + " was not deleted.\n");
            }
        } else {
            MessageUI.displaySearchNotFoundMessage("Event " + id);
        }
    }

    //calling restore deleted events function
    public void restoreEvent() {

        if (!getAllDeletedEvents().isEmpty()) {

            displayDeletedEvents();
            System.out.println();
            String id = eventUI.getEventID("restored");

            if (eventsMap.containsKey(id) && eventsMap.get(id).getIsDeleted()) {
                Event event = eventsMap.get(id);

                eventUI.printEventDetails(event);

                char confirm = eventUI.getRestoreConfirmation();
                if (confirm == 'Y') {
                    event.setIsDeleted(false);

                    eventsMap.put(id, event);
                    eventDAO.saveToFile(eventsMap, "event.txt");
                    System.out.println("Event " + id + " restored.\n");
                    clearGraphUndoAndRedoStacks();
                } else {
                    System.out.println("Event " + id + " was not restored.\n");
                }

            } else {
                MessageUI.displaySearchNotFoundMessage("Event " + id);
            }

        } else {
            System.out.println("\nNo events available for restoration.\n");
        }

    }

    //calling search function menu
    public void searchEventMenu() {
        int choice = 0;
        do {
            choice = eventUI.getSearchChoice(); // Assume this method prompts the user for their search choice

            switch (choice) {
                case 1:
                    singleSearch();
                    break;
                case 2:
                    batchSearch();
                    break;
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0); // Continue until user chooses to return to the main menu
    }

    public Event search(String eventID) {
        if (eventsMap.containsKey(eventID) && !(eventsMap.get(eventID).getIsDeleted())) {
            return eventsMap.get(eventID);
        } else {
            return null;
        }
    }

    public void singleSearch() {
        String id = eventUI.getEventID("search");
        Event event = search(id);
        if (event != null) {
            eventUI.printEventDetails(event);
        } else {
            MessageUI.displaySearchNotFoundMessage("Event " + id);
        }

    }

    public void batchSearch() {

        int choice = 0;
        do {
            choice = eventUI.getBatchSearchChoice(); // Get user's choice for batch search

            switch (choice) {
                case 1:
                    searchByName();
                    break;
                case 2:
                    searchByDateRange();
                    break;
                case 3:
                    searchByLocation();
                    break;
                case 4:
                    searchByVolunteerRange();
                    break;
                case 5:
                    searchByAmountRaised();
                    break;
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0);
    }

    public void searchByName() {
        String str = eventUI.getSearchString();
        boolean found = false;
        ListInterface<Event> events = eventsMap.values();
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            if (!events.getEntry(i + 1).getIsDeleted()) {

                Event existingEvent = events.getEntry(i + 1);
                if (existingEvent.getName().toLowerCase().contains(str.toLowerCase())) {
                    found = true;
                    allFoundEvents.add(existingEvent);
                }
            }
        }

        if (!found) {
            System.out.println("\nNo events found.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }

    }

    public void searchByDateRange() {
        // Retrieve the start and end dates from the user
        LocalDate startDate = eventUI.getSearchStartDate();
        LocalDate endDate = eventUI.getSearchEndDate(startDate);

        // Ensure the start date is before the end date
        if (startDate.isAfter(endDate)) {
            System.out.println("Start date cannot be after end date.");
            return;
        }

        // Flag to check if any event was found
        boolean found = false;
        ListInterface<Event> events = eventsMap.values();

        // Create a SortedListInterface to store events that match the search criteria
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        // Iterate through all Event objects in the eventsMap
        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            Event event = events.getEntry(i + 1); // Retrieve the event

            if (!event.getIsDeleted()) {
                // Get the event's start and end dates
                LocalDate eventStartDate = event.getStartDate();
                LocalDate eventEndDate = event.getEndDate();

                // Check if the event's date range overlaps with the search range
                if ((eventStartDate.isBefore(endDate) || eventStartDate.isEqual(endDate))
                        && (eventEndDate.isAfter(startDate) || eventEndDate.isEqual(startDate))) {
                    // If overlapping, add the event to the found events list
                    allFoundEvents.add(event);
                    found = true;
                }
            }
        }

        // Inform the user if no events were found
        if (!found) {
            System.out.println("\nNo events found within the specified date range.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }
    }

    public void searchByLocation() {
        String str = eventUI.getSearchString();
        boolean found = false;
        ListInterface<Event> events = eventsMap.values();
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            if (!events.getEntry(i + 1).getIsDeleted()) {

                Event existingEvent = events.getEntry(i + 1);
                if (existingEvent.getLocation().toLowerCase().contains(str.toLowerCase())) {
                    found = true;
                    allFoundEvents.add(existingEvent);
                }
            }
        }

        if (!found) {
            System.out.println("\nNo events found.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }
    }

    public void searchByVolunteerRange() {
        int[] numVolunteers = eventUI.inputEditNumVolunteer();
        boolean found = false;
        ListInterface<Event> events = (ArrayList<Event>) eventsMap.values();
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            if (!events.getEntry(i + 1).getIsDeleted()) {
                Event existingEvent = events.getEntry(i + 1);

                // Check if the current number of volunteers is within the specified range
                int currentVolunteers = existingEvent.getCurrentVolunteer();
                if (currentVolunteers >= numVolunteers[0] && currentVolunteers <= numVolunteers[1]) {
                    allFoundEvents.add(existingEvent);
                    found = true;
                }
            }

        }

        if (!found) {
            System.out.println("\nNo events found within the volunteer range.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }

    }

    public void searchByAmountRaised() {
        double min = eventUI.inputMinValue();
        double max = eventUI.inputMaxValue(min);

        boolean found = false;
        ListInterface<Event> events = (ArrayList<Event>) eventsMap.values();
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            if (!events.getEntry(i + 1).getIsDeleted()) {
                Event existingEvent = events.getEntry(i + 1);

                //check if current amount raised is within specified range.
                if (existingEvent.getCurrentAmount() >= min && existingEvent.getCurrentAmount() <= max) {
                    allFoundEvents.add(existingEvent);
                    found = true;
                }
            }

        }

        if (!found) {
            System.out.println("\nNo events found within the amount raised range.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }

    }

    public void editEvent() {
        StackInterface<Event> undoStack = new LinkedStack<>();
        StackInterface<Event> redoStack = new LinkedStack<>();

        String id = eventUI.getEventID("edited");
        Event event = search(id);
        if (event != null) {
            eventUI.printEditHeader(id);
            eventUI.printEventDetails(event);
            boolean pastEvent = event.getEndDate().isBefore(LocalDate.now());
            boolean continueEditing = true;

            while (continueEditing) {
                int choice = eventUI.getEditChoice(!undoStack.isEmpty(), !redoStack.isEmpty(), pastEvent);
                Event originalEvent = new Event(event.getId(), event.getName(), event.getType(), event.getStartDate(), event.getEndDate(), event.getLocation(), event.getMinVolunteer(), event.getMaxVolunteer(), event.getCurrentVolunteer(), event.getCurrentAmount(), event.getGoalAmount(), event.getIsDeleted());

                switch (choice) {
                    case 1:
                        event.setName(eventUI.inputEditEventName());
                        break;
                    case 2:
                        event.setType(eventUI.getNewEventType());
                        break;
                    case 3:
                        event.setStartDate(eventUI.getNewStartDate(event.getEndDate()));
                        break;

                    case 4:
                        event.setEndDate(eventUI.inputEditEndDate(event.getStartDate()));
                        break;
                    case 5:
                        event.setLocation(eventUI.inputEditLocation());
                        break;
                    case 6:
                        int[] volunteers = eventUI.inputEditNumVolunteer();
                        event.setMinVolunteer(volunteers[0]);
                        event.setMaxVolunteer(volunteers[1]);
                        break;
                    case 7:
                        event.setGoalAmount(eventUI.inputEditGoalAmount());
                        break;
                    case 8:
                        if (!undoStack.isEmpty()) {

                            redoStack.push(event);

                            Event undoEvent = undoStack.pop();

                            // Replace the current event in the eventsMap
                            eventsMap.put(id, undoEvent);

                            // Update the current event reference
                            event = undoEvent;
                            System.out.println("Undo successful. Current event details:");

                        } else {
                            System.out.println("No actions to undo.");
                        }

                        break;
                    case 9:
                        if (!redoStack.isEmpty()) {
                            // Save current state to undo stack
                            undoStack.push(event);

                            // Pop the previous state from the redo stack
                            Event redoEvent = redoStack.pop();

                            // Replace the current event in the eventsMap
                            eventsMap.put(id, redoEvent);

                            // Update the current event reference
                            event = redoEvent;
                            System.out.println("Redo successful. Current event details:");
                        } else {
                            System.out.println("No actions to redo.");
                        }

                        break;

                    default:
                        undoStack.clear();
                        redoStack.clear();
                        continueEditing = false;
                        break;

                }

                if (choice >= 1 && choice <= 7) {
                    undoStack.push(originalEvent);

                    redoStack.clear();
                    eventsMap.put(id, event);
                    System.out.println("Event updated. Updated event details:");
                }

                eventUI.printEventDetails(event);
                eventDAO.saveToFile(eventsMap, "event.txt");
                clearGraphUndoAndRedoStacks();

            }

        } else {
            MessageUI.displaySearchNotFoundMessage("Event " + id);
        }

    }

    public void EventVolunteerListMenu() {
        int choice = 0;
        do {
            choice = eventUI.getVolunteerListingChoice();

            switch (choice) {
                case 1:
                    listAllEventsForVolunteer();
                    break;
                case 2:
                    listAllEventsForMultipleVolunteer();
                    break;
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0);
    }

    private SortedListInterface<Event> getEventsForVolunteer(String volunteerID) {
        Volunteer volunteer = volunteersMap.get(volunteerID);
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        if (volunteer == null) {
            MessageUI.displaySearchNotFoundMessage("Volunteer with the ID, " + volunteerID);
            return allFoundEvents;  // Return empty list if volunteer not found
        }

        ListInterface<String> eventIDsForVolunteer = eventGraph.getNeighbours(volunteerID);

        if (eventIDsForVolunteer != null) {
            for (int i = 0; i < eventIDsForVolunteer.getNumberOfEntries(); i++) {
                String eventID = eventIDsForVolunteer.getEntry(i + 1);
                if (eventsMap.containsKey(eventID)) {
                    if (!allFoundEvents.add(eventsMap.get(eventID))) {
                        System.out.println("Unable to add event " + eventID + " to the sorted list.");
                    }
                } else {
                    MessageUI.displaySearchNotFoundMessage("Event with the ID, " + eventID);
                }
            }
        }

        return allFoundEvents;
    }

    public void removeVolunteerFromEvent() {
        String id = eventUI.getVolunteerID("");
        Volunteer volunteer = volunteersMap.get(id);
        if (volunteer == null) {
            MessageUI.displaySearchNotFoundMessage("Volunteer with the ID, " + id);
            return;
        }

        SortedListInterface<Event> allFoundEvents = getEventsForVolunteer(id);

        if (!allFoundEvents.isEmpty()) {
            SortedListInterface<Event> upcomingEvents = filterEventsAfterToday(allFoundEvents);

            if (!upcomingEvents.isEmpty()) {
                eventUI.printEventHeader(upcomingEvents.getNumberOfEntries() + " upcoming event(s) found: ");
                eventUI.printAllEvents(getAllEvents(upcomingEvents));
                // remove volunteer here...
                SetInterface<String> desiredEventIDList = obtainDesiredEventIDList(upcomingEvents);

                if (desiredEventIDList.isEmpty()) {
                    System.out.println("No events chosen. Deletion cancelled.");
                    return;
                }

                String combinedString = "";
                char confirm = eventUI.getDeleteConfirmation();
                if (confirm == 'Y') {
                    graphUndoStack.push(deepCopyGraph(eventGraph));
                    Iterator<String> eventIterator = desiredEventIDList.iterator();
                    while (eventIterator.hasNext()) {
                        String eventID = eventIterator.next();
                        if (!eventGraph.removeUndirectedEdge(eventID, (String) id)) {
                            System.out.println("Volunteer was not successfully deleted from Event " + eventID + ".");
                        } else {
                            //removed successfully, so need to reduce curr volunteer of event by 1
                            Event event = eventsMap.get(eventID);
                            event.setCurrentVolunteer(event.getCurrentVolunteer() - 1);
                            combinedString += eventID;
                            if (eventIterator.hasNext()) {
                                combinedString += ", ";
                            }
                        }
                    }

                    //save back to text file...
                    eventDAO.saveGraphToFile(eventGraph, FILENAME2);
                    System.out.println("Volunteer " + id + " was successfully deleted from Event(s) " + combinedString);

                } else {
                    System.out.println("Deletion Cancelled.");
                }

            } else {
                System.out.println("No upcoming events for the volunteer.");
            }
        } else {
            System.out.println("The volunteer has not been assigned to any event.");
        }
    }

    public SortedListInterface<Event> filterEventsAfterToday(SortedListInterface<Event> eventsList) {
        SortedListInterface<Event> filteredEvents = new SortedLinkedList<>();
        LocalDate today = LocalDate.now(); // e.g., 31/8
        LocalDate tomorrow = today.plusDays(1); // e.g., 1/9

        for (int i = 1; i <= eventsList.getNumberOfEntries(); i++) {
            Event event = eventsList.getEntry(i);
            LocalDate eventDate = event.getStartDate();
            if (!eventDate.isBefore(tomorrow)) { // Check if eventDate is today or later
                filteredEvents.add(event);
            }
        }

        return filteredEvents;
    }

    public SetInterface<String> obtainDesiredEventIDList(SortedListInterface<Event> upcomingEvents) {
        SetInterface<String> desiredEvents = new ArraySet<>();
        StackInterface<String> undoStack = new LinkedStack<>();
        StackInterface<String> redoStack = new LinkedStack<>();
        String userInput = "";
        int count = 0;
        MapInterface<String, Event> selectedEventsMap = new HashMap<>();

        // Populate the selectedEventsMap with upcoming events
        for (int i = 0; i < upcomingEvents.getNumberOfEntries(); i++) {
            selectedEventsMap.put(upcomingEvents.getEntry(i + 1).getId(), upcomingEvents.getEntry(i + 1));
        }

        do {
            // Dynamically generate the prompt message
            String promptMessage = (count + 1) + " (Type 'done' to stop";

            if (!desiredEvents.isEmpty()) {
                promptMessage += ", 'remove' to remove last added event";
            }
            if (!undoStack.isEmpty()) {
                promptMessage += ", 'undo' to undo last action";
            }
            if (!redoStack.isEmpty()) {
                promptMessage += ", 'redo' to redo last action";
            }

            promptMessage += ")";

            userInput = eventUI.getEventIDForVolunteer(promptMessage);

            if (userInput.equalsIgnoreCase("done")) {
                System.out.println();
                break;
            }

            if (userInput.equalsIgnoreCase("remove")) {
                if (!desiredEvents.isEmpty()) {
                    Iterator<String> iterator = desiredEvents.iterator();
                    String lastAddedEvent = "";
                    while (iterator.hasNext()) {
                        lastAddedEvent = iterator.next(); // Get the last added event
                    }

                    if (desiredEvents.remove(lastAddedEvent)) {
                        undoStack.push(lastAddedEvent); // Add to undo stack
                        redoStack.clear(); // Clear redo stack as new action invalidates redo history
                        System.out.println("Removed event with ID " + lastAddedEvent);
                        count--;
                    }

                } else {
                    System.out.println("No events to remove.");
                }
            } else if (userInput.equalsIgnoreCase("undo")) {
                if (!undoStack.isEmpty()) {
                    String undoneEvent = undoStack.pop();
                    if (desiredEvents.add(undoneEvent)) {
                        redoStack.push(undoneEvent); // Add to redo stack
                        count++;
                        System.out.println("Action Undone. The event " + undoneEvent + " has been readded");
                    } else {
                        System.out.println("Event with ID " + undoneEvent + " is already in the list.");
                    }
                } else {
                    System.out.println("No actions to undo.");
                }
            } else if (userInput.equalsIgnoreCase("redo")) {
                if (!redoStack.isEmpty()) {
                    String redoneEvent = redoStack.pop();
                    if (desiredEvents.remove(redoneEvent)) {
                        undoStack.push(redoneEvent); // Add back to undo stack
                        count++;
                        System.out.println("Action Redone. The event " + redoneEvent + " has been removed");
                    }

                } else {
                    System.out.println("No actions to redo.");
                }
            } else if (selectedEventsMap.containsKey(userInput)) {
                if (desiredEvents.add(userInput)) {
                    undoStack.clear(); // Clear the stack when a new event is added
                    redoStack.clear(); // Clear redo stack as new action invalidates redo history
                    count++;
                    System.out.println("Added event with ID " + userInput);
                } else {
                    System.out.println("Event with ID " + userInput + " is already in the list.");
                }
            } else {
                System.out.println("Event with ID " + userInput + " does not exist or is a past event.");
            }

            // Print the current state of chosen events
            System.out.println("Current number of chosen events: " + desiredEvents.size());
            if (!desiredEvents.isEmpty()) {
                System.out.print("Current Chosen Events: ");
                Iterator<String> itr = desiredEvents.iterator();
                while (itr.hasNext()) {
                    System.out.print(itr.next());
                    if (itr.hasNext()) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println("\n");
        } while (!userInput.equalsIgnoreCase("done"));

        return desiredEvents;
    }

    public void listAllEventsForVolunteer() {
        String id = eventUI.getVolunteerID("");
        SortedListInterface<Event> allFoundEvents = getEventsForVolunteer(id);

        if (!allFoundEvents.isEmpty()) {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }
    }

    //allowing flexible search up to any number of volunteers, then show the common events
    public void listAllEventsForMultipleVolunteer() {
        SetInterface<String> desiredVolunteerIDs = obtainDesiredVolunteerIDList();
        ListInterface<SetInterface<String>> volunteerEventIDSets = getVolunteerEventIDSets(desiredVolunteerIDs);

        if (volunteerEventIDSets.isEmpty()) {   //means the volunter id list is empty, so end operation
            System.out.println("No volunteers listed. Exiting to menu.");
        } else {
            SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();
            //SortedListInterface<Event> commonEvents = new SortedLinkedList<>();

            SetInterface<String> CommonEventSet = volunteerEventIDSets.getEntry(1);
            for (int i = 1; i <= volunteerEventIDSets.getNumberOfEntries(); i++) {
                SetInterface<String> currentEventSet = volunteerEventIDSets.getEntry(i);

                // For common events, perform intersection
                CommonEventSet = CommonEventSet.intersection(currentEventSet);

            }

            Iterator<String> myItr = CommonEventSet.iterator();
            while (myItr.hasNext()) {
                String eventID = myItr.next();
                if (eventsMap.containsKey(eventID)) {
                    if (!allFoundEvents.add(eventsMap.get(eventID))) {
                        System.out.println("Unable to add event " + eventID + " to the sorted list.");
                    }
                } else {
                    MessageUI.displaySearchNotFoundMessage("Event with the ID, " + eventID);
                }
            }

            String combinedStringOfVolunteerIDs = "";
            Iterator<String> myItr2 = desiredVolunteerIDs.iterator();
            while (myItr2.hasNext()) {
                combinedStringOfVolunteerIDs += myItr2.next();
                if (myItr2.hasNext()) {
                    combinedStringOfVolunteerIDs += ", ";
                }
            }

            if (allFoundEvents.isEmpty()) {
                System.out.println("No common events found for the selected volunteers: " + combinedStringOfVolunteerIDs);
            } else {

                eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " common event(s) found for " + combinedStringOfVolunteerIDs + ": ");
                eventUI.printAllEvents(getAllEvents(allFoundEvents));
            }

        }

    }

    public ListInterface<SetInterface<String>> getVolunteerEventIDSets(SetInterface<String> desiredVolunteerIDs) {

        ListInterface<SetInterface<String>> volunteerEventIDSets = new ArrayList<>();

        if (!desiredVolunteerIDs.isEmpty()) {

            Iterator itr = desiredVolunteerIDs.iterator();
            while (itr.hasNext()) {
                SetInterface<String> eventIDsForVolunteer = new ArraySet<>();
                ListInterface<String> eventIDs = eventGraph.getNeighbours((String) itr.next());
                if (eventIDs != null) {
                    for (int j = 0; j < eventIDs.getNumberOfEntries(); j++) {
                        eventIDsForVolunteer.add(eventIDs.getEntry(j + 1));
                    }
                }
                volunteerEventIDSets.add(eventIDsForVolunteer);
            }

        }

        return volunteerEventIDSets;
    }

    public SetInterface<String> obtainDesiredVolunteerIDList() {
        SetInterface<String> desiredVolunteers = new ArraySet<>();
        StackInterface<String> undoStack = new LinkedStack<>();
        StackInterface<String> redoStack = new LinkedStack<>();
        String userInput = "";
        int count = 0;

        do {
            // Dynamically generate the prompt message
            String promptMessage = (count + 1) + " (Type 'done' to stop";

            if (!desiredVolunteers.isEmpty()) {
                promptMessage += ", 'remove' to remove last added volunteer";
            }
            if (!undoStack.isEmpty()) {
                promptMessage += ", 'undo' to undo last action";
            }
            if (!redoStack.isEmpty()) {
                promptMessage += ", 'redo' to redo last action";
            }

            promptMessage += ")";

            userInput = eventUI.getVolunteerID(promptMessage);

            if (userInput.equalsIgnoreCase("done")) {
                System.out.println();
                break;
            }

            if (userInput.equalsIgnoreCase("remove")) {
                if (!desiredVolunteers.isEmpty()) {
                    Iterator<String> iterator = desiredVolunteers.iterator();
                    String lastAddedVolunteer = "";
                    while (iterator.hasNext()) {
                        lastAddedVolunteer = iterator.next(); // Get the last added volunteer
                    }

                    if (desiredVolunteers.remove(lastAddedVolunteer)) {
                        undoStack.push(lastAddedVolunteer); // Add to undo stack
                        redoStack.clear(); // Clear redo stack as new action invalidates redo history
                        count--;
                        System.out.println("Removed volunteer with ID " + lastAddedVolunteer);
                    }
                } else {
                    System.out.println("No volunteers to remove.");
                }
            } else if (userInput.equalsIgnoreCase("undo")) {
                if (!undoStack.isEmpty()) {
                    String undoneVolunteer = undoStack.pop();
                    if (desiredVolunteers.add(undoneVolunteer)) {
                        redoStack.push(undoneVolunteer); // Add to redo stack
                        count++;
                        System.out.println("Action Undone. The volunteer " + undoneVolunteer + " has been re-added.");
                    } else {
                        System.out.println("Volunteer with ID " + undoneVolunteer + " is already in the list.");
                    }
                } else {
                    System.out.println("No actions to undo.");
                }
            } else if (userInput.equalsIgnoreCase("redo")) {
                if (!redoStack.isEmpty()) {
                    String redoneVolunteer = redoStack.pop();
                    if (desiredVolunteers.remove(redoneVolunteer)) {
                        undoStack.push(redoneVolunteer); // Add back to undo stack
                        count++;
                        System.out.println("Action Redone. The volunteer " + redoneVolunteer + " has been removed.");
                    }
                } else {
                    System.out.println("No actions to redo.");
                }
            } else if (volunteersMap.containsKey(userInput)) {
                if (desiredVolunteers.add(userInput)) {
                    undoStack.clear(); // Clear the stack when a new volunteer is added
                    redoStack.clear(); // Clear redo stack as new action invalidates redo history
                    count++;
                    System.out.println("Added volunteer with ID " + userInput);
                } else {
                    System.out.println("Volunteer with ID " + userInput + " is already in the list.");
                }
            } else {
                System.out.println("Volunteer with ID " + userInput + " does not exist.");
            }

            // Print the current state of chosen volunteers
            System.out.println("Current number of chosen volunteers: " + desiredVolunteers.size());
            if (!desiredVolunteers.isEmpty()) {
                System.out.print("Current Chosen Volunteers: ");
                Iterator<String> itr = desiredVolunteers.iterator();
                while (itr.hasNext()) {
                    System.out.print(itr.next());
                    if (itr.hasNext()) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println("\n");
        } while (!userInput.equalsIgnoreCase("done"));

        return desiredVolunteers;
    }

    //calling display all event function
    public void displayAllEvents() {
        eventUI.printEventHeader("Event List (Earliest to Latest):");
        eventUI.printAllEvents(getAllEvents(createSortedEventList()));
    }

    public void displayDeletedEvents() {
        eventUI.printEventHeader("Deleted Events");
        eventUI.printAllEvents(getAllDeletedEvents());

    }

    public String getAllEvents(SortedListInterface<Event> sortedEvents) {
        String str = "";

        for (int i = 0; i < sortedEvents.getNumberOfEntries(); i++) {
            if (!sortedEvents.getEntry(i + 1).getIsDeleted()) {
                str += sortedEvents.getEntry(i + 1) + "\n";
            }
        }
        return str;
    }

    public String getAllDeletedEvents() {
        String str = "";
        SortedListInterface<Event> sortedEvents = createSortedEventList();
        for (int i = 0; i < sortedEvents.getNumberOfEntries(); i++) {
            if (sortedEvents.getEntry(i + 1).getIsDeleted()) {
                str += sortedEvents.getEntry(i + 1) + "\n";
            }
        }
        return str;
    }

    private String generateEventID() {
        return String.format("E%05d", eventsMap.size() + 1);
    }

    public SortedListInterface<Event> createSortedEventList() {
        SortedListInterface<Event> sortedEvents = new SortedLinkedList<>();
        ListInterface<Event> events = eventsMap.values();
        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            sortedEvents.add(events.getEntry(i + 1));
        }

        return sortedEvents;
    }

    public MapInterface<String, Event> getEventMap() {
        return eventsMap;
    }

    public static void main(String[] args) {
        EventMaintenance em = new EventMaintenance();
        em.runEventMaintenance();
    }

    private class DonorReportData {

        private String donorID;
        private double totalContribution;
        private int numberOfEventsSupported;
        private double averageContributionPerEvent;

        DonorReportData(String donorID) {
            this.donorID = donorID;
            this.totalContribution = 0;
            this.numberOfEventsSupported = 0;
            this.averageContributionPerEvent = 0;
        }
    }
}
