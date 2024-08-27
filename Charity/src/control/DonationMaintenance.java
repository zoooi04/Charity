/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.Donation;
import boundary.DonationMaintenanceUI;
import utility.MessageUI;
import adt.MapInterface;
import adt.HashMap;
/**
 *
 * @author huaiern
 */
public class DonationMaintenance{
    private final DonationMaintenanceUI donationUI = new DonationMaintenanceUI();
    private MapInterface<String,Donation> map = new HashMap<>();
    private static final String FILENAME = "donation.dat";
    
    // <editor-fold defaultstate="collapsed" desc="Driver">
    public void DonationMaintenanceDriver(){
        int choice = 0;
        do {
            choice = donationUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    donationUI.printDonationHeader();
                    break;
                case 2:
                    break;
                case 3:
                    if (create()) {
                        donationUI.printDonationHeader();
                        
                    }
                    break;
                case 4:
//                    if (remove(donationGraph)) {
//                        donationUI.printDonationHeader();
//                        display(donationGraph);
//                    }
                    break;
                case 5:
//                    if (update(donationGraph)) {;
//                        donationUI.printDonationHeader();
//                        display(donationGraph);
//                    }
                    break;
                case 6:
                    //report
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CRUD">
    public boolean create() {
        Donation donation = new Donation();
        
        //input
        System.out.print("Enter donation ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        
        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        
        System.out.print("Enter donor ID: ");
        String donorID = scanner.nextLine();
        
        System.out.print("Enter event ID:");
        String eventID = scanner.nextLine();
        
        
        Donation.DonationType inputEnum = null;

        do {
            System.out.print("Enter donation Type(C/F/I): ");
            String inputValue = scanner.nextLine();
            switch (inputValue) {
                case "c", "C" ->
                    inputEnum = Donation.DonationType.CASH;
                case "f", "F" ->
                    inputEnum = Donation.DonationType.FOOD;
                case "i", "I" ->
                    inputEnum = Donation.DonationType.ITEM;
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (inputEnum == null);
        
        
        //assign properties
        donation.setId(id);
        donation.setQuantity(quantity);
        donation.setMessage(message);
        //donation.setDonor(inputDonor());
        //donation.setEvent(inputEvent());
        donation.setType(inputEnum);
        
        
        //add in donation map
        if(!map.containsKey(donation.getId())){
            map.put(donation.getId(), donation);
            return true;
        }else{
            MessageUI.displayUnableCreateObjectMessage();
            return false;
        }
    }

    
    public boolean remove(Donation donation) {
        if(!map.isEmpty()){
            map.remove(donation.getId());
            return true;
        }else{
            System.out.println("Empty donation.");
            return false;
        }
        
    }

    
    public boolean update(Donation donation) {
        if(!map.isEmpty() && map.containsKey(donation.getId())){
            //put can overwrite existing key value
            map.put(donation.getId(),donation);
        }
    }

    
    public boolean search(HashMap<String,Donation> donationMap, Donation donation) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



   
    public boolean report(Donation donation) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="main">
    public static void main(String[] args) {
        try {
            DonationMaintenance donationMaintenance = new DonorMaintenance();
            donationMaintenance.donationMaintenanceDriver();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    // </editor-fold>
}
