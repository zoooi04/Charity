/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a donee (recipient of donations) with details including their ID,
 * type, and personal information. This class extends Person, implements
 * Serializable for object serialization, and Comparable for natural ordering.
 *
 * @author BEH JING HEN
 */
public class Donee extends Person implements Serializable, Comparable<Donee> {

    private String id; // Unique identifier for the donee
    private Type type; // Type of the donee (e.g., FAMILY, ORGANISATION, INDIVIDUAL)
    
     /**
     * Enumeration for Donee types with associated priority.(Higher number = Higher Priority)
     */
    public enum Type {
        FAMILY(3),
        ORGANISATION(2),
        INDIVIDUAL(1);

        private final int priority; // Priority value for each donee type

        Type(int priority) {
            this.priority = priority;
        }
        
        /**
         * Gets the priority of the donee type.
         * 
         * @return The priority value
         */
        public int getPriority() {
            return priority;
        }
    }

    public Donee() {
    }
    
    /**
     * Constructs a Donee with specified details.
     * 
     * @param id The unique identifier for the donee
     * @param type The type of the donee
     * @param name The name of the donee
     * @param age The age of the donee
     * @param birthday The birthday of the donee
     * @param gender The gender of the donee
     * @param phoneNo The phone number of the donee
     * @param registerDate The registration date of the donee
     */
    public Donee(String id, Type type, String name, int age, LocalDate birthday, Person.Gender gender, String phoneNo, LocalDateTime registerDate) {
        super(name, age, birthday, gender, phoneNo);
        setRegisterDate(registerDate);
        this.id = id;
        this.type = type;
    }
    
    /**
     * Gets the unique identifier for the donee.
     * 
     * @return The unique identifier
     */
    public String getId() {
        return id;
    }
    
    /**
     * Gets the type of the donee.
     * 
     * @return The type of the donee
     */
    public Type getType() {
        return type;
    }
    
    /**
     * Sets the unique identifier for the donee.
     * 
     * @param id The new unique identifier
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Sets the type of the donee.
     * 
     * @param type The new type of the donee
     */
    public void setType(Type type) {
        this.type = type;
    }
    
    /**
     * Gets the registration date of the donee.
     * 
     * @return The registration date
     */
    @Override
    public LocalDateTime getRegisterDate() {
        return super.getRegisterDate();
    }
    
    /**
     * Returns a string representation of the Donee including personal details, ID, and type.
     * 
     * @return A formatted string with Donee details
     */
    @Override
    public String toString() {
        return super.toString() + String.format("%-12s%-15s", id, type);
    }
    
    /**
     * Compares this Donee with another Donee for ordering based on type and registration date.
     * 
     * @param other The other Donee to compare with
     * @return A negative integer, zero, or a positive integer as this Donee is less than, equal to, or greater than the other Donee
     */
    @Override
    public int compareTo(Donee other) {
        // Category comparison: FAMILY > INDIVIDUAL > ORGANISATION
        return this.type.compareTo(other.type);
    }
    
    /**
     * Updates this Donee's details with those from another Donee.
     * 
     * @param other The Donee to copy details from
     */
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

}
