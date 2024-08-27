/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Donation;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author huaiern
 */
public class DonationMaintenanceUI {
    Scanner scanner = new Scanner(System.in);

    // <editor-fold defaultstate="collapsed" desc="menu">
    public int getMenuChoice() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. List all Donation");
        System.out.println("2. Search Donation");
        System.out.println("3. Add new Donation");
        System.out.println("4. Delete Donation");
        System.out.println("5. Edit Donation");
        System.out.println("6. Donation Report");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public int getUpdateMenuChoice() {
        System.out.println("\nUPDATE MENU");
        System.out.println("1. Quantity");
        System.out.println("2. Message");
        System.out.println("3. Donation Type");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
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
        outputStr += "\nList of Donation:\n";
        outputStr += "\n" + "=".repeat(200) + "\n";
        outputStr += String.format("%-10s%-3s%-50s%-10%-30s%-30s%-10s%-10s",
                "ID",
                "Quantity",
                "Message",
                "Name",
                "Event",
                "Type",
                "Date");
        outputStr += "\n" + "=".repeat(200) + "\n";
        System.out.print(outputStr);
    }
    // </editor-fold>


    


}
