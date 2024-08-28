/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Donor;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 */
public class DonorMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    // <editor-fold defaultstate="collapsed" desc="menu">
    public int getMenuChoice() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. List Donor");
        System.out.println("2. Search Donor");
        System.out.println("3. Add new Donor");
        System.out.println("4. Delete Donor");
        System.out.println("5. Edit Donor");
        System.out.println("6. Donor Report");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public int getUpdateMenuChoice() {
        System.out.println("\nUPDATE MENU");
        System.out.println("1. Type");
        System.out.println("2. Category");
        System.out.println("99. Confirm");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public int getDisplayMenuChoice(){
        System.out.println("\nDISPLAY MENU");
        System.out.println("1. Type");
        System.out.println("2. Category");
        System.out.println("3. Sort");
        System.out.println("0. Back");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public int getDisplayTypeMenuChoice(){
        System.out.println("\nDISPLAY MENU");
        System.out.println("1. Organisation");
        System.out.println("2. Individual");
        System.out.println("0. Back");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public int getDisplayCategoryMenuChoice(){
        System.out.println("\nDISPLAY MENU");
        System.out.println("1. Government");
        System.out.println("2. Private");
        System.out.println("3. Public");
        System.out.println("0. Back");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public int getDisplaySortMenuChoice(){
        System.out.println("\nDISPLAY MENU");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Phone Number");
        System.out.println("4. Registered Date");
        System.out.println("0. Back");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
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
        System.out.print("Enter donor Id: ");
        String inputValue = scanner.nextLine();
        return inputValue;
    }

    public Donor.Type inputDonorType() {
        Donor.Type inputEnum = null;

        do {
            System.out.print("Enter donor Type(I/O): ");
            String inputValue = scanner.nextLine();
            switch (inputValue) {
                case "i", "I" ->
                    inputEnum = Donor.Type.INDIVIDUAL;
                case "o", "O" ->
                    inputEnum = Donor.Type.ORGANISATION;
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (inputEnum == null);
        return inputEnum;
    }

    public Donor.Category inputDonorCategory() {
        Donor.Category inputEnum = null;

        do {
            System.out.print("Enter donor category(G/V/U): ");
            String inputValue = scanner.nextLine();
            switch (inputValue) {
                case "g", "G" ->
                    inputEnum = Donor.Category.GOVERNMENT;
                case "v", "V" ->
                    inputEnum = Donor.Category.PRIVATE;
                case "u", "U" ->
                    inputEnum = Donor.Category.PUBLIC;
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (inputEnum == null);
        return inputEnum;
    }
    // </editor-fold>

    public void inputDonorDetails(Donor donor) {
        donor.setType(inputDonorType());
        donor.setCategory(inputDonorCategory());
    }

}
