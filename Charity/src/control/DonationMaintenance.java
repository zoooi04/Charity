
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.GraphInterface;
import dao.DonationInitializer;
import entity.Donation;
import java.util.Scanner;
import boundary.DonationMaintenanceUI;
import utility.MessageUI;
import adt.MapInterface;
import adt.HashMap;
import adt.ListInterface;
import adt.SortedLinkedList;
import adt.SortedListInterface;
import adt.WeightedGraph;
import dao.DAO;
import entity.Donor;
import entity.Event;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
/**
 *
 * @author huaiern
 */
public class DonationMaintenance{
    private final DonationMaintenanceUI donationUI = new DonationMaintenanceUI();
    private MapInterface<String,Donation> donationMap;
    private final DAO<MapInterface<String,Donation>> dao = new DAO<>();
    private static final String FILENAME = "donationHashMap.dat";
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    
    private static DonorMaintenance donorM = new DonorMaintenance();


    //private static final String ID_COUNT_FILE = "donationIdCount.txt";
    
    
    public DonationMaintenance(){
        donationMap = (HashMap<String,Donation>) dao.retrieveFromFile(FILENAME);
        if(donationMap==null){
            donationMap = DonationInitializer.initializeDonation();
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
                    //track donation by categories
                    trackByCategories();
                    break;
                case 7:
                    //List donation by donor
                    listDonationByDonor();
                    break;
                case 8:
                    //filter donation by criteria
                    filterDonationByCriteria();
                    break;
                case 9:
                    //donation report
                    report();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Main Functions">
    public boolean create() {
        Donation donation = new Donation();
        
        int quantity = -1;  // Initialize with an invalid value
        System.out.print("Enter quantity: ");
        // Keep looping until a valid, non-negative integer is provided
        while (!scanner.hasNextInt() || (quantity = scanner.nextInt()) < 0) {
            System.out.println("Invalid input. Please enter a valid quantity.");
            scanner.nextLine();  // Consume the invalid input
            System.out.print("Enter quantity: ");
        }
        scanner.nextLine();  // Consume the newline character after the valid integer input
        
        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        
        
        Donor donor = null;
        while(donor==null){
            System.out.print("Enter donor ID: ");
            String donorID = scanner.nextLine();
            donor = getDonorById(donorID);
            if(donor == null){
                System.out.println("Donor does not exist!\nPlease re-enter a valid donor ID.");
            }
        }
  
        
        Event event = null;
        while (event == null) {
            System.out.print("Enter event ID:");
            String eventID = scanner.nextLine();
            event = getEventById(eventID);
            if (event == null) {
                System.out.println("Event does not exist!\nPlease re-enter a valid event ID.");
            }
        }
        
        

        
        
        
        LocalDate startDate = event.getStartDate();
        LocalDate endDate = event.getEndDate();
        String startDateStr = startDate.format(formatter);
        String endDateStr = endDate.format(formatter);
        
        LocalDate date = null;
        boolean isValid = false;
        while (!isValid) {
            System.out.println("\nEvent period: " + startDateStr + " -- " + endDateStr);
            System.out.print("Enter date within event period (dd/MM/yyyy): ");
            String inputDate = scanner.nextLine();

            try {
                date = LocalDate.parse(inputDate, formatter); // Try to parse the date
                isValid = true; // If parsing is successful, set the flag to true
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in dd/MM/yyyy format.");
            }
            
            if(date!=null){
                if (date.isBefore(startDate) || date.isAfter(endDate)) {
                    isValid = false;
                }
            }
            
            
        }
        
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
        donation.setId(getNextId());
        donation.setQuantity(quantity);
        donation.setMessage(message);
        donation.setDonor(donor);
        donation.setEvent(event);
        donation.setType(inputEnum);
        
        
        donation.setDate(date);
        
        //add in donation donationMap
        if(!donationMap.containsKey(donation.getId())){
            donationMap.put(donation.getId(), donation);
            saveDonationList();
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
        String id = scanner.nextLine().toUpperCase();
        
        //use id to get the donation object
        Donation donation = donationMap.get(id);
        
        if(!donationMap.isEmpty()&& donation!=null){
            if(donationMap.remove(donation.getId())!=null){
                saveDonationList();
                System.out.println("Successfully removed");
                return true;
            }else{
                System.out.println("This donation does not exist!");
                return false;
            }
            
        }else{
            System.out.println("Donation does not exist");
            return false;
        }
        
    }

    
    public boolean update() {
        System.out.print("Enter donation ID to update: ");
        String id = scanner.nextLine().toUpperCase();
        
        if(!donationMap.isEmpty() && donationMap.containsKey(id)){
int quantity = -1;  // Initialize with an invalid value
        System.out.print("Enter quantity: ");
        // Keep looping until a valid, non-negative integer is provided
        while (!scanner.hasNextInt() || (quantity = scanner.nextInt()) < 0) {
            System.out.println("Invalid input. Please enter a valid quantity.");
            scanner.nextLine();  // Consume the invalid input
            System.out.print("Enter quantity: ");
        }
        scanner.nextLine();  // Consume the newline character after the valid integer input

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
            
            LocalDate date = null;
            boolean isValid = false;
            while (!isValid) {
                System.out.print("Enter date (dd/MM/yyyy): ");
                String inputDate = scanner.nextLine();

                try {
                    date = LocalDate.parse(inputDate, formatter); // Try to parse the date
                    isValid = true; // If parsing is successful, set the flag to true
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter the date in dd/MM/yyyy format.");
                }
            }
            
            Donation donation = donationMap.get(id);
            donation.setQuantity(quantity);
            donation.setMessage(message);
            donation.setType(inputEnum);
            donation.setDate(date);
            
            //put can overwrite existing key value
            donationMap.put(id,donation);
            System.out.println("Successfully updated");
            return true;
        }else{
            MessageUI.displayObjectNotFoundMessage();
            return false;
        }
        
    }

    
    public Donation searchById() {
        System.out.print("Enter donation Id to search: ");
        String id = scanner.nextLine().toUpperCase();
        
        return donationMap.get(id.toUpperCase());
    }
    
    public void trackByCategories(){
        SortedListInterface<Donation> donationList = getDonationListSortedById();        
        
        int choice = 0;
        do {
            choice = donationUI.getCategoriesMenuChoice();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    //Cash
                    donationUI.printDonationHeader();
                    for(int i = 1; i<= donationList.getNumberOfEntries();i++){
                        Donation donation = donationList.getEntry(i);
                        if(donation.getType().equals(Donation.DonationType.CASH)){
                            displayDonation(donation,false,false);
                        }
                    }
                    break;
                case 2:
                    //Food
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getType().equals(Donation.DonationType.FOOD)) {
                            displayDonation(donation,false,false);
                        }
                    }
                    break;
                case 3:
                    //Item
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getType().equals(Donation.DonationType.ITEM)) {
                            displayDonation(donation,false,false);
                        }
                    }
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    
    public void listDonationByDonor(){
        SortedListInterface<Donation> donationList = getDonationListSortedById();

        
        int choice = 0;
        do {
            choice = donationUI.getDonorInfoMenuChoice();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    //Donor Id
                    System.out.print("Enter donor Id: ");
                    String id = scanner.nextLine();
                    
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getDonor().getId().equals(id)) {
                            displayDonation(donation, true,false);
                        }
                    }
                    break;
                case 2:
                    //Donor Name
                    System.out.print("Enter donor name: ");
                    String name = scanner.nextLine();
                    
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if (donation.getDonor().getName().toUpperCase().equals(name.toUpperCase())) {
                            displayDonation(donation, false,false);
                        }
                    }
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0);
    }
    
    public void filterDonationByCriteria(){
        SortedListInterface<Donation> donationList = getDonationListSortedById();
        
        int choice = 0;
        do {
            choice = donationUI.getFilterCriteria();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    //Message Availability
                    String message;
                    do{
                        System.out.print("Show donation with message? (Y/N): ");
                        message = scanner.nextLine().trim().toUpperCase();
                    }while(!message.equals("Y") && !message.equals("N"));
                    
                    
                    donationUI.printDonationHeader();
                    if(message.equals("Y")){
                        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                            Donation donation = donationList.getEntry(i);
                            if (!donation.getMessage().isBlank()) {
                                displayDonation(donation, false,false);
                            }
                        }
                    }else if (message.equals("N")){
                        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                            Donation donation = donationList.getEntry(i);
                            if (donation.getMessage().isBlank()) {
                                displayDonation(donation, false,false);
                            }
                        }
                    }
                    break;
                case 2:
                    //Event
                    int choice2 = 0;
                    do {
                        choice2 = donationUI.getEventInfoMenuChoice();
                        switch (choice2) {
                            case 0:
                                break;
                            case 1:
                                //Donor Id
                                System.out.print("Enter event Id: ");
                                String id = scanner.nextLine();

                                donationUI.printDonationHeader();
                                for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                                    Donation donation = donationList.getEntry(i);
                                    if (donation.getEvent().getId().equals(id)) {
                                        displayDonation(donation, true,true);
                                    }
                                }
                                break;
                            case 2:
                                //Donor Name
                                System.out.print("Enter event name: ");
                                String name = scanner.nextLine();

                                donationUI.printDonationHeader();
                                for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                                    Donation donation = donationList.getEntry(i);
                                    if (donation.getEvent().getName().toUpperCase().equals(name.toUpperCase())) {
                                        displayDonation(donation, false,false);
                                    }
                                }
                                break;
                            default:
                                MessageUI.displayInvalidChoiceMessage();
                                break;
                        }
                    } while (choice2 != 0);
                    break;
                case 3:
                    //Date
                    LocalDate date1 = null;
                    boolean isValid = false;
                    while (!isValid) {
                        System.out.print("Enter start date (dd/MM/yyyy): ");
                        String dateStr1 = scanner.nextLine();

                        try {
                            date1 = LocalDate.parse(dateStr1, formatter); // Try to parse the date
                            isValid = true; // If parsing is successful, set the flag to true
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please enter the date in dd/MM/yyyy format.");
                        }
                    }
                    
                    LocalDate date2 = null;
                    isValid = false;
                    while (!isValid) {
                        System.out.print("Enter end date (dd/MM/yyyy): ");
                        String dateStr2 = scanner.nextLine();

                        try {
                            date2 = LocalDate.parse(dateStr2, formatter); // Try to parse the date
                            isValid = true; // If parsing is successful, set the flag to true
                            if(date2.isBefore(date1)){
                                System.out.println("End date cannot be before Start date.");
                                isValid = false;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please enter the date in dd/MM/yyyy format.");
                        }
                    }
                    
                    
                    donationUI.printDonationHeader();
                    for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
                        Donation donation = donationList.getEntry(i);
                        if ((donation.getDate().isAfter(date1) || donation.getDate().isEqual(date1))
                                && (donation.getDate().isBefore(date2) || donation.getDate().isEqual(date2))) {
                            displayDonation(donation, false,false);
                        }
                            
                            
                        
                    } 
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0);
    }
    

 
    public void report(){}

    
    public void displayAll(){
        SortedListInterface<Donation> sortedDonations = getDonationListSortedById();
        for(int i = 1; i <= sortedDonations.getNumberOfEntries();i++){
            Donation d = sortedDonations.getEntry(i);
            displayDonation(d,false,false);
        }
    }
    
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="other support function">
    public void saveDonationList(){
        dao.saveToFile(donationMap,FILENAME);
    }
    

    public static String getFileName(){
        return FILENAME;
    }
    
    public String getNextId(){
        
        SortedListInterface<Donation> donationList = getDonationListSortedById();
        String idCount = donationList.getEntry(1).getId();
        
        int intCount = Integer.parseInt(idCount.substring(4));
        intCount++;

        int alphaAsci = (int) idCount.charAt(3);
        if (intCount == 0) {
            alphaAsci++;
        }
        char alpha = (char) alphaAsci;
        String strCount = String.format("%04d", intCount);

        String newId = "DNT" + alpha + strCount;
        
        return newId;
    }
    
    
    public void displayDonation(Donation d, boolean donorShowId, boolean eventShowId){
        String donorInfo = d.getDonor().getName();
        if(donorShowId){
            donorInfo = d.getDonor().getId();
        }
        
        String eventInfo = d.getEvent().getName();
        if(eventShowId){
            eventInfo = d.getEvent().getId();
        }
        
        
        System.out.printf("%-15s%-15s%-50s%-30s%-30s%-15s%-15s\n",
                d.getId(),
                d.getQuantity(),
                d.getMessage(),
                donorInfo,//no way to set donor yet
                eventInfo,
                d.getType(),
                d.getDate().format(formatter));
    }
    
    public boolean isInteger(String str){
        try{
            int i = Integer.parseInt(str);
            return true;
        }catch(NumberFormatException ex){
            return false;
        }
    }
    
    public static Donor getDonorById(String id){

        ListInterface<Donor> list = donorM.getDonorList();
        for(int i = 1; i <= list.getNumberOfEntries(); i++){
            if(list.getEntry(i).getId().equals(id)){
                //found donor
                return list.getEntry(i);
            }
        }

        return null;
    }
    
    public static Event getEventById(String id){
        EventMaintenance eventM = new EventMaintenance();
        MapInterface<String,Event> eventMap = eventM.getEventMap();
        return eventMap.get(id);
        
    }
    
    public SortedListInterface<Donation> getDonationListSortedById(){
        SortedListInterface<Donation> sortedDonations = new SortedLinkedList<>();
        ListInterface<Donation> donationList = donationMap.values();
        for (int i = 1; i <= donationList.getNumberOfEntries(); i++) {
            sortedDonations.add(donationList.getEntry(i));
        }
        return sortedDonations;
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

