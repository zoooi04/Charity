/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Ooi Choon Chong
 */
public class Donor extends Person implements Serializable { // Serialization is the process of converting an object's state into a byte stream, so it can be easily saved to a file

    public enum Type {
        INDIVIDUAL,
        ORGANISATION,
        FAMILY
    }

    public enum Category {
        GOVERNMENT,
        PRIVATE,
        PUBLIC
    }

    private String id;
    private Type type;
    private Category category;

    public Donor() {
    }

    public Donor(String id, Type type, Category category, String name, int age, LocalDate birthDay, Gender gender, String phoneNo) {
        super(name, age, birthDay, gender, phoneNo);
        this.id = id;
        this.type = type;
        this.category = category;
    }

    public Donor(String id, Type type, Category category, Person person) {
        super(person.getName(), person.getAge(), person.getBirthday(), person.getGender(), person.getPhoneNo());
        this.id = id;
        this.type = type;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void updateFrom(Donor donor) {
        this.setName(donor.getName());
        this.setAge(donor.getAge());
        this.setBirthday(donor.getBirthday());
        this.setGender(donor.getGender());
        this.setPhoneNo(donor.getPhoneNo());
        this.setRegisterDate(donor.getRegisterDate());
        this.setId(donor.getId());
        this.setType(donor.getType());
        this.setCategory(donor.getCategory());
    }

    @Override
    public String toString() {
        return super.toString() + String.format("%-20s%-20s%-20s", this.id, this.type, this.category);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
