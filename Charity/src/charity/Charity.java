/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package charity;

import boundary.CharityUI;
import control.DonorMaintenance;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 */
public class Charity {

    private static CharityUI charityUI = new CharityUI();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int choice = 0;
        do {
            choice = charityUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                try {
                    DonorMaintenance donorMaintenance = new DonorMaintenance();
                    donorMaintenance.donorMaintenanceDriver();
                } catch (Exception e) {
                    System.err.println("An unexpected error occurred: " + e.getMessage());
                }
                break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

}
