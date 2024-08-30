/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import boundary.PersonMaintenanceUI;
import entity.Person;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 * @param <T>
 */
public class PersonMaintenance<T extends Person & Comparable<T>> implements ControlInterface<T> {

    protected final PersonMaintenanceUI personUI = new PersonMaintenanceUI();

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
                choice = personUI.getUpdateMenuChoice();
                switch (choice) {
                    case 0:
                        MessageUI.displayExitMessage();
                        break;
                    case 1:
                        newEntry.setName(personUI.inputPersonName());
                        break;
                    case 2:
                        newEntry.setPhoneNo(personUI.inputPersonPhoneNo());
                        break;
                    case 3:
                        newEntry.setBirthday(personUI.inputPersonBirthday());
                        break;
                    case 4:
                        newEntry.setGender(personUI.inputPersonGender());
                        break;
                    case 5:
                        newEntry.setIsActive(!((Person) newEntry).isIsActive());
                        personUI.printPersonActivate((Person) newEntry);
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
