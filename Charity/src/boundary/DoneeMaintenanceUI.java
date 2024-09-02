/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package boundary;

import entity.Donee;
import entity.Person;
import java.util.Scanner;
import adt.ListHeap;
import adt.Heap;
import dao.DAO;
import utility.MessageUI;

/**
 *
 * @author BEH JING HEN
 */

/**
 * UI for managing Donee operations such as listing, searching, adding, and updating donees.
 * Provides an interactive menu-driven interface for user interaction.
 */
public class DoneeMaintenanceUI {
    
    private PersonMaintenanceUI personUI = new PersonMaintenanceUI();
    
    private static final String ID_PREFIX = "DN24"; // Prefix for generating Donee IDs
    
    private ListHeap<Donee> doneeHeap = new Heap<>(); // Heap structure for managing Donees
    private final DAO<ListHeap<Donee>> dao = new DAO<>(); // Data Access Object for file operations
    
    private static final String FILENAME = "donee.dat"; // Filename for storing Donee data
    
    private final Scanner scanner = new Scanner(System.in);
    
    // <editor-fold defaultstate="collapsed" desc="menu">
    // Display main menu and get user's choice
    public int getMenuChoice() {
        System.out.println("\nDonee Maintenance Menu:");
        System.out.println("1. List all donees");
        System.out.println("2. Search donee");
        System.out.println("3. Add new donee");
        System.out.println("4. Remove donee");
        System.out.println("5. Update donee");
        System.out.println("6. Summary Report");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }
    
    // Prints the header for displaying Donee information in a formatted manner
    public void printDoneeHeader() {
        String outputStr = "";
        outputStr += "\n" + "=".repeat(150) + "\n";
        outputStr += String.format("%-30s%-5s%-13s%-10s%-15s%-22s%-15s%-12s%-15s",
                "Name",
                "Age",
                "BirthDay",
                "Gender",
                "Phone Number",
                "Register Date",
                "Status",
                "Id",
                "Type");
        outputStr += "\n" + "=".repeat(150) + "\n";
        System.out.print(outputStr);
    }
    
    // Displays the update menu and returns the user's choice
    public int getUpdateMenuChoice()  {
        System.out.println("\nUPDATE MENU");
        System.out.println("1. Update Donee Type");
        System.out.println("99. Confirm to end update");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }
    
    // Prompts the user to select a search option and returns the chosen option
    public int inputSearchOption() {
        System.out.println("\nSelect a search option:");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Gender");
        System.out.println("4. Search by Phone Number");
        System.out.println("5. Search by Type");
        
        int option = 0;
        
        // Input validation to ensure user selects a valid option
        while (option < 1 || option > 5) {
            System.out.print("Enter your choice (1-5): ");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume the leftover newline character here
                if (option < 1 || option > 5) {
                    System.out.println("Invalid choice. Please select a valid option.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.next(); // Clear invalid input
            }
        }

        return option;
    }
    
    // Prompts the user to input a search criterion and returns it as a string
    public String inputSearchCriteria() {
        System.out.println("Please choose a search criterion:");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Gender");
        System.out.println("4. Phone Number");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                return "id";
            case 2:
                return "name";
            case 3:
                return "gender";
            case 4:
                return "phone";
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                return inputSearchCriteria(); // Recursively prompt again for valid input
        }
    }
    
    // Displays the summary report menu and returns the user's choice
    public int showSummaryMenu(){
        System.out.println("\nSummary Report Menu:");
        System.out.println("1. Monthly Donee Registration Report");
        System.out.println("2. Donee Distribution Report");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="input">
    // Input donee details from user
    public void inputDoneeDetails(Donee donee) {
        doneeHeap =  (ListHeap<Donee>) dao.retrieveFromFile(FILENAME);
        
        if (doneeHeap == null) {
            doneeHeap = new Heap<>(); // Initialize the heap if it is null
        }
        
        String generatedId = ID_PREFIX + String.format("%05d", doneeHeap.size() + 1);
        donee.setId(generatedId);
        
        System.out.println("Donee ID: " + donee.getId());

        donee.setType(inputDoneeType());
    }

    // Prompts the user to select the Donee type and returns it
    public Donee.Type inputDoneeType() {
        System.out.println("\n==========");
        System.out.println("Donee Type");
        System.out.println("==========");
        System.out.println("1. Individual");
        System.out.println("2. Organisation");
        System.out.println("3. Family");
        System.out.println("Select Donee Type:");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                return Donee.Type.INDIVIDUAL;
            case 2:
                return Donee.Type.ORGANISATION;
            case 3:
                return Donee.Type.FAMILY;
            default:
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                return inputDoneeType();
        }
    }

    // Get donee ID from user
    public String inputDoneeId() {
        System.out.print("Enter Donee ID to search: ");
        return scanner.nextLine();
    }
    
    // Prompts the user to input a Donee name using Person UI
    public String inputDoneeName() {
        return personUI.inputPersonName();
    }
    
    // Prompts the user to input a Donee phone number using Person UI
    public String inputDoneePhone() {
        return personUI.inputPersonPhoneNo();
    }
    
    // Prompts the user to input the Donee's gender
    public Person.Gender inputDoneeGender() {
        Person.Gender inputGender = null;

        do {
            System.out.print("Enter person gender(M/F): ");
            String inputValue = scanner.nextLine();
            switch (inputValue) {
                case "m", "M" ->
                    inputGender = Person.Gender.MALE;
                case "f", "F" ->
                    inputGender = Person.Gender.FEMALE;
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (inputGender == null);
        return inputGender;
    }

    // Method to prompt the user to input the search value based on the selected criteria
    public String inputSearchValue(String searchCriteria) {
        switch (searchCriteria.toLowerCase()) {
            case "id":
                return inputDoneeId();
            case "name":
                return inputDoneeName();
            case "gender":
                return inputDoneeGender().toString();
            case "phone":
                return inputDoneePhone();
            default:
                throw new IllegalArgumentException("Invalid search criteria: " + searchCriteria);
        }
    }
    
    // Prompts the user to input a valid month and year
    public int[] inputMonthAndYear() {
        int month = 0;
        int year = 0;
        
        // Loop to ensure valid month input
        while (true) {
            try {
                System.out.print("Enter month (1-12): ");
                String monthInput = scanner.nextLine().trim();
                month = Integer.parseInt(monthInput);

                if (month < 1 || month > 12) {
                    throw new NumberFormatException("Invalid month");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid month (1-12).");
            }
        }

        // Loop to ensure valid year input
        while (true) {
            try {
                System.out.print("Enter year (e.g., 2024): ");
                String yearInput = scanner.nextLine().trim();
                year = Integer.parseInt(yearInput);

                if (year < 1000 || year > 9999) {  // Example validation for a reasonable year range
                    throw new NumberFormatException("Invalid year");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid year.");
            }
        }

        return new int[]{month, year};
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="others">
    // List all donees
    public void listAllDonee(String doneeList) {
        System.out.println(doneeList);
    }

    // Display an invalid choice message
    public void displayInvalidChoiceMessage() {
        System.out.println("Invalid choice. Please try again.");
    }

    // Display an object not found message
    public void displayObjectNotFoundMessage() {
        System.out.println("Donee not found.");
    }

    // Display exit message
    public void displayExitMessage() {
        System.out.println("Exiting Donee Maintenance...");
    }
    
    public boolean confirmDeletion(Donee doneeToRemove) {
        // Display the Donee details and ask for confirmation
        printDoneeHeader();
        System.out.println(doneeToRemove.toString());
        System.out.println("\nAre you sure you want to delete the following Donee?");
        System.out.print("Type 'Y' to confirm deletion or 'N' to cancel: ");
        String input = scanner.nextLine();

        return input.equalsIgnoreCase("Y");
    }
    // </editor-fold>
}
