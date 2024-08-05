/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Donor;
import entity.Person;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 */
public class DonorMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Add new Donor");
        System.out.println("2. List all Donor");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public void listAllDonor(String outputStr) {
        System.out.println("\nList of Donor:\n" + outputStr);
    }

    public void printDonorDetails(Donor donor) {
        System.out.println("Donor Details");
        System.out.println("Donor id: " + donor.getId());
        System.out.println("Donor type:" + donor.getType());
        System.out.println("Donor category: " + donor.getCategory());
    }

    public String inputDonorId() {
        System.out.print("Enter donor Id: ");
        String inputValue = scanner.nextLine();
        return inputValue;
    }

    public Donor.Type inputDonorType() {
        Donor.Type inputEnum = null;

        do {
            System.out.print("Enter donor Type(I/O/F): ");
            String inputValue = scanner.nextLine();
            switch (inputValue) {
                case "i", "I" -> inputEnum = Donor.Type.INDIVIDUAL;
                case "o", "O" -> inputEnum = Donor.Type.ORGANISATION;
                case "f", "F" -> inputEnum = Donor.Type.FAMILY;
                default -> MessageUI.displayInvalidChoiceMessage();
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
                case "g", "G" -> inputEnum = Donor.Category.GOVERNMENT;
                case "v", "V" -> inputEnum = Donor.Category.PRIVATE;
                case "u", "U" -> inputEnum = Donor.Category.PUBLIC;
                default -> MessageUI.displayInvalidChoiceMessage();
            }
        } while (inputEnum == null);
        return inputEnum;
    }

    public Donor inputDonorDetails(Person person) {
        String donorId = inputDonorId();
        Donor.Type donorType = inputDonorType();
        Donor.Category donorCategory = inputDonorCategory();      
        System.out.println();
        return new Donor(donorId, donorType, donorCategory, person);
    }

}
