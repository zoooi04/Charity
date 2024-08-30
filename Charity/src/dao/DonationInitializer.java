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

    // Donations for Event E00001
    static Donation donation4 = new Donation("DNTA0004", 100, "Happy to contribute!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation5 = new Donation("DNTA0005", 20, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation6 = new Donation("DNTA0006", 5, "Hope this helps!", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00001").getStartDate());

// Donations for Event E00002
    static Donation donation7 = new Donation("DNTA0007", 150, "Keep up the great work!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation8 = new Donation("DNTA0008", 30, "Happy to support!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation9 = new Donation("DNTA0009", 10, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00002").getStartDate());

// Donations for Event E00003
    static Donation donation10 = new Donation("DNTA0010", 200, "Great event!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation11 = new Donation("DNTA0011", 40, "Good cause!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation12 = new Donation("DNTA0012", 3, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00003").getStartDate());

// Donations for Event E00004
    static Donation donation13 = new Donation("DNTA0013", 75, "Keep going!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation14 = new Donation("DNTA0014", 15, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation15 = new Donation("DNTA0015", 8, "Wonderful effort!", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00004").getStartDate());

// Donations for Event E00005
    static Donation donation16 = new Donation("DNTA0016", 250, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation17 = new Donation("DNTA0017", 60, "Hope it helps!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation18 = new Donation("DNTA0018", 12, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00005").getStartDate());

// Donations for Event E00006
    static Donation donation19 = new Donation("DNTA0019", 300, "Proud to support!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation20 = new Donation("DNTA0020", 70, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation21 = new Donation("DNTA0021", 6, "Amazing work!", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00006").getStartDate());

// Donations for Event E00007
    static Donation donation22 = new Donation("DNTA0022", 180, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation23 = new Donation("DNTA0023", 25, "Good luck!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation24 = new Donation("DNTA0024", 7, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00007").getStartDate());

// Donations for Event E00008
    static Donation donation25 = new Donation("DNTA0025", 220, "Let's make a difference!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation26 = new Donation("DNTA0026", 80, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation27 = new Donation("DNTA0027", 4, "Love what you do!", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00008").getStartDate());


//  Method to return a collection of with hard-coded entity values
    public static MapInterface<String,Donation> initializeDonation() {
        DAO<MapInterface<String, Donation>> dao = new DAO<>();
        
        MapInterface<String,Donation> donationMap = new HashMap<>();
        donationMap.put(donation1.getId(),donation1);
        donationMap.put(donation2.getId(), donation2);
        donationMap.put(donation3.getId(), donation3);
        donationMap.put(donation1.getId(), donation1);
        donationMap.put(donation2.getId(), donation2);
        donationMap.put(donation3.getId(), donation3);
        donationMap.put(donation4.getId(), donation4);
        donationMap.put(donation5.getId(), donation5);
        donationMap.put(donation6.getId(), donation6);
        donationMap.put(donation7.getId(), donation7);
        donationMap.put(donation8.getId(), donation8);
        donationMap.put(donation9.getId(), donation9);
        donationMap.put(donation10.getId(), donation10);
        donationMap.put(donation11.getId(), donation11);
        donationMap.put(donation12.getId(), donation12);
        donationMap.put(donation13.getId(), donation13);
        donationMap.put(donation14.getId(), donation14);
        donationMap.put(donation15.getId(), donation15);
        donationMap.put(donation16.getId(), donation16);
        donationMap.put(donation17.getId(), donation17);
        donationMap.put(donation18.getId(), donation18);
        donationMap.put(donation19.getId(), donation19);
        donationMap.put(donation20.getId(), donation20);
        donationMap.put(donation21.getId(), donation21);
        donationMap.put(donation22.getId(), donation22);
        donationMap.put(donation23.getId(), donation23);
        donationMap.put(donation24.getId(), donation24);
        donationMap.put(donation25.getId(), donation25);
        donationMap.put(donation26.getId(), donation26);
        donationMap.put(donation27.getId(), donation27);

        
        
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
