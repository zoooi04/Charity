/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package boundary;

import java.util.Scanner;
import entity.Donation;
import entity.Donee;
import control.DistributionMaintenance;
import control.DonationMaintenance;
import control.DoneeMaintenance;

/**
 *
 * @author BEH JING HEN
 */

/**
 * User Interface for Distribution Maintenance.
 * Provides a menu-driven interface for users to manage donations and donees.
 * Options include distributing donations, requeue donees, printing distributions, and restoring donations.
 */
public class DistributionMaintenanceUI {

    private final DistributionMaintenance distributionMaintenance;
    private final DoneeMaintenance doneeMaintenance;

    public DistributionMaintenanceUI(DistributionMaintenance distributionMaintenance, DoneeMaintenance doneeMaintenance) {
        this.distributionMaintenance = distributionMaintenance;
        this.doneeMaintenance = doneeMaintenance;
    }
    
     /**
     * Displays the main menu for Distribution Maintenance.
     * Allows the user to select an option to perform various operations.
     */
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nDistribution Maintenance Menu:");
            System.out.println("1. Distribute Donation");
            System.out.println("2. Requeue Donee");
            System.out.println("3. Print All Distributions");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    // Distribute donations to donees
                    distributionMaintenance.distributeDonation(doneeMaintenance);
                    distributionMaintenance.saveDonations();
                    break;
                case 2:
                    // Requeue donees that meet certain criteria
                    distributionMaintenance.requeueDonee(doneeMaintenance);
                    break;
                case 3:
                    // Print all distributions
                    distributionMaintenance.printAllDistributions();
                    break;
                case 4:
                    distributionMaintenance.restoreAllDonations();
                    break;
                case 0:
                    // Exit the menu
                    exit = true;
                    System.out.println("Exiting Distribution Maintenance...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
     /**
     * Method to get input from the user.
     * Prompts the user with the given message and returns the input as a String.
     * 
     * @param prompt The message to display to the user.
     * @return The input provided by the user.
     */
    public static String getInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
