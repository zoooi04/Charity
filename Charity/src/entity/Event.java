package entity;

/**
 *
 * @author andrew
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event implements Serializable, Comparable {

    private String id;
    private String name;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private int minVolunteer;
    private int maxVolunteer;
    private int currentVolunteer;
    private double currentAmount;
    private double goalAmount;
    private boolean isDeleted;

    public Event() {
    }

    public Event(String id, String name, String type, LocalDate startDate, LocalDate endDate, String location, int minVolunteer, int maxVolunteer, int currentVolunteer, double currentAmount, double goalAmount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.minVolunteer = minVolunteer;
        this.maxVolunteer = maxVolunteer;
        this.currentVolunteer = currentVolunteer;
        this.currentAmount = currentAmount;
        this.goalAmount = goalAmount;
        isDeleted = false;
    }

    public Event(String id, String name, String type, LocalDate startDate, LocalDate endDate, String location, int minVolunteer, int maxVolunteer, int currentVolunteer, double currentAmount, double goalAmount, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.minVolunteer = minVolunteer;
        this.maxVolunteer = maxVolunteer;
        this.currentVolunteer = currentVolunteer;
        this.currentAmount = currentAmount;
        this.goalAmount = goalAmount;
        this.isDeleted = isDeleted;
    }

    //getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public int getMinVolunteer() {
        return minVolunteer;
    }

    public int getMaxVolunteer() {
        return maxVolunteer;
    }

    public int getCurrentVolunteer() {
        return currentVolunteer;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMinVolunteer(int minVolunteer) {
        this.minVolunteer = minVolunteer;
    }

    public void setMaxVolunteer(int maxVolunteer) {
        this.maxVolunteer = maxVolunteer;
    }

    public void setCurrentVolunteer(int currentVolunteer) {
        this.currentVolunteer = currentVolunteer;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startDateStr = startDate.format(formatter);
        String endDateStr = endDate.format(formatter);

        return String.format("%-10s%-30s%-12s%-12s%-12s%-55s%-15d%-15d%-15d%3s%9.2f%15s%9.2f", id, name, type, startDateStr, endDateStr, location, minVolunteer, maxVolunteer, currentVolunteer, "", currentAmount, "", goalAmount);
    }

    @Override
    public boolean equals(Object o) {
        String other = (String) o;
        return this.name.equalsIgnoreCase(other);
    }

    @Override
    public int compareTo(Object o) {
        Event otherEvent = (Event) o;

        return this.startDate.compareTo(otherEvent.startDate);
    }

    public Event deepCopy() {
        return new Event(this.id, this.name, this.type, this.startDate, this.endDate, this.location,
                this.minVolunteer, this.maxVolunteer, this.currentVolunteer,
                this.currentAmount, this.goalAmount, this.isDeleted);
    }

}
