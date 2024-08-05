/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDate;


/**
 *
 * @author Ooi Choon Chong
 */
public class Donor extends Person{
    
    public enum Type{
        INDIVIDUAL,
        ORGANISATION,
        FAMILY
    }
    
    public enum Category{
        GOVERNMENT,
        PRIVATE,
        PUBLIC
    }
    
    private String id;
    private Type type;
    private Category category;

    
    public Donor(){}

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
 
    @Override
    public String toString() {
        return super.toString() + String.format("%20s\n%20s\n%20s\n", this.id, this.type, this.category);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
}
