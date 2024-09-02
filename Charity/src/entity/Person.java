/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a person with various attributes such as name, age, birthday, gender, and phone number.
 * This class implements Serializable to allow for the object's state to be converted into a byte stream,
 * which can be useful for saving to files or transferring over networks.
 * 
 * @author Ooi Choon Chong
 */
public class Person implements Serializable { // Serialization is the process of converting an object's state into a byte stream, so it can be easily saved to a file
    
    // Enumeration for gender options
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    private String name;
    private int age;
    private LocalDate birthday;
    private Gender gender;
    private String phoneNo;
    private LocalDateTime registerDate = LocalDateTime.now();
    private boolean isActive = true;
    private boolean isDeleted = false;

    public Person() {

    }
    
    // Parameterized constructor to initialize Person with specific details
    public Person(String name, int age, LocalDate birthday, Gender gender, String phoneNo) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    /**
     * Provides a formatted string representation of the Person object.
     * 
     * @return A string representation of the Person's details.
     */
    @Override
    public String toString() {
        String activeStatus = "";
        if(this.isIsActive()){
            activeStatus += "Active";
        }else{
            activeStatus += "De-activate";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String regDate = this.registerDate.format(dateTimeFormatter);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String birthDate = this.birthday.format(dateFormatter);
        return String.format("%-30s%-5s%-13s%-10s%-15s%-22s%-15s", this.name, this.age, birthDate, this.gender, this.phoneNo, regDate, activeStatus);
    }
    
    /**
     * Computes a hash code for the Person object.
     * 
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
