package dao;

import adt.*;
import entity.Donor;
import java.time.LocalDate;

/**
 *
 * @author Ooi Choon Chong
 */

public class DonorInitializer {
    
    Donor donor1 = new Donor("AA2400001", Donor.Type.INDIVIDUAL, Donor.Category.GOVERNMENT, "Name 1", 18, LocalDate.of(2020,01,01), Donor.Gender.MALE, "0123456789");
    Donor donor2 = new Donor("AA2400002", Donor.Type.ORGANISATION, Donor.Category.PRIVATE, "Name 2", 19, LocalDate.of(2020,01,02), Donor.Gender.MALE, "0123456780");
    Donor donor3 = new Donor("AA2400003", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "Name 3", 20, LocalDate.of(2020,01,03), Donor.Gender.MALE, "0123456781");
    
//  Method to return a collection of with hard-coded entity values
  public ListInterface<Donor> initializeProducts() {
    ListInterface<Donor> donorList = new ArrayList<>();
    donorList.add(donor1);
    donorList.add(donor2);
    donorList.add(donor3);
    return donorList;
  }

  public static void main(String[] args) {
    // To illustrate how to use the initializeProducts() method
    DonorInitializer p = new DonorInitializer();
    ListInterface<Donor> donorList = p.initializeProducts();
    System.out.println("\nDonor:\n" + donorList);
  }

}
