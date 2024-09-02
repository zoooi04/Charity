/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.Heap;
import adt.ListHeap;
import adt.HashMap;
import adt.MapInterface;
import adt.ArrayList;
import adt.ListInterface;
import boundary.DoneeMaintenanceUI;
import dao.DAO;
import dao.DoneeInitializer;
import entity.Donee;
import entity.Person;
import entity.Distribution;
import java.time.LocalDateTime;
import utility.MessageUI;
import java.util.Comparator;

/**
 *
 * This class provides operations for maintaining Donee entities, including
 * adding, updating, and removing Donees. It also manages a priority queue of
 * Donees and provides methods for generating reports about distributions.
 *
 * @author BEH JING HEN
 */
public class DoneeMaintenance extends PersonMaintenance<Donee> {

    private final DoneeMaintenanceUI doneeUI = new DoneeMaintenanceUI();

    private ListHeap<Donee> doneeHeap = new Heap<>();
    private ListHeap<Donee> queue = new Heap<>();
    private ListInterface<Distribution> records = new ArrayList<>();
    private MapInterface<Distribution, Double> distributionArrList;

    private final DAO<ListHeap<Donee>> dao = new DAO<>();
    private final DAO<ListInterface<Distribution>> daoRecord = new DAO<>();

    private static final String FILENAME = "donee.dat";
    private static final String QUEUEFILENAME = "doneeQueue.dat";
    private static final String DISTRIBUTION_FILENAME = "distributionRecords.dat";

    public DoneeMaintenance() {
        doneeHeap = (ListHeap<Donee>) dao.retrieveFromFile(FILENAME);
        queue = (ListHeap<Donee>) dao.retrieveFromFile(QUEUEFILENAME);
        records = (ListInterface<Distribution>) daoRecord.retrieveFromFile(DISTRIBUTION_FILENAME);

        if (doneeHeap == null) {
            doneeHeap = DoneeInitializer.initializeDonees(); // Initializes Donees if no saved data is found
            saveDoneeList(); // Save initialized list to file
        }

        if (queue == null) {
            queue = DoneeInitializer.initializeDonees(); // Initializes Queue if no saved data is found
            saveQueueList(); // Save initialized queue to file
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Driver">
    // Main method that drives the Donee Maintenance functionality
    public void doneeMaintenanceDriver() {
        int choice = 0;

        do {
            choice = doneeUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage(); // Exit option
                    break;
                case 1:
                    doneeUI.printDoneeHeader(); // Display all Donees
                    display(doneeHeap);
                    break;
                case 2:
                    Donee donee = new Donee();
                    if (search(doneeHeap, donee)) {  // Search for Donee
                    }
                    break;
                case 3:
                    if (create(doneeHeap)) { // Add a new Done
                        doneeUI.printDoneeHeader();
                        display(doneeHeap);
                    }
                    break;
                case 4:
                    if (remove(doneeHeap)) { // Remove a Donee
                        doneeUI.printDoneeHeader();
                        display(doneeHeap);
                    }
                    break;
                case 5:
                    if (update(doneeHeap)) { // Update a Donee's information
                    }
                    break;
                case 6:
                    summaryReportMain(); // Generate summary report 
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();  // Handle invalid input
            }
        } while (choice != 0); // Loop until user chooses to exit
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CRUD">
    // Create a new Donee and add it to the heap and queue
    public boolean create(ListHeap<Donee> doneeHeap) {
        Donee newDonee = new Donee();
        if (super.create(newDonee)) { // Call superclass method to create the Donee
            doneeUI.inputDoneeDetails(newDonee); //Get input from user()
            doneeHeap.add(newDonee);  // Add the new Donee to the heap
            queue.add(newDonee); // Add Donee to the queue
            saveDoneeList(); // Save changes to file
            saveQueueList(); // Save updated queue to file
            return true; // Creation was successful
        } else {
            MessageUI.displayUnableCreateObjectMessage();  // Failed to create Donee
        }
        return false;
    }

    //Remove a Donee by set the status(isDeleted) to true
    public boolean remove(ListHeap<Donee> doneeHeap) {
        // Prompt the user to input the ID of the Donee to remove
        String doneeId = doneeUI.inputDoneeId();

        // Search for the Donee by ID in the heap
        int position = searchForID(doneeHeap, doneeId);

        if (position >= 0) {
            // Get the Donee at the found position
            Donee doneeToRemove = doneeHeap.getAnyValue(position);

            // Ask the user for confirmation to delete
            boolean confirmDelete = doneeUI.confirmDeletion(doneeToRemove);

            if (confirmDelete) {
                // Mark the Donee as deleted
                doneeToRemove.setIsDeleted(true);
                System.out.println("\nSuccessful delete donee:" + doneeId);
                return true;
            } else {
                MessageUI.displayCancellationMessage(); // Cancel deletion
                return false;
            }
        } else {
            MessageUI.displayNotFoundMessage(doneeId); // Donee not found
            return false;
        }
    }

    // Remove a Donee from the heap when they receive a donation
    public boolean removeQueue(ListHeap<Donee> doneeHeap) {
        if (!doneeHeap.isEmpty()) {
            Donee highestPriorityDonee = doneeHeap.peekMaxValue(); // Get the highest priority Donee

            if (!highestPriorityDonee.isIsActive()) {
                System.out.println("Donee is deactivated and cannot be removed!");
                return false;
            }

            highestPriorityDonee = doneeHeap.remove(); // Removes the root element
            saveQueueList();
            return true;
        }
        System.out.println("Heap is empty, no donee to remove!");
        return false;
    }

    // Requeue a Donee back to the queue based on their ID
    public boolean requeueDonee(ListHeap<Donee> doneeHeap, ListHeap<Donee> queue) {

        String doneeId = doneeUI.inputDoneeId().trim(); // Get Donee ID from user

        // Check if the Donee ID exists in the queue
        int queuePosition = searchForID(queue, doneeId);
        if (queuePosition >= 0) {
            System.out.println("Donee : " + doneeId + " is already in the queue and cannot be requeued!");
            return false;
        }

        // Search for the Donee by ID in the main heap (donee.dat)
        int doneePosition = searchForID(doneeHeap, doneeId);
        if (doneePosition >= 0) {
            Donee doneeToRequeue = doneeHeap.getAnyValue(doneePosition);

            // Check if the Donee is deleted
            if (doneeToRequeue.isIsDeleted()) {
                System.out.println("Donee : " + doneeId + " is deleted and cannot be requeued!");
                return false;
            }
            // Check if the Donee is de-active
            if (!doneeToRequeue.isIsActive()) {
                System.out.println("Donee : " + doneeId + " is deactivated and cannot be requeued!");
                return false;
            }

            // If all checks are passed, requeue the Donee
            queue.add(doneeToRequeue);
            saveQueueList();  // Save the updated queue list to the file
            System.out.println("Donee : " + doneeId + " has been successfully requeued.");
            return true;
        } else {
            MessageUI.displayNotFoundMessage(doneeId); // Donee not found
            return false;
        }
    }

    // Update the information of an existing Donee    
    public boolean update(ListHeap<Donee> donorHeap) {
        int[] position = {-1};

        // Search for the donor in the heap
        if (searchForUpdate(donorHeap, position)) {
            boolean confirm = false;
            do {
                // Get the donor object to update
                Donee updateDonee = donorHeap.getAnyValue(position[0]);

                // Skip null Donee objects
                if (updateDonee == null || updateDonee.isIsDeleted()) {
                    continue;
                }

                if (super.update(updateDonee)) { // Update Donee using superclass method
                    int choice;
                    do {
                        doneeUI.printDoneeHeader();
                        System.out.println(updateDonee.toString()); // Print current Donee details
                        System.out.println();
                        choice = doneeUI.getUpdateMenuChoice(); // Get user's choice for updating   
                        switch (choice) {
                            case 0:
                                MessageUI.displayExitMenuMessage();
                                break;
                            case 1:
                                Donee.Type currentType = updateDonee.getType();
                                Donee.Type newType = doneeUI.inputDoneeType();

                                if (currentType.equals(newType)) {
                                    System.out.println("The new type is the same as the current type.");
                                    MessageUI.displayErrorMessage();
                                } else {
                                    updateDonee.setType(newType);
                                }
                                break;
                            case 99:
                                confirm = true;
                                break;
                            default:
                                MessageUI.displayInvalidChoiceMessage();
                                break;
                        }
                    } while (choice != 0 && choice != 99);
                } else {
                    confirm = true;
                }

                if (checkDoneeIdInQueueFile(updateDonee.getId())) {
                    saveQueueList();
                }
            } while (!confirm);

            saveDoneeList();

            return true;
        } else {
            return false;
        }
    }

    // Search for a Donee in the heap based on user-specified criteria
    public boolean search(ListHeap<Donee> doneeHeap, Object criteria) {
        boolean found = false;

        int searchOption = doneeUI.inputSearchOption();

        // Variables to store user inputs for search
        String inputDoneeId = "";
        String inputDoneeName = "";
        Person.Gender inputDoneeGender = null;
        String inputDoneePhoneNo = "";
        Donee.Type inputDoneeType = null;

        // Determine which attribute to search by
        switch (searchOption) {
            case 1:
                inputDoneeId = doneeUI.inputDoneeId();
                break;
            case 2:
                inputDoneeName = doneeUI.inputDoneeName();
                break;
            case 3:
                inputDoneeGender = doneeUI.inputDoneeGender();
                break;
            case 4:
                inputDoneePhoneNo = doneeUI.inputDoneePhone();
                break;
            case 5:
                inputDoneeType = doneeUI.inputDoneeType();
                break;
            default:
                MessageUI.displayInvalidChoiceMessage();
                return false;
        }

        doneeUI.printDoneeHeader(); // Print header for search results

        // Iterate over Donee heap to find matching Donees
        for (int i = 0; i < doneeHeap.size(); i++) {
            Donee donee = doneeHeap.getAnyValue(i);

            // Skip null Donee objects
            if (donee == null || donee.isIsDeleted()) {
                continue;
            }

            boolean matches = true;

            // Compare based on the selected search option
            switch (searchOption) {
                case 1:
                    if (!inputDoneeId.equals(donee.getId())) {
                        matches = false;
                    }
                    break;
                case 2:
                    if (!inputDoneeName.equalsIgnoreCase(donee.getName())) {
                        matches = false;
                    }
                    break;
                case 3:
                    if (inputDoneeGender == null || !inputDoneeGender.equals(donee.getGender())) {
                        matches = false;
                    }
                    break;
                case 4:
                    if (!inputDoneePhoneNo.equals(donee.getPhoneNo())) {
                        matches = false;
                    }
                    break;
                case 5:
                    if (inputDoneeType == null || !inputDoneeType.equals(donee.getType())) {
                        matches = false;
                    }
                    break;
            }

            if (matches) {
                found = true;
                System.out.println(donee.toString()); // Print each matched Donee
            }
        }

        if (!found) {
            MessageUI.displayDoneeNotFoundMessage();
        }

        return found;
    }

    // Display all donee information
    public void display(ListHeap<Donee> newEntry) {
        doneeUI.listAllDonee(getAllDonee());
    }

    // Generates a summary report of donees added in a specified month and year
    public void summaryReport1() {

        int[] monthAndYear = doneeUI.inputMonthAndYear();
        int month = monthAndYear[0];
        int year = monthAndYear[1];

        // Check if the doneeHeap is null or empty
        if (doneeHeap == null || doneeHeap.isEmpty()) {
            System.out.println("No donees found.");
            return;
        }

        System.out.println("\n\nDonees added in : " + month + "/" + year);

        boolean found = false;
        int count = 0;
        doneeUI.printDoneeHeader();

        // Iterate through the doneeHeap to find donees added in the specified month and year
        for (int i = 0; i < doneeHeap.size(); i++) {
            Donee donee = doneeHeap.getAnyValue(i);
            if (donee != null && donee.getRegisterDate() != null) {
                LocalDateTime registrationDate = donee.getRegisterDate();
                int registrationMonth = registrationDate.getMonthValue();
                int registrationYear = registrationDate.getYear();

                // Check if registration date matches the specified month and year
                if (registrationMonth == month && registrationYear == year) {
                    System.out.println(donee);
                    found = true;
                    count++;
                }
            }
        }
        // Print the count of donees added
        System.out.println(count + "  donee(s) added");

        // If no donees were found, print a message
        if (!found) {
            System.out.println("No donees were added in this month and year.");
        }
    }

    // Generate a summary report for total amounts distributed by Donee type and donation type
    public void summaryReport2() {
        int[] monthAndYear = doneeUI.inputMonthAndYear();
        int month = monthAndYear[0];
        int year = monthAndYear[1];

        // If no records are retrieved, return
        if (records == null || records.isEmpty()) {
            System.out.println("No distribution records found for " + month + "/" + year + ".");
            return;
        }

        // Initialize summary maps for total amounts by Donee type and donation type
        MapInterface<String, MapInterface<String, Double>> totalAmountsByDoneeTypeAndDonation = new HashMap<>();
        MapInterface<String, Integer> distributionCountsByDonee = new HashMap<>();

        // Iterate over records to populate the summary maps
        for (int i = 1; i <= records.getNumberOfEntries(); i++) {
            Distribution record = records.getEntry(i);

            // Extract the month and year from the record's dateTime
            LocalDateTime recordDate = record.getDateTime();
            int recordMonth = recordDate.getMonthValue();
            int recordYear = recordDate.getYear();

            // Filter records by the user-specified month and year
            if (recordMonth != month || recordYear != year) {
                continue;  // Skip records that do not match the month and year
            }

            // Extract donee type, donation type, and amount from the record
            String doneeType = record.getDonee().getType().name();  // getType() returns an enum
            String donationType = record.getDonation().getType().name();  // getType() returns an enum
            double amount = record.getDonation().getQuantity();  // getQuantity() returns the donation amount eith double

            // Update total amounts by donee type and donation type
            MapInterface<String, Double> donationAmounts = totalAmountsByDoneeTypeAndDonation.get(doneeType);
            if (donationAmounts == null) {
                donationAmounts = new HashMap<>();
                totalAmountsByDoneeTypeAndDonation.put(doneeType, donationAmounts);
            }

            Double currentAmount = donationAmounts.get(donationType);
            if (currentAmount == null) {
                currentAmount = 0.0;
            }
            donationAmounts.put(donationType, currentAmount + amount);

            // Update distribution counts by donee
            String doneeId = record.getDonee().getId();
            Integer currentCount = distributionCountsByDonee.get(doneeId);
            if (currentCount == null) {
                currentCount = 0;
            }
            distributionCountsByDonee.put(doneeId, currentCount + 1);
        }

        // Print the summary report
        System.out.println("\n\n==================================================");
        System.out.println("              Distribution Summary Report          ");
        System.out.println("                    " + month + "/" + year + "                    ");
        System.out.println("==================================================");

        System.out.println("\nTotal Amount Distributed by Donee Type:");
        ArrayList<String> doneeTypes = totalAmountsByDoneeTypeAndDonation.keySet();
        for (int i = 1; i <= doneeTypes.getNumberOfEntries(); i++) {
            String doneeType = doneeTypes.getEntry(i);
            MapInterface<String, Double> donationAmounts = totalAmountsByDoneeTypeAndDonation.get(doneeType);

            System.out.println("\n  Donee Type: " + doneeType);
            System.out.println("  ------------------------------------------");
            ArrayList<String> donationTypes = donationAmounts.keySet();
            for (int j = 1; j <= donationTypes.getNumberOfEntries(); j++) {
                String donationType = donationTypes.getEntry(j);
                Double amount = donationAmounts.get(donationType);
                System.out.printf("  | %-20s | Amount: %7.2f |\n", donationType, (amount != null ? amount : 0.0));
            }
            System.out.println("  ------------------------------------------");
        }

        System.out.println("\nDistribution Counts and Amounts by Donee:");
        ArrayList<String> doneeIds = distributionCountsByDonee.keySet();
        for (int i = 1; i <= doneeIds.getNumberOfEntries(); i++) {
            String doneeId = doneeIds.getEntry(i);
            Integer count = distributionCountsByDonee.get(doneeId);

            System.out.println("\nDonee ID: " + doneeId);
            System.out.println("  Number of Donation Recieved: " + (count != null ? count : 0));
            System.out.println("  --------------------------------------------");
            System.out.println("  Donation Details:");

            // Display the donation types and their amounts for each donee within the specified month and year
            for (int j = 1; j <= records.getNumberOfEntries(); j++) {
                Distribution record = records.getEntry(j);
                LocalDateTime recordDate = record.getDateTime();
                if (record.getDonee().getId().equals(doneeId)
                        && recordDate.getMonthValue() == month
                        && recordDate.getYear() == year) {
                    String donationType = record.getDonation().getType().name();
                    double amount = record.getDonation().getQuantity();
                    System.out.printf("    | %-20s | Amount: %7.2f |\n", donationType, amount);
                }
            }
            System.out.println("  --------------------------------------------");
        }
    }

    // Generates a summary report of the top 5 donees by distribution count
    public void summaryReport3() {
        int[] monthAndYear = doneeUI.inputMonthAndYear();
        int month = monthAndYear[0];
        int year = monthAndYear[1];

        // If no records are retrieved, return
        if (records == null || records.isEmpty()) {
            System.out.println("No distribution records found for " + month + "/" + year + ".");
            return;
        }

        // Initialize a map for distribution counts by donee
        MapInterface<String, Integer> distributionCountsByDonee = new HashMap<>();

        for (int i = 1; i <= records.getNumberOfEntries(); i++) {
            Distribution record = records.getEntry(i);

            // Extract the month and year from the record's dateTime
            LocalDateTime recordDate = record.getDateTime();
            int recordMonth = recordDate.getMonthValue();
            int recordYear = recordDate.getYear();

            // Filter records by the user-specified month and year
            if (recordMonth != month || recordYear != year) {
                continue;  // Skip records that do not match the month and year
            }

            // Update distribution counts by donee
            String doneeId = record.getDonee().getId();
            Integer currentCount = distributionCountsByDonee.get(doneeId);
            if (currentCount == null) {
                currentCount = 0;
            }
            distributionCountsByDonee.put(doneeId, currentCount + 1);
        }

        // Convert to a list and sort by distribution count
        // Create a list of donee IDs
        ArrayList<String> doneeIds = distributionCountsByDonee.keySet();

        for (int i = 1; i <= doneeIds.getNumberOfEntries(); i++) {
            for (int j = 1; j <= doneeIds.getNumberOfEntries() - 1; j++) {
                String doneeId1 = doneeIds.getEntry(j);
                String doneeId2 = doneeIds.getEntry(j + 1);
                if (distributionCountsByDonee.get(doneeId1) < distributionCountsByDonee.get(doneeId2)) {
                    // Swap doneeId1 and doneeId2 manually using a temporary list
                    String temp = doneeIds.getEntry(j);
                    doneeIds.remove(j);  // Remove the element at index j
                    doneeIds.add(j, doneeIds.getEntry(j));  // Re-add the element that was at index j+1
                    doneeIds.remove(j + 1);  // Remove the element at index j+1
                    doneeIds.add(j + 1, temp);  // Insert the temp element at index j+1
                }
            }
        }

        // Display the top 5 donees
        System.out.println("\n\n==================================================");
        System.out.println("        Top 5 Donees by Distribution Count");
        System.out.println("                    " + month + "/" + year + "                    ");
        System.out.println("==================================================");

        int topCount = Math.min(5, doneeIds.getNumberOfEntries());
        for (int i = 1; i <= topCount; i++) {
            String doneeId = doneeIds.getEntry(i);
            Integer count = distributionCountsByDonee.get(doneeId);

            System.out.println("\nDonee ID: " + doneeId);
            System.out.println("  Number of Donations Received: " + (count != null ? count : 0));
            System.out.println("  --------------------------------------------");
            System.out.println("  Donation Details:");

            // Display the donation types and their amounts for each donee within the specified month and year
            for (int j = 1; j <= records.getNumberOfEntries(); j++) {
                Distribution record = records.getEntry(j);
                LocalDateTime recordDate = record.getDateTime();
                if (record.getDonee().getId().equals(doneeId)
                        && recordDate.getMonthValue() == month
                        && recordDate.getYear() == year) {
                    String donationType = record.getDonation().getType().name();
                    double amount = record.getDonation().getQuantity();
                    System.out.printf("    | %-20s | Amount: %7.2f |\n", donationType, amount);
                }
            }
            System.out.println("  --------------------------------------------");
        }
    }

    // Main method to handle the summary report menu
    public void summaryReportMain() {
        int summaryChoice;

        // Loop to display the summary menu and handle user choices
        do {
            summaryChoice = doneeUI.showSummaryMenu();
            switch (summaryChoice) {
                case 1:
                    summaryReport1();
                    break;
                case 2:
                    summaryReport2();
                    break;
                case 3:
                    summaryReport3();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (summaryChoice != 0);

    }

    // Searches for a Donee by ID in the doneeHeap and returns the index if found
    private int searchForID(ListHeap<Donee> doneeHeap, String doneeId) {
        for (int i = 0; i <= doneeHeap.size(); i++) {
            Donee currentDonee = doneeHeap.getAnyValue(i);
            if (currentDonee != null && currentDonee.getId().equals(doneeId)) {
                return i; // Return the index if the Donee is found
            }
        }
        return -1; // Return -1 if the Donee is not found
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other support functions">
    // Retrieves a string representation of all donees
    public String getAllDonee() {
        StringBuilder outputStr = new StringBuilder();
        for (int i = 0; i < doneeHeap.size(); i++) {
            Donee donee = doneeHeap.getAnyValue(i);
            if (!donee.isIsDeleted()) {
                outputStr.append(donee).append("\n");
            }
        }
        return outputStr.toString();
    }

    // Searches for a Donee by ID for update purposes and returns true if found
    public boolean searchForUpdate(ListHeap<Donee> doneeHeap, int[] position) {
        doneeUI.printDoneeHeader();
        display(doneeHeap);
        String inputDoneeId = doneeUI.inputDoneeId().trim();

        boolean found = false;

        // Iterate through doneeHeap to find Donee for update
        for (int i = 0; i < doneeHeap.size(); i++) {
            Donee donee = doneeHeap.getAnyValue(i);
            if (donee != null && donee.getId().equals(inputDoneeId)) {
                position[0] = i;
                found = true;
                break;
            }
        }

        if (!found) {
            MessageUI.displayDoneeNotFoundMessage();
        }
        return found;
    }

    // Saves the donee list to a file
    public void saveDoneeList() {
        dao.saveToFile(doneeHeap, FILENAME);
    }

    // Saves the queue list to a file
    public void saveQueueList() {
        dao.saveToFile(queue, QUEUEFILENAME);
    }

    // Checks if a Donee ID exists in the queue file
    private boolean checkDoneeIdInQueueFile(String doneeId) {

        for (int i = 1; i <= queue.size(); i++) {
            Donee donee = queue.getAnyValue(i);

            // Skip null Donee objects
            if (donee == null || donee.isIsDeleted()) {
                continue;
            }

            if (donee.getId().equals(doneeId)) {
                return true;
            }
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="main">
    // Main method for the DoneeMaintenance class\
    public static void main(String[] args) {
        try {
            DoneeMaintenance doneeMaintenance = new DoneeMaintenance();
            doneeMaintenance.doneeMaintenanceDriver();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    // </editor-fold>
}
