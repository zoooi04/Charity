/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

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
import adt.StackInterface;
import adt.WeightedGraph;
import dao.DAO;
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
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DonorMaintenance donorM = new DonorMaintenance();
    //array stack for faster access to undo redo
    private ArrayStack<Operation<Donation>> undo = new ArrayStack<>();
    private ArrayStack<Operation<Donation>> redo = new ArrayStack<>();

    //private static final String ID_COUNT_FILE = "donationIdCount.txt";
    public DonationMaintenance() {
        donationMap = (HashMap<String, Donation>) dao.retrieveFromFile(FILENAME);
        if (donationMap == null) {
            donationMap = DonationInitializer.initializeDonation();
        }
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
                    donationUI.printDonationHeader();
                    displayAll();
                    break;
                case 2:
                    //Search
                    System.out.println();
                    Donation searchResult = searchById();
                    if (searchResult != null) {
                        System.out.println("\n" + searchResult.getDetails() + "\nPress to continue..");
                        scanner.nextLine();
                    } else {
                        System.out.println("\nNothing found..");
                    }
                    break;
                case 3:
                    //create
                    System.out.println();
                    if (create()) {
                        donationUI.printDonationHeader();
                        displayAll();
                    }
                    break;
                case 4:
                    //delete
                    System.out.println();
                    if (remove()) {
                        donationUI.printDonationHeader();
                        displayAll();
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
                    displayAll();
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
                            displayDonation(donation, false, false);
                        }
                    }
                    break;
                case 2:
                    //Food
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getType().equals(Donation.DonationType.FOOD) && !donation.getIsDeleted()) {
                            displayDonation(donation, false, false);
                        }
                    }
                    break;
                case 3:
                    //Item
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getType().equals(Donation.DonationType.ITEM) && !donation.getIsDeleted()) {
                            displayDonation(donation, false, false);
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
                    System.out.print("Enter donor Id: ");
                    String id = scanner.nextLine();

                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getDonor().getId().equals(id) && !donation.getIsDeleted()) {
                            displayDonation(donation, true, false);
                        }
                    }
                    break;
                case 2:
                    //Donor Name
                    System.out.print("Enter donor name: ");
                    String name = scanner.nextLine();

                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getDonor().getName().toUpperCase().equals(name.toUpperCase()) && !donation.getIsDeleted()) {
                            displayDonation(donation, false, false);
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
                    String message;
                    do {
                        System.out.print("Show donation with message? (Y/N): ");
                        message = scanner.nextLine().trim().toUpperCase();
                    } while (!message.equals("Y") && !message.equals("N"));

                    donationUI.printDonationHeader();
                    if (message.charAt(0) == 'Y') {
                        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                            Donation donation = donationList.getEntry(i);
                            if (!donation.getMessage().isBlank() && !donation.getIsDeleted()) {
                                displayDonation(donation, false, false);
                            }
                        }
                    } else if (message.charAt(0) == 'N') {
                        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                            Donation donation = donationList.getEntry(i);
                            if (donation.getMessage().isBlank() && !donation.getIsDeleted()) {
                                displayDonation(donation, false, false);
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
                                System.out.print("Enter event Id: ");
                                String id = scanner.nextLine();

                                donationUI.printDonationHeader();
                                for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                                    Donation donation = donationList.getEntry(i);
                                    if (donation.getEvent().getId().equals(id) && !donation.getIsDeleted()) {
                                        displayDonation(donation, true, true);
                                    }
                                }
                                break;
                            case 2:
                                //Donor Name
                                System.out.print("Enter event name: ");
                                String name = scanner.nextLine();

                                donationUI.printDonationHeader();
                                for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                                    Donation donation = donationList.getEntry(i);
                                    if (donation.getEvent().getName().toUpperCase().equals(name.toUpperCase()) && !donation.getIsDeleted()) {
                                        displayDonation(donation, false, false);
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
                    LocalDate date1 = null;
                    boolean isValid = false;
                    while (!isValid) {
                        System.out.print("Enter start date (dd/MM/yyyy): ");
                        String dateStr1 = scanner.nextLine();

                        try {
                            date1 = LocalDate.parse(dateStr1, formatter); // Try to parse the date
                            isValid = true; // If parsing is successful, set the flag to true
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please enter the date in dd/MM/yyyy format.");
                        }
                    }

                    LocalDate date2 = null;
                    isValid = false;
                    while (!isValid) {
                        System.out.print("Enter end date (dd/MM/yyyy): ");
                        String dateStr2 = scanner.nextLine();

                        try {
                            date2 = LocalDate.parse(dateStr2, formatter); // Try to parse the date
                            isValid = true; // If parsing is successful, set the flag to true
                            if (date2.isBefore(date1)) {
                                System.out.println("End date cannot be before Start date.");
                                isValid = false;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please enter the date in dd/MM/yyyy format.");
                        }
                    }

                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if ((donation.getDate().isAfter(date1) || donation.getDate().isEqual(date1))
                                && (donation.getDate().isBefore(date2) || donation.getDate().isEqual(date2))) {

                            if (!donation.getIsDeleted()) {
                                displayDonation(donation, false, false);
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
        // Bubble sort events id based on their number of donor in ascending order        
        n = topDonorEventIdList.getNumberOfEntries();
        for (int i = 1; i <= n - 1; i++) {
            for (int j = 1; j <= n - i; j++) {
                // Get the totaldonor of the current and next event IDs
                String eventId = topDonorEventIdList.getEntry(j);
                String eventId2 = topDonorEventIdList.getEntry(j + 1);
                int totalDonor1 = eventDonorNoMap.get(eventId);
                int totalDonor2 = eventDonorNoMap.get(eventId2);

                // Swap if the first total donor is greater than the second
                if (totalDonor1 > totalDonor2) {
                    // Replace entries based on position
                    topDonorEventIdList.replace(j, eventId);
                    topDonorEventIdList.replace(j + 1, eventId2);
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
        //pop stack to get back the top 3 most highest;
        System.out.println("Event with the Largest Single Donation: ");

//        for (int i = 0; i < highestDonationList.getNumberOfEntries();i++) {
//           System.out.println(highestDonationList.getEntry(i+1));
//        }
        for (int i = 0; i < 3; i++) {
            Donation donation = highestDonationList.getEntry(highestDonationList.getNumberOfEntries() - i);
            System.out.printf((i + 1) + ". %-36s\t(RM %.2f)\n", donation.getEvent().getName(), donation.getQuantity());
        }

        System.out.println(("-").repeat(60));
        System.out.println("Event with the Highest Number of Donor: ");
        for (int i = 0; i < 3; i++) {
            String eventId = topDonorEventIdList.getEntry(topDonorEventIdList.getNumberOfEntries() - i);
            Event event = getEventById(eventId);
            System.out.printf((i + 1) + ". %-36s\t(%d donors)\n", event.getName(), eventDonorNoMap.get(eventId));
        }
        System.out.println(("=").repeat(60));

    }

    public void summaryReport() {
        // Generate a graph representation from the donation map
        GraphInterface<String, Donation> graph = getGraphFromMap();

        // Retrieve all donations from the donation map
        ListInterface<Donation> list = donationMap.values();

        // Initialize variables to calculate total amount, types, and highest donation
        double totalDonationAmount = 0.0;
        int type1 = 0, type2 = 0, type3 = 0;
        double totalFood = 0, totalItem = 0;
        double highest = 0;
        Donation highestDonation = null;

        // Loop through all donations to calculate totals and counts
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            Donation donation = list.getEntry(i);
            totalDonationAmount += donation.getQuantity();  // Sum total donation amount

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
        double averageTotalAmount = totalDonationAmount / donationMap.size();

        // Determine the most popular donation type
        String popularType;
        if (type1 >= type2 && type1 >= type3) {
            popularType = "Cash";
        } else if (type2 >= type1 && type2 >= type3) {
            popularType = "Food";
        } else {
            popularType = "Item";
        }

        // Get all event IDs from the graph
        ListInterface<String> idList = graph.getAllVertexObjects();
        ListInterface<String> eventIdList = graph.getAllVertexObjects();
        for (int i = 1; i <= idList.getNumberOfEntries(); i++) {
            String id = idList.getEntry(i);
            if (id.charAt(0) == 'A') {  // Assuming event IDs start with 'A'
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

        // Print out the summary report
        System.out.println();
        System.out.println();
        System.out.println(("=").repeat(50));
        System.out.println("Overall Summary Report:");
        System.out.println(("-").repeat(50));
        System.out.printf("%-40s: " + donationMap.size() + " donations\n", "Total number of donation");
        System.out.printf("%-40s: RM %.2f\n", "Total donation amount (Cash)", totalDonationAmount);
        System.out.printf("%-40s: %d donations\n", "Average number of donation ", averageNoOfDonation);
        System.out.printf("%-40s: RM %.2f\n", "Average donation amount (Cash)", averageTotalAmount);
        System.out.printf("%-40s: " + popularType + "\n", "Most popular donation type");
        System.out.printf("%-40s: RM %.2f by %s\n", "Highest donation amount received ", highest, highestDonation.getDonor().getName());
        System.out.println(("=").repeat(50));
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

        // Print the report
        System.out.println();
        System.out.println();
        System.out.println(("=").repeat(80));
        System.out.println("Monthly Donation Performance Analysis:");
        System.out.println(("-").repeat(80));
        System.out.printf("%-20s%10s%20s%21s\n", "Month", "Donations", "Total Amount(RM)", "Proportion");

        // List of month names for printing purposes
        String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        // Iterate through the months to print each row of the report
        for (int i = 0; i < 12; i++) {
            System.out.printf(
                    "%-20s%10d%20.2f%20.2f%%\n",
                    monthNames[i],
                    donationCounts.getEntry(i + 1),
                    totalAmounts.getEntry(i + 1),
                    percentages.getEntry(i + 1)
            );
        }

        System.out.println(("-").repeat(80));
        System.out.println("Peak donation month : " + Month.of(bestMonth));
        System.out.println(("=").repeat(80));
    }

    //list all donations
    public void displayAll() {
        SortedListInterface<Donation> sortedDonations = getDonationListSortedById();
        for (int i = 1; i <= sortedDonations.getNumberOfEntries(); i++) {
            Donation d = sortedDonations.getEntry(i);
            if (!d.getIsDeleted()) {
                displayDonation(d, false, false);
            }
        }
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

    //display donations in lines
    public void displayDonation(Donation d, boolean donorShowId, boolean eventShowId) {
        String donorInfo = d.getDonor().getName();
        if (donorShowId) {
            donorInfo = d.getDonor().getId();
        }

        String eventInfo = d.getEvent().getName();
        if (eventShowId) {
            eventInfo = d.getEvent().getId();
        }

        System.out.printf("%-15s%-20.2f%-50s%-30s%-30s%-15s%-15s\n",
                d.getId(),
                d.getQuantity(),
                truncate(d.getMessage(),35),
                donorInfo,//no way to set donor yet
                eventInfo,
                d.getType(),
                d.getDate().format(formatter));
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
