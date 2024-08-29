package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Volunteer extends Person implements Serializable {
    
    public enum EventType {
        CHARITY,
        FUNDRAISING,
        COMMUNITY_SERVICE,
        OTHER
    }
    
    private String id;
    private EventType eventType;

    public Volunteer() {
        super();
    }

    public Volunteer(String name, int age, LocalDate birthday, Gender gender, String phoneNo, String id, EventType eventType) {
        super(name, age, birthday, gender, phoneNo);
        this.id = id;
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return String.format("%s%-10s%-20s", super.toString(), id, eventType);
    }
}
