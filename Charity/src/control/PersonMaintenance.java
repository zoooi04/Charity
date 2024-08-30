/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import boundary.DoneeMaintenanceUI;
import boundary.PersonMaintenanceUI;
import entity.Person;
import java.time.LocalDate;
import java.util.Date;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 * @param <T>
 */
public class PersonMaintenance<T extends Person & Comparable<T>> implements ControlInterface<T> {

    protected final PersonMaintenanceUI personUI = new PersonMaintenanceUI();
    private final DoneeMaintenanceUI doneeUI = new DoneeMaintenanceUI();

    public PersonMaintenance() {
    }

    // <editor-fold defaultstate="collapsed" desc="CURD">
    @Override
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

    @Override
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
    @Override
    public boolean update(T newEntry) {
        if (newEntry instanceof Person) {
            int choice = -1;
            do {
                doneeUI.printDoneeHeader();
                System.out.println(newEntry.toString());
                choice = personUI.getUpdateMenuChoice();
                switch (choice) {
                case 0:
                    MessageUI.displayExitMenuMessage();
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
                    int newAge = personUI.inputPersonAge();
                    if (newEntry.getAge() == newAge) {
                        System.out.println("The new age is the same as the existing age. Please enter a different age.");
                        MessageUI.displayErrorMessage();
                    } else {
                        newEntry.setAge(newAge);
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
                    String newPhoneNo = personUI.inputPersonPhoneNo();
                    if (newEntry.getPhoneNo().equals(newPhoneNo)) {
                        System.out.println("The new phone number is the same as the existing phone number.");
                        MessageUI.displayErrorMessage();
                    } else {
                        newEntry.setPhoneNo(newPhoneNo);
                    }
                    break;
                case 6:
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

    @Override
    public void display(T newEntry) {
        System.out.println("Person does not have display, use it in child class");
    }

    @Override
    public boolean search(T newEntry, T newObject) {
        System.out.println("Person does not have search, use it in child class");
        return true;
    }

    @Override
    public boolean report(T newEntry) {
        if (newEntry instanceof Person) {
        } else {
            return false;
        }
        return true;
    }
    // </editor-fold>
}
