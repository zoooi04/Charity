/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.ArrayList;
import entity.Donation;
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
    
    public void getDeleteDonor(String id) {
        System.out.println("\nDo you want to delete " + id + " ?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
    }

    public int getDeleteDonorConfirmation(String id) {
        getDeleteDonor(id);
        while (!scanner.hasNextInt()) {
            scanner.next();
            getDeleteDonor(id);
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
        System.out.println("9. Clear");
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
        System.out.println("9. Clear");
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

    private void getDisplaySortMenu(Donor.Type enumType, Donor.Category enumCategory) {
        System.out.print("\nSort by Details: ");
        String filterStr = "\nFilter by: ";
        if (enumType == null && enumCategory == null) {
            filterStr += "None";
        }
        if (enumType != null) {
            filterStr += enumType + " ";
        }
        if (enumCategory != null) {
            filterStr += enumCategory + " ";
        }
        System.out.println(filterStr);
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Phone Number");
        System.out.println("4. Registered Date");
        System.out.println("0. Back");
        System.out.print("Enter Selection: ");
    }

    public int getDisplaySortMenuChoice(Donor.Type enumType, Donor.Category enumCategory) {
        getDisplaySortMenu(enumType, enumCategory);
        while (!scanner.hasNextInt()) {
            scanner.next();
            getDisplaySortMenu(enumType, enumCategory);
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }

    private void getSearchMenu() {
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

    private void getDisplayReportMenu() {
        System.out.println("\nSelection of Report: ");
        System.out.println("1. Analysis of Donor");
        System.out.println("2. Analysis of Donor Age Level");
        System.out.println("3. Top 10 Active Donor");
        System.out.println("0. Back");
        System.out.print("Enter Selection: ");
    }

    public int getDisplayReportMenuChoice() {
        getDisplayReportMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getDisplayReportMenu();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the leftover newline character
        return choice;
    }

    private void getDisplayReportMenuDonor() {
        System.out.println("\nSelection of Donor Report: ");
        System.out.println("1. Gender");
        System.out.println("2. Type");
        System.out.println("3. Category");
        System.out.println("0. Back");
        System.out.print("Enter Selection: ");
    }

    public int getDisplayReportMenuDonorChoice() {
        getDisplayReportMenuDonor();
        while (!scanner.hasNextInt()) {
            scanner.next();
            getDisplayReportMenuDonor();
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

    public void printDonorReportHeader() {
        String outputStr = "";
        outputStr += "\n\t\tReport of donor";
        outputStr += "\n" + "=".repeat(48) + "\n";
        outputStr += String.format("%-24s%12s%12s",
                "Group",
                "Number",
                "Percentage");
        outputStr += "\n" + "=".repeat(48) + "\n";
        System.out.print(outputStr);
    }

    public void printDonorReportBody(ArrayList<Donor> arrList, int group, int total) {
        String outputStr = "";
        String groupName = "";
        switch (group) {
            case 1:
                groupName = String.format("%s", arrList.getEntry(1).getGender());
                break;
            case 2:
                groupName = String.format("%s", arrList.getEntry(1).getType());
                break;
            case 3:
                groupName = String.format("%s", arrList.getEntry(1).getCategory());
                break;
        }
        outputStr += String.format("%-24s%12s%12s",
                groupName,
                arrList.getNumberOfEntries(),
                arrList.getNumberOfEntries() * 100 / total + "%");
        System.out.println(outputStr);
    }

    public void printActiveDonorReportHeader() {
        String outputStr = "";
        outputStr += "\n\t\tReport of active donor";
        outputStr += "\n" + "=".repeat(64) + "\n";
        outputStr += String.format("%-16s%12s%12s%12s%12s",
                "Group",
                "Active",
                "Non-Active",
                "Total",
                "Active(%)");
        outputStr += "\n" + "=".repeat(64) + "\n";
        System.out.print(outputStr);
    }

    public void printActiveDonorReportBody(int[] active, int[] inActive) {
        for (int i = 0; i < active.length; i++) {
            String outputStr = "";
            String group = "";
            switch (i) {
                case 0:
                    group = "age below 21";
                    break;
                case 1:
                    group = "age  21 - 40";
                    break;
                case 2:
                    group = "age  41 - 60";
                    break;
                case 3:
                    group = "age  61 - 80";
                    break;
                case 4:
                    group = "age  over 80";
                    break;
                case 5:
                    group = "All Donor";
                    break;
            }

            int total = active[i] + inActive[i];
            int percentage = (total == 0 ? 0 : active[i] * 100 / total);
            if (i == 5) {
                printDonorReportSeperateLine(64);
            }
            outputStr += String.format("%-16s%12s%12s%12s%12s\n",
                    group,
                    active[i],
                    inActive[i],
                    total,
                    percentage + "%");
            System.out.print(outputStr);
        }
        System.out.print("=".repeat(64) + "\n");
    }

    public void printDonorTop3ReportHeader() {
        String outputStr = "";
        outputStr += "\n\t\tReport of Top 3 Donor for Each Type Donation";
        outputStr += "\n" + "=".repeat(70) + "\n";
        outputStr += String.format("%-16s%-30s%-12s%12s",
                "Type Donation",
                "Donor Name",
                "Donor ID",
                "Quantity");
        outputStr += "\n" + "=".repeat(70) + "\n";
        System.out.print(outputStr);
    }

    public void printDonorTop3ReportBody(Donation.DonationType type, ArrayList<Donor> donor, ArrayList<Double> qty) {
        String outputStr = "";
        String typeStr = "";
        int count = 3;
        if (donor.getNumberOfEntries() < 3) {
            count = donor.getNumberOfEntries();
        }
        for (int i = 1; i <= count; i++) {
            if (i == 1) {
                typeStr = type.toString();
            } else {
                typeStr = "";
            }
            if (donor.getEntry(i) != null && qty.getEntry(i) != null) {
                outputStr += String.format("%-16s%-30s%-12s%12.2f\n",
                        typeStr,
                        donor.getEntry(i).getName(),
                        donor.getEntry(i).getId(),
                        qty.getEntry(i));
            }
        }
        System.out.print(outputStr);
    }

    public void printDonorReportSeperateLine(int repeat) {
        String outputStr = "";
        outputStr += "-".repeat(repeat) + "\n";
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
