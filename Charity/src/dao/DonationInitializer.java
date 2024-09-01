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

    static Donation donationA = new Donation("DNTA0001", 200, "Good Work, Keep Going!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donationB = new Donation("DNTA0002", 50, "Nice one!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donationC = new Donation("DNTA0003", 2, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00001").getStartDate());

    // Donations for Event E00001
    static Donation donationD = new Donation("DNTA0004", 100, "Happy to contribute!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donationE = new Donation("DNTA0005", 20, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donationF = new Donation("DNTA0006", 5, "Hope this helps!", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00001").getStartDate());

// Donations for Event E00002
    static Donation donationG = new Donation("DNTA0007", 150, "Keep up the great work!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donationH = new Donation("DNTA0008", 30, "Happy to support!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donationI = new Donation("DNTA0009", 10, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00002").getStartDate());

// Donations for Event E00003
    static Donation donationJ = new Donation("DNTA0010", 200, "Great event!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donationK = new Donation("DNTA0011", 40, "Good cause!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donationL = new Donation("DNTA0012", 3, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00003").getStartDate());

// Donations for Event E00004
    static Donation donationM = new Donation("DNTA0013", 75, "Keep going!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donationN = new Donation("DNTA0014", 15, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donationO = new Donation("DNTA0015", 8, "Wonderful effort!", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00004").getStartDate());

// Donations for Event E00005
    static Donation donationP = new Donation("DNTA0016", 250, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donationQ = new Donation("DNTA0017", 60, "Hope it helps!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donationR = new Donation("DNTA0018", 12, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00005").getStartDate());

// Donations for Event E00006
    static Donation donationS = new Donation("DNTA0019", 300, "Proud to support!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donationT = new Donation("DNTA0020", 70, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donationU = new Donation("DNTA0021", 6, "Amazing work!", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00006").getStartDate());

// Donations for Event E00007
    static Donation donationV = new Donation("DNTA0022", 180, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donationW = new Donation("DNTA0023", 25, "Good luck!", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donationX = new Donation("DNTA0024", 7, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00007").getStartDate());

// Donations for Event E00008
    static Donation donationY = new Donation("DNTA0025", 220, "Let's make a difference!", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donationZ = new Donation("DNTA0026", 80, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.FOOD, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donationAA = new Donation("DNTA0027", 4, "Love what you do!", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.ITEM, DonationMaintenance.getEventById("E00008").getStartDate());

    //E00001
    static Donation donation270 = new Donation("DNTA0270", 1000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation271 = new Donation("DNTA0271", 2000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation272 = new Donation("DNTA0272", 3000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation273 = new Donation("DNTA0273", 4000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation274 = new Donation("DNTA0274", 5000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation275 = new Donation("DNTA0275", 6000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation276 = new Donation("DNTA0276", 7000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation277 = new Donation("DNTA0277", 8000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation278 = new Donation("DNTA0278", 9000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation279 = new Donation("DNTA0279", 8000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation280 = new Donation("DNTA0280", 7000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation281 = new Donation("DNTA0281", 6000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation282 = new Donation("DNTA0282", 5000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation283 = new Donation("DNTA0283", 4000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation284 = new Donation("DNTA0284", 3000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation285 = new Donation("DNTA0285", 2000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation286 = new Donation("DNTA0286", 1000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation287 = new Donation("DNTA0287", 500, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation288 = new Donation("DNTA0288", 2000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation289 = new Donation("DNTA0289", 3000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());
    static Donation donation290 = new Donation("DNTA0290", 1000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00001"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00001").getStartDate());

    //E00002
    static Donation donation291 = new Donation("DNTA0291", 9000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation292 = new Donation("DNTA0292", 8000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation293 = new Donation("DNTA0293", 7000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation294 = new Donation("DNTA0294", 6000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation295 = new Donation("DNTA0295", 5000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation296 = new Donation("DNTA0296", 4000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation28 = new Donation("DNTA0028", 3000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation29 = new Donation("DNTA0029", 2000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation30 = new Donation("DNTA0030", 1000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation31 = new Donation("DNTA0031", 2000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation32 = new Donation("DNTA0032", 3000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation33 = new Donation("DNTA0033", 4000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation34 = new Donation("DNTA0034", 5000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation35 = new Donation("DNTA0035", 6000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation36 = new Donation("DNTA0036", 7000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation37 = new Donation("DNTA0037", 8000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation38 = new Donation("DNTA0038", 9000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation39 = new Donation("DNTA0039", 10000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation40 = new Donation("DNTA0040", 9000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation41 = new Donation("DNTA0041", 8000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());
    static Donation donation42 = new Donation("DNTA0042", 7000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00002"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00002").getStartDate());

    //E00003
    static Donation donation43 = new Donation("DNTA0043", 3000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation44 = new Donation("DNTA0044", 4000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation45 = new Donation("DNTA0045", 5000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation46 = new Donation("DNTA0046", 6000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation47 = new Donation("DNTA0047", 7000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation48 = new Donation("DNTA0048", 8000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation49 = new Donation("DNTA0049", 9000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation50 = new Donation("DNTA0050", 10000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation51 = new Donation("DNTA0051", 2000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation52 = new Donation("DNTA0052", 1000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation53 = new Donation("DNTA0053", 2000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation54 = new Donation("DNTA0054", 3000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation55 = new Donation("DNTA0055", 4000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation56 = new Donation("DNTA0056", 5000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation57 = new Donation("DNTA0057", 6000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation58 = new Donation("DNTA0058", 7000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation59 = new Donation("DNTA0059", 8000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation60 = new Donation("DNTA0060", 9000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation61 = new Donation("DNTA0061", 10000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation62 = new Donation("DNTA0062", 9000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());
    static Donation donation63 = new Donation("DNTA0063", 8000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00003"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00003").getStartDate());

    //E00004
    static Donation donation64 = new Donation("DNTA0064", 6000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation65 = new Donation("DNTA0065", 7000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation66 = new Donation("DNTA0066", 8000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation67 = new Donation("DNTA0067", 9000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation68 = new Donation("DNTA0068", 10000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation69 = new Donation("DNTA0069", 9000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation70 = new Donation("DNTA0070", 8000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation71 = new Donation("DNTA0071", 7000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation72 = new Donation("DNTA0072", 6000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation73 = new Donation("DNTA0073", 5000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation74 = new Donation("DNTA0074", 4000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation75 = new Donation("DNTA0075", 3000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation76 = new Donation("DNTA0076", 2000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation77 = new Donation("DNTA0077", 1000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation78 = new Donation("DNTA0078", 2000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation79 = new Donation("DNTA0079", 3000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation80 = new Donation("DNTA0080", 4000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation81 = new Donation("DNTA0081", 5000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation82 = new Donation("DNTA0082", 6000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation83 = new Donation("DNTA0083", 7000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());
    static Donation donation84 = new Donation("DNTA0084", 8000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00004"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00004").getStartDate());

// Event E00005
    static Donation donation85 = new Donation("DNTA0085", 20000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation86 = new Donation("DNTA0086", 8000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation87 = new Donation("DNTA0087", 7000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation88 = new Donation("DNTA0088", 6000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation89 = new Donation("DNTA0089", 5000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation90 = new Donation("DNTA0090", 4000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation91 = new Donation("DNTA0091", 3000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation92 = new Donation("DNTA0092", 2000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation93 = new Donation("DNTA0093", 1000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation94 = new Donation("DNTA0094", 2000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation95 = new Donation("DNTA0095", 3000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation96 = new Donation("DNTA0096", 4000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation97 = new Donation("DNTA0097", 5000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation98 = new Donation("DNTA0098", 6000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation99 = new Donation("DNTA0099", 7000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation100 = new Donation("DNTA0100", 8000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation101 = new Donation("DNTA0101", 9000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation102 = new Donation("DNTA0102", 8000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation103 = new Donation("DNTA0103", 7000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation104 = new Donation("DNTA0104", 6000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());
    static Donation donation105 = new Donation("DNTA0105", 5000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00005"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00005").getStartDate());

// Event E00006
    static Donation donation106 = new Donation("DNTA00106", 1000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation107 = new Donation("DNTA00107", 2000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation108 = new Donation("DNTA00108", 3000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation109 = new Donation("DNTA00109", 4000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation110 = new Donation("DNTA00110", 5000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation111 = new Donation("DNTA00111", 6000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation112 = new Donation("DNTA00112", 7000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation113 = new Donation("DNTA00113", 8000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation114 = new Donation("DNTA00114", 9000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation115 = new Donation("DNTA00115", 9000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation116 = new Donation("DNTA00116", 8000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation117 = new Donation("DNTA00117", 7000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation118 = new Donation("DNTA00118", 6000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation119 = new Donation("DNTA00119", 5000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation120 = new Donation("DNTA00120", 4000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation121 = new Donation("DNTA00121", 3000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation122 = new Donation("DNTA00122", 2000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());
    static Donation donation123 = new Donation("DNTA00123", 1000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00006"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00006").getStartDate());

// Event E00007
    static Donation donation124 = new Donation("DNTA0124", 5000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation125 = new Donation("DNTA0125", 4000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation126 = new Donation("DNTA0126", 3000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation127 = new Donation("DNTA0127", 2000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation128 = new Donation("DNTA0128", 1000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation129 = new Donation("DNTA0129", 2000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation130 = new Donation("DNTA0130", 3000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation131 = new Donation("DNTA0131", 4000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation132 = new Donation("DNTA0132", 5000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation133 = new Donation("DNTA0133", 6000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation134 = new Donation("DNTA0134", 7000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation135 = new Donation("DNTA0135", 8000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation136 = new Donation("DNTA0136", 9000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation137 = new Donation("DNTA0137", 10000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation138 = new Donation("DNTA0138", 9000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation139 = new Donation("DNTA0139", 8000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation140 = new Donation("DNTA0140", 7000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation141 = new Donation("DNTA0141", 6000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation142 = new Donation("DNTA0142", 5000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation143 = new Donation("DNTA0143", 4000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());
    static Donation donation144 = new Donation("DNTA0144", 3000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00007"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00007").getStartDate());

// Event E00008
    static Donation donation145 = new Donation("DNTA0145", 2000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation146 = new Donation("DNTA0146", 1000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation147 = new Donation("DNTA0147", 2000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation148 = new Donation("DNTA0148", 3000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation149 = new Donation("DNTA0149", 4000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation150 = new Donation("DNTA0150", 5000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation151 = new Donation("DNTA0151", 6000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation152 = new Donation("DNTA0152", 7000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation153 = new Donation("DNTA0153", 8000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation154 = new Donation("DNTA0154", 9000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation155 = new Donation("DNTA0155", 10000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation156 = new Donation("DNTA0156", 9000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation157 = new Donation("DNTA0157", 8000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation158 = new Donation("DNTA0158", 7000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation159 = new Donation("DNTA0159", 6000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation160 = new Donation("DNTA0160", 5000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation161 = new Donation("DNTA0161", 4000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation162 = new Donation("DNTA0162", 3000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation163 = new Donation("DNTA0163", 2000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());
    static Donation donation164 = new Donation("DNTA0164", 1000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00008"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00008").getStartDate());

// Event E00009
    static Donation donation165 = new Donation("DNTA0165", 1000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation166 = new Donation("DNTA0166", 2000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation167 = new Donation("DNTA0167", 3000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation168 = new Donation("DNTA0168", 4000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation169 = new Donation("DNTA0169", 5000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation170 = new Donation("DNTA0170", 6000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation171 = new Donation("DNTA0171", 7000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation172 = new Donation("DNTA0172", 8000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation173 = new Donation("DNTA0173", 9000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation174 = new Donation("DNTA0174", 10000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation175 = new Donation("DNTA0175", 11000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation176 = new Donation("DNTA0176", 12000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation177 = new Donation("DNTA0177", 13000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation178 = new Donation("DNTA0178", 14000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation179 = new Donation("DNTA0179", 15000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation180 = new Donation("DNTA0180", 16000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation181 = new Donation("DNTA0181", 17000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation182 = new Donation("DNTA0182", 18000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation183 = new Donation("DNTA0183", 19000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation184 = new Donation("DNTA0184", 20000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());
    static Donation donation185 = new Donation("DNTA0185", 21000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00009"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00009").getStartDate());

// Event E00010
    static Donation donation186 = new Donation("DNTA0186", 1000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation187 = new Donation("DNTA0187", 2000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation188 = new Donation("DNTA0188", 3000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation189 = new Donation("DNTA0189", 4000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation190 = new Donation("DNTA0190", 5000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation191 = new Donation("DNTA0191", 6000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation192 = new Donation("DNTA0192", 7000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation193 = new Donation("DNTA0193", 8000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation194 = new Donation("DNTA0194", 9000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation195 = new Donation("DNTA0195", 10000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation196 = new Donation("DNTA0196", 11000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation197 = new Donation("DNTA0197", 12000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation198 = new Donation("DNTA0198", 13000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation199 = new Donation("DNTA0199", 14000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation200 = new Donation("DNTA0200", 15000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation201 = new Donation("DNTA0201", 16000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation202 = new Donation("DNTA0202", 17000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation203 = new Donation("DNTA0203", 18000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation204 = new Donation("DNTA0204", 19000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation205 = new Donation("DNTA0205", 20000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());
    static Donation donation206 = new Donation("DNTA0206", 21000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00010"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00010").getStartDate());

// Event E00011
    static Donation donation207 = new Donation("DNTA0207", 1000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation208 = new Donation("DNTA0208", 2000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation209 = new Donation("DNTA0209", 3000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation210 = new Donation("DNTA0210", 4000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation211 = new Donation("DNTA0211", 5000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation212 = new Donation("DNTA0212", 6000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation213 = new Donation("DNTA0213", 7000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation214 = new Donation("DNTA0214", 8000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation215 = new Donation("DNTA0215", 9000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation216 = new Donation("DNTA0216", 10000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation217 = new Donation("DNTA0217", 11000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation218 = new Donation("DNTA0218", 12000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation219 = new Donation("DNTA0219", 13000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation220 = new Donation("DNTA0220", 14000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation221 = new Donation("DNTA0221", 15000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation222 = new Donation("DNTA0222", 16000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation223 = new Donation("DNTA0223", 17000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation224 = new Donation("DNTA0224", 18000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation225 = new Donation("DNTA0225", 19000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation226 = new Donation("DNTA0226", 20000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());
    static Donation donation227 = new Donation("DNTA0227", 21000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00011"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00011").getStartDate());

// Event E00012
    static Donation donation228 = new Donation("DNTA0228", 1000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation229 = new Donation("DNTA0229", 2000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation230 = new Donation("DNTA0230", 3000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation231 = new Donation("DNTA0231", 4000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation232 = new Donation("DNTA0232", 5000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation233 = new Donation("DNTA0233", 6000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation234 = new Donation("DNTA0234", 7000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation235 = new Donation("DNTA0235", 8000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation236 = new Donation("DNTA0236", 9000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation237 = new Donation("DNTA0237", 10000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation238 = new Donation("DNTA0238", 11000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation239 = new Donation("DNTA0239", 12000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation240 = new Donation("DNTA0240", 13000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation241 = new Donation("DNTA0241", 14000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation242 = new Donation("DNTA0242", 15000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation243 = new Donation("DNTA0243", 16000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation244 = new Donation("DNTA0244", 17000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation245 = new Donation("DNTA0245", 18000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation246 = new Donation("DNTA0246", 19000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation247 = new Donation("DNTA0247", 20000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());
    static Donation donation248 = new Donation("DNTA0248", 21000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00012"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00012").getStartDate());

// Event E00013
    static Donation donation249 = new Donation("DNTA0249", 1000, "", DonationMaintenance.getDonorById("AA2400001"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation250 = new Donation("DNTA0250", 2000, "", DonationMaintenance.getDonorById("AA2400002"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation251 = new Donation("DNTA0251", 3000, "", DonationMaintenance.getDonorById("AA2400003"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation252 = new Donation("DNTA0252", 4000, "", DonationMaintenance.getDonorById("AA2400004"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation253 = new Donation("DNTA0253", 5000, "", DonationMaintenance.getDonorById("AA2400005"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation254 = new Donation("DNTA0254", 6000, "", DonationMaintenance.getDonorById("AA2400006"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation255 = new Donation("DNTA0255", 7000, "", DonationMaintenance.getDonorById("AA2400007"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation256 = new Donation("DNTA0256", 8000, "", DonationMaintenance.getDonorById("AA2400008"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation257 = new Donation("DNTA0257", 9000, "", DonationMaintenance.getDonorById("AA2400009"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation258 = new Donation("DNTA0258", 10000, "", DonationMaintenance.getDonorById("AA2400010"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation259 = new Donation("DNTA0259", 11000, "", DonationMaintenance.getDonorById("AA2400011"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation260 = new Donation("DNTA0260", 12000, "", DonationMaintenance.getDonorById("AA2400012"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation261 = new Donation("DNTA0261", 13000, "", DonationMaintenance.getDonorById("AA2400013"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation262 = new Donation("DNTA0262", 14000, "", DonationMaintenance.getDonorById("AA2400014"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation263 = new Donation("DNTA0263", 15000, "", DonationMaintenance.getDonorById("AA2400015"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation264 = new Donation("DNTA0264", 16000, "", DonationMaintenance.getDonorById("AA2400016"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation265 = new Donation("DNTA0265", 17000, "", DonationMaintenance.getDonorById("AA2400017"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation266 = new Donation("DNTA0266", 18000, "", DonationMaintenance.getDonorById("AA2400018"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation267 = new Donation("DNTA0267", 19000, "", DonationMaintenance.getDonorById("AA2400019"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation268 = new Donation("DNTA0268", 20000, "", DonationMaintenance.getDonorById("AA2400020"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());
    static Donation donation269 = new Donation("DNTA0269", 21000, "", DonationMaintenance.getDonorById("AA2400021"), DonationMaintenance.getEventById("E00013"), Donation.DonationType.CASH, DonationMaintenance.getEventById("E00013").getStartDate());

//  Method to return a collection of with hard-coded entity values
    public static MapInterface<String, Donation> initializeDonation() {
        DAO<MapInterface<String, Donation>> dao = new DAO<>();

        MapInterface<String, Donation> donationMap = new HashMap<>();

        donationMap.put(donationA.getId(), donationA);
        donationMap.put(donationB.getId(), donationB);
        donationMap.put(donationC.getId(), donationC);
        donationMap.put(donationD.getId(), donationD);
        donationMap.put(donationE.getId(), donationE);
        donationMap.put(donationF.getId(), donationF);
        donationMap.put(donationG.getId(), donationG);
        donationMap.put(donationH.getId(), donationH);
        donationMap.put(donationI.getId(), donationI);
        donationMap.put(donationJ.getId(), donationJ);
        donationMap.put(donationK.getId(), donationK);
        donationMap.put(donationL.getId(), donationL);
        donationMap.put(donationM.getId(), donationM);
        donationMap.put(donationN.getId(), donationN);
        donationMap.put(donationO.getId(), donationO);
        donationMap.put(donationP.getId(), donationP);
        donationMap.put(donationQ.getId(), donationQ);
        donationMap.put(donationR.getId(), donationR);
        donationMap.put(donationS.getId(), donationS);
        donationMap.put(donationT.getId(), donationT);
        donationMap.put(donationU.getId(), donationU);
        donationMap.put(donationV.getId(), donationV);
        donationMap.put(donationW.getId(), donationW);
        donationMap.put(donationX.getId(), donationX);
        donationMap.put(donationY.getId(), donationY);
        donationMap.put(donationZ.getId(), donationZ);
        donationMap.put(donationAA.getId(), donationAA);

        donationMap.put(donation270.getId(), donation270);
        donationMap.put(donation271.getId(), donation271);
        donationMap.put(donation272.getId(), donation272);
        donationMap.put(donation273.getId(), donation273);
        donationMap.put(donation274.getId(), donation274);
        donationMap.put(donation275.getId(), donation275);
        donationMap.put(donation276.getId(), donation276);
        donationMap.put(donation277.getId(), donation277);
        donationMap.put(donation278.getId(), donation278);
        donationMap.put(donation279.getId(), donation279);
        donationMap.put(donation280.getId(), donation280);
        donationMap.put(donation281.getId(), donation281);
        donationMap.put(donation282.getId(), donation282);
        donationMap.put(donation283.getId(), donation283);
        donationMap.put(donation284.getId(), donation284);
        donationMap.put(donation285.getId(), donation285);
        donationMap.put(donation286.getId(), donation286);
        donationMap.put(donation287.getId(), donation287);
        donationMap.put(donation288.getId(), donation288);
        donationMap.put(donation289.getId(), donation289);
        donationMap.put(donation290.getId(), donation290);
        donationMap.put(donation291.getId(), donation291);
        donationMap.put(donation292.getId(), donation292);
        donationMap.put(donation293.getId(), donation293);
        donationMap.put(donation294.getId(), donation294);
        donationMap.put(donation295.getId(), donation295);
        donationMap.put(donation296.getId(), donation296);

        donationMap.put(donation28.getId(), donation28);
        donationMap.put(donation29.getId(), donation29);
        donationMap.put(donation30.getId(), donation30);
        donationMap.put(donation31.getId(), donation31);
        donationMap.put(donation32.getId(), donation32);
        donationMap.put(donation33.getId(), donation33);
        donationMap.put(donation34.getId(), donation34);
        donationMap.put(donation35.getId(), donation35);
        donationMap.put(donation36.getId(), donation36);
        donationMap.put(donation37.getId(), donation37);
        donationMap.put(donation38.getId(), donation38);
        donationMap.put(donation39.getId(), donation39);
        donationMap.put(donation40.getId(), donation40);
        donationMap.put(donation41.getId(), donation41);
        donationMap.put(donation42.getId(), donation42);
        donationMap.put(donation43.getId(), donation43);
        donationMap.put(donation44.getId(), donation44);
        donationMap.put(donation45.getId(), donation45);
        donationMap.put(donation46.getId(), donation46);
        donationMap.put(donation47.getId(), donation47);
        donationMap.put(donation48.getId(), donation48);
        donationMap.put(donation49.getId(), donation49);
        donationMap.put(donation50.getId(), donation50);
        donationMap.put(donation51.getId(), donation51);
        donationMap.put(donation52.getId(), donation52);
        donationMap.put(donation53.getId(), donation53);
        donationMap.put(donation54.getId(), donation54);
        donationMap.put(donation55.getId(), donation55);
        donationMap.put(donation56.getId(), donation56);
        donationMap.put(donation57.getId(), donation57);
        donationMap.put(donation58.getId(), donation58);
        donationMap.put(donation59.getId(), donation59);
        donationMap.put(donation60.getId(), donation60);
        donationMap.put(donation61.getId(), donation61);
        donationMap.put(donation62.getId(), donation62);
        donationMap.put(donation63.getId(), donation63);
        donationMap.put(donation64.getId(), donation64);
        donationMap.put(donation65.getId(), donation65);
        donationMap.put(donation66.getId(), donation66);
        donationMap.put(donation67.getId(), donation67);
        donationMap.put(donation68.getId(), donation68);
        donationMap.put(donation69.getId(), donation69);
        donationMap.put(donation70.getId(), donation70);
        donationMap.put(donation71.getId(), donation71);
        donationMap.put(donation72.getId(), donation72);
        donationMap.put(donation73.getId(), donation73);
        donationMap.put(donation74.getId(), donation74);
        donationMap.put(donation75.getId(), donation75);
        donationMap.put(donation76.getId(), donation76);
        donationMap.put(donation77.getId(), donation77);
        donationMap.put(donation78.getId(), donation78);
        donationMap.put(donation79.getId(), donation79);
        donationMap.put(donation80.getId(), donation80);
        donationMap.put(donation81.getId(), donation81);
        donationMap.put(donation82.getId(), donation82);
        donationMap.put(donation83.getId(), donation83);
        donationMap.put(donation84.getId(), donation84);
        donationMap.put(donation85.getId(), donation85);
        donationMap.put(donation86.getId(), donation86);
        donationMap.put(donation87.getId(), donation87);
        donationMap.put(donation88.getId(), donation88);
        donationMap.put(donation89.getId(), donation89);
        donationMap.put(donation90.getId(), donation90);
        donationMap.put(donation91.getId(), donation91);
        donationMap.put(donation92.getId(), donation92);
        donationMap.put(donation93.getId(), donation93);
        donationMap.put(donation94.getId(), donation94);
        donationMap.put(donation95.getId(), donation95);
        donationMap.put(donation96.getId(), donation96);
        donationMap.put(donation97.getId(), donation97);
        donationMap.put(donation98.getId(), donation98);
        donationMap.put(donation99.getId(), donation99);
        donationMap.put(donation100.getId(), donation100);
        donationMap.put(donation101.getId(), donation101);
        donationMap.put(donation102.getId(), donation102);
        donationMap.put(donation103.getId(), donation103);
        donationMap.put(donation104.getId(), donation104);
        donationMap.put(donation105.getId(), donation105);
        donationMap.put(donation106.getId(), donation106);
        donationMap.put(donation107.getId(), donation107);
        donationMap.put(donation108.getId(), donation108);
        donationMap.put(donation109.getId(), donation109);
        donationMap.put(donation110.getId(), donation110);
        donationMap.put(donation111.getId(), donation111);
        donationMap.put(donation112.getId(), donation112);
        donationMap.put(donation113.getId(), donation113);
        donationMap.put(donation114.getId(), donation114);
        donationMap.put(donation115.getId(), donation115);
        donationMap.put(donation116.getId(), donation116);
        donationMap.put(donation117.getId(), donation117);
        donationMap.put(donation118.getId(), donation118);
        donationMap.put(donation119.getId(), donation119);
        donationMap.put(donation120.getId(), donation120);
        donationMap.put(donation121.getId(), donation121);
        donationMap.put(donation122.getId(), donation122);
        donationMap.put(donation123.getId(), donation123);
        donationMap.put(donation124.getId(), donation124);
        donationMap.put(donation125.getId(), donation125);
        donationMap.put(donation126.getId(), donation126);
        donationMap.put(donation127.getId(), donation127);
        donationMap.put(donation128.getId(), donation128);
        donationMap.put(donation129.getId(), donation129);
        donationMap.put(donation130.getId(), donation130);
        donationMap.put(donation131.getId(), donation131);
        donationMap.put(donation132.getId(), donation132);
        donationMap.put(donation133.getId(), donation133);
        donationMap.put(donation134.getId(), donation134);
        donationMap.put(donation135.getId(), donation135);
        donationMap.put(donation136.getId(), donation136);
        donationMap.put(donation137.getId(), donation137);
        donationMap.put(donation138.getId(), donation138);
        donationMap.put(donation139.getId(), donation139);
        donationMap.put(donation140.getId(), donation140);
        donationMap.put(donation141.getId(), donation141);
        donationMap.put(donation142.getId(), donation142);
        donationMap.put(donation143.getId(), donation143);
        donationMap.put(donation144.getId(), donation144);
        donationMap.put(donation145.getId(), donation145);
        donationMap.put(donation146.getId(), donation146);
        donationMap.put(donation147.getId(), donation147);
        donationMap.put(donation148.getId(), donation148);
        donationMap.put(donation149.getId(), donation149);
        donationMap.put(donation150.getId(), donation150);
        donationMap.put(donation151.getId(), donation151);
        donationMap.put(donation152.getId(), donation152);
        donationMap.put(donation153.getId(), donation153);
        donationMap.put(donation154.getId(), donation154);
        donationMap.put(donation155.getId(), donation155);
        donationMap.put(donation156.getId(), donation156);
        donationMap.put(donation157.getId(), donation157);
        donationMap.put(donation158.getId(), donation158);
        donationMap.put(donation159.getId(), donation159);
        donationMap.put(donation160.getId(), donation160);
        donationMap.put(donation161.getId(), donation161);
        donationMap.put(donation162.getId(), donation162);
        donationMap.put(donation163.getId(), donation163);
        donationMap.put(donation164.getId(), donation164);
        donationMap.put(donation165.getId(), donation165);
        donationMap.put(donation166.getId(), donation166);
        donationMap.put(donation167.getId(), donation167);
        donationMap.put(donation168.getId(), donation168);
        donationMap.put(donation169.getId(), donation169);
        donationMap.put(donation170.getId(), donation170);
        donationMap.put(donation171.getId(), donation171);
        donationMap.put(donation172.getId(), donation172);
        donationMap.put(donation173.getId(), donation173);
        donationMap.put(donation174.getId(), donation174);
        donationMap.put(donation175.getId(), donation175);
        donationMap.put(donation176.getId(), donation176);
        donationMap.put(donation177.getId(), donation177);
        donationMap.put(donation178.getId(), donation178);
        donationMap.put(donation179.getId(), donation179);
        donationMap.put(donation180.getId(), donation180);
        donationMap.put(donation181.getId(), donation181);
        donationMap.put(donation182.getId(), donation182);
        donationMap.put(donation183.getId(), donation183);
        donationMap.put(donation184.getId(), donation184);
        donationMap.put(donation185.getId(), donation185);
        donationMap.put(donation186.getId(), donation186);
        donationMap.put(donation187.getId(), donation187);
        donationMap.put(donation188.getId(), donation188);
        donationMap.put(donation189.getId(), donation189);
        donationMap.put(donation190.getId(), donation190);
        donationMap.put(donation191.getId(), donation191);
        donationMap.put(donation192.getId(), donation192);
        donationMap.put(donation193.getId(), donation193);
        donationMap.put(donation194.getId(), donation194);
        donationMap.put(donation195.getId(), donation195);
        donationMap.put(donation196.getId(), donation196);
        donationMap.put(donation197.getId(), donation197);
        donationMap.put(donation198.getId(), donation198);
        donationMap.put(donation199.getId(), donation199);
        donationMap.put(donation200.getId(), donation200);
        donationMap.put(donation201.getId(), donation201);
        donationMap.put(donation202.getId(), donation202);
        donationMap.put(donation203.getId(), donation203);
        donationMap.put(donation204.getId(), donation204);
        donationMap.put(donation205.getId(), donation205);
        donationMap.put(donation206.getId(), donation206);
        donationMap.put(donation207.getId(), donation207);
        donationMap.put(donation208.getId(), donation208);
        donationMap.put(donation209.getId(), donation209);
        donationMap.put(donation210.getId(), donation210);
        donationMap.put(donation211.getId(), donation211);
        donationMap.put(donation212.getId(), donation212);
        donationMap.put(donation213.getId(), donation213);
        donationMap.put(donation214.getId(), donation214);
        donationMap.put(donation215.getId(), donation215);
        donationMap.put(donation216.getId(), donation216);
        donationMap.put(donation217.getId(), donation217);
        donationMap.put(donation218.getId(), donation218);
        donationMap.put(donation219.getId(), donation219);
        donationMap.put(donation220.getId(), donation220);
        donationMap.put(donation221.getId(), donation221);
        donationMap.put(donation222.getId(), donation222);
        donationMap.put(donation223.getId(), donation223);
        donationMap.put(donation224.getId(), donation224);
        donationMap.put(donation225.getId(), donation225);
        donationMap.put(donation226.getId(), donation226);
        donationMap.put(donation227.getId(), donation227);
        donationMap.put(donation228.getId(), donation228);
        donationMap.put(donation229.getId(), donation229);
        donationMap.put(donation230.getId(), donation230);
        donationMap.put(donation231.getId(), donation231);
        donationMap.put(donation232.getId(), donation232);
        donationMap.put(donation233.getId(), donation233);
        donationMap.put(donation234.getId(), donation234);
        donationMap.put(donation235.getId(), donation235);
        donationMap.put(donation236.getId(), donation236);
        donationMap.put(donation237.getId(), donation237);
        donationMap.put(donation238.getId(), donation238);
        donationMap.put(donation239.getId(), donation239);
        donationMap.put(donation240.getId(), donation240);
        donationMap.put(donation241.getId(), donation241);
        donationMap.put(donation242.getId(), donation242);
        donationMap.put(donation243.getId(), donation243);
        donationMap.put(donation244.getId(), donation244);
        donationMap.put(donation245.getId(), donation245);
        donationMap.put(donation246.getId(), donation246);
        donationMap.put(donation247.getId(), donation247);
        donationMap.put(donation248.getId(), donation248);
        donationMap.put(donation249.getId(), donation249);
        donationMap.put(donation250.getId(), donation250);
        donationMap.put(donation251.getId(), donation251);
        donationMap.put(donation252.getId(), donation252);
        donationMap.put(donation253.getId(), donation253);
        donationMap.put(donation254.getId(), donation254);
        donationMap.put(donation255.getId(), donation255);
        donationMap.put(donation256.getId(), donation256);
        donationMap.put(donation257.getId(), donation257);
        donationMap.put(donation258.getId(), donation258);
        donationMap.put(donation259.getId(), donation259);
        donationMap.put(donation260.getId(), donation260);
        donationMap.put(donation261.getId(), donation261);
        donationMap.put(donation262.getId(), donation262);
        donationMap.put(donation263.getId(), donation263);
        donationMap.put(donation264.getId(), donation264);
        donationMap.put(donation265.getId(), donation265);
        donationMap.put(donation266.getId(), donation266);
        donationMap.put(donation267.getId(), donation267);
        donationMap.put(donation268.getId(), donation268);
        donationMap.put(donation269.getId(), donation269);

        dao.saveToFile(donationMap, DonationMaintenance.getFileName());
        return donationMap;
    }

    public static void main(String[] args) {
        // To illustrate how to use the initializeProducts() method
        MapInterface<String, Donation> donationMap = DonationInitializer.initializeDonation();
        ListInterface<Donation> list = donationMap.values();
        MapInterface<String,Double> map = new HashMap<>();
        for (int i = 0; i < list.getNumberOfEntries(); i++) {
            Donation donation = list.getEntry(i + 1);
            if (donation.getType() == Donation.DonationType.CASH) {
                String eventId = donation.getEvent().getId();
                if (map.get(eventId) == null) {
                    // If the event ID is not in the map, add it with the current donation's quantity
                    map.put(eventId, (double) donation.getQuantity());
                } else {
                    // If the event ID is already in the map, update the quantity
                    double num = map.get(eventId);
                    num += donation.getQuantity();
                    map.put(eventId, num); // Put the updated value back into the map
                }
            }
        }
        
        ListInterface<String> ids = map.keySet();
        
        for(int i = 0; i < ids.getNumberOfEntries(); i++){
            System.out.println(DonationMaintenance.getEventById(ids.getEntry(i+1)).getName()+ ":  "+ids.getEntry(i+1) + "-----" + map.get(ids.getEntry(i+1)));
        }
        
        System.out.println("\nDonation:\n" + donationMap);
        String id = "DNTA0001";
    }
}
