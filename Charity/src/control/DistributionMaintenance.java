package control;

import java.io.Serializable;
import java.time.LocalDateTime;
import adt.HashMap;
import adt.MapInterface;
import adt.Heap;
import adt.ListHeap;
import java.util.Scanner;
import entity.Donation;
import entity.Donee;
import entity.Person;
import entity.Distribution;
import control.DoneeMaintenance;
import dao.DAO;
import adt.ArrayList;
import adt.ListInterface;

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
        
        // Initialize donations as a new HashMap if the file is empty or doesn't exist
        if (donations == null) {
            donations = new HashMap<>();
        }

        // Load the queue from the file
        queue = queueDao.retrieveFromFile(QUEUE_FILENAME);
        
        // Initialize queue as a new Heap if the file is empty or doesn't exist
        if (queue == null) {
            queue = new Heap<>();
        }
        
        doneeHeap = doneeDao.retrieveFromFile(DONEE_FILENAME);
        
        // Initialize queue as a new Heap if the file is empty or doesn't exist
        if (doneeHeap == null) {
            doneeHeap = new Heap<>();
        }
        
        distributions = distributionDao.retrieveFromFile(DISTRIBUTION_FILENAME);
        
        // Initialize queue as a new Heap if the file is empty or doesn't exist
        if (distributions == null) {
            distributions = new ArrayList<>();
        }
        
    }

    public void distributeDonation(DonationMaintenance donationMaintenance, DoneeMaintenance doneeMaintenance) {
        Scanner scanner = new Scanner(System.in);

        // Display available donations
        System.out.println("Available Donations:");
        donationMaintenance.displayAll();

        // Ask the user to input a Donation ID
        System.out.print("Enter Donation ID to distribute: ");
        String donationId = scanner.nextLine().trim();

        // Find the corresponding donation
        Donation selectedDonation = donations.get(donationId);

        if (selectedDonation == null) {
            System.out.println("Donation not found!");
            return;
        }
        Donee removedDonee = queue.peekMaxValue(); // Get the removed Donee
        String doneeId = removedDonee.getId();
        // Remove a Donee from the queue
        
        if (!queue.isEmpty() && doneeMaintenance.removeQueue(queue)) {
            // Create the Distribution record
            Distribution distribution = new Distribution("D001", removedDonee, selectedDonation, LocalDateTime.now());
            System.out.println("Distribution created: " + distribution);
            
            saveDistribution(distribution);
            printAllDistributions();
            
            // Remove the donation from the list
            donations.remove(selectedDonation.getId());
            saveQueue();
           
            
        } else {
            System.out.println("Heap is empty, no Donee to remove!");
            System.out.println("Failed to remove a Donee from the queue!");
        }
    }

    // Method to save the donations map to the file
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

    // Display menu and handle user input
    public void displayMenu(DonationMaintenance donationMaintenance, DoneeMaintenance doneeMaintenance) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nDistribution Maintenance Menu:");
            System.out.println("1. Distribute Donation");
            System.out.println("2. Requeue Donee");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    distributeDonation(donationMaintenance, doneeMaintenance);
                    break;
                case 2:
                    requeueDonee(doneeMaintenance);
                    break;
                case 3:
                    printAllDistributions();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting Distribution Maintenance...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void printAllDistributions() {
        System.out.println("All Distributions:");
        for (int i = 1; i <= distributions.getNumberOfEntries(); i++) {
            try {
                Distribution distribution = distributions.getEntry(i); 
                System.out.println(distribution);
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Error accessing index " + i + ": " + e.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        DistributionMaintenance distributionMaintenance = new DistributionMaintenance();
        DoneeMaintenance doneeMaintenance = new DoneeMaintenance();
        DonationMaintenance donationMaintenance = new DonationMaintenance();

        distributionMaintenance.displayMenu(donationMaintenance, doneeMaintenance);
    }
}
