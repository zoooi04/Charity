package entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author huaiern
 */
public class Donation implements Serializable, Comparable<Donation>{
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String id;
    private int quantity;
    private String message;
    private Donor donor;
    private Event event;
    private DonationType type;
    private LocalDate date;
    
    // Enums for DonationType
    public enum DonationType {
        CASH,
        FOOD,
        ITEM
    }
    
    public Donation(){
        
    }
    
    public Donation(String id, int quantity, String message, Donor donor, Event event, DonationType type, LocalDate date){
        this.id = id;
        this.quantity = quantity;
        this.message = message;
        this.donor = donor;
        this.event = event;
        this.type = type;
        this.date = date;
    }
    
    public String getId(){
        return id;
    }
    
    public int getQuantity(){
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
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setQuantity(int quantity){
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
                             Quantity: %d
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
        
        if(idCount>anotherIdCount){
            return 0;
        }else if(idCount==anotherIdCount){
            return -1;
        }else{
            return 1;
        }
    }
}
