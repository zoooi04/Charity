package entity;
import java.time.LocalDate;


/**
 *
 * @author huaiern
 */
public class Donation {
    private String id;
    private int quantity;
    private String message;
    //private Donor donor;
    //private Event event;
    private String type;
    private LocalDate date;
    
    public Donation(){
        
    }
    
    public Donation(String id, int quantity, String message, String type, LocalDate date){
        
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
    
    public String getType(){
        return type;
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getString(){
        return String.format("""
                             Donation ID: %s
                             Quantity: %d
                             Type: %s
                             Message: %s
                             Date: %s""", id, quantity, type,message,date);
    }
}
