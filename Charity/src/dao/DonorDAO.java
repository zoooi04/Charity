//package dao;
//
//import adt.*;
//import entity.Donor;
//import java.io.*;
//
///**
// *
// * @author Kat Tan
// */
//public class DonorDAO {
//  private String fileName = "donor.dat"; // For security and maintainability, should not have filename hardcoded here.
//  
//  public void saveToFile(ListInterface<Donor> donorList) {
//    File file = new File(fileName);
//    try {
//      ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
//      ooStream.writeObject(donorList);
//      ooStream.close();
//    } catch (FileNotFoundException ex) {
//      System.out.println("\nFile not found");
//    } catch (IOException ex) {
//      System.out.println("\nCannot save to file");
//    }
//  }
//
//  public ListInterface<Donor> retrieveFromFile() {
//    File file = new File(fileName);
//    ListInterface<Donor> donorList = new ArrayList<>();
//    try {
//      ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
//      donorList = (ArrayList<Donor>) (oiStream.readObject());
//      oiStream.close();
//    } catch (FileNotFoundException ex) {
//      System.out.println("\nNo such file.");
//    } catch (IOException ex) {
//      System.out.println("\nCannot read from file.");
//    } catch (ClassNotFoundException ex) {
//      System.out.println("\nClass not found.");
//    } finally {
//      return donorList;
//    }
//  }
//}
