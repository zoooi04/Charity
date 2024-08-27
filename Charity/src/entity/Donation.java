package entity;


import java.time.LocalDateTime;


/**
 *
 * @author huaiern
 */
public class Donation {
    private String id;
    private int quantity;
    private String message;
    private Donor donor;
    //private Event event;
    private DonationType type;
    private LocalDateTime date;
    
    // Enums for DonationType
    public enum DonationType {
        CASH,
        FOOD,
        ITEM
    }
    
    public Donation(){
        
    }
    
    public Donation(String id, int quantity, String message, Donor donor,String type, LocalDateTime date){
        
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
    
    public LocalDateTime getDate(){
        return date;
    }
    
    public Donor getDonor(){
        return donor;
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
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public void setDonor(Donor donor){
        this.donor = donor;
    }
    
    @Override
    public String toString(){
        return String.format("""
                             Donation ID: %s\n
                             Quantity: %d\n
                             Type: %s\n
                             Message: %s\n
                             Date: %s\n
                             Donor: \n
                             Event: \n""", id, quantity, type,message,date);
    }
}
