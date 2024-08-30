package dao;


import adt.HashMap;
import adt.ListInterface;
import adt.MapInterface;
import control.DonationMaintenance;
import entity.Donation;
import entity.Donor;
import entity.Event;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author huaiern
 */
public class DonationInitializer {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    static Donation donation1 = new Donation("DNTA0001", 200, "Good Work, Keep Going!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00001"),Donation.DonationType.CASH,DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation2 = new Donation("DNTA0002", 50, "Nice one!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00001"),Donation.DonationType.FOOD,DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation3 = new Donation("DNTA0003", 2, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00001"),Donation.DonationType.ITEM,DonationMaintenance.getEventById("E00001").getStartDate());


//  Method to return a collection of with hard-coded entity values
    public static MapInterface<String,Donation> initializeDonation() {
        DAO<MapInterface<String, Donation>> dao = new DAO<>();
        
        MapInterface<String,Donation> donationMap = new HashMap<>();
        donationMap.put(donation1.getId(),donation1);
        donationMap.put(donation2.getId(), donation2);
        donationMap.put(donation3.getId(), donation3);
        
        dao.saveToFile(donationMap,DonationMaintenance.getFileName());
        return donationMap;
    }

    public static void main(String[] args) {
        // To illustrate how to use the initializeProducts() method
        MapInterface<String,Donation> donationMap = DonationInitializer.initializeDonation();
        System.out.println("\nDonation:\n" + donationMap);
        String id = "DNTA0001";
    }
}
