package control;

import adt.Heap;
import adt.ListHeap;
import boundary.DoneeMaintenanceUI;
import entity.Donee;
import utility.MessageUI;

public class DoneeMaintenance extends PersonMaintenance<Donee> {

    private final DoneeMaintenanceUI doneeUI = new DoneeMaintenanceUI();
    private ListHeap<Donee> doneeHeap;
    private static final String FILENAME = "donee.dat";

    public DoneeMaintenance() {
        super(FILENAME);
        doneeHeap = new Heap<>(); // Ensure this is the correct type
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
                    if (search(doneeHeap, donee)) {
                        doneeUI.printDoneeHeader();
                        System.out.println(donee);
                    }
                    break;
                case 3:
                    if (create(doneeHeap)) {
                        doneeUI.printDoneeHeader();
                        display(doneeHeap);
                    }
                    break;
                case 4:
                    if (remove(doneeHeap)) {
                        doneeUI.printDoneeHeader();
                        display(doneeHeap);
                    }
                    break;
                case 5:
                    if (update(doneeHeap)) {
                        doneeUI.printDoneeHeader();
                        display(doneeHeap);
                    }
                    break;
                case 6:
                    // Add functionality if needed
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CRUD">
    public boolean create(ListHeap<Donee> newEntry) {
        Donee newDonee = new Donee();
        if (super.create(newDonee)) {
            doneeUI.inputDoneeDetails(newDonee);
            newEntry.add(newDonee);
            saveDoneeList();
            return true;
        } else {
            MessageUI.displayUnableCreateObjectMessage();
        }
        return false;
    }

public boolean remove(ListHeap<Donee> doneeHeap) {
    if (!doneeHeap.isEmpty()) {
        Donee highestPriorityDonee = doneeHeap.peekMaxValue(); // Check the root element without removing it

        if (!highestPriorityDonee.isIsActive()) {
            System.out.println("Donee is deactivated and cannot be removed!");
            return false;
        }

        highestPriorityDonee = doneeHeap.remove(); // Removes the root element
        highestPriorityDonee.setIsDeleted(true);
        saveDoneeList(); // Save the updated list
        return true;
    }
    System.out.println("Heap is empty, no donee to remove!");
    return false;
}

    public boolean update(ListHeap<Donee> newEntry) {
        int[] position = {-1};
        if (search(newEntry, position)) {
            Donee updateDonee = newEntry.getAnyValue(position[0]);
            boolean confirm = false;
            do {
                if (super.update(updateDonee)) {
                    int choice;
                    do {
                        choice = doneeUI.getUpdateMenuChoice();
                        switch (choice) {
                            case 0:
                                MessageUI.displayExitMessage();
                                break;
                            case 1:
                                updateDonee.setType(doneeUI.inputDoneeType());
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
            saveDoneeList();
            return true;
        } else {
            MessageUI.displayObjectNotFoundMessage();
            return false;
        }
    }
    
    public boolean search(ListHeap<Donee> newEntry, Object newObject) {
        boolean found = false;
        String inputDoneeId = doneeUI.inputDoneeId();
        for (int i = 0; !found && i < newEntry.size(); i++) {
            Donee donee = newEntry.getAnyValue(i);
            if (inputDoneeId.equals(donee.getId())) {
                found = true;
                if (newObject instanceof int[]) {
                    int[] foundPosition = (int[]) newObject;
                    foundPosition[0] = i;
                } else if (newObject instanceof Donee) {
                    Donee foundDonee = (Donee) newObject;
                    // Ensure the Donee class has a proper method to update from another Donee
                    foundDonee.updateFrom(donee);
                } else {
                    return false;
                }
            }
        }
        if (!found) {
            MessageUI.displayObjectNotFoundMessage();
        }
        return found;
    }

    public void display(ListHeap<Donee> newEntry) {
        doneeUI.listAllDonee(getAllDonee());
    }

    public boolean report(ListHeap<Donee> newEntry) {
        // Add report logic if needed
        return true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other support functions">
    public String getAllDonee() {
        StringBuilder outputStr = new StringBuilder();
        for (int i = 0; i < doneeHeap.size(); i++) {
            Donee donee = doneeHeap.getAnyValue(i);
            if (!donee.isIsDeleted()) {
                outputStr.append(donee).append("\n");
            }
        }
        return outputStr.toString();
    }

    public void saveDoneeList() {
       super.saveHeapToFile(doneeHeap, FILENAME);
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
