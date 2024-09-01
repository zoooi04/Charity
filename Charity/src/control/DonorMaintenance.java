/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayList;
import adt.BinarySearchTree;
import adt.BinarySearchTreeInterface;
import adt.HashMap;
import adt.LinkedQueue;
import adt.ListInterface;
import adt.MapInterface;
import adt.QueueInterface;
import boundary.DonorMaintenanceUI;
import dao.DAO;
import dao.DonorInitializer;
import entity.Donation;
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
    private static Donor lastSearchDonor = null;

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
//                    Donor donor = new Donor();
//                    if (search(donorList, donor)) {
//                        donorUI.printDonorHeader();
//                        System.out.println(donor);
//                    }

                    int[] position = {-1};
                    if (search(donorList, position)) {
                        donorUI.printDonorDetails(lastSearchDonor);
//                        System.out.println(lastSearchDonor);
                    }
                    break;
                case 3:
                    create(donorList);
                    break;
                case 4:
                    remove(donorList);
                    break;
                case 5:
                    update(donorList);
                    break;
                case 6:
                    report(donorList);
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
        boolean searchNewDonor = true, updateSelectedDonor = false;
        if (newEntry instanceof ArrayList<?>) {
            int[] position = {-1};
            if (lastSearchDonor != null) {
                int choice = donorUI.getUpdateDonorConfirmation(lastSearchDonor.getId());
                do {
                    switch (choice) {
                        case 0:
                            return false;
                        case 1:
                            searchNewDonor = false;
                            updateSelectedDonor = true;
                            break;
                        case 2:
                            break;
                        default:
                            break;
                    }
                } while (choice < 0 || choice > 2);
            }

            if (searchNewDonor) {
                if (search(newEntry, position)) {
                    updateSelectedDonor = true;
                }
            }

            if (updateSelectedDonor) {

                boolean confirm = false;
                do {
                    Donor updateDonor = lastSearchDonor;
                    if (super.update(updateDonor)) {
                        int choice;
                        do {
                            choice = donorUI.getUpdateMenuChoice();
                            switch (choice) {
                                case 0:
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
            }

        } else {
            return false;
        }
        return true;
    }

    // Search donor details
    public boolean search(ListInterface<Donor> newEntry, Object newObject) {
        boolean found = false;
        MapInterface<String, QueueInterface<Donor>> donorHMap;
        String selectedDonorId = "";
        int choice = 0;
        while (choice == 0) {
            switch (donorUI.getSearchMenuChoice()) {
                case 0:
                    choice = -1;
                    break;
                case 1:
                    selectedDonorId = donorUI.inputDonorId().trim();
                    choice = -1;
                    break;
                case 2:
                    String selectedDonorName = donorUI.inputPersonName().trim().toLowerCase();
                    donorHMap = toHashMap(newEntry, false);
                    if (!donorHMap.containsKey(selectedDonorName)) {
                        MessageUI.displayObjectNotFoundMessage();
                    } else {
                        QueueInterface<Donor> foundDonorInMap = donorHMap.get(selectedDonorName);
                        Iterator<Donor> itr = foundDonorInMap.getIterator();
                        int i = 1;
                        MapInterface<Integer, String> selectedDonorHMap = new HashMap<>();
                        while (itr.hasNext()) {
                            selectedDonorHMap.put(i++, itr.next().getId());
                        }
                        // list donor base on name
                        // let user select numbering
                        donorUI.printDonorSearchHeader();
                        do {
                            i = 1;
                            itr = foundDonorInMap.getIterator();
                            while (itr.hasNext()) {
                                System.out.println(" [" + i++ + "] " + itr.next() + "\n");
                            }
                            choice = donorUI.inputSelection();
                            if (choice > 0 && choice < i) {
                                selectedDonorId = selectedDonorHMap.get(choice);
                            } else if (choice == 0) {
                                break;
                            }
                        } while (choice <= 0 || choice >= i);
                    }
                    break;
                default:
                    break;
            }
        }

        if (choice == 0) {
            return false;
        }

        if (selectedDonorId == null) {
            return false;
        }

        if (newObject instanceof Donor) {
            donorHMap = toHashMap(newEntry);
            if (!donorHMap.containsKey(selectedDonorId)) {
                return false;
            }
            if (donorHMap.get(selectedDonorId) == null) {
                return false;
            }
            if (donorHMap.get(selectedDonorId).getFront() == null) {
                return false;
            }
            Donor donorInMap = (Donor) donorHMap.get(selectedDonorId).getFront();

            Donor foundDonor = (Donor) newObject;
            foundDonor.updateFrom(donorInMap);
            lastSearchDonor = foundDonor;
            found = true;
        } else if (newObject instanceof int[]) {

            for (int i = 1; !found && i <= newEntry.getNumberOfEntries(); i++) {
                Object entry = newEntry.getEntry(i);

                if (entry instanceof Donor) {
                    Donor donor = (Donor) entry;
                    if (selectedDonorId.equals(donor.getId())) {
                        lastSearchDonor = (Donor) entry;
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
        Donor.Type donorFilterTypeEnum = null;
        Donor.Category donorFilterCategoryEnum = null;
        do {
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
                                    donorFilterTypeEnum = Donor.Type.ORGANISATION;
                                    break;
                                case 2:
                                    donorFilterTypeEnum = Donor.Type.INDIVIDUAL;
                                    break;
                                case 9:
                                    donorFilterTypeEnum = null;
                                    break;
                                default:
                                    break;
                            }
                        } while ((choiceDetail < 0 || choiceDetail > 2) && choiceDetail != 9);
                        break;
                    case 2:
                        do {
                            choiceDetail = donorUI.getDisplayCategoryMenuChoice();
                            switch (choiceDetail) {
                                case 0:
                                    break;
                                case 1:
                                    donorFilterCategoryEnum = Donor.Category.GOVERNMENT;
                                    break;
                                case 2:
                                    donorFilterCategoryEnum = Donor.Category.PRIVATE;
                                    break;
                                case 3:
                                    donorFilterCategoryEnum = Donor.Category.PUBLIC;
                                    break;
                                case 9:
                                    donorFilterCategoryEnum = null;
                                    break;
                                default:
                                    break;
                            }
                        } while ((choiceDetail < 0 || choiceDetail > 3) && choiceDetail != 9);
                        break;
                    case 3:
                        // exit and jump to sort
                        break;

                    default:
                        break;
                }

            } while (choice != 0 && choice != 3);

            if (choice == 3) {
                do {
                    choiceDetail = donorUI.getDisplaySortMenuChoice(donorFilterTypeEnum, donorFilterCategoryEnum);
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
                            // insert into binary tree if donor is not deleted
                            if (!newEntry.getEntry(i).isIsDeleted()) {

                                if (donorFilterTypeEnum == null && donorFilterCategoryEnum == null) {
                                    bstBy.insert(newEntry.getEntry(i));
                                }

                                if (donorFilterTypeEnum == newEntry.getEntry(i).getType() && donorFilterCategoryEnum == null) {
                                    bstBy.insert(newEntry.getEntry(i));
                                }

                                if (donorFilterTypeEnum == null && donorFilterCategoryEnum == newEntry.getEntry(i).getCategory()) {
                                    bstBy.insert(newEntry.getEntry(i));
                                }

                                if (donorFilterTypeEnum == newEntry.getEntry(i).getType() && donorFilterCategoryEnum == newEntry.getEntry(i).getCategory()) {
                                    bstBy.insert(newEntry.getEntry(i));
                                }
                            }
                        }
                        donorUI.printDonorHeader();
                        Iterator it = bstBy.iterator();
                        while (it.hasNext()) {
                            System.out.print(it.next() + "\n");
                        }
                        System.out.println("\n");
                    }
                } while (choiceDetail != 0);

            }
        } while (choiceDetail == 0 && choice != 0);
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

        DonationMaintenance donationM = new DonationMaintenance();

        // Iterating HashMap through for loop
        ListInterface<Donation> donationArrList = donationM.getDonationMap().values();
        ListInterface<Donor> donorActiveArrList = new ArrayList<>();
        int z = 0;

        // all record
        for (int i = 1; i <= donationArrList.getNumberOfEntries(); i++) {
            donorActiveArrList.add(donationArrList.getEntry(i).getDonor());
        }

        // each donor only 1 time
        ListInterface<Donor> donorActiveOnceArrList = new ArrayList<>();
        for (int i = 1; i <= donorActiveArrList.getNumberOfEntries(); i++) {
            if (!donorActiveOnceArrList.contains(donorActiveArrList.getEntry(i))) {
                donorActiveOnceArrList.add(donorActiveArrList.getEntry(i));
            }
        }

        int choice = 0, choiceDetail;
        do {
            choice = donorUI.getDisplayReportMenuChoice();
            switch (choice) {
                case 0:
                    break;
                case 1:

                    // 1.
                    // Report for all Donor
                    //
                    // Gender
                    MapInterface<Donor.Gender, ListInterface<Donor>> donorGenderMap = new HashMap<>();

                    for (int i = 1; i <= newEntry.getNumberOfEntries(); i++) {

                        Donor donor = newEntry.getEntry(i);
                        if (donor == null) {
                            continue;  // Skip this entry
                        }

                        Donor.Gender key = null;
                        if (null != newEntry.getEntry(i).getGender()) {
                            switch (newEntry.getEntry(i).getGender()) {
                                case FEMALE: {
                                    key = Donor.Gender.FEMALE;
                                    break;
                                }
                                case MALE: {
                                    key = Donor.Gender.MALE;
                                    break;
                                }
                                case OTHER: {
                                    key = Donor.Gender.OTHER;
                                    break;
                                }
                                default:
                                    break;
                            }
                        }

                        ListInterface<Donor> list = donorGenderMap.get(key);

                        if (list == null) {
                            // If no queue exists for this key, create a new one
                            list = new ArrayList<>();
                            donorGenderMap.put(key, list);
                        }
                        // Enqueue the donor into the existing or newly created queue
                        list.add(donor);
                    }

                    // Type
                    MapInterface<Donor.Type, ListInterface<Donor>> donorTypeMap = new HashMap<>();

                    for (int i = 1; i <= newEntry.getNumberOfEntries(); i++) {

                        Donor donor = newEntry.getEntry(i);
                        if (donor == null) {
                            continue;  // Skip this entry
                        }

                        Donor.Type key = null;
                        if (null != newEntry.getEntry(i).getType()) {
                            switch (newEntry.getEntry(i).getType()) {
                                case ORGANISATION: {
                                    key = Donor.Type.ORGANISATION;
                                    break;
                                }
                                case INDIVIDUAL: {
                                    key = Donor.Type.INDIVIDUAL;
                                    break;
                                }
                                default:
                                    break;
                            }
                        }

                        ListInterface<Donor> list = donorTypeMap.get(key);

                        if (list == null) {
                            // If no queue exists for this key, create a new one
                            list = new ArrayList<>();
                            donorTypeMap.put(key, list);
                        }
                        // Enqueue the donor into the existing or newly created queue
                        list.add(donor);
                    }

                    // Category
                    MapInterface<Donor.Category, ListInterface<Donor>> donorCategoryMap = new HashMap<>();

                    for (int i = 1; i <= newEntry.getNumberOfEntries(); i++) {

                        Donor donor = newEntry.getEntry(i);
                        if (donor == null) {
                            continue;  // Skip this entry
                        }

                        Donor.Category key = null;
                        if (null != newEntry.getEntry(i).getCategory()) {
                            switch (newEntry.getEntry(i).getCategory()) {
                                case GOVERNMENT: {
                                    key = Donor.Category.GOVERNMENT;
                                    break;
                                }
                                case PRIVATE: {
                                    key = Donor.Category.PRIVATE;
                                    break;
                                }
                                case PUBLIC: {
                                    key = Donor.Category.PUBLIC;
                                    break;
                                }
                                default:
                                    break;
                            }
                        }

                        ListInterface<Donor> list = donorCategoryMap.get(key);

                        if (list == null) {
                            // If no queue exists for this key, create a new one
                            list = new ArrayList<>();
                            donorCategoryMap.put(key, list);
                        }
                        // Enqueue the donor into the existing or newly created queue
                        list.add(donor);
                    }

                    donorUI.printDonorReportHeader();

                    donorUI.printDonorReportBody((ArrayList) donorGenderMap.get(Donor.Gender.MALE), 1, newEntry.getNumberOfEntries());
                    donorUI.printDonorReportBody((ArrayList) donorGenderMap.get(Donor.Gender.FEMALE), 1, newEntry.getNumberOfEntries());
                    donorUI.printDonorReportBody((ArrayList) donorGenderMap.get(Donor.Gender.OTHER), 1, newEntry.getNumberOfEntries());
                    donorUI.printDonorReportSeperateLine(48);
                    donorUI.printDonorReportBody((ArrayList) donorTypeMap.get(Donor.Type.ORGANISATION), 2, newEntry.getNumberOfEntries());
                    donorUI.printDonorReportBody((ArrayList) donorTypeMap.get(Donor.Type.INDIVIDUAL), 2, newEntry.getNumberOfEntries());
                    donorUI.printDonorReportSeperateLine(48);
                    donorUI.printDonorReportBody((ArrayList) donorCategoryMap.get(Donor.Category.GOVERNMENT), 3, newEntry.getNumberOfEntries());
                    donorUI.printDonorReportBody((ArrayList) donorCategoryMap.get(Donor.Category.PRIVATE), 3, newEntry.getNumberOfEntries());
                    donorUI.printDonorReportBody((ArrayList) donorCategoryMap.get(Donor.Category.PUBLIC), 3, newEntry.getNumberOfEntries());
                    donorUI.printDonorReportSeperateLine(48);

                    break;
                case 2:
                    // Report of Active Donor
                    // number of donor in each level of age
                    int participant = 0;
                    int[] ageActive = new int[6];
                    int[] ageInActive = new int[6];

                    for (int i = 1; i <= newEntry.getNumberOfEntries(); i++) {
                        if (donorActiveOnceArrList.contains(newEntry.getEntry(i))) {
                            participant++;
                            if (newEntry.getEntry(i).getAge() < 21) {
                                ageActive[0]++;
                            } else if (newEntry.getEntry(i).getAge() < 41) {
                                ageActive[1]++;
                            } else if (newEntry.getEntry(i).getAge() < 61) {
                                ageActive[2]++;
                            } else if (newEntry.getEntry(i).getAge() < 81) {
                                ageActive[3]++;
                            } else { // over 80
                                ageActive[4]++;
                            }
                        } else {
                            if (newEntry.getEntry(i).getAge() < 21) {
                                ageInActive[0]++;
                            } else if (newEntry.getEntry(i).getAge() < 41) {
                                ageInActive[1]++;
                            } else if (newEntry.getEntry(i).getAge() < 61) {
                                ageInActive[2]++;
                            } else if (newEntry.getEntry(i).getAge() < 81) {
                                ageInActive[3]++;
                            } else { // over 80
                                ageInActive[4]++;
                            }
                        }
                    }

                    ageActive[5] = participant;
                    ageInActive[5] = newEntry.getNumberOfEntries() - participant;

                    System.out.println((z++) + "");
                    donorUI.printActiveDonorReportHeader();
                    donorUI.printActiveDonorReportBody(ageActive, ageInActive);

                    break;
                case 3:
                    // list top 3 donor contribution
                    MapInterface<Donation.DonationType, MapInterface<Donor, Double>> donorDonation = new HashMap<>();

                    for (int i = 1; i <= donationArrList.getNumberOfEntries(); i++) {
                        Donation.DonationType type = donationArrList.getEntry(i).getType();
                        Double qty = donationArrList.getEntry(i).getQuantity();
                        Donor donor = donationArrList.getEntry(i).getDonor();
                        Double totalQty = 0.0;

                        if (donorDonation.containsKey(type)) {
                            if (donorDonation.get(type).containsKey(donor)) {
                                totalQty = donorDonation.get(type).get(donor) + qty;
                            } else {
                                totalQty = qty;
                            }
                        } else {
                            totalQty = qty;
                        }

                        MapInterface<Donor, Double> donorDonationDetail = new HashMap<>();
                        donorDonationDetail.put(donor, totalQty);
                        donorDonation.put(type, donorDonationDetail);
                    }

                    Donation.DonationType donationType = null;
                    donorUI.printDonorTop3ReportHeader();
                    for (int i = 0; i < donorDonation.size(); i++) {
                        switch (i) {
                            case 0:
                                donationType = Donation.DonationType.CASH;
                                break;
                            case 1:
                                donationType = Donation.DonationType.FOOD;
                                break;
                            case 2:
                                donationType = Donation.DonationType.ITEM;
                                break;
                            default:
                                break;
                        }
                        ListInterface<Double> HighestQty = new ArrayList<>();
                        ListInterface<Donor> HighestDonor = new ArrayList<>();
                        for (int j = 1; j <= donorActiveOnceArrList.getNumberOfEntries(); j++) {
                            Donor donor = donorActiveOnceArrList.getEntry(j);
                            Double value = donorDonation.get(donationType).get(donor);

                            if (value != null) {
                                if (HighestQty.isEmpty() || value > HighestQty.getEntry(1)) {
                                    HighestQty.add(1, value);
                                    HighestDonor.add(1, donor);
                                } else if (value > HighestQty.getEntry(2)) {
                                    HighestQty.add(2, value);
                                    HighestDonor.add(2, donor);
                                } else if (value > HighestQty.getEntry(3)) {
                                    HighestQty.add(3, value);
                                    HighestDonor.add(3, donor);
                                } else {
                                    HighestQty.add(value);
                                    HighestDonor.add(donor);
                                }
                            }
                        }

                        donorUI.printDonorTop3ReportBody(donationType, (ArrayList<Donor>) HighestDonor, (ArrayList<Double>) HighestQty);
                        donorUI.printDonorReportSeperateLine(70);
                        HighestQty.clear();
                        HighestDonor.clear();
                    }
                    break;

                default:
                    break;
            }

        } while (choice != 0);

        return false;
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

    public ListInterface<Donor> getDonorList() {
        return donorList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="adt convertor">
    public MapInterface<String, QueueInterface<Donor>> toHashMap(ListInterface<Donor> donorArrList) {
        return toHashMap(donorArrList, true);
    }

    /**
     * Converts the ArrayList to a Map using a key extractor function.
     *
     * @param donorArrList
     * @param useIDAsKey
     * @return A Map where the keys are strings and the values are the elements
     * of the ArrayList.
     */
    public MapInterface<String, QueueInterface<Donor>> toHashMap(ListInterface<Donor> donorArrList, boolean useIDAsKey) {
        MapInterface<String, QueueInterface<Donor>> donorMap = new HashMap<>();

        for (int i = 1; i <= donorArrList.getNumberOfEntries(); i++) {

            Donor donor = donorArrList.getEntry(i);
            if (donor == null) {
                continue;  // Skip this entry
            }
            String key = useIDAsKey ? donor.getId() : donor.getName().trim().toLowerCase();
            QueueInterface<Donor> queue = donorMap.get(key);

            if (queue == null) {
                // If no queue exists for this key, create a new one
                queue = new LinkedQueue<>();
                donorMap.put(key, queue);
            }
            // Enqueue the donor into the existing or newly created queue
            queue.enqueue(donor);
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
