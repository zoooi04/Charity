package entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author huaiern
 */
public class Donation implements Serializable, Comparable<Donation>, Cloneable{
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String id;
    private double quantity;
    private String message;
    private Donor donor;
    private Event event;
    private DonationType type;
    private LocalDate date;
    private boolean isDeleted;
    
    // Enums for DonationType
    public enum DonationType {
        CASH,
        FOOD,
        ITEM,
    }
    
    public Donation(){
        
    }
    
    public Donation(String id, double quantity, String message, Donor donor, Event event, DonationType type, LocalDate date){
        this.id = id;
        this.quantity = quantity;
        this.message = message;
        this.donor = donor;
        this.event = event;
        this.type = type;
        this.date = date;
        this.isDeleted = false;
    }
    
    public String getId(){
        return id;
    }
    
    public double getQuantity(){
        return quantity;
    }
    
    public String getMessage(){
        return message;
    }
    
    public DonationType getType(){
        return type;
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    public Donor getDonor(){
        return donor;
    }
    
    public Event getEvent(){
        return event;
    }
    
    public boolean getIsDeleted(){
        return isDeleted;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setQuantity(double quantity){
        this.quantity = quantity;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    public void setType(DonationType type) {
        this.type = type;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void setDonor(Donor donor){
        this.donor = donor;
    }
    
    public void setEvent(Event event){
        this.event = event;
    }
    
    public void setIsDeleted(boolean isDeleted){
        this.isDeleted = isDeleted;
    }
    
    @Override
    public Donation clone() {
        try {
            return (Donation) super.clone();
        } catch (CloneNotSupportedException e) {
            return null; // Handle clone failure
        }
    }
    
    @Override
    public String toString(){
        return id;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Donation){
            Donation donation = (Donation) obj;
            if(id.equals(donation.getId())){
                return true;
            }
        }
        return false;
        
    }
    
    public String getDetails(){
        return String.format("""
                             ================
                             Donation Details
                             =================
                             Donation ID: %s
                             Quantity: %.2f
                             Type: %s
                             Message: %s
                             Date: %s
                             Donor: %s
                             Event: %s""", id, quantity, type, message, date.format(formatter),donor.getId(),event.getId());
    }
    
    @Override
    public int compareTo(Donation d){       
        int idCount = Integer.parseInt(id.substring(5));
        int anotherIdCount = Integer.parseInt(d.id.substring(5));

        if (idCount > anotherIdCount) {
            return 0;
        } else if (idCount == anotherIdCount) {
            return -1;
        } else {
            return 1;
        }
        
    }
}
