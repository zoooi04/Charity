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
public class DonorMaintenanceUI {

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
        return scanner.nextInt();
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
        return scanner.nextInt();
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
        return scanner.nextInt();
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
        return scanner.nextInt();
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
        return scanner.nextInt();
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
        return scanner.nextInt();
    }

    public void getSearchMenu() {
        System.out.println("\nSelection of Search: ");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("0. Back");
    }

    public int getSearchMenuChoice() {
        getSearchMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getSearchMenu();
        }
        return scanner.nextInt();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="output">
    public void listAllDonor(String outputStr) {
        System.out.println(outputStr);
    }

    public void printDonorDetails(Donor donor) {
        System.out.println("Donor Details");
        System.out.println("Donor id: " + donor.getId());
        System.out.println("Donor type:" + donor.getType());
        System.out.println("Donor category: " + donor.getCategory());
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="input">
    public String inputDonorId() {
        Pattern pattern = Pattern.compile("AA24[0-9]*");
        System.out.print("Enter donor Id: ");
        while (!scanner.hasNext(pattern)) {
            scanner.next();
            System.out.print("Enter donor Id: ");
        }
        return scanner.next();

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
    // </editor-fold>

    public void inputDonorDetails(Donor donor) {
        donor.setType(inputDonorType());
        donor.setCategory(inputDonorCategory());
    }

}
