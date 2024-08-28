package dao;

import adt.*;
import entity.Donor;
import java.time.LocalDate;

/**
 *
 * @author Ooi Choon Chong
 */
public class DonorInitializer {

    static Donor donor1 = new Donor("AA2400001", Donor.Type.INDIVIDUAL, Donor.Category.GOVERNMENT, "Ooi Choon Chong", 18, LocalDate.of(2006, 01, 01), Donor.Gender.MALE, "0123456789");
    static Donor donor2 = new Donor("AA2400002", Donor.Type.ORGANISATION, Donor.Category.PRIVATE, "Andrew Pheng", 19, LocalDate.of(2005, 02, 02), Donor.Gender.FEMALE, "0123456780");
    static Donor donor3 = new Donor("AA2400003", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "Beh Jing Hen", 20, LocalDate.of(2004, 03, 03), Donor.Gender.MALE, "0123456781");

//  Method to return a collection of with hard-coded entity values
    public static ListInterface<Donor> initializeDonor() {
        ListInterface<Donor> donorList = new ArrayList<>();
        donorList.add(donor1);
        donorList.add(donor2);
        donorList.add(donor3);
        return donorList;
    }

    public static void main(String[] args) {
        // To illustrate how to use the initializeProducts() method
        DonorInitializer p = new DonorInitializer();
        ListInterface<Donor> donorList = p.initializeDonor();
        System.out.println("\nDonor:\n" + donorList);
    }

}
