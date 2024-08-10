/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import adt.ListInterface;
import boundary.DonorMaintenanceUI;
import entity.Donor;
import entity.Person;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 */
public class DonorMaintenance extends PersonMaintenance<Donor> {

    private final DonorMaintenanceUI donorUI = new DonorMaintenanceUI();
    private ListInterface<Donor> donorList = new ArrayList<>();
    private static final String FILENAME = "donor.dat";

    public DonorMaintenance() {
        super(FILENAME);
        donorList = (ListInterface<Donor>) getPersonList();
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

    // <editor-fold defaultstate="collapsed" desc="CRUD">
    public void display(ListInterface<Donor> newEntry) {
        donorUI.listAllDonor(getAllDonor());
    }

    public boolean search(ListInterface<Donor> newEntry, Object newObject) {
        boolean found = false;
        if (newEntry instanceof ArrayList<?>) {
            String inputDonorId = donorUI.inputDonorId();
            for (int i = 1; !found && i <= newEntry.getNumberOfEntries(); i++) {
                Object entry = newEntry.getEntry(i);

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
        if (!found) {
            MessageUI.displayObjectNotFoundMessage();
        }
        return found;
    }

    public boolean create(ListInterface<Donor> newEntry) {
        if (newEntry instanceof ArrayList<?>) {
            ArrayList<Donor> arrListDonor = (ArrayList<Donor>) newEntry;

            Donor newDonor = new Donor();
            if (super.create(newDonor)) {
                donorUI.inputDonorDetails(newDonor);
                arrListDonor.add(newDonor);
                saveDonorList();
                return true;
            } else {
                MessageUI.displayUnableCreateObjectMessage();
            }
        }
        return false;
    }

    // likedlist / hashmap
    public boolean remove(ListInterface<Donor> newEntry) {
        if (newEntry instanceof ArrayList<?>) {
            int[] position = {-1};
            if (search(newEntry, position)) {
                Object entry = newEntry.getEntry(position[0]);
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
        return true;
    }

    public boolean update(ListInterface<Donor> newEntry) {
        if (newEntry instanceof ArrayList<?>) {
            int[] position = {-1};
            if (search(newEntry, position)) {
                boolean confirm = false;
                do {
                    Donor updateDonor = (Donor) newEntry.getEntry(position[0]);
                    if (super.update(updateDonor)) {
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
                saveDonorList();
            } else {
                MessageUI.displayObjectNotFoundMessage();
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean report(ListInterface<Donor> newEntry) {

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
//    private void refreshDisplay() {
//        donorUI.printDonorHeader();
//        display(donorList);
//    }

    public void saveDonorList() {
        super.saveListToFile(donorList, FILENAME);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="main">
    public static void main(String[] args) {
        try {
            DonorMaintenance donorMaintenance = new DonorMaintenance();
            donorMaintenance.donorMaintenanceDriver();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    // </editor-fold>

}
