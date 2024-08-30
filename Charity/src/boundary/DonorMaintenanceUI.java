/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Donor;
import java.util.Scanner;
import java.util.regex.Pattern;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 */
public class DonorMaintenanceUI extends PersonMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    // <editor-fold defaultstate="collapsed" desc="menu">
    private void getMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. List Donor");
        System.out.println("2. Search Donor");
        System.out.println("3. Add new Donor");
        System.out.println("4. Delete Donor");
        System.out.println("5. Edit Donor");
        System.out.println("6. Donor Report");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
    }

    public int getMenuChoice() {
        getMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getMenu();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }

    private void getUpdateMenu() {
        System.out.println("\nUPDATE MENU");
        System.out.println("1. Type");
        System.out.println("2. Category");
        System.out.println("99. Confirm");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
    }

    public int getUpdateMenuChoice() {
        getUpdateMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getUpdateMenu();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }

    public void getUpdateDonor(String id) {
        System.out.println("\nDo you want to update " + id + " ?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
    }

    public int getUpdateDonorConfirmation(String id) {
        getUpdateDonor(id);
        while (!scanner.hasNextInt()) {
            scanner.next();
            getUpdateDonor(id);
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }

    private void getDisplayMenu() {
        System.out.println("\nDISPLAY MENU");
        System.out.println("1. Type");
        System.out.println("2. Category");
        System.out.println("3. Sort");
        System.out.println("0. Back");
        System.out.print("Enter Choice: ");
    }

    public int getDisplayMenuChoice() {
        getDisplayMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getDisplayMenu();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }

    private void getDisplayTypeMenu() {
        System.out.println("\nSelection of Type: ");
        System.out.println("1. Organisation");
        System.out.println("2. Individual");
        System.out.println("0. Back");
        System.out.print("Enter Selection: ");
    }

    public int getDisplayTypeMenuChoice() {
        getDisplayTypeMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getDisplayTypeMenu();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }

    private void getDisplayCategoryMenu() {
        System.out.println("\nSelection of Category: ");
        System.out.println("1. Government");
        System.out.println("2. Private");
        System.out.println("3. Public");
        System.out.println("0. Back");
        System.out.print("Enter Selection: ");
    }

    public int getDisplayCategoryMenuChoice() {
        getDisplayCategoryMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getDisplayCategoryMenu();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }

    private void getDisplaySortMenu() {
        System.out.println("\nSort by Details: ");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Phone Number");
        System.out.println("4. Registered Date");
        System.out.println("0. Back");
    }

    public int getDisplaySortMenuChoice() {
        getDisplaySortMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getDisplaySortMenu();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }

    public void getSearchMenu() {
        System.out.println("\nSelection of Search: ");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("0. Back");
        System.out.print("Enter Selection: ");
    }

    public int getSearchMenuChoice() {
        getSearchMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getSearchMenu();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="output">
    public void listAllDonor(String outputStr) {
        System.out.println(outputStr);
    }

    public void printDonorDetails(Donor donor) {
        super.printPersonDetails(donor);
        System.out.print("\n" + "=".repeat(48) + "\n");
        System.out.print("\tDonor Details");
        System.out.print("\n" + "=".repeat(48) + "\n");
        System.out.println("Donor id                 : " + donor.getId());
        System.out.println("Donor type               : " + donor.getType());
        System.out.println("Donor category           : " + donor.getCategory());
    }

    public void printDonorHeader() {
        String outputStr = "";
        outputStr += "\nList of Donor:\n";
        outputStr += "\n" + "=".repeat(150) + "\n";
        outputStr += String.format("%-30s%-5s%-13s%-10s%-15s%-22s%-15s%-12s%-15s%-13s",
                "Name",
                "Age",
                "BirthDay",
                "Gender",
                "Phone Number",
                "Register Date",
                "Status",
                "id",
                "type",
                "category");
        outputStr += "\n" + "=".repeat(150) + "\n";
        System.out.print(outputStr);
    }

    public void printDonorSearchHeader() {
        String outputStr = "";
        outputStr += "\nList of Donor:\n";
        outputStr += "\n" + "=".repeat(150) + "\n";
        outputStr += String.format("%5s%-30s%-5s%-13s%-10s%-15s%-22s%-15s%-12s%-15s%-13s",
                "",
                "Name",
                "Age",
                "BirthDay",
                "Gender",
                "Phone Number",
                "Register Date",
                "Status",
                "id",
                "type",
                "category");
        outputStr += "\n" + "=".repeat(150) + "\n";
        System.out.print(outputStr);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="input">
    public String inputDonorId() {
        Pattern pattern = Pattern.compile("AA24[0-9]{5}");
        String inputStr;

        while (true) {
            System.out.print("Enter donor ID (format: AA24XXXXX): ");
            inputStr = scanner.nextLine();

            // Check if input is not empty and matches the pattern
            if (!inputStr.isEmpty() && pattern.matcher(inputStr).matches()) {
                break; // Exit the loop if the ID is valid
            } else {
                System.out.println("Invalid ID format. Please try again.");
            }
        }

        return inputStr;
    }

    public Donor.Type inputDonorType() {
        switch (getDisplayTypeMenuChoice()) {
            case 1:
                return Donor.Type.ORGANISATION;
            case 2:
                return Donor.Type.INDIVIDUAL;
            default:
                MessageUI.displayInvalidChoiceMessage();
        }
        return null;
    }

    public Donor.Category inputDonorCategory() {
        switch (getDisplayCategoryMenuChoice()) {
            case 1:
                return Donor.Category.GOVERNMENT;
            case 2:
                return Donor.Category.PRIVATE;
            case 3:
                return Donor.Category.PUBLIC;
            default:
                MessageUI.displayInvalidChoiceMessage();
        }
        return null;
    }

    public int inputSelection() {
        System.out.print("Enter Selection (0-exit) : ");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Enter Selection (0-exit) : ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }
    // </editor-fold>

    public void inputDonorDetails(Donor donor) {
        donor.setType(inputDonorType());
        donor.setCategory(inputDonorCategory());
    }

}
