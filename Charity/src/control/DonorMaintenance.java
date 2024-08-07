/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import adt.ListInterface;
import boundary.DonorMaintenanceUI;
import dao.DAO;
import entity.Donor;
import entity.Person;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 */
public class DonorMaintenance implements ControlInterface {

    private ListInterface<Donor> donorList = new ArrayList<>();
    private PersonMaintenance personControl = new PersonMaintenance();
    private DAO donorDAO = new DAO();
    private DonorMaintenanceUI donorUI = new DonorMaintenanceUI();

    public DonorMaintenance() {
        donorList = donorDAO.retrieveFromFile("donor.dat");
    }

    // <editor-fold defaultstate="collapsed" desc="Driver">
    public void donorMaintenanceDriver() {
        int choice = 0;
        do {
            choice = donorUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    donorUI.printDonorHeader();
                    display(donorList);
                    break;
                case 2:
                    Donor donor = new Donor();
                    if (search(donorList, donor)) {
                        donorUI.printDonorHeader();
                        System.out.println(donor);
                    }
                    break;
                case 3:
                    if (create(donorList)) {
                        donorUI.printDonorHeader();
                        display(donorList);
                    }
                    break;
                case 4:
                    if (remove(donorList)) {
                        donorUI.printDonorHeader();
                        display(donorList);
                    }
                    break;
                case 5:
                    if (update(donorList)) {
                        donorUI.printDonorHeader();
                        display(donorList);
                    }
                    break;
                case 6:
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CURD">
    @Override
    public void display(Object newEntry) {
        donorUI.listAllDonor(getAllDonor());
    }

    @Override
    public boolean search(Object newEntry, Object newObject) {
        boolean found = false;
        if (newEntry instanceof ListInterface<?>) {
            ListInterface<?> listInterface = (ListInterface<?>) newEntry;

            if (listInterface instanceof ArrayList<?>) {
                String inputDonorId = donorUI.inputDonorId();
                for (int i = 1; !found && i <= listInterface.getNumberOfEntries(); i++) {
                    Object entry = listInterface.getEntry(i);

                    if (entry instanceof Donor) {
                        Donor donor = (Donor) entry;
                        if (inputDonorId.equals(donor.getId())) {
                            found = true;
                            if (newObject instanceof int[]) {
                                int[] foundPosition = (int[]) newObject;
                                foundPosition[0] = i;
                            } else if (newObject instanceof Donor) {
                                Donor foundDonor = (Donor) newObject;
                                foundDonor.updateFrom(donor);
                            } else {
                                return false;
                            }
                        }
                    } else {
                        // Nothing happen, continue loop
                    }

                }
            } else {
                return false;
            }
        } /*Hash map interface*/ else {
            return false;
        }

        if (!found) {
            MessageUI.displayObjectNotFoundMessage();
        }
        return found;
    }

    @Override
    public boolean create(Object newEntry) {
        Person newPerson = new Person();
        if (newEntry instanceof ListInterface<?>) {
            ListInterface<?> listInterface = (ListInterface<?>) newEntry;

            if (listInterface instanceof ArrayList<?>) {
                ArrayList<Donor> arrListDonor = (ArrayList<Donor>) listInterface;
                if (personControl.create(newPerson)) {
                    Donor newDonor = donorUI.inputDonorDetails(newPerson);
                    arrListDonor.add(newDonor);
                    donorDAO.saveToFile(arrListDonor, "donor.dat");
                } else {
                    MessageUI.displayUnableCreateObjectMessage();
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override   // likedlist / hashmap
    public boolean remove(Object newEntry) {
        if (newEntry instanceof ListInterface<?>) {
            ListInterface<?> listInterface = (ListInterface<?>) newEntry;

            if (listInterface instanceof ArrayList<?>) {
                int[] position = {-1};
                if (search(newEntry, position)) {
                    Object entry = listInterface.getEntry(position[0]);
                    if (entry instanceof Donor) {
                        Donor paramDonor = (Donor) entry;
                        paramDonor.setIsDeleted(true);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Object newEntry) {
        if (newEntry instanceof ListInterface<?>) {
            ListInterface<?> listInterface = (ListInterface<?>) newEntry;

            if (listInterface instanceof ArrayList<?>) {
                int[] position = {-1};
                if (search(newEntry, position)) {
                    boolean confirm = false;
                    do {
                        Person updatePerson = (Person) ((ListInterface<?>) newEntry).getEntry(position[0]);
                        if (personControl.update(updatePerson)) {
                            Donor updateDonor = (Donor) ((ListInterface<?>) newEntry).getEntry(position[0]);
                            updateDonor.updateFrom(updatePerson);
                            int choice;
                            do {
                                choice = donorUI.getUpdateMenuChoice();
                                switch (choice) {
                                    case 0:
                                        MessageUI.displayExitMessage();
                                        break;
                                    case 1:
                                        updateDonor.setType(donorUI.inputDonorType());
                                        break;
                                    case 2:
                                        updateDonor.setCategory(donorUI.inputDonorCategory());
                                        break;
                                    case 99:
                                        confirm = true;
                                        break;
                                    default:
                                        MessageUI.displayInvalidChoiceMessage();
                                        break;
                                }
                            } while (choice != 0 && choice != 99);
                        } else {
                            confirm = true;
                        }
                    } while (!confirm);
                    donorDAO.saveToFile((ListInterface<Donor>) newEntry, "donor.dat");
                } else {
                    MessageUI.displayObjectNotFoundMessage();
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean report(Object newEntry) {
        if (newEntry instanceof ListInterface<?>) {
            ListInterface<?> listInterface = (ListInterface<?>) newEntry;

            if (listInterface instanceof ArrayList<?>) {

            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other support function">
    public String getAllDonor() {
        String outputStr = "";
        for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
            if (!donorList.getEntry(i).isIsDeleted()) {
                outputStr += donorList.getEntry(i) + "\n";
            }
        }
        return outputStr;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="main">
    public static void main(String[] args) {
        DonorMaintenance donorMaintenance = new DonorMaintenance();
        donorMaintenance.donorMaintenanceDriver();
    }
    // </editor-fold>

}
