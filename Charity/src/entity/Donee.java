package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Donee extends Person implements Serializable, Comparable<Donee> {

    private String id;
    private Type type;

    public enum Type {
        FAMILY(3),
        ORGANISATION(2),
        INDIVIDUAL(1);

        private final int priority;

        Type(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    public Donee() {
    }

    public Donee(String id, Type type, String name, int age, LocalDate birthday, Person.Gender gender, String phoneNo, LocalDateTime registerDate) {
        super(name, age, birthday, gender, phoneNo);
        setRegisterDate(registerDate);
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedRegisterDate = this.getRegisterDate().format(dateTimeFormatter);

        // Formatting the attributes
        return String.format("%-30s%-10d%-20s%-10s%-20s%-30s%-20s%-20s%-20s",
                this.getName(), 
                this.getAge(), 
                this.getBirthday(), 
                this.getGender(), 
                this.getPhoneNo(), 
                formattedRegisterDate, 
                this.isIsActive() ? "Active" : "De-activated", 
                this.getId(), 
                this.getType());
    }
    
    @Override
    public int compareTo(Donee other) {
        // Category comparison: FAMILY > ORGANISATION > INDIVIDUAL
        int typeComparison = this.type.compareTo(other.type);
        
        return typeComparison;
    }


    public void updateFrom(Donee other) {
        this.setId(other.getId());
        this.setName(other.getName());
        this.setAge(other.getAge());
        this.setBirthday(other.getBirthday());
        this.setGender(other.getGender());
        this.setPhoneNo(other.getPhoneNo());
        this.setRegisterDate(other.getRegisterDate());
        this.setIsActive(other.isIsActive());
        this.setType(other.getType());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
