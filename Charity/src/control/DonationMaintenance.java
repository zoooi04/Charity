/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import adt.ArrayStack;
import adt.GraphInterface;
import dao.DonationInitializer;
import entity.Donation;
import java.util.Scanner;
import boundary.DonationMaintenanceUI;
import utility.MessageUI;
import adt.MapInterface;
import adt.HashMap;
import adt.LinkedList;
import adt.ListInterface;
import adt.SortedLinkedList;
import adt.SortedListInterface;
import adt.WeightedGraph;
import dao.DAO;
import dao.EventDAO;
import entity.Donation.DonationType;
import entity.Donor;
import entity.Event;
import entity.Operation;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

/**
 *
 * @author huaiern
 */
public class DonationMaintenance {

    private final DonationMaintenanceUI donationUI = new DonationMaintenanceUI();
    private MapInterface<String, Donation> donationMap;
    private final DAO<MapInterface<String, Donation>> dao = new DAO<>();
    private static final String FILENAME = "donationHashMap.dat";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DonorMaintenance donorM = new DonorMaintenance();
    private final EventDAO eventDao = new EventDAO();
    private MapInterface<String, Event> eventMap = new HashMap<>();

    //array stack for faster access to undo redo
    private ArrayStack<Operation<Donation>> undo = new ArrayStack<>();
    private ArrayStack<Operation<Donation>> redo = new ArrayStack<>();

    //private static final String ID_COUNT_FILE = "donationIdCount.txt";
    public DonationMaintenance() {
        donationMap = (HashMap<String, Donation>) dao.retrieveFromFile(FILENAME);
        if (donationMap == null) {
            donationMap = DonationInitializer.initializeDonation();
        }
        eventMap = eventDao.retrieveFromFile("event.txt");
    }

    // <editor-fold defaultstate="collapsed" desc="Driver">
    public void donationMaintenanceDriver() {
        int choice = 0;
        do {
            choice = donationUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    //List all
                    donationUI.displayAll();
                    break;
                case 2:
                    //Search
                    System.out.println();
                    Donation searchResult = searchById();
                    if (searchResult != null) {
                        System.out.println("\n" + searchResult.getDetails() + "\nPress to continue..");
                        donationUI.bufferLine();
                    } else {
                        System.out.println("\nNothing found..");
                    }
                    break;
                case 3:
                    //create
                    System.out.println();
                    if (create()) {
                        //too much record to displaay
                        donationUI.displayAll();
                    }
                    break;
                case 4:
                    //delete
                    System.out.println();
                    if (remove()) {
                        //too much to display
                        donationUI.displayAll();
                    }
                    break;
                case 5:
                    //update
                    System.out.println();
                    update();
                    break;
                case 6:
                    if (!undo()) {
                        System.out.println("No undo available.");
                    }
                    break;
                case 7:
                    if (!redo()) {
                        System.out.println("No redo available");
                    }
                    break;
                case 8:
                    //track donation by categories
                    trackByCategories();
                    break;
                case 9:
                    //List donation by donor
                    listDonationByDonor();
                    break;
                case 10:
                    //filter donation by OTHER criteria
                    filterDonationByCriteria();
                    break;
                case 11:
                    //donation report
                    report();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Main Functions">
    public boolean create() {
        Donation donation = new Donation();

        //get properties
        double quantity = donationUI.getQuantityInput();
        String message = donationUI.getMessageInput();
        Donor donor = donationUI.getDonorIdInput();
        Event event = donationUI.getEventIdInput();
        LocalDate date = donationUI.getDateInput(event, formatter);
        DonationType inputEnum = donationUI.getTypeInput();

        //assign properties
        donation.setId(getNextId());
        donation.setQuantity(quantity);
        donation.setMessage(message);
        donation.setDonor(donor);
        donation.setEvent(event);
        donation.setType(inputEnum);
        donation.setDate(date);
        donation.setIsDeleted(false);

        //add in map and save in file
        if (!donationMap.containsKey(donation.getId())) {
            donationMap.put(donation.getId(), donation);
            saveDonationList();
            if (donation.getType() == Donation.DonationType.CASH) {
                //if cash then update in event txt
                ListInterface<Event> eventList = eventMap.values();
                Event updatedEvent = null;
                for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
                    Event eventRecord = eventList.getEntry(i + 1);
                    if (eventRecord.getId().equals(event.getId())) {
                        //if found then break out of for loop
                        eventRecord.setCurrentAmount(eventRecord.getCurrentAmount() + quantity);
                        updatedEvent = eventRecord;
                        break;
                    }
                }

                if (updatedEvent != null && eventMap.containsKey(updatedEvent.getId())) {
                    eventMap.put(updatedEvent.getId(), updatedEvent);
                    eventDao.saveToFile(eventMap, "event.txt");
                }
            }

            System.out.println("Successfully added");
            Operation op = new Operation(Operation.Type.CREATE, donation.clone());
            undo.push(op);
            redo.clear();
            return true;
        } else {
            MessageUI.displayUnableCreateObjectMessage();
            System.out.println("This donation existed!");
            return false;
        }
    }

    public Donation searchById() {
        String id = donationUI.getDonationId("to search: ");
        return donationMap.get(id.toUpperCase());
    }

    public boolean remove() {
        String id = donationUI.getDonationId("to be removed");

        //use id to get the donation object
        Donation donation = (donationMap.get(id));
        if (donation != null && !donation.getIsDeleted()) {
            Donation oldDonation = donation.clone();
            donation.setIsDeleted(true);

            if (donation.getType() == Donation.DonationType.CASH) {
                //if cash then update in event txt
                ListInterface<Event> eventList = eventMap.values();
                Event updatedEvent = null;
                for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
                    Event eventRecord = eventList.getEntry(i + 1);
                    if (eventRecord.getId().equals(donation.getEvent().getId())) {
                        //if found then break out of for loop
                        eventRecord.setCurrentAmount(eventRecord.getCurrentAmount() - donation.getQuantity());
                        updatedEvent = eventRecord;
                        break;
                    }
                }

                if (updatedEvent != null && eventMap.containsKey(updatedEvent.getId())) {
                    eventMap.put(updatedEvent.getId(), updatedEvent);
                    eventDao.saveToFile(eventMap, "event.txt");
                }
            }

            Operation op = new Operation(Operation.Type.DELETE, donation.clone(), oldDonation);
            undo.push(op);
            redo.clear();
            dao.saveToFile(donationMap, FILENAME);
            return true;
        } else {
            System.out.println("Donation does not exist");
            return false;
        }

    }

    public boolean update() {
        String id = donationUI.getDonationId("to be updated");
        Donation donation = donationMap.get(id);
        if (donation != null) {
            Donation oldDonation = donation.clone();
            boolean updated = false;
            int choice = 0;
            do {
                if (updated) {
                    //update in map
                    donationMap.put(id, donation);
                    //save to file
                    dao.saveToFile(donationMap, FILENAME);
                    Operation op = new Operation(Operation.Type.UPDATE, donation.clone(), oldDonation);
                    undo.push(op);
                    redo.clear();
                    //display updated list
                    donationUI.printDonationHeader();
                    donationUI.displayAll();
                    System.out.println("\nSuccessfully updated");
                    updated = false;
                }
                choice = donationUI.getUpdateMenuChoice();
                switch (choice) {
                    case 0:
                        return false;
                    case 1:
                        //quantity
                        Double quantity = donationUI.getQuantityInput();

                        if (donation.getType() == Donation.DonationType.CASH) {
                            //if cash then update in event txt
                            ListInterface<Event> eventList = eventMap.values();
                            Event updatedEvent = null;
                            for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
                                Event eventRecord = eventList.getEntry(i + 1);
                                if (eventRecord.getId().equals(donation.getEvent().getId())) {
                                    //if found then break out of for loop
                                    double newAmount = eventRecord.getCurrentAmount() - donation.getQuantity();
                                    eventRecord.setCurrentAmount(newAmount + quantity);
                                    updatedEvent = eventRecord;
                                    break;
                                }
                            }

                            if (updatedEvent != null && eventMap.containsKey(updatedEvent.getId())) {
                                eventMap.put(updatedEvent.getId(), updatedEvent);
                                eventDao.saveToFile(eventMap, "event.txt");
                            }
                        }

                        donation.setQuantity(quantity);

                        updated = true;
                        break;
                    case 2:
                        //message
                        String message = donationUI.getMessageInput();
                        donation.setMessage(message);
                        updated = true;
                        break;
                    case 3:
                        //type
                        DonationType type = donationUI.getTypeInput();

                        if (type == Donation.DonationType.CASH && donation.getType() != Donation.DonationType.CASH) {
                            //if previously not cash want change to cash
                            ListInterface<Event> eventList = eventMap.values();
                            Event updatedEvent = null;
                            for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
                                Event eventRecord = eventList.getEntry(i + 1);
                                if (eventRecord.getId().equals(donation.getEvent().getId())) {
                                    //if found then break out of for loop
                                    eventRecord.setCurrentAmount(eventRecord.getCurrentAmount() + donation.getQuantity());
                                    updatedEvent = eventRecord;
                                    break;
                                }
                            }

                            if (updatedEvent != null && eventMap.containsKey(updatedEvent.getId())) {
                                eventMap.put(updatedEvent.getId(), updatedEvent);
                                eventDao.saveToFile(eventMap, "event.txt");
                            }
                        } else if (type != Donation.DonationType.CASH && donation.getType() == Donation.DonationType.CASH) {
                            //if previously is cash but want change to others
                            ListInterface<Event> eventList = eventMap.values();
                            Event updatedEvent = null;
                            for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
                                Event eventRecord = eventList.getEntry(i + 1);
                                if (eventRecord.getId().equals(donation.getEvent().getId())) {
                                    //if found then break out of for loop
                                    eventRecord.setCurrentAmount(eventRecord.getCurrentAmount() - donation.getQuantity());
                                    updatedEvent = eventRecord;
                                    break;
                                }
                            }

                            if (updatedEvent != null && eventMap.containsKey(updatedEvent.getId())) {
                                eventMap.put(updatedEvent.getId(), updatedEvent);
                                eventDao.saveToFile(eventMap, "event.txt");
                            }
                        }
                        donation.setType(type);
                        updated = true;
                        break;
                    case 4:
                        //date
                        LocalDate date = donationUI.getDateInput(donation.getEvent(), formatter);
                        donation.setDate(date);
                        updated = true;
                        break;
                    default:
                        MessageUI.displayInvalidChoiceMessage();
                        break;
                }
            } while (choice != 0);
        } else {
            MessageUI.displayObjectNotFoundMessage();
            return false;
        }
        return true;
    }

    /**
     * Tracks and lists donations based on their categories, such as Cash, Food,
     * or Item. Prompts the user to select a category and displays all donations
     * within that category.
     */
    public void trackByCategories() {
        SortedListInterface<Donation> donationList = getDonationListSortedById();

        int choice = 0;
        do {
            choice = donationUI.getCategoriesMenuChoice();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    //Cash
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getType().equals(Donation.DonationType.CASH) && !donation.getIsDeleted()) {
                            donationUI.displayDonation(donation, false, false);
                        }
                    }
                    break;
                case 2:
                    //Food
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getType().equals(Donation.DonationType.FOOD) && !donation.getIsDeleted()) {
                            donationUI.displayDonation(donation, false, false);

                        }
                    }
                    break;
                case 3:
                    //Item
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getType().equals(Donation.DonationType.ITEM) && !donation.getIsDeleted()) {
                            donationUI.displayDonation(donation, false, false);

                        }
                    }
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    /**
     * Lists donations made by a specific donor, filtered by donor ID or donor
     * name.
     */
    public void listDonationByDonor() {
        SortedListInterface<Donation> donationList = getDonationListSortedById();

        int choice = 0;
        do {
            choice = donationUI.getDonorInfoMenuChoice();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    //Donor Id
                    String id = donationUI.getDonorInputSimple();

                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getDonor().getId().equals(id) && !donation.getIsDeleted()) {
                            donationUI.displayDonation(donation, false, false);

                        }
                    }
                    break;
                case 2:
                    //Donor Name
                    String name = donationUI.getDonorNameSimple();

                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getDonor().getName().toUpperCase().contains(name.toUpperCase()) && !donation.getIsDeleted()) {
                            donationUI.displayDonation(donation, false, false);

                        }
                    }
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0);
    }

    /**
     * Filters donations based on user-selected criteria, including message
     * availability, event details (ID or name), and date range.
     */
    public void filterDonationByCriteria() {
        SortedListInterface<Donation> donationList = getDonationListSortedById();

        int choice = 0;
        do {
            choice = donationUI.getFilterCriteria();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    //Message Availability
                    String message = donationUI.getMessageFilterOption();

                    donationUI.printDonationHeader();
                    if (message.charAt(0) == 'Y') {
                        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                            Donation donation = donationList.getEntry(i);
                            if (!donation.getMessage().isBlank() && !donation.getIsDeleted()) {
                                donationUI.displayDonation(donation, false, false);

                            }
                        }
                    } else if (message.charAt(0) == 'N') {
                        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                            Donation donation = donationList.getEntry(i);
                            if (donation.getMessage().isBlank() && !donation.getIsDeleted()) {
                                donationUI.displayDonation(donation, false, false);

                            }
                        }
                    }
                    break;
                case 2:
                    //Event
                    int choice2 = 0;
                    do {
                        choice2 = donationUI.getEventInfoMenuChoice();
                        switch (choice2) {
                            case 0:
                                break;
                            case 1:
                                //Donor Id
                                String id = donationUI.getEventIdSimple();

                                donationUI.printDonationHeader();
                                for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                                    Donation donation = donationList.getEntry(i);
                                    if (donation.getEvent().getId().equals(id) && !donation.getIsDeleted()) {
                                        donationUI.displayDonation(donation, false, false);

                                    }
                                }
                                break;
                            case 2:
                                //Donor Name
                                String name = donationUI.getEventNameSimple();

                                donationUI.printDonationHeader();
                                for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                                    Donation donation = donationList.getEntry(i);
                                    if (donation.getEvent().getName().toUpperCase().contains(name.toUpperCase()) && !donation.getIsDeleted()) {
                                        donationUI.displayDonation(donation, false, false);

                                    }
                                }
                                break;
                            default:
                                MessageUI.displayInvalidChoiceMessage();
                                break;
                        }
                    } while (choice2 != 0);
                    break;
                case 3:
                    //Date
                    LocalDate date1 = donationUI.getStartDate();
                    LocalDate date2 = donationUI.getEndDate(date1);

                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if ((donation.getDate().isAfter(date1) || donation.getDate().isEqual(date1))
                                && (donation.getDate().isBefore(date2) || donation.getDate().isEqual(date2))) {

                            if (!donation.getIsDeleted()) {
                                donationUI.displayDonation(donation, false, false);

                            }

                        }
                    }
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0);
    }

    public void report() {
        int choice = 0;
        do {
            choice = donationUI.getReportChoice();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    topChartReport();
                    break;
                case 2:
                    summaryReport();
                    break;
                case 3:
                    monthlyDonationAnalysisReport();
                    break;
                case 4:
                    top5OfEachType();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void topChartReport() {
        GraphInterface<String, Donation> graph = getGraphFromMap();

        //get all donor and event in the graph
        ListInterface<String> idList = graph.getAllVertexObjects();

        /*
        *   Filter out the Donor vertices and get the Event vertices
        *   Bubble sort 3
        the IDs based on their donation received(indeg)
        *   Function: List the top 3 most donation received Event
         */
        ListInterface<String> eventIdList = new LinkedList<>();
        //filter out donors id and get only event ids
        for (int i = 1; i <= idList.getNumberOfEntries(); i++) {
            String id = idList.getEntry(i);
            if (id.charAt(0) == 'E') {
                eventIdList.add(id);
            }
        }

        // Bubble sort event IDs based on their in-degrees in ascending order        
        int n = eventIdList.getNumberOfEntries();
        for (int i = 1; i <= n - 1; i++) {
            for (int j = 1; j <= n - i; j++) {
                // Get the in-degrees of the current and next event IDs
                String firstId = eventIdList.getEntry(j);
                String secondId = eventIdList.getEntry(j + 1);
                int firstDeg = graph.getIndeg(firstId);
                int secondDeg = graph.getIndeg(secondId);

                // Swap if the first degree is greater than the second
                if (firstDeg > secondDeg) {
                    // Replace entries based on position
                    eventIdList.replace(j, secondId);
                    eventIdList.replace(j + 1, firstId);
                }
            }
        }

        /*
        *   Get the highest donation (edge) connected to each event
        *   bubble sort the highest donations of each event and sort them compare highest
        *   Function: List the top 3 with the largest single donation
         */
        //loop through event list
        ListInterface<Donation> highestDonationList = new LinkedList<>();
        for (int i = 1; i <= eventIdList.getNumberOfEntries(); i++) {
            //get all donation of this event into a list
            ListInterface<Donation> eventDonationList = new LinkedList<>();
            String eventId = eventIdList.getEntry(i);
            //get the event donor vertices
            ListInterface<String> neighbourList = graph.getNeighbours(eventId);
            for (int j = 1; j <= neighbourList.getNumberOfEntries(); j++) {
                String donorId = neighbourList.getEntry(j);
                //get the donation (edge's weight) between them
                Donation donation = graph.getEdgeWeight(donorId, eventId);
                if (donation.getType() == Donation.DonationType.CASH) {
                    eventDonationList.add(donation);
                }
            }

            //find the highest donation for this event
            double highest = 0;
            Donation highestDonation = null;

            for (int j = 1; j <= eventDonationList.getNumberOfEntries(); j++) {
                Donation donation = eventDonationList.getEntry(j);
                //filter only the money
                if (donation.getType() == Donation.DonationType.CASH) {
                    //get the highest single donation of this event i
                    if (donation.getQuantity() > highest) {
                        highest = donation.getQuantity();
                        highestDonation = donation;
                    }
                }

            }

            highestDonationList.add(highestDonation);

        }

        // Bubble sort donations based on their highest quantity in ascending order        
        n = highestDonationList.getNumberOfEntries();
        for (int i = 1; i <= n - 1; i++) {
            for (int j = 1; j <= n - i; j++) {
                // Get the donation of the current and next position
                Donation d1 = highestDonationList.getEntry(j);
                Donation d2 = highestDonationList.getEntry(j + 1);

                // Swap if the first donation quantity is greater than the second
                if (d1.getQuantity() > d2.getQuantity()) {
                    // Replace entries based on position
                    highestDonationList.replace(j, d2);
                    highestDonationList.replace(j + 1, d1);
                }
            }

        }

        /*
        *   Store the eventid and their respective total donor into a map interface
        *   Sort it and get the top 3
        *   Function: get the top 3 event with the highest number of donor
         */
        MapInterface<String, Integer> eventDonorNoMap = new HashMap<>();
        for (int i = 1; i <= eventIdList.getNumberOfEntries(); i++) {
            String eventId = eventIdList.getEntry(i);
            //get a list of edges (donation) connected to this event
            ListInterface<String> donors = graph.getNeighbours(eventId);
            ListInterface<String> existedDonorId = new LinkedList<>();
            int totalDonor = 0;
            for (int j = 1; j <= donors.getNumberOfEntries(); j++) {

                String donorId = donors.getEntry(j);
                //filter out the same donor
                if (existedDonorId.isEmpty() || !existedDonorId.contains(donorId)) {
                    totalDonor++;
                    existedDonorId.add(donorId);
                }

            }
            //put event and their total donors in pair
            eventDonorNoMap.put(eventId, totalDonor);
        }

        //get a copy of eventIdList
        ListInterface<String> topDonorEventIdList = ((LinkedList<String>) eventIdList).copyList();
        //previously sorted according to donations, now is donor
        // Bubble sort events id based on their number of donor in descending order
        int n1 = topDonorEventIdList.getNumberOfEntries();
        for (int i = 1; i <= n1 - 1; i++) {
            for (int j = 1; j <= n1 - i; j++) {
                // Get the total donor of the current and next event IDs
                String eventId = topDonorEventIdList.getEntry(j);
                String eventId2 = topDonorEventIdList.getEntry(j + 1);
                int totalDonor1 = eventDonorNoMap.get(eventId);
                int totalDonor2 = eventDonorNoMap.get(eventId2);

                // Swap if the first total donor is less than the second (descending order)
                if (totalDonor1 < totalDonor2) {
                    // Replace entries based on position
                    topDonorEventIdList.replace(j, eventId2);
                    topDonorEventIdList.replace(j + 1, eventId);
                }
            }
        }

        //Display report
        System.out.println();
        System.out.println();
        System.out.println(("=").repeat(60));
        System.out.println("Event Donation Top Chart");

        System.out.println(("-").repeat(60));
        System.out.println("Event with the Highest Number of Donation: ");
        //get top 3 from the list (last 3)
        for (int i = 0; i < 3; i++) {
            String eventId = eventIdList.getEntry(eventIdList.getNumberOfEntries() - i);
            Event event = getEventById(eventId);
            System.out.printf((i + 1) + ". %-36s\t(" + graph.getIndeg(eventId) + " donations)\n", event.getName());

        }

        System.out.println(("-").repeat(60));
        System.out.println("Event with the Highest Number of Donor: ");
        for (int i = 0; i < 3; i++) {
            String eventId = topDonorEventIdList.getEntry(i + 1); // Adjust the index to start from the top of the sorted list
            Event event = getEventById(eventId);
            System.out.printf((i + 1) + ". %-36s\t(%d donors)\n", event.getName(), eventDonorNoMap.get(eventId));
        }

        System.out.println(("-").repeat(60));
        //pop stack to get back the top 3 most highest;
        System.out.println("Event with the Largest Single Donation: ");
        for (int i = 0; i < 3; i++) {
            Donation donation = highestDonationList.getEntry(highestDonationList.getNumberOfEntries() - i);
            System.out.printf((i + 1) + ". %-36s\t(RM %.2f)\n", donation.getEvent().getName(), donation.getQuantity());
        }

        System.out.println(("=").repeat(60));

    }

    public void summaryReport() {
        // Generate a graph representation from the donation map
        GraphInterface<String, Donation> graph = getGraphFromMap();

        // Retrieve all donations from the donation map
        ListInterface<Donation> list = donationMap.values();

        // Initialize variables to calculate total amount, types, and highest donation
        double totalCashAmount = 0.0;
        double totalFoodAmount = 0.0;
        double totalItemAmount = 0.0;
        int cashCount = 0;
        int foodCount = 0;
        int itemCount = 0;

        int type1 = 0, type2 = 0, type3 = 0;
        double highest = 0;
        Donation highestDonation = null;

        // Loop through all donations to calculate totals and counts
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            Donation donation = list.getEntry(i);
            if (donation.getType() == Donation.DonationType.CASH) {
                totalCashAmount += donation.getQuantity();
                cashCount++;
            } else if (donation.getType() == Donation.DonationType.FOOD) {
                totalFoodAmount += donation.getQuantity();
                foodCount++;
            } else {
                totalItemAmount += donation.getQuantity();
                itemCount++;
            }

            // Count the number of each type of donation
            switch (donation.getType()) {
                case CASH:
                    type1++;
                    break;
                case FOOD:
                    type2++;
                    break;
                case ITEM:
                    type3++;
                    break;
            }

            // Check for the highest cash donation
            if (donation.getType() == Donation.DonationType.CASH && donation.getQuantity() > highest) {
                highest = donation.getQuantity();
                highestDonation = donation;
            }
        }

        // Calculate average donation amount
        double averageTotalAmount = totalCashAmount / donationMap.size();

        // Determine the most popular donation type
        String popularType;
        int highestPopularType = 0;
        if (type1 >= type2 && type1 >= type3) {
            popularType = "Cash";
            highestPopularType = type1;
        } else if (type2 >= type1 && type2 >= type3) {
            popularType = "Food";
            highestPopularType = type2;
        } else {
            popularType = "Item";
            highestPopularType = type3;
        }

        // Get all event IDs from the graph
        ListInterface<String> idList = graph.getAllVertexObjects();
        ListInterface<String> eventIdList = new ArrayList<>();
        for (int i = 1; i <= idList.getNumberOfEntries(); i++) {
            String id = idList.getEntry(i);
            if (id.charAt(0) == 'E') {  // Assuming event IDs start with 'E'
                eventIdList.add(id);
            }
        }

        // Calculate the total in-degrees (number of donations per event)
        int totalIndeg = 0;
        for (int i = 1; i <= eventIdList.getNumberOfEntries(); i++) {
            String id = eventIdList.getEntry(i);
            totalIndeg += graph.getIndeg(id);
        }

        // Calculate the average number of donations per event
        int averageNoOfDonation = (int) Math.ceil(totalIndeg /= eventIdList.getNumberOfEntries());

        double totalAmount = 0.0;
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            if (list.getEntry(i).getType() == Donation.DonationType.CASH) {
                totalAmount = totalAmount + list.getEntry(i).getQuantity();
            }
        }
        double averageAmountPerEvent = totalAmount / eventIdList.getNumberOfEntries();

        //Calculate the total number of donor
        ListInterface<String> entitiesId = graph.getAllVertexObjects();
        int totalDonor = 0;
        for (int i = 0; i < entitiesId.getNumberOfEntries(); i++) {
            if (entitiesId.getEntry(i + 1).charAt(0) == 'A') {
                totalDonor++;
            }
        }

        //average no of donations per donor
        int averageNoOfDonationPerDonor = (int) Math.ceil(list.getNumberOfEntries() / totalDonor);
        double averageAmountPerDonor = totalCashAmount / totalDonor;

        MapInterface<String, Double> contriMap = new HashMap<>();
        for (int i = 1; i <= idList.getNumberOfEntries(); i++) {
            String id = idList.getEntry(i);
            if (id.charAt(0) == 'A') {
                for (int j = 1; j <= list.getNumberOfEntries(); j++) {
                    Donation donation = list.getEntry(j);
                    if (donation.getDonor().getId().equals(id)) {
                        if (!contriMap.containsKey(id)) {
                            contriMap.put(id, donation.getQuantity());
                        } else {
                            double oldQty = contriMap.get(id);
                            contriMap.put(id, oldQty + donation.getQuantity());
                        }
                    }
                }
            }
        }

        double highestContribution = 0.0;
        Donor highestContributor = null;
        ListInterface<String> contriList = contriMap.keySet();
        for (int i = 1; i <= contriList.getNumberOfEntries(); i++) {
            String id = contriList.getEntry(i);
            if (contriMap.get(id) > highestContribution) {
                highestContribution = contriMap.get(id);
                highestContributor = getDonorById(id);
            }
        }

        // Print out the summary report
        System.out.println();
        System.out.println();
        System.out.println(("=").repeat(90));
        System.out.println("Summary Report");
        System.out.println(("-").repeat(90));
        System.out.printf("%-50s: " + totalDonor + " donors \n", "Total number of donor ");
        System.out.printf("%-50s: " + donationMap.size() + " donations\n", "Total number of donation ");
        System.out.printf("%-50s: RM %.2f\n", "Total donation amount (Cash)", totalCashAmount);
        System.out.printf("%-50s: %.1f kg\n", "Total donation amount (Food)", totalFoodAmount);
        System.out.printf("%-50s: %.1f kg\n", "Total donation amount (Other Items)", totalItemAmount);
        System.out.println(("-").repeat(90));
        System.out.printf("%-50s: " + averageNoOfDonationPerDonor + " donation \n", "Average number of donation per donor ");
        System.out.printf("%-50s: RM %.2f\n", "Average amount of donation per donor ", averageAmountPerDonor);
        System.out.printf("%-50s: %d donations\n", "Average number of donation per Event", averageNoOfDonation);
        System.out.printf("%-50s: RM %.2f \n", "Average amount of donation per Event", averageAmountPerEvent);
        System.out.printf("%-50s: RM %.2f\n", "Average amount per donation (Cash)", totalCashAmount / cashCount);
        System.out.printf("%-50s: %.1f kg\n", "Average amount per donation (Food)", totalFoodAmount / foodCount);
        System.out.printf("%-50s: %.1f kg\n", "Average amount per donation (Other Items)", totalItemAmount / itemCount);
        System.out.println(("-").repeat(90));
        System.out.printf("%-50s: " + popularType + " (" + highestPopularType + " donations)\n", "Most popular donation type");
        System.out.printf("%-50s: RM %.2f (by %s)\n", "Highest donation amount received: ", highest, highestDonation.getDonor().getName());
        System.out.printf("%-50s: %s (total of RM %.2f)\n", "Top contributor: ", highestContributor.getName(), highestContribution);
        System.out.println(("=").repeat(90));
    }

    public void monthlyDonationAnalysisReport() {
        ListInterface<Donation> donationList = donationMap.values();

        // Use ListInterface<> to store monthly donation counts and amounts
        ListInterface<Integer> donationCounts = new LinkedList<>();
        ListInterface<Double> totalAmounts = new LinkedList<>();
        ListInterface<Double> percentages = new LinkedList<>();

        // Initialize lists for each month
        for (int i = 0; i < 12; i++) {
            donationCounts.add(0);  // Initialize with 0 donations for each month
            totalAmounts.add(0.0);  // Initialize with 0.0 total amount for each month
            percentages.add(0.0);  // Initialize with 0.0 percentage for each month
        }

        // Calculate donation counts and total amounts per month
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            int month = donation.getDate().getMonthValue() - 1;  // Convert to zero-based index for lists

            if (donation.getType() == Donation.DonationType.CASH) {
                // Update the count and total amount for the respective month
                donationCounts.replace(month + 1, donationCounts.getEntry(month + 1) + 1);
                totalAmounts.replace(month + 1, totalAmounts.getEntry(month + 1) + donation.getQuantity());
            }
        }

        // Calculate total donation amount and identify the peak month
        double total = 0;
        double highest = 0.0;
        int bestMonth = 1;

        for (int i = 0; i < totalAmounts.getNumberOfEntries(); i++) {
            total += totalAmounts.getEntry(i + 1);  // Sum up total amounts

            if (totalAmounts.getEntry(i + 1) > highest) {
                highest = totalAmounts.getEntry(i + 1);
                bestMonth = i + 1;  // Store the month with the highest total donation amount
            }
        }

        // Calculate percentage of total donations for each month
        for (int i = 0; i < totalAmounts.getNumberOfEntries(); i++) {
            if (total > 0) {  // Validate 0 divisions
                percentages.replace(i + 1, totalAmounts.getEntry(i + 1) / total * 100);
            } else {
                percentages.replace(i + 1, 0.0);  // Set percentage to 0 if total is 0
            }
        }

        int countHighest = 0;
        int countHighestMonth = 1;
        for (int i = 1; i <= donationCounts.getNumberOfEntries(); i++) {
            if (donationCounts.getEntry(i) > countHighest) {
                countHighest = donationCounts.getEntry(i);
                countHighestMonth = i;
            }
        }

        // Print the report
        System.out.println();
        System.out.println();
        System.out.println(("=").repeat(70));
        System.out.println("Cash Donation Monthly Performance:");
        System.out.println(("-").repeat(70));
        System.out.printf("%-20s%10s%20s%21s\n", "Month", "Donations", "Total Amount(RM)", "Proportion(%)");

        // List of month names for printing purposes
        String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        // Iterate through the months to print each row of the report
        for (int i = 0; i < 12; i++) {
            System.out.printf(
                    "%-20s%10d%20.2f%20.2f\n",
                    monthNames[i],
                    donationCounts.getEntry(i + 1),
                    totalAmounts.getEntry(i + 1),
                    percentages.getEntry(i + 1)
            );
        }

        System.out.println(("-").repeat(70));
        System.out.println("Peak donation month : " + Month.of(bestMonth));
        System.out.println("Most active month   : " + Month.of(countHighestMonth) + " (" + countHighest + ")");
        System.out.println(("=").repeat(70));
    }

    public void top5OfEachType() {
        ListInterface<Donation> donationList = donationMap.values();

        ListInterface<Donation> cashList = new LinkedList<>();
        ListInterface<Donation> foodList = new LinkedList<>();
        ListInterface<Donation> itemList = new LinkedList<>();

        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            Donation donation = donationList.getEntry(i);
            switch (donation.getType()) {
                case CASH:
                    cashList.add(donation);
                    break;
                case FOOD:
                    foodList.add(donation);
                    break;
                case ITEM:
                    itemList.add(donation);
                    break;
            }
        }

        // Bubble sort  in descending order
        for (int i = 1; i <= cashList.getNumberOfEntries() - 1; i++) {
            for (int j = 1; j <= cashList.getNumberOfEntries() - i; j++) {
                // Get the total donor of the current and next event IDs
                Donation d1 = cashList.getEntry(j);
                Donation d2 = cashList.getEntry(j + 1);
                double d1Qty = d1.getQuantity();
                double d2Qty = d2.getQuantity();

                // Swap if the first total donor is less than the second (descending order)
                if (d1Qty < d2Qty) {
                    // Replace entries based on position
                    cashList.replace(j, d2);
                    cashList.replace(j + 1, d1);
                }
            }
        }

        // Bubble sort  in descending order
        for (int i = 1; i <= foodList.getNumberOfEntries() - 1; i++) {
            for (int j = 1; j <= foodList.getNumberOfEntries() - i; j++) {
                // Get the total donor of the current and next event IDs
                Donation d1 = foodList.getEntry(j);
                Donation d2 = foodList.getEntry(j + 1);
                double d1Qty = d1.getQuantity();
                double d2Qty = d2.getQuantity();

                // Swap if the first total donor is less than the second (descending order)
                if (d1Qty < d2Qty) {
                    // Replace entries based on position
                    foodList.replace(j, d2);
                    foodList.replace(j + 1, d1);
                }
            }
        }

        // Bubble sort  in descending order
        for (int i = 1; i <= itemList.getNumberOfEntries() - 1; i++) {
            for (int j = 1; j <= itemList.getNumberOfEntries() - i; j++) {
                // Get the total donor of the current and next event IDs
                Donation d1 = itemList.getEntry(j);
                Donation d2 = itemList.getEntry(j + 1);
                double d1Qty = d1.getQuantity();
                double d2Qty = d2.getQuantity();

                // Swap if the first total donor is less than the second (descending order)
                if (d1Qty < d2Qty) {
                    // Replace entries based on position
                    foodList.replace(j, d2);
                    foodList.replace(j + 1, d1);
                }
            }
        }

        int choice = 0;
        do {
            choice = donationUI.getTopTypeReportChoice();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    donationUI.printTop5Cash(cashList);
                    break;
                case 2:
                    donationUI.printTop5Food(foodList);
                    break;
                case 3:
                    donationUI.printTop5Item(itemList);
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);

    }

    public boolean undo() {
        Operation op = null;
        boolean success = false;
        if (!undo.isEmpty()) {
            op = undo.pop();
            Donation donation = (Donation) op.getData();

            switch (op.getType()) {
                case CREATE:
                    //delete the added item
                    if (donationMap.remove(donation.getId()) != null) {
                        success = true;
                        redo.push(op);
                    }
                    break;
                case UPDATE:
                    //revise the item
                    donationMap.put(donation.getId(), (Donation) op.getPreviousData());
                    success = true;
                    redo.push(op);
                    break;
                case DELETE:
                    //bring back the item
                    donationMap.put(donation.getId(), (Donation) op.getPreviousData());

                    if (donation.getType() == Donation.DonationType.CASH) {
                        //if cash then update in event txt
                        ListInterface<Event> eventList = eventMap.values();
                        Event updatedEvent = null;
                        for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
                            Event eventRecord = eventList.getEntry(i + 1);
                            if (eventRecord.getId().equals(donation.getEvent().getId())) {
                                //if found then break out of for loop
                                eventRecord.setCurrentAmount(eventRecord.getCurrentAmount() + donation.getQuantity());
                                updatedEvent = eventRecord;
                                break;
                            }
                        }

                        if (updatedEvent != null && eventMap.containsKey(updatedEvent.getId())) {
                            eventMap.put(updatedEvent.getId(), updatedEvent);
                            eventDao.saveToFile(eventMap, "event.txt");
                        }
                    }

                    success = true;
                    redo.push(op);
                    break;
                default:
                    System.out.println("Undo failed.. \nUnknown Previous Operation Type Error");
                    break;
            }
            if (success) {
                dao.saveToFile(donationMap, FILENAME);
            }
        }

        return success;
    }

    public boolean redo() {
        Operation op = null;
        boolean success = false;
        if (!redo.isEmpty()) {
            op = redo.pop();
            Donation donation = (Donation) op.getData();
            switch (op.getType()) {
                case CREATE:
                    //bring back the item
                    donationMap.put(donation.getId(), donation);
                    success = true;
                    undo.push(op);
                    break;
                case UPDATE:
                    //revise the item
                    donationMap.put(donation.getId(), (Donation) op.getData());
                    success = true;
                    undo.push(op);
                    break;
                case DELETE:
                    //delete the added item
                    donationMap.put(donation.getId(), (Donation) op.getData());
                    if (donation.getType() == Donation.DonationType.CASH) {
                        //if cash then update in event txt
                        ListInterface<Event> eventList = eventMap.values();
                        Event updatedEvent = null;
                        for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
                            Event eventRecord = eventList.getEntry(i + 1);
                            if (eventRecord.getId().equals(donation.getEvent().getId())) {
                                //if found then break out of for loop
                                eventRecord.setCurrentAmount(eventRecord.getCurrentAmount() - donation.getQuantity());
                                updatedEvent = eventRecord;
                                break;
                            }
                        }

                        if (updatedEvent != null && eventMap.containsKey(updatedEvent.getId())) {
                            eventMap.put(updatedEvent.getId(), updatedEvent);
                            eventDao.saveToFile(eventMap, "event.txt");
                        }
                    }
                    success = true;
                    undo.push(op);
                    break;
                default:
                    System.out.println("Redo failed..\nUnknown Previous Operation Type Error");
                    break;
            }
            if (success) {
                dao.saveToFile(donationMap, FILENAME);
            }

        }
        return success;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other support function">
    //save whole hashmap to file
    public void saveDonationList() {
        dao.saveToFile(donationMap, FILENAME);
    }

    //get donation file name
    public static String getFileName() {
        return FILENAME;
    }

    //get next id by checking the last id in the map
    public String getNextId() {
        SortedListInterface<Donation> donationList = getDonationListSortedById();
        String idCount = donationList.getEntry(1).getId();

        int intCount = Integer.parseInt(idCount.substring(4));
        intCount++;

        int alphaAsci = (int) idCount.charAt(3);
        if (intCount == 0) {
            alphaAsci++;
        }
        char alpha = (char) alphaAsci;
        String strCount = String.format("%04d", intCount);

        String newId = "DNT" + alpha + strCount;

        return newId;
    }

    //get donor object by their id
    public static Donor getDonorById(String id) {
        ListInterface<Donor> list = donorM.getDonorList();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            if (list.getEntry(i).getId().equals(id)) {
                //found donor
                return list.getEntry(i);
            }
        }
        return null;
    }

    //get event object by their id
    public static Event getEventById(String id) {
        EventMaintenance eventM = new EventMaintenance();
        MapInterface<String, Event> eventMap = eventM.getEventMap();
        return eventMap.get(id);

    }

    //convert this donation map into sorted list
    public SortedListInterface<Donation> getDonationListSortedById() {
        SortedListInterface<Donation> sortedDonations = new SortedLinkedList<>();
        ListInterface<Donation> donationList = donationMap.values();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            sortedDonations.add(donationList.getEntry(i));
        }
        return sortedDonations;
    }

    /*
        *   Convert Map values to Graph
        *   Using graph, map out the relationship of Donor and Event
        *   using Donation as weight
     */
    public GraphInterface<String, Donation> getGraphFromMap() {
        GraphInterface<String, Donation> graph = new WeightedGraph<>();
        ListInterface<Donation> list = donationMap.values();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            Donation donation = list.getEntry(i);
            Event event = donation.getEvent();
            Donor donor = donation.getDonor();

            //form relationship between Donor and Event for each donation
            graph.addVertex(event.getId());
            graph.addVertex(donor.getId());
            graph.addUndirectedEdge(donor.getId(), event.getId(), donation);
        }
        return graph;
    }

    public static String truncate(String text, int maxLength) {
        if (text == null) {
            return null;
        }
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "...";
    }

    public MapInterface<String, Donation> getDonationMap() {
        return donationMap;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="main">
    public static void main(String[] args) {
//       try {
        DonationMaintenance donationMaintenance = new DonationMaintenance();
        donationMaintenance.donationMaintenanceDriver();
//        } catch (Exception e) {
//            System.err.println("An unexpected error occurred: " + e.getMessage());
//        }
    }
    // </editor-fold>
}
