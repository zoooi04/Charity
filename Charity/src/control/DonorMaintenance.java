/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import adt.BinarySearchTree;
import adt.BinarySearchTreeInterface;
import adt.HashMap;
import adt.ListInterface;
import adt.MapInterface;
import boundary.DonorMaintenanceUI;
import dao.DAO;
import dao.DonorInitializer;
import entity.Donor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 */
public class DonorMaintenance extends PersonMaintenance<Donor> {

    private final DonorMaintenanceUI donorUI = new DonorMaintenanceUI();
    private ListInterface<Donor> donorList = new ArrayList<>();
    private final DAO<ListInterface<Donor>> dao = new DAO<>();
    private static final String FILENAME = "donor.dat";

    public DonorMaintenance() {
        donorList = dao.retrieveFromFile(FILENAME);
        if (donorList == null) {
            donorList = DonorInitializer.initializeDonor();
        }
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
                        // display(donorList);
                    }
                    break;
                case 4:
                    if (remove(donorList)) {
                        // display(donorList);
                    }
                    break;
                case 5:
                    if (update(donorList)) {
                        // display(donorList);
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
    // Add a new donor
    public boolean create(ListInterface<Donor> newEntry) {
        if (newEntry instanceof ArrayList<?>) {
            ArrayList<Donor> arrListDonor = (ArrayList<Donor>) newEntry;

            Donor newDonor = new Donor();
            if (super.create(newDonor)) {
                donorUI.inputDonorDetails(newDonor);
                DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
                String year = LocalDate.now().format(yearFormatter);
                String newId = "AA" + year + String.format("%05d", newEntry.getNumberOfEntries() + 1);
                newDonor.setId(newId);
                arrListDonor.add(newDonor);
                saveDonorList();
                return true;
            } else {
                MessageUI.displayUnableCreateObjectMessage();
            }
        }
        return false;
    }

    // Remove a donor
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

    // Update donors details
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

    // Search donor details
    public boolean search(ListInterface<Donor> newEntry, Object newObject) {
        boolean found = false;
        String selectedDonorId = "";

        // menu
        // 1. name
        // 2. id
        // search through name;
        switch (donorUI.getSearchMenuChoice()) {
            case 0:
                break;
            case 1:

                // list donor base on name
                // let user select numbering
                break;
            case 2:
                selectedDonorId = donorUI.inputDonorId().trim();
                break;
            default:
                break;
        }

        MapInterface<String, Donor> donorHMap = toHashMap(newEntry);
        Donor foundDonorInMap = donorHMap.get(selectedDonorId);

        if (!donorHMap.containsKey(selectedDonorId)) {
            return false;
        }

        if (newObject instanceof Donor) {
            Donor foundDonor = (Donor) newObject;
            foundDonor.updateFrom(foundDonorInMap);
            found = true;
        } else if (newObject instanceof int[]) {

            for (int i = 1; !found && i <= newEntry.getNumberOfEntries(); i++) {
                Object entry = newEntry.getEntry(i);

                if (entry instanceof Donor) {
                    Donor donor = (Donor) entry;
                    if (selectedDonorId.equals(donor.getId())) {
                        found = true;
                        if (newObject instanceof int[]) {
                            int[] foundPosition = (int[]) newObject;
                            foundPosition[0] = i;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        if (!found) {
            MessageUI.displayObjectNotFoundMessage();
        }
        return found;
    }

    // Display (general / filter)
    public void display(ListInterface<Donor> newEntry) {
        int choice = 0;
        int choiceDetail = 0;
        String donorFilterResultStr = "";
        do {
            choice = donorUI.getDisplayMenuChoice();
            switch (choice) {
                case 0:
                    break;
                case 1:
                    do {
                        choiceDetail = donorUI.getDisplayTypeMenuChoice();
                        switch (choiceDetail) {
                            case 0:
                                break;
                            case 1:
                                donorFilterResultStr = getAllDonor(Donor.Type.INDIVIDUAL);
                                break;
                            case 2:
                                donorFilterResultStr = getAllDonor(Donor.Type.ORGANISATION);
                                break;
                            default:
                                break;
                        }
                        if (choiceDetail > 0 && choiceDetail < 3) {
                            donorUI.printDonorHeader();
                            donorUI.listAllDonor(donorFilterResultStr);
                        }
                    } while (choiceDetail != 0);
                    break;
                case 2:
                    do {
                        choiceDetail = donorUI.getDisplayCategoryMenuChoice();
                        switch (choiceDetail) {
                            case 0:
                                break;
                            case 1:
                                donorFilterResultStr = getAllDonor(Donor.Category.GOVERNMENT);
                                break;
                            case 2:
                                donorFilterResultStr = getAllDonor(Donor.Category.PRIVATE);
                                break;
                            case 3:
                                donorFilterResultStr = getAllDonor(Donor.Category.PUBLIC);
                                break;
                            default:
                                break;
                        }
                        if (choiceDetail > 0 && choiceDetail < 4) {
                            donorUI.printDonorHeader();
                            donorUI.listAllDonor(donorFilterResultStr);
                        }
                    } while (choiceDetail != 0);
                    break;
                case 3:
                    do {
                        choiceDetail = donorUI.getDisplaySortMenuChoice();
                        Comparator<Donor> compareBy = null;
                        switch (choiceDetail) {
                            case 0:
                                break;
                            case 1:
                                compareBy = Comparator.comparing(Donor::getId);
                                break;
                            case 2:
                                compareBy = Comparator.comparing(Donor::getName);
                                break;
                            case 3:
                                compareBy = Comparator.comparing(Donor::getPhoneNo);
                                break;
                            case 4:
                                compareBy = Comparator.comparing(Donor::getRegisterDate);
                                break;
                            default:
                                break;
                        }
                        if (choiceDetail > 0 && choiceDetail < 5) {
                            BinarySearchTreeInterface<Donor> bstBy = new BinarySearchTree<>(compareBy);
                            for (int i = 1; i <= newEntry.getNumberOfEntries(); ++i) {
                                bstBy.insert(newEntry.getEntry(i));
                            }
                            donorUI.printDonorHeader();
                            Iterator it = bstBy.iterator();
                            while (it.hasNext()) {
                                System.out.print(it.next() + "\n");
                            }
                            System.out.println("\n");
                        }
                    } while (choiceDetail != 0);
                    break;

                default:
                    break;
            }

        } while (choice != 0);
    }

    // List donors with all the donations made
    // not yet implement 
    // donor event
    //       event
    //       event
    // donor event
    //       event
    // Generate summary reports
    public boolean report(ListInterface<Donor> newEntry) {

        return true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other support function">
    public String getAllDonor(Enum<?> filter) {
        StringBuilder outputStr = new StringBuilder();
        if (filter instanceof Donor.Type) {
            Donor.Type typeFilter = (Donor.Type) filter;
            for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
                Donor donor = donorList.getEntry(i);
                if (!donor.isIsDeleted() && donor.getType() == typeFilter) {
                    outputStr.append(donor).append("\n");
                }
            }
        } else if (filter instanceof Donor.Category) {
            Donor.Category categoryFilter = (Donor.Category) filter;
            for (int i = 1; i <= donorList.getNumberOfEntries(); i++) {
                Donor donor = donorList.getEntry(i);
                if (!donor.isIsDeleted() && donor.getCategory() == categoryFilter) {
                    outputStr.append(donor).append("\n");
                }
            }
        } else {
            // Handle cases where filter is not of Type or Category (optional)
        }
        return outputStr.toString();
    }

    public void saveDonorList() {
        dao.saveToFile(donorList, FILENAME);
    }
    
    public ListInterface<Donor> getDonorList(){
        return donorList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="adt convertor">
    /**
     * Converts the ArrayList to a Map using a key extractor function.
     *
     * @param keyExtractor A function that extracts the key from an element of
     * type T.
     * @return A Map where the keys are strings and the values are the elements
     * of the ArrayList.
     */
    public MapInterface<String, Donor> toHashMap(ListInterface<Donor> donorArrList) {
        MapInterface<String, Donor> donorMap = new HashMap<>();

        for (int i = 1; i <= donorArrList.getNumberOfEntries(); i++) {

            Donor donor = donorArrList.getEntry(i);
            if (donor == null) {
                continue;  // Skip this entry
            }
            donorMap.put(donor.getId(), donor);
        }

        return donorMap;
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
