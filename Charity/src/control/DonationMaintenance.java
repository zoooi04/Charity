/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import entity.Donation;
import java.util.Scanner;
import boundary.DonationMaintenanceUI;
import utility.MessageUI;
import adt.MapInterface;
import adt.HashMap;
import adt.ListInterface;
import dao.DAO;
import entity.Donor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
/**
 *
 * @author huaiern
 */
public class DonationMaintenance{
    private final DonationMaintenanceUI donationUI = new DonationMaintenanceUI();
    private MapInterface<String,Donation> map;
    private final DAO<MapInterface<String,Donation>> dao = new DAO<>();
    private static final String FILENAME = "donationHashMap.dat";
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private static final String ID_COUNT_FILE = "donationIdCount.txt";
    
    
    public DonationMaintenance(){
        HashMap fileMap = (HashMap<String,Donation>) dao.retrieveFromFile(FILENAME);
        if(fileMap!=null){
            map = fileMap;
        }else{
            map = new HashMap<>();
        }
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Driver">
    public void donationMaintenanceDriver(){
        int choice = 0;
        do {
            choice = donationUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    //List all
                    donationUI.printDonationHeader();
                    displayAll();
                    break;
                case 2:
                    //Search
                    System.out.println();
                    Donation searchResult = searchById();
                    if(searchResult!=null){
                        System.out.println("\n"+searchResult.getDetails()+"\nPress to continue..");
                        scanner.nextLine();
                    }else{
                        System.out.println("\nNothing found..");
                    }
                    break;
                case 3:
                    //create
                    System.out.println();
                    if(create()){
                        donationUI.printDonationHeader();
                        displayAll();
                    }
                    break;
                case 4:
                    //delete
                    System.out.println();
                    if (remove()) {
                        donationUI.printDonationHeader();
                        displayAll();
                    }
                    break;
                case 5:
                    //update
                    System.out.println();
                    if (update()) {
                        donationUI.printDonationHeader();
                        displayAll();
                    }
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
        
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        
        System.out.print("Enter donor ID: ");
        String donorID = scanner.nextLine();
        
        System.out.print("Enter event ID:");
        String eventID = scanner.nextLine();
        
        System.out.print("Enter date (dd/MM/yyyy): ");
        String inputDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(inputDate,formatter);
        
        Donation.DonationType inputEnum = null;

        do {
            System.out.print("Enter donation Type(C = Cash/F = Food/I = Item): ");
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
        donation.setId(getIdCount());
        donation.setQuantity(quantity);
        donation.setMessage(message);
        //donation.setDonor(inputDonor());
        //donation.setEvent(inputEvent());
        donation.setType(inputEnum);
        donation.setDate(date);
        
        //add in donation map
        if(!map.containsKey(donation.getId())){
            map.put(donation.getId(), donation);
            saveDonationList();
            incrementIdCount(getIdCount());
            System.out.println("Successfully added");
            return true;
        }else{
            MessageUI.displayUnableCreateObjectMessage();
            System.out.println("This donation existed!");
            return false;
        }
    }

    
    public boolean remove() {
        System.out.print("Enter donation ID to remove: ");
        String id = scanner.nextLine();
        
        //use id to get the donation object
        Donation donation = map.get(id);
        
        if(!map.isEmpty()){
            if(map.remove(donation.getId())!=null){
                saveDonationList();
                System.out.println("Successfully removed");
                return true;
            }else{
                System.out.println("This donation does not exist!");
                return false;
            }
            
        }else{
            System.out.println("Empty donation.");
            return false;
        }
        
    }

    
    public boolean update() {
        System.out.print("Enter donation ID to update: ");
        String id = scanner.nextLine();
        
        if(!map.isEmpty() && map.containsKey(id)){
            System.out.print("Enter new quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new message: ");
            String message = scanner.nextLine();

            Donation.DonationType inputEnum = null;

            do {
                System.out.print("Enter donation Type(C = Cash/F = Food/I = Item): ");
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
            
            System.out.print("Enter new date (dd/MM/yyyy): ");
            String inputDate = scanner.nextLine();
            LocalDate date = LocalDate.parse(inputDate, formatter);
            
            Donation donation = map.get(id);
            donation.setQuantity(quantity);
            donation.setMessage(message);
            donation.setType(inputEnum);
            donation.setDate(date);
            
            //put can overwrite existing key value
            map.put(id,donation);
            System.out.println("Successfully updated");
            return true;
        }else{
            MessageUI.displayObjectNotFoundMessage();
            return false;
        }
        
    }

    
    public Donation searchById() {
        System.out.print("Enter donation Id to search: ");
        String id = scanner.nextLine();
        
        return map.get(id);
        
        
    }
   
    public boolean report(Donation donation) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void displayAll(){
        ArrayList<Donation> donationList = map.values(); 
        for(int i = 1; i <= donationList.getNumberOfEntries();i++){
            Donation d = donationList.getEntry(i);
            System.out.printf("%-15s%-15s%-50s%-30s%-30s%-15s%-15s\n",
                    d.getId(),
                    d.getQuantity(),
                    d.getMessage(),
                    "donor replacement",//no way to set donor yet
                    "event replacement",
                    d.getType(),
                    d.getDate().format(formatter));
        }
        
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="other support function">
    public void saveDonationList(){
        dao.saveToFile(map,FILENAME);
    }
    
    public static String getIdCount(){
        String data = null;
        try {
            File idCountFile = new File(ID_COUNT_FILE);
            if(!idCountFile.exists() || idCountFile.length()==0){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(ID_COUNT_FILE))) {
                    writer.write("DNTA0001");
                }catch(IOException e){
                    System.out.println("Cannot initialize donation count file");
                }
                return "DNTA0001";
            }
            Scanner myReader = new Scanner(idCountFile);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot read donation Id Count.");
            e.printStackTrace();
            
        }
        return data;
    }
    
    public void incrementIdCount(String idCount){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ID_COUNT_FILE))) {
            int intCount = Integer.parseInt(idCount.substring(5));
            intCount++;
            
            int alphaAsci = (int) idCount.charAt(3);
            if(intCount == 0){
                alphaAsci++;
            }
            char alpha = (char) alphaAsci;
            String strCount = String.format("%04d",intCount);
            
            String newId = "DNT"+ alpha + strCount;
            System.out.println("new incremented Id: "+ newId);
            writer.write(newId);
        } catch (IOException e) {
            System.out.println("Cannot initialize donation count file");
        }
    }
    // </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="main">
    public static void main(String[] args) {
//       try {
            DonationMaintenance donationMaintenance = new DonationMaintenance();
            donationMaintenance.donationMaintenanceDriver();
//        } catch (Exception e) {
//            System.err.println("An unexpected error occurred: " + e.getMessage());
//        }
    }
    // </editor-fold>
}
