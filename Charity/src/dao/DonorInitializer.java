package dao;

import adt.*;
import entity.Donor;
import java.time.LocalDate;

/**
 *
 * @author Ooi Choon Chong
 */
public class DonorInitializer {


//  Method to return a collection of with hard-coded entity values
    public static ListInterface<Donor> initializeDonor() {
        ListInterface<Donor> donorList = new ArrayList<>();
        Donor donor = null;
        
        System.out.println("Initializing... Please Wait");
        
        donor = new Donor("AA2400001", Donor.Type.INDIVIDUAL, Donor.Category.GOVERNMENT, "Ooi Choon Chong", 18, LocalDate.of(2006, 01, 01), Donor.Gender.MALE, "0123456789");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400002", Donor.Type.ORGANISATION, Donor.Category.PRIVATE, "Andrew Pheng", 19, LocalDate.of(2005, 02, 02), Donor.Gender.FEMALE, "0123456780");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400003", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "Beh Jing Hen", 20, LocalDate.of(2004, 03, 03), Donor.Gender.MALE, "0123456781");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400004", Donor.Type.ORGANISATION, Donor.Category.GOVERNMENT, "Tan Jian Hui", 67, LocalDate.of(1957, 06, 20), Donor.Gender.OTHER, "0189997777");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400005", Donor.Type.INDIVIDUAL, Donor.Category.PRIVATE, "John Lee", 30, LocalDate.of(1994, 4, 25), Donor.Gender.MALE, "0129876543");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400006", Donor.Type.INDIVIDUAL, Donor.Category.GOVERNMENT, "Edward Tan", 55, LocalDate.of(1969, 9, 16), Donor.Gender.OTHER, "0121234567");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400007", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "Ava", 85, LocalDate.of(1939, 8, 19), Donor.Gender.FEMALE, "0121234567");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400008", Donor.Type.ORGANISATION, Donor.Category.PUBLIC, "Scarlet", 38, LocalDate.of(1986, 3, 21), Donor.Gender.FEMALE, "0128765432");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400009", Donor.Type.ORGANISATION, Donor.Category.GOVERNMENT, "Jolin Ooi", 29, LocalDate.of(1995, 7, 28), Donor.Gender.FEMALE, "0126781234");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400010", Donor.Type.INDIVIDUAL, Donor.Category.PRIVATE, "John Lee", 19, LocalDate.of(2005, 10, 2), Donor.Gender.MALE, "0125678901");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400011", Donor.Type.INDIVIDUAL, Donor.Category.PRIVATE, "Luke", 27, LocalDate.of(1997, 3, 5), Donor.Gender.MALE, "0124567890");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400012", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "John Lim", 66, LocalDate.of(1958, 12, 9), Donor.Gender.OTHER, "0129871234");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400013", Donor.Type.ORGANISATION, Donor.Category.PRIVATE, "Luke", 78, LocalDate.of(1946, 2, 19), Donor.Gender.MALE, "0123456780");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400014", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "Mary", 53, LocalDate.of(1971, 11, 3), Donor.Gender.FEMALE, "0187654321");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400015", Donor.Type.INDIVIDUAL, Donor.Category.GOVERNMENT, "Haris", 47, LocalDate.of(1977, 9, 15), Donor.Gender.MALE, "0188765432");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400016", Donor.Type.INDIVIDUAL, Donor.Category.PRIVATE, "Kyle Lim", 90, LocalDate.of(1934, 4, 22), Donor.Gender.FEMALE, "0184321987");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400017", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "Mary Anne", 26, LocalDate.of(1998, 5, 28), Donor.Gender.FEMALE, "0186789012");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400018", Donor.Type.ORGANISATION, Donor.Category.PRIVATE, "Goh Ze", 32, LocalDate.of(1992, 7, 27), Donor.Gender.MALE, "0185432109");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400019", Donor.Type.ORGANISATION, Donor.Category.PUBLIC, "Kyle Tan", 48, LocalDate.of(1976, 1, 24), Donor.Gender.FEMALE, "0187896543");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400020", Donor.Type.INDIVIDUAL, Donor.Category.GOVERNMENT, "Lai Hee", 37, LocalDate.of(1987, 4, 16), Donor.Gender.OTHER, "0186543219");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        
        donor = new Donor("AA2400021", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "Kyle Chin", 34, LocalDate.of(1990, 9, 30), Donor.Gender.FEMALE, "0187890123");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        donor = new Donor("AA2400022", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "Mei Lin", 34, LocalDate.of(1990, 9, 30), Donor.Gender.FEMALE, "0188394857");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        donor = new Donor("AA2400023", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "Henry", 34, LocalDate.of(1990, 9, 30), Donor.Gender.MALE, "0189403910");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        donor = new Donor("AA2400024", Donor.Type.INDIVIDUAL, Donor.Category.PUBLIC, "William", 34, LocalDate.of(1990, 9, 30), Donor.Gender.MALE, "0180983909");
        donorList.add(donor);
        try{Thread.sleep(1100);}catch(InterruptedException e){};
        DAO<ListInterface<Donor>> dao = new DAO<>();
        dao.saveToFile(donorList, "donor.dat");
        
        System.out.println("Done Initialize!");
        return donorList;
    }

    public static void main(String[] args) {
        // To illustrate how to use the initializeProducts() method
        ListInterface<Donor> donorList = DonorInitializer.initializeDonor();
        System.out.println("\nDonor:\n" + donorList);
    }

}