package entity;

/**
 *
 * @author andrew
 */
public class Event_Volunteer {
    private Event event;
    private Volunteer volunteer;
    
    public Event_Volunteer(){}
    
    public Event_Volunteer(Event event, Volunteer volunteer){
        this.event = event;
        this.volunteer = volunteer;
    }
    
    //getter
    public Event getEvent(){
        return event;
    }
    
    public Volunteer getVolunteer(){
        return volunteer;
    }
}
