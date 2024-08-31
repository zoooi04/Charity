package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Distribution implements Serializable {

    private String id;
    private Donee donee;
    private Donation donation;
    private LocalDateTime dateTime;
    

    public Distribution(String id, Donee donee, Donation donation, LocalDateTime dateTime) {
        this.id = id;
        this.donee = donee;
        this.donation = donation;
        this.dateTime = dateTime;
        
    }

    public String getId() {
        return id;
    }

    public Donee getDonee() {
        return donee;
    }

    public Donation getDonation() {
        return donation;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDonee(Donee donee) {
        this.donee = donee;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Retrieving relevant details from Donee and Donation
        String doneeId = donee.getId();
        String doneeType = donee.getType().name();
        String donationId = donation.getId();
        String donationType = donation.getType().name();
        int donationQuantity = donation.getQuantity();

        return String.format("%-15s %-15s %-15s %-15s %-15s %-10d %-20s",
                id,
                doneeId,
                doneeType,
                donationId,
                donationType,
                donationQuantity,
                dateTime.format(formatter));
    }
}
