package boundary;

import entity.Donee;
import java.util.Scanner;

/**
 * User Interface for Donee Maintenance.
 */
public class DoneeMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    // <editor-fold defaultstate="collapsed" desc="menu">
    // Display the main menu and get the user's choice
    public int getMenuChoice() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. List all Donees");
        System.out.println("2. Search Donee");
        System.out.println("3. Add new Donee");
        System.out.println("4. Remove Donee");
        System.out.println("5. Update Donee");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return choice;
    }

    // Get the choice for updating donee details
    public int getUpdateMenuChoice() {
        System.out.println("\nUPDATE MENU");
        System.out.println("1. Update Category");
        System.out.println("99. Confirm Update");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return choice;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="output">
    // Print all donees
    public void listAllDonees(String outputStr) {
        System.out.println(outputStr);
    }

    // Print details of a donee
    public void printDoneeDetails(Donee donee) {
        System.out.println("Donee Details");
        System.out.println("ID: " + donee.getId());
        System.out.println("Type: " + donee.getType());
        System.out.println("Category: " + donee.getCategory());
        System.out.println("Received Amount: " + donee.getReceivedAmount());
    }

    // Print the header for the donee list
    public void printDoneeHeader() {
        String outputStr = "";
        outputStr += "\nList of Donees:\n";
        outputStr += "\n" + "=".repeat(150) + "\n";
        outputStr += String.format("%-10s%-20s%-20s%-20s%-20s%-20s%-20s%-20s",
                "ID",
                "Type",
                "Category",
                "Received Amount",
                "Name",
                "Age",
                "Birthday",
                "Gender");
        outputStr += "\n" + "=".repeat(150) + "\n";
        System.out.print(outputStr);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="input">
    // Input donee ID
    public String inputDoneeId() {
        System.out.print("Enter Donee ID: ");
        return scanner.nextLine();
    }

    // Input donee type
    public Donee.DoneeType inputDoneeType() {
        Donee.DoneeType inputEnum = null;

        do {
            System.out.print("Enter Donee Type (INDIVIDUAL, ORGANISATION, FAMILIAR): ");
            String inputValue = scanner.nextLine().toUpperCase();
            try {
                inputEnum = Donee.DoneeType.valueOf(inputValue);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (inputEnum == null);
        return inputEnum;
    }

    // Input donee category
    public Donee.Categories inputDoneeCategory() {
        Donee.Categories inputEnum = null;

        do {
            System.out.print("Enter Donee Category (HEALTH, EDUCATION, WELFARE): ");
            String inputValue = scanner.nextLine().toUpperCase();
            try {
                inputEnum = Donee.Categories.valueOf(inputValue);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (inputEnum == null);
        return inputEnum;
    }

    // Input all details for a donee
    public void inputDoneeDetails(Donee donee) {
        donee.setId(inputDoneeId());
        donee.setType(inputDoneeType());
        donee.setCategory(inputDoneeCategory());
        System.out.print("Enter Received Amount: ");
        donee.setReceivedAmount(scanner.nextDouble());
        scanner.nextLine(); // Clear buffer
    }
    // </editor-fold>
}
