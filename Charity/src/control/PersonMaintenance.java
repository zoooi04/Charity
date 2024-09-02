/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import boundary.DoneeMaintenanceUI;
import boundary.DonorMaintenanceUI;
import boundary.PersonMaintenanceUI;
import entity.Donee;
import entity.Donor;
import entity.Person;
import entity.Volunteer;
import java.time.LocalDate;
import utility.MessageUI;

/**
 * Handles the maintenance operations for `Person` objects.
 *
 * @param <T> A type parameter that extends `Person` and implements
 * `Comparable<T>`.
 *
 * @author Ooi Choon Chong
 */
public class PersonMaintenance<T extends Person & Comparable<T>> {

    protected final PersonMaintenanceUI personUI = new PersonMaintenanceUI();

    public PersonMaintenance() {
    }

    // <editor-fold defaultstate="collapsed" desc="CURD">
    /**
     * Creates a new person entry and updates the properties of the given
     * `newEntry`.
     *
     * @param newEntry The `Person` object to be updated with new details.
     * @return `true` if the creation was successful, `false` otherwise.
     */
    public boolean create(T newEntry) {
        if (newEntry instanceof Person) {
            Person createdPerson = personUI.inputPersonDetails();
            if (createdPerson != null) {
                newEntry.setName(createdPerson.getName());
                newEntry.setBirthday(createdPerson.getBirthday());
                newEntry.setAge(createdPerson.getAge());
                newEntry.setGender(createdPerson.getGender());
                newEntry.setPhoneNo(createdPerson.getPhoneNo());
                newEntry.setRegisterDate(createdPerson.getRegisterDate());
            } else {
                MessageUI.displayUnableCreateObjectMessage();
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Removes the given person entry.
     *
     * @param newEntry The `Person` object to be removed.
     * @return `true` if the removal was successful, `false` otherwise.
     */
    public boolean remove(T newEntry) {
        if (newEntry instanceof Person) {
        } else {
            return false;
        }
        return true;
    }

    /**
     *
     * @param newEntry
     * @return (false if select 0. Back), (true if select 99. Next Page)
     */
    public boolean update(T newEntry) {
        if (newEntry instanceof Person) {
            int choice = -1;
            do {
                if (newEntry instanceof Donor) {
                    DonorMaintenanceUI donorUI = new DonorMaintenanceUI();
                    donorUI.printDonorHeader();
                } else if (newEntry instanceof Donee) {
                    DoneeMaintenanceUI doneeUI = new DoneeMaintenanceUI();
                    doneeUI.printDoneeHeader();
                }
                System.out.println(newEntry.toString());
                choice = personUI.getUpdateMenuChoice();
                switch (choice) {
                    case 0:
                        MessageUI.displayExitMessage();
                        break;
                    case 1:
                        String newName = personUI.inputPersonName();
                        if (newEntry.getName().equals(newName)) {
                            System.out.println("The new name is the same as the existing name.");
                            MessageUI.displayErrorMessage();
                        } else {
                            newEntry.setName(newName);
                        }
                        break;
                    case 2:
                        String newPhoneNo = personUI.inputPersonPhoneNo();
                        if (newEntry.getPhoneNo().equals(newPhoneNo)) {
                            System.out.println("The new phone number is the same as the existing phone number.");
                            MessageUI.displayErrorMessage();
                        } else {
                            newEntry.setPhoneNo(newPhoneNo);
                        }
                        break;
                    case 3:
                        LocalDate newBirthday = personUI.inputPersonBirthday();
                        if (newEntry.getBirthday().equals(newBirthday)) {
                            System.out.println("The new birthday is the same as the existing birthday.");
                            MessageUI.displayErrorMessage();
                        } else {
                            newEntry.setBirthday(newBirthday);
                        }
                        break;
                    case 4:
                        Person.Gender newGender = personUI.inputPersonGender();
                        if (newEntry.getGender().equals(newGender)) {
                            System.out.println("The new gender is the same as the existing gender.");
                            MessageUI.displayErrorMessage();
                        } else {
                            newEntry.setGender(newGender);
                        }
                        break;
                    case 5:
                        boolean newIsActive = !((Person) newEntry).isIsActive();
                        if (((Person) newEntry).isIsActive() == newIsActive) {
                            System.out.println("The active status is already " + newIsActive);
                            MessageUI.displayErrorMessage();
                        } else {
                            newEntry.setIsActive(newIsActive);
                            personUI.printPersonActivate((Person) newEntry);
                        }
                        break;
                    case 99:
                        return true;
                    default:
                        MessageUI.displayInvalidChoiceMessage();
                        break;
                }
            } while (choice != 0);
        }
        return false;
    }

    /**
     * Displays the details of the given person entry. This method is intended
     * to be overridden in a subclass.
     *
     * @param newEntry The `Person` object to be displayed.
     */
    public void display(T newEntry) {
        System.out.println("Person does not have display, use it in child class");
    }

    /**
     * Searches for a person entry based on the given search criteria. This
     * method is intended to be overridden in a subclass.
     *
     * @param newEntry The `Person` object to be searched.
     * @param newObject The search criteria object.
     * @return `true` if the search was successful, `false` otherwise.
     */
    public boolean search(T newEntry, T newObject) {
        System.out.println("Person does not have search, use it in child class");
        return true;
    }
    // </editor-fold>
}
