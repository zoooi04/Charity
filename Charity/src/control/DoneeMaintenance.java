package control;

import adt.Heap;
import adt.ListInterface;
import boundary.DoneeMaintenanceUI;
import entity.Donee;
import utility.MessageUI;

/**
 * Maintenance control class for handling Donee operations using a Heap.
 */
public class DoneeMaintenance extends PersonMaintenance<Donee> {

    private final DoneeMaintenanceUI doneeUI = new DoneeMaintenanceUI();
    private Heap<Donee> doneeHeap = new Heap<>(10); 
    private static final String FILENAME = "donee.txt";

    public DoneeMaintenance() {
        super(FILENAME);
        // You may need to load the existing donee data into the heap here if needed.
        // doneeHeap = loadDoneeHeapFromFile();
    }

    // <editor-fold defaultstate="collapsed" desc="Driver">
    public void doneeMaintenanceDriver() {
        int choice = 0;
        do {
            choice = doneeUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    doneeUI.printDoneeHeader();
                    display(doneeHeap);
                    break;
                case 2:
                    Donee donee = new Donee();
                    if (search(donee)) {
                        doneeUI.printDoneeHeader();
                        doneeUI.printDoneeDetails(donee);
                    }
                    break;
                case 3:
                    if (create()) {
                        doneeUI.printDoneeHeader();
                        display(doneeHeap);
                    }
                    break;
                case 4:
                    if (remove()) {
                        doneeUI.printDoneeHeader();
                        display(doneeHeap);
                    }
                    break;
                case 5:
                    if (update()) {
                        doneeUI.printDoneeHeader();
                        display(doneeHeap);
                    }
                    break;
                case 6:
                    // Implement any additional functionality here
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CRUD">
    // Add a new donee
    public boolean create() {
        Donee newDonee = new Donee();
        if (super.create(newDonee)) {
            doneeUI.inputDoneeDetails(newDonee);
            doneeHeap.add(newDonee);
            saveDoneeHeap();

            // Debugging output
            System.out.println("Added Donee: " + newDonee);
            System.out.println("Donee Heap Size after addition: " + doneeHeap.size());

            return true;
        } else {
            MessageUI.displayUnableCreateObjectMessage();
        }
        return false;
    }

    // Remove a donee
    public boolean remove() {
        Donee doneeToRemove = doneeHeap.peekMaxValue();  // Assuming you want to remove the root of the heap
        if (doneeToRemove != null) {
            doneeToRemove.setIsDeleted(true);
            doneeHeap.remove();  // Remove the root from the heap

            // Debugging output
            System.out.println("Removed Donee: " + doneeToRemove);
            return true;
        } else {
            MessageUI.displayObjectNotFoundMessage();
            return false;
        }
    }

    // Update donee details
    public boolean update() {
        Donee doneeToUpdate = doneeHeap.peekMaxValue();  // Assuming you update the root of the heap
        if (doneeToUpdate != null) {
            boolean confirm = false;
            do {
                if (super.update(doneeToUpdate)) {
                    int choice;
                    do {
                        choice = doneeUI.getUpdateMenuChoice();
                        switch (choice) {
                            case 0:
                                MessageUI.displayExitMessage();
                                break;
                            case 1:
                                doneeToUpdate.setCategory((Donee.Categories) doneeUI.inputDoneeCategory());
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
            saveDoneeHeap();
            return true;
        } else {
            MessageUI.displayObjectNotFoundMessage();
            return false;
        }
    }

    // Search for a donee
    public boolean search(Donee donee) {
        String inputDoneeId = doneeUI.inputDoneeId();
        for (int i = 0; i < doneeHeap.size(); i++) {
            Donee currentDonee = doneeHeap.peekMaxValue();  // This should be replaced with a proper search in the heap
            if (currentDonee != null && inputDoneeId.equals(currentDonee.getId())) {
                donee.updateFrom(currentDonee);
                return true;
            }
        }
        MessageUI.displayObjectNotFoundMessage();
        return false;
    }

    // Display donees
    public void display(Heap<Donee> doneeHeap) {
        // Debugging output
        System.out.println("Displaying Donees:");
        for (int i = 0; i < doneeHeap.size(); i++) {
            Donee donee = doneeHeap.peekMaxValue();  // This should iterate over all elements in the heap
            if (donee != null && !donee.isIsDeleted()) {
                System.out.println("Donee " + i + ": " + donee);
            }
        }

        doneeUI.listAllDonees(getAllDonees());
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other support function">
    public String getAllDonees() {
        StringBuilder outputStr = new StringBuilder();
        for (int i = 0; i < doneeHeap.size(); i++) {
            Donee donee = doneeHeap.peekMaxValue();  // This should iterate over all elements in the heap
            if (donee != null && !donee.isIsDeleted()) {
                outputStr.append(donee).append("\n");
            }
        }
        return outputStr.toString();
    }

    public void saveDoneeHeap() {
        super.saveListToFile((ListInterface<Donee>) doneeHeap, FILENAME);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="main">
    public static void main(String[] args) {
        try {
            DoneeMaintenance doneeMaintenance = new DoneeMaintenance();
            doneeMaintenance.doneeMaintenanceDriver();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    // </editor-fold>
}
