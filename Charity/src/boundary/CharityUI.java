/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import java.util.Scanner;

/**
 *
 * @author Ooi Choon Chong
 */
public class CharityUI {

    Scanner scanner = new Scanner(System.in);

    // <editor-fold defaultstate="collapsed" desc="menu">
    private void getMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Donor Maintenance");
        System.out.println("2. Donation");
        System.out.println("3. Distribution");
        System.out.println("4. Donee");
        System.out.println("5. Event");
        System.out.println("6. Volunteer");
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
    // </editor-fold>

}
