/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ListInterface;
import adt.SortedListInterface;
import control.DonationMaintenance;
import static control.DonationMaintenance.getDonorById;
import static control.DonationMaintenance.getEventById;
import static control.DonationMaintenance.truncate;
import control.DonorMaintenance;
import control.EventMaintenance;
import entity.Donation;
import entity.Donation.DonationType;
import entity.Donor;
import entity.Event;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author huaiern
 */
public class DonationMaintenanceUI {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Scanner scanner = new Scanner(System.in);

    // <editor-fold defaultstate="collapsed" desc="menu">
    public int getMenuChoice() {
        System.out.println("\nDONATION MANAGEMENT MENU");
        System.out.println("1. List all Donation");
        System.out.println("2. Search donation");
        System.out.println("3. Add Donation");
        System.out.println("4. Remove Donation");
        System.out.println("5. Amend Donation");
        System.out.println("6. Undo Last Operation");
        System.out.println("7. Redo Last Operation");
        System.out.println("8. Filter donation by Type");
        System.out.println("9. Filter donation by Donor");
        System.out.println("10.Filter donation by Other Criteria");
        System.out.println("11.Donation Report");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public int getUpdateMenuChoice() {
        System.out.println("\nUPDATE MENU");
        System.out.println("1. Quantity");
        System.out.println("2. Message");
        System.out.println("3. Donation Type");
        System.out.println("4. Donation Date");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public int getCategoriesMenuChoice() {
        System.out.println("\nSELECT CATEGORIES");
        System.out.println("1. Cash");
        System.out.println("2. Food");
        System.out.println("3. Item");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public int getDonorInfoMenuChoice() {
        System.out.println("\nFILTER BY:");
        System.out.println("1. Donor Id");
        System.out.println("2. Donor Name");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public int getFilterCriteria() {
        System.out.println("\nCHOOSE CRITERIA:");
        System.out.println("1. Message Availability");
        System.out.println("2. Event");
        System.out.println("3. Date Range");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public int getEventInfoMenuChoice() {
        System.out.println("\nFILTER BY:");
        System.out.println("1. Event Id");
        System.out.println("2. Event Name");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public int getReportChoice() {
        System.out.println("\nREPORT:");
        System.out.println("1. Event Performance Top Chart");
        System.out.println("2. Summary Report");
        System.out.println("3. Monthly Donation Performance Analysis");
        System.out.println("4. Top 5 Donations of Each Type");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    
    public int getTopTypeReportChoice(){
        System.out.println("\nCHOOSE A TYPE:");
        System.out.println("1. Cash");
        System.out.println("2. Food");
        System.out.println("3. Item");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="output">
    public double getQuantityInput() {
        double quantity = -1;  // Initialize with an invalid value
        System.out.print("Enter quantity: ");
        // Keep looping until a valid, non-negative integer is provided
        while (!scanner.hasNextDouble() || (quantity = scanner.nextDouble()) < 0) {
            System.out.println("Invalid input. Please enter a valid quantity.");
            scanner.nextLine();  // Consume the invalid input
            System.out.print("Enter quantity: ");
        }
        scanner.nextLine();  // Consume the newline character after the valid integer input
        return quantity;
    }

    public String getMessageInput() {
        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        return message;
    }

    public Donor getDonorIdInput() {
        Donor donor = null;
        while (donor == null) {
            System.out.print("Enter donor ID: ");
            String donorID = scanner.nextLine();
            donor = getDonorById(donorID);
            if (donor == null) {
                System.out.println("Donor does not exist!\nPlease re-enter a valid donor ID.");
            }
        }
        return donor;
    }

    public Event getEventIdInput() {
        EventMaintenance em = new EventMaintenance();
        em.displayAllEvents();
        Event event = null;
        while (event == null) {
            System.out.print("Enter event ID:");
            String eventID = scanner.nextLine();
            event = getEventById(eventID);
            if(event!=null){
                if (event.getStartDate().isAfter(LocalDate.now())) {
                    event = null;
                    System.out.println("Event have not started.\nPlease re-enter a valid event ID.");
                }
            }else{
                System.out.println("Event does not exist. \nPlease re-enter a valid event ID.");

            }
        }
        return event;
    }

    public LocalDate getDateInput(Event event, DateTimeFormatter formatter) {
        LocalDate date = null;
        boolean isValid = false;
        while (!isValid) {
            LocalDate startDate = event.getStartDate();
            LocalDate endDate = event.getEndDate();
            String startDateStr = startDate.format(formatter);
            String endDateStr = endDate.format(formatter);

            System.out.println("\nEvent period: " + startDateStr + " -- " + endDateStr);
            System.out.print("Enter date within event period (dd/MM/yyyy): ");
            String inputDate = scanner.nextLine();
            try {
                date = LocalDate.parse(inputDate, formatter); // Try to parse the date
                isValid = true; // If parsing is successful, set the flag to true
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in dd/MM/yyyy format.");
            }

            if (date != null) {
                if (date.isBefore(startDate) || date.isAfter(endDate)) {
                    isValid = false;
                }
            }
        }
        return date;
    }

    public DonationType getTypeInput() {
        Donation.DonationType inputEnum = null;

        do {
            System.out.print("Enter donation Type(C = Cash/F = Food/I = Item): ");
            String inputValue = scanner.nextLine();
            switch (inputValue) {
                case "c", "C" ->
                    inputEnum = Donation.DonationType.CASH;
                case "f", "F" ->
                    inputEnum = Donation.DonationType.FOOD;
                case "i", "I" ->
                    inputEnum = Donation.DonationType.ITEM;
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (inputEnum == null);
        return inputEnum;
    }

    public String getDonationId(String additional) {
        System.out.print("Enter donation ID (DNTAxxxx)" + additional + ": ");
        String id = scanner.nextLine().toUpperCase().trim();
        return id;
    }

    public boolean getConfirmation(String additional) {
        String message;
        do {
            System.out.print("Are you sure you want to " + additional + "? (Y/N): ");
            message = scanner.nextLine().trim().toUpperCase();
        } while (!message.equals("Y") && !message.equals("N"));

        return message.charAt(0) == 'Y';

    }
    
    public int getMonthInput(){
        int month = 0;
        System.out.print("Enter month: ");
        while (!scanner.hasNextInt() && (month < 1 || month > 12)) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter month: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public String getDonorInputSimple(){
        System.out.print("Enter donor Id: ");
        String id = scanner.nextLine();
        return id;
    }
    
    public String getDonorNameSimple(){
        System.out.print("Enter donor name: ");
        String name = scanner.nextLine();
        return name;
    }
    
    public String getMessageFilterOption(){
        String message;
        do {
            System.out.print("Show donation with message? (Y/N): ");
            message = scanner.nextLine().trim().toUpperCase();
        } while (!message.equals("Y") && !message.equals("N"));

        return message;
    }
    
    public LocalDate getStartDate(){
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
        return date1;
    }
    
    public LocalDate getEndDate(LocalDate date1){
        LocalDate date2 = null;
        boolean isValid = false;
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
        return date2;
    }
    
    public String getEventIdSimple(){
        System.out.print("Enter event Id (Exxxxx): ");
        String id = scanner.nextLine();
        return id;
    }
    
    public String getEventNameSimple(){
        System.out.print("Enter event name: ");
        String name = scanner.nextLine();
        return name;
    }
    
    public void bufferLine(){
        scanner.nextLine();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="output">
    public void listAllDonation(String outputStr) {
        System.out.println(outputStr);
    }

    public void printDonationDetails(Donation donation) {
        System.out.println("Donation Details");
        System.out.println(donation.toString());
    }

    public void printDonationHeader() {
        String outputStr = "";
        outputStr += "\n" + "=".repeat(200) + "\n";
        outputStr += String.format("%-15s%-20s%-50s%-30s%-30s%-15s%-15s",
                "ID",
                "Quantity(RM/KG)",
                "Message",
                "Donor",
                "Event",
                "Type",
                "Date");
        outputStr += "\n" + "=".repeat(200) + "\n";
        System.out.print(outputStr);
    }
    
    public void printTop5Cash(ListInterface<Donation> cashList){
        System.out.println();
        System.out.println();
        System.out.println(("=").repeat(80));
        System.out.println("Top 5 Cash Donation");
        System.out.println(("-").repeat(80));
        System.out.printf("   %-20s%-30s%-30s\n", "Amount(RM)", "Donor", "Event Name");
        System.out.println(("-").repeat(80));

        for (int i = 1; i <= 5; i++) {
            Donation donation = cashList.getEntry(i);
            System.out.printf(i + ". %-20.2f%-30s%-30s\n", donation.getQuantity(), donation.getDonor().getName(), donation.getEvent().getName());
        }
        System.out.println(("=").repeat(80));
    }
    
    public void printTop5Food(ListInterface<Donation> foodList){
        System.out.println(("=").repeat(80));
        System.out.println("Top 5 Food Donation");
        System.out.println(("-").repeat(80));
        System.out.printf("%-20s%-30s%-30s\n", "Quantity(kg)", "Donor", "Event Name");
        System.out.println(("-").repeat(80));

        for (int i = 1; i <= 5; i++) {
            Donation donation = foodList.getEntry(i);
            System.out.printf(i + ". %-20.2f%-30s%-30s\n", donation.getQuantity(), donation.getDonor().getName(), donation.getEvent().getName());
        }
        System.out.println(("=").repeat(80));

    }
    
    public void printTop5Item(ListInterface<Donation> itemList){
        System.out.println(("=").repeat(80));
        System.out.println("Top 5 Item Donation");
        System.out.println(("-").repeat(80));
        System.out.printf("%-20s%-30s%-30s\n", "Quantity(kg)", "Donor", "Event Name");
        System.out.println(("-").repeat(80));

        for (int i = 1; i <= 5; i++) {
            Donation donation = itemList.getEntry(i);
            System.out.printf(i + ". %-20.2f%-30s%-30s\n", donation.getQuantity(), donation.getDonor().getName(), donation.getEvent().getName());
        }
        System.out.println(("=").repeat(80));

    }
    
    
    //list all donations with pagination
    public void displayAll() {
        DonationMaintenance dm = new DonationMaintenance();
        Scanner scanner = new Scanner(System.in);
        SortedListInterface<Donation> sortedDonations = dm.getDonationListSortedById();
        int recordsPerPage = 20;  // Number of records to display per page
        int totalRecords = sortedDonations.getNumberOfEntries();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        int currentPage=1;  // Start on the first page

        while (true) {
            System.out.printf("\n\n");
            // Calculate the start and end indices for the current page
            int startIndex = (currentPage - 1)* recordsPerPage +1;
            int endIndex = Math.min(currentPage*recordsPerPage, totalRecords);


            printDonationHeader();
            for (int i = startIndex; i<=endIndex; i++) {
                Donation d = sortedDonations.getEntry(i);
                if (!d.getIsDeleted()) {
                    displayDonation(d, false, false);
                }
            }
            // Display the current page
            System.out.println("\nPage " + currentPage + " of " + totalPages);

            
            System.out.println("\nEnter page number to view (1 to " + totalPages + "), or '0' to exit:");

            String input = scanner.nextLine();  

            if (input.equals("0")) {
                break;  
            }

            try {
                int pageSelection = Integer.parseInt(input);
                if (pageSelection >= 1 && pageSelection <= totalPages) {
                    currentPage = pageSelection;  // Set the new current page
                } else {
                    System.out.println("Invalid page number. Please enter a number between 1 and " + totalPages + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid page number.");
            }
        }
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
                truncate(d.getMessage(), 35),
                donorInfo,//no way to set donor yet
                eventInfo,
                d.getType(),
                d.getDate().format(formatter));
    }
    // </editor-fold>

}
