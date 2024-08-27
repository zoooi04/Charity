package boundary;

import entity.Donee;
import java.util.Scanner;

public class DoneeMaintenanceUI {
    
    private PersonMaintenanceUI personUI = new PersonMaintenanceUI();
    private final Scanner scanner = new Scanner(System.in);
    private static int idCounter = 2400001;  // Starting point for ID generation
    private static final String ID_PREFIX = "DN";
    
    // Display main menu and get user's choice
    public int getMenuChoice() {
        System.out.println("Donee Maintenance Menu:");
        System.out.println("1. List all donees");
        System.out.println("2. Search donee");
        System.out.println("3. Add new donee");
        System.out.println("4. Remove donee");
        System.out.println("5. Update donee");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }
    
   // Get search criteria from user
    public int getSearchMenuChoice() {
        System.out.println("Select search criteria:");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Phone Number");
        System.out.println("4. Search by Type");
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }
    
    public void printDoneeHeader() {
        String outputStr = "";
        outputStr += "\n" + "=".repeat(200) + "\n";
        outputStr += String.format("%-30s%-10s%-20s%-10s%-20s%-30s%-20s%-20s%-20s%-20s",
                "Name",
                "Age",
                "BirthDay",
                "Gender",
                "Phone Number",
                "Register Date",
                "Status",
                "Id",
                "Type",
                "Amount Request");
        outputStr += "\n" + "=".repeat(200) + "\n";
        System.out.print(outputStr);
    }

    // Input donee details from user
    public void inputDoneeDetails(Donee donee) {
        
        String generatedId = ID_PREFIX + idCounter;
        donee.setId(generatedId);
        idCounter++;  // Increment the counter for the next ID
        
        System.out.println("Donee ID: " + donee.getId());

        donee.setType(inputDoneeType());
    }

    // Get donee type from user
    public Donee.Type inputDoneeType() {
        System.out.println("Select Donee Type:");
        System.out.println("1. Individual");
        System.out.println("2. Organisation");
        System.out.println("3. Family");

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
                return inputDoneeType(); // Recursively prompt until a valid choice is made
        }
    }

    // Get donee ID from user
    public String inputDoneeId() {
        System.out.print("Enter Donee ID to search: ");
        return scanner.nextLine();
    }
    
    public String inputDoneeName() {
        return personUI.inputPersonName();
    }

    public String inputDoneePhone() {
        return personUI.inputPersonPhoneNo();
    }

    // Get update menu choice from user
    public int getUpdateMenuChoice() {
        System.out.println("Update Menu:");
        System.out.println("1. Update Donee Type");
        System.out.println("2. Update Received Amount");
        System.out.println("99. Confirm");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }

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
}
