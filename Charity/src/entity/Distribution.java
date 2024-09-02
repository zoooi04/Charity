/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author BEH JING HEN
 */
public class Distribution implements Serializable {

    private String id; // Unique identifier for the distribution
    private Donee donee; // Reference to the Donee receiving the donation
    private Donation donation; // Reference to the Donation being given
    private LocalDateTime dateTime; // Date and time when the distribution took place

    /**
     * Constructs a new Distribution object with the specified details.
     *
     * @param id Unique identifier for the distribution
     * @param donee The Donee receiving the donation
     * @param donation The Donation being given
     * @param dateTime The date and time of the distribution
     */
    public Distribution(String id, Donee donee, Donation donation, LocalDateTime dateTime) {
        this.id = id;
        this.donee = donee;
        this.donation = donation;
        this.dateTime = dateTime;
    }

    /**
     * Gets the unique identifier for this distribution.
     *
     * @return The unique identifier
     */
    public String getId() {
        return id;
    }
    
     /**
     * Gets the Donee receiving the donation.
     *
     * @return The Donee
     */
    public Donee getDonee() {
        return donee;
    }
    
    /**
     * Gets the Donation being given.
     *
     * @return The Donation
     */
    public Donation getDonation() {
        return donation;
    }
    
    /**
     * Gets the date and time of the distribution.
     *
     * @return The date and time
     */

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    /**
     * Sets the unique identifier for this distribution.
     *
     * @param id The new unique identifier
     */
    public void setId(String id) {
        this.id = id;
    }
    
     /**
     * Sets the Donee receiving the donation.
     *
     * @param donee The new Donee
     */
    public void setDonee(Donee donee) {
        this.donee = donee;
    }
    
     /**
     * Sets the Donation being given.
     *
     * @param donation The new Donation
     */
    public void setDonation(Donation donation) {
        this.donation = donation;
    }
    
    /**
     * Sets the date and time of the distribution.
     *
     * @param dateTime The new date and time
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    /**
     * Returns a string representation of the Distribution in a formatted manner.
     * 
     * @return A string containing the distribution details
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Retrieving relevant details from Donee and Donation
        String doneeId = donee.getId();
        String doneeType = donee.getType().name();
        String donationId = donation.getId();
        String donationType = donation.getType().name();
        double donationQuantity = donation.getQuantity();
        
        // Format and return the string representation
        return String.format("%-15s %-15s %-15s %-15s %-15s %-10.2f %-20s",
                id,
                doneeId,
                doneeType,
                donationId,
                donationType,
                donationQuantity,
                dateTime.format(formatter));
    }
}
