/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package control;

import java.io.Serializable;
import java.time.LocalDateTime;
import adt.HashMap;
import adt.MapInterface;
import adt.Heap;
import adt.ListHeap;
import adt.ListInterface;
import adt.ArrayList;
import adt.SortedLinkedList;
import adt.SortedListInterface;
import entity.Donation;
import entity.Donee;
import entity.Distribution;
import dao.DAO;
import boundary.DistributionMaintenanceUI;
import boundary.DoneeMaintenanceUI;
import boundary.DistributionMaintenanceUI;

/**
 *
 * @author BEH JING HEN
 */

public class DistributionMaintenance {

    private HashMap<String, Donation> donations;
    private ListHeap<Donee> queue;
    private ListHeap<Donee> doneeHeap;
    private ListInterface<Distribution> distributions;
    
    private final DAO<HashMap<String, Donation>> dao = new DAO<>();
    private final DAO<ListHeap<Donee>> queueDao = new DAO<>();
    private final DAO<ListHeap<Donee>> doneeDao = new DAO<>();
    private final DAO<ListInterface<Distribution>> distributionDao = new DAO<>();
    
    private static final String DONATION_FILENAME = "donationHashMap.dat";
    private static final String QUEUE_FILENAME = "doneeQueue.dat";
    private static final String DONEE_FILENAME = "donee.dat";
    private static final String DISTRIBUTION_FILENAME = "distributionRecords.dat";
    
    public DistributionMaintenance() {
        donations = dao.retrieveFromFile(DONATION_FILENAME);
        if (donations == null) {
            donations = new HashMap<>();
        }

        queue = queueDao.retrieveFromFile(QUEUE_FILENAME);
        if (queue == null) {
            queue = new Heap<>();
        }
        
        doneeHeap = doneeDao.retrieveFromFile(DONEE_FILENAME);
        if (doneeHeap == null) {
            doneeHeap = new Heap<>();
        }
        
        distributions = distributionDao.retrieveFromFile(DISTRIBUTION_FILENAME);
        if (distributions == null) {
            distributions = new ArrayList<>();
        }
    }

    public void distributeDonation(DoneeMaintenance doneeMaintenance) {

        // Display available donations
        System.out.println("Available Donations:");
        printAllDonations();

        // Ask the user to input a Donation ID
        String donationId = DistributionMaintenanceUI.getInput("Enter Donation ID to distribute: ").trim();

        // Find the corresponding donation
        Donation selectedDonation = donations.get(donationId);

        if (selectedDonation == null) {
            System.out.println("Donation not found!");
            return;
        }

        if (!queue.isEmpty() && doneeMaintenance.removeQueue(queue)) {
            // Get the removed Donee
            Donee removedDonee = queue.peekMaxValue(); 
            
            // Generate a new Distribution ID
            String newId = generateNewDistributionId();
            
            // Create the Distribution record
            Distribution distribution = new Distribution(newId, removedDonee, selectedDonation, LocalDateTime.now());
            System.out.println("Distribution created successful!! " );

            // Save the distribution record
            saveDistribution(distribution);

            // Set the donation's status to isDeleted
            selectedDonation.setIsDeleted(true);

            // Save the updated donations back to the file
            saveDonations();


            // Save the updated queue
            saveQueue();

            // Print all distributions
            printAllDistributions();

        } else {
            System.out.println("Heap is empty, no Donee to remove!");
            System.out.println("Failed to remove a Donee from the queue!");
        }
    }

    public void saveDonations() {
        dao.saveToFile(donations, DONATION_FILENAME);
    }
    
    public void saveQueue(){
        queueDao.saveToFile(queue,QUEUE_FILENAME);
    }
    
    private void saveDistribution(Distribution distribution) {
        distributions.add(distribution);
        distributionDao.saveToFile(distributions, DISTRIBUTION_FILENAME);
    }
    
    public void requeueDonee(DoneeMaintenance doneeMaintenance) {
        boolean success = doneeMaintenance.requeueDonee(doneeHeap, queue);
        saveQueue();
        if (!success) {
            System.out.println("Failed to requeue Donee.");
        }
    }

    public void printAllDistributions() {
        
        System.out.println("All Distributions:");
        printDistributionHeader();
        for (int i = 1; i <= distributions.getNumberOfEntries(); i++) {
            try {
                Distribution distribution = distributions.getEntry(i); 
                System.out.println(distribution);
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Error accessing index " + i + ": " + e.getMessage());
            }
        }
    }
    
    public void printDistributionHeader(){
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.printf("%15s %8s %16s %17s %15s %9s %12s",
                          "DistributionID", "Donee ID", "DoneeType", 
                          "Donation ID", "DonationType", "Quantity", "DateTime");
        System.out.println("\n--------------------------------------------------------------------------------------------------------------");

    }

    public void restoreAllDonations() {
        int numberOfEntries = donations.size();  
        ArrayList<String> keys = donations.keySet();  

        for (int i = 1; i <= numberOfEntries; i++) {
            String key = keys.getEntry(i);  
            Donation donation = donations.get(key);  

            if (donation != null) {
                donation.setIsDeleted(false);  
            }
        }
        saveDonations();
        System.out.println("All donations have been restored.");
    }
    
    private String generateNewDistributionId() {
        int maxId = 0;

        for (int i = 1; i <= distributions.getNumberOfEntries(); i++) {
            try {
                Distribution distribution = distributions.getEntry(i);
                String id = distribution.getId();
                int idNumber = Integer.parseInt(id.substring(3));
                if (idNumber > maxId) {
                    maxId = idNumber;
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Error accessing index " + i + ": " + e.getMessage());
            }
        }

        // Increment the ID number and format it
        maxId++;
        return String.format("DIS%05d", maxId);
    }

    public HashMap<String, Donation> getDonations() {
        return donations;
    }

    public ListHeap<Donee> getQueue() {
        return queue;
    }

    public ListHeap<Donee> getDoneeHeap() {
        return doneeHeap;
    }

    public ListInterface<Distribution> getDistributions() {
        return distributions;
    }
    
    public void printAllDonations() {
        System.out.println("All Donations:");
        System.out.println("========================================================================================================================================================================================================");
        System.out.printf("%-15s %-20s %-50s %-30s %-30s %-15s %-15s%n", "ID", "Quantity(RM/KG)", "Message", "Donor", "Event", "Type", "Date");
        System.out.println("========================================================================================================================================================================================================");
        // Create a sorted list from the donations HashMap
        SortedLinkedList<Donation> sortedDonations = new SortedLinkedList<>();
        ArrayList<String> keys = donations.keySet();
        for (int i = 1; i <= keys.getNumberOfEntries(); i++) {
            String key = keys.getEntry(i);
            Donation donation = donations.get(key);
            if (donation != null && !donation.getIsDeleted()) {
                sortedDonations.add(donation); // Add donations to the sorted list
            }
        }

        // Second index-based loop to print all sorted donations
    for (int i = 1; i <= sortedDonations.getNumberOfEntries(); i++) {
        Donation donation = sortedDonations.getEntry(i);
        String donationID = donation.getId();
        String quantity = String.format("%.2f", donation.getQuantity());
        String message = donation.getMessage().isEmpty() ? "-" : donation.getMessage();
        String donorName = donation.getDonor().getName();
        String eventName = donation.getEvent().getName();
        String type = donation.getType().toString();
        String date = donation.getDate().toString();

        System.out.printf("%-15s %-20s %-50s %-30s %-30s %-15s %-15s%n",
                donationID, quantity, message, donorName, eventName, type, date);
    }
        
    }
    
    // Main method to start the application
    public static void main(String[] args) {
        DistributionMaintenance maintenance = new DistributionMaintenance();
        DoneeMaintenance doneeMaintenance = new DoneeMaintenance();

        DistributionMaintenanceUI ui = new DistributionMaintenanceUI(maintenance, doneeMaintenance);
        ui.displayMenu();
    }
}
