package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Donee extends Person implements Serializable, Comparable<Donee> {

    private String id;
    private Type type;

    public enum Type {
        FAMILY(3),
        ORGANISATION(1),
        INDIVIDUAL(2);

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
    public LocalDateTime getRegisterDate() {
        return super.getRegisterDate();
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format("%-12s%-15s", id, type);
    }

    @Override
    public int compareTo(Donee other) {
        // Category comparison: FAMILY > ORGANISATION > INDIVIDUAL
        int typeComparison = this.type.compareTo(other.type);
        if (typeComparison != 0) {
            return typeComparison;
        }
        // If types are the same, compare by registerDate (earlier dates come first)
        return this.getRegisterDate().compareTo(other.getRegisterDate());
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
