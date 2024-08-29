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
        System.out.println("2. Search specific Donation by Id");
        System.out.println("3. Add new Donation");
        System.out.println("4. Remove Donation");
        System.out.println("5. Amend Donation");
        System.out.println("6. Filter donation by Type");
        System.out.println("7. Filter donation by Donor");
        System.out.println("8. Filter donation by other Criteria");
        System.out.println("9. Donation Report");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        while(!scanner.hasNextInt()){
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
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while(!scanner.hasNextInt()){
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    // </editor-fold>

    public int getCategoriesMenuChoice(){
        System.out.println("\nSELECT CATEGORIES");
        System.out.println("1. Cash");
        System.out.println("2. Food");
        System.out.println("3. Item");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while(!scanner.hasNextInt()){
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public int getDonorInfoMenuChoice() {
        System.out.println("\nFilter BY:");
        System.out.println("1. Donor Id");
        System.out.println("2. Donor Name");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while(!scanner.hasNextInt()){
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public int getFilterCriteria()  {
        System.out.println("\nCHOOSE CRITERIA:");
        System.out.println("1. Message Availability");
        System.out.println("2. Event");
        System.out.println("3. Date");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while(!scanner.hasNextInt()){
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public int getEventInfoMenuChoice(){
        System.out.println("\nFilter BY:");
        System.out.println("1. Event Id");
        System.out.println("2. Event Name");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        while(!scanner.hasNextInt()){
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Consume the invalid input  
            System.out.print("Enter choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
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
        outputStr += String.format("%-15s%-15s%-50s%-30s%-30s%-15s%-15s",
                "ID",
                "Quantity",
                "Message",
                "Donor",
                "Event",
                "Type",
                "Date");
        outputStr += "\n" + "=".repeat(200) + "\n";
        System.out.print(outputStr);
    }
    // </editor-fold>


    


}
