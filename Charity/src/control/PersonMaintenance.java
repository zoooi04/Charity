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
 */
public class PersonMaintenance implements ControlInterface {

    private PersonMaintenanceUI personUI = new PersonMaintenanceUI();

    public PersonMaintenance() {
    }

    // <editor-fold defaultstate="collapsed" desc="CURD">
    @Override
    public void display(Object newEntry) {
        System.out.println("Person does not have display, use it in child class");
    }

    @Override
    public boolean search(Object newEntry, Object newObject) {
        System.out.println("Person does not have search, use it in child class");
        return true;
    }

    @Override
    public boolean create(Object newEntry) {
        if (newEntry instanceof Person) {
            Person person = (Person) newEntry;
            Person createdPerson = personUI.inputPersonDetails();
            if (createdPerson != null) {
                person.setName(createdPerson.getName());
                person.setAge(createdPerson.getAge());
                person.setBirthday(createdPerson.getBirthday());
                person.setGender(createdPerson.getGender());
                person.setPhoneNo(createdPerson.getPhoneNo());
                person.setRegisterDate(createdPerson.getRegisterDate());
            } else {
                MessageUI.displayUnableCreateObjectMessage();
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(Object newEntry) {
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
    public boolean update(Object newEntry) {
        if (newEntry instanceof Person) {
            int choice = -1;
            do {
                choice = personUI.getUpdateMenuChoice();
                switch (choice) {
                    case 0:
                        MessageUI.displayExitMessage();
                        break;
                    case 1:
                        ((Person) newEntry).setName(personUI.inputPersonName());
                        break;
                    case 2:
                        ((Person) newEntry).setAge(personUI.inputPersonAge());
                        break;
                    case 3:
                        ((Person) newEntry).setBirthday(personUI.inputPersonBirthday());
                        break;
                    case 4:
                        ((Person) newEntry).setGender(personUI.inputPersonGender());
                        break;
                    case 5:
                        ((Person) newEntry).setPhoneNo(personUI.inputPersonPhoneNo());
                        break;
                    case 6:
                        ((Person) newEntry).setIsActive(!((Person) newEntry).isIsActive());
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
    public boolean report(Object newEntry) {
        if (newEntry instanceof Person) {
        } else {
            return false;
        }
        return true;
    }
    // </editor-fold>

}
