package entity;

import java.io.Serializable;
import java.time.LocalDate;


public class Donee extends Person implements Serializable, Comparable<Donee> {

    private String id;
    private Categories category;
    private DoneeType type;
    private double receivedAmount;

    // Enums for Categories and DoneeType
    public enum Categories {
        HEALTH,
        EDUCATION,
        WELFARE
    }

    public enum DoneeType {
        INDIVIDUAL,
        ORGANISATION,
        FAMILIAR
    }

    // Default constructor
    public Donee() {
        super();
    }

    // Parameterized constructor
    public Donee(String id, Categories category, DoneeType type, double receivedAmount, String name, int age, LocalDate birthday, Gender gender, String phoneNo) {
        super(name, age, birthday, gender, phoneNo);
        this.id = id;
        this.category = category;
        this.type = type;
        this.receivedAmount = receivedAmount;
    }

    // Getter and Setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public DoneeType getType() {
        return type;
    }

    public void setType(DoneeType type) {
        this.type = type;
    }

    public double getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }
    
    public void updateFrom(Donee currentDonee) {
        if (currentDonee != null) {
            this.id = currentDonee.getId();
            this.category = currentDonee.getCategory();
            this.type = currentDonee.getType();
            this.receivedAmount = currentDonee.getReceivedAmount();
            this.setName(currentDonee.getName());
            this.setAge(currentDonee.getAge());
            this.setBirthday(currentDonee.getBirthday());
            this.setGender(currentDonee.getGender());
            this.setPhoneNo(currentDonee.getPhoneNo());
            this.setRegisterDate(currentDonee.getRegisterDate());
            this.setIsActive(currentDonee.isIsActive());
        }
    }

    @Override
    public int compareTo(Donee other) {
        return Double.compare(this.receivedAmount, other.receivedAmount);
    }

    @Override
    public String toString() {
        return String.format("%-30s%-10s%-20s%-10s%-20s%-30s%-20s%-20s%-20s%-20s",
                this.getName(), this.getAge(), this.getBirthday(), this.getGender(), this.getPhoneNo(),
                this.getRegisterDate(), this.isIsActive() ? "Active" : "De-activated", this.id, this.category, this.type);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
