package control;

/**
 *
 * @author AndrewPhengQiJinn
 */
import utility.MessageUI;
import boundary.EventMaintenanceUI;
import entity.*;
import adt.*;
import dao.EventDAO;
import java.time.LocalDate;

public class EventMaintenance {

    //private SortedListInterface<Event> events = new SortedLinkedList<>();
    private MapInterface<String, Event> eventsMap = new HashMap<>();
    private EventDAO eventDAO = new EventDAO();
    private EventMaintenanceUI eventUI = new EventMaintenanceUI();
    private static final String FILENAME = "event.txt";

    public EventMaintenance() {
        eventsMap = eventDAO.retrieveFromFile(FILENAME);
    }

    public void runEventMaintenance() {
        int ch = 0;
        do {
            ch = eventUI.getChoice();
            switch (ch) {
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                case 1:
                    addEvent();
                    break;
                case 2:
                    deleteEvent();
                    break;

                /*
                    int ch2 = -1;
                    do {
                        ch2 = eventUI.getDeleteChoice();
                        switch (ch2) {
                            case 0 ->
                                MessageUI.displayExitToMainMenuMessage();
                            case 1 -> {
                                singleDelete();
                            }
                            case 2 -> {
                            }

                            default ->
                                MessageUI.displayInvalidChoiceMessage();
                        }
                    } while (ch2 != 0);
                    break;
                 */
                case 3:
                    restoreEvent();
                    break;

                case 4:
                    searchEventMenu();
                    break;

                case 5:
                    editEvent();
                    break;

                case 6:
                    displayAllEvents();
                    break;

                default:
                    MessageUI.displayInvalidChoiceMessage();

            }
        } while (ch != 0);
    }

    //calling add an event function
    public void addEvent() {
        Event newEvent = eventUI.AddNewEventUI(generateEventID());

        eventsMap.put(newEvent.getId(), newEvent);

        System.out.println("\nEvent added successfully.\n");

        /*
        if (events.add(newEvent)) {
            eventDAO.saveToFile(events, "event.txt");
            System.out.println("\nEvent added successfully.\n");
        } else {
            MessageUI.displayInvalidAddMessage("event\n");
        }
         */
    }

    //calling single delete function
    public void deleteEvent() {
        String id = eventUI.getEventID("deleted");
        Event event = null;
        event = search(id);
        if (event != null) {
            eventUI.printEventDetails(event);
            char confirm = eventUI.getDeleteConfirmation();
            if (confirm == 'Y') {
                event.setIsDeleted(true); // Mark it as deleted

                // Update the hashmap value of the key
                eventsMap.put(id, event);
                eventDAO.saveToFile(eventsMap, "event.txt");
                System.out.println("Event " + id + " deleted.\n");
            } else {
                System.out.println("Event " + id + " was not deleted.\n");
            }
        } else {
            MessageUI.displaySearchNotFoundMessage("Event " + id);
        }
    }

    //calling restore deleted events function
    public void restoreEvent() {

        if (!getAllDeletedEvents().isEmpty()) {

            displayDeletedEvents();
            System.out.println();
            String id = eventUI.getEventID("restored");

            if (eventsMap.containsKey(id) && eventsMap.get(id).getIsDeleted()) {
                Event event = eventsMap.get(id);

                eventUI.printEventDetails(event);

                char confirm = eventUI.getRestoreConfirmation();
                if (confirm == 'Y') {
                    event.setIsDeleted(false);

                    eventsMap.put(id, event);
                    eventDAO.saveToFile(eventsMap, "event.txt");
                    System.out.println("Event " + id + " restored.\n");
                } else {
                    System.out.println("Event " + id + " was not restored.\n");
                }

            } else {
                MessageUI.displaySearchNotFoundMessage("Event " + id);
            }

        } else {
            System.out.println("\nNo events available for restoration.\n");
        }

    }

    //calling search function menu
    public void searchEventMenu() {
        int choice = 0;
        do {
            choice = eventUI.getSearchChoice(); // Assume this method prompts the user for their search choice

            switch (choice) {
                case 1:
                    singleSearch();
                    break;
                case 2:
                    batchSearch();
                    break;
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0); // Continue until user chooses to return to the main menu
    }

    public Event search(String eventID) {
        if (eventsMap.containsKey(eventID) && !(eventsMap.get(eventID).getIsDeleted())) {
            return eventsMap.get(eventID);
        } else {
            return null;
        }
    }

    public void singleSearch() {
        String id = eventUI.getEventID("search");
        Event event = search(id);
        if (event != null) {
            eventUI.printEventDetails(event);
        } else {
            MessageUI.displaySearchNotFoundMessage("Event " + id);
        }

    }

    public void batchSearch() {

        int choice = 0;
        do {
            choice = eventUI.getBatchSearchChoice(); // Get user's choice for batch search

            switch (choice) {
                case 1:
                    searchByName();
                    break;
                case 2:
                    searchByDateRange();
                    break;
                case 3:
                    searchByLocation();
                    break;
                case 4:
                    searchByVolunteerRange();
                    break;
                case 5:
                    searchByAmountRaised();
                    break;
                case 0:
                    MessageUI.displayExitToMainMenuMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0);
    }

    public void searchByName() {
        String str = eventUI.getSearchString();
        boolean found = false;
        ListInterface<Event> events = eventsMap.values();
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            if (!events.getEntry(i + 1).getIsDeleted()) {

                Event existingEvent = events.getEntry(i + 1);
                if (existingEvent.getName().toLowerCase().contains(str.toLowerCase())) {
                    found = true;
                    allFoundEvents.add(existingEvent);
                }
            }
        }

        if (!found) {
            System.out.println("\nNo events found.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }

    }

    public void searchByDateRange() {
        // Retrieve the start and end dates from the user
        LocalDate startDate = eventUI.inputStartDate();
        LocalDate endDate = eventUI.inputEndDate(startDate, "");

        // Ensure the start date is before the end date
        if (startDate.isAfter(endDate)) {
            System.out.println("Start date cannot be after end date.");
            return;
        }

        // Flag to check if any event was found
        boolean found = false;
        ListInterface<Event> events = eventsMap.values();

        // Create a SortedListInterface to store events that match the search criteria
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        // Iterate through all Event objects in the eventsMap
        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            Event event = events.getEntry(i + 1); // Retrieve the event

            if (!event.getIsDeleted()) {
                // Get the event's start and end dates
                LocalDate eventStartDate = event.getStartDate();
                LocalDate eventEndDate = event.getEndDate();

                // Check if the event's date range overlaps with the search range
                if ((eventStartDate.isBefore(endDate) || eventStartDate.isEqual(endDate))
                        && (eventEndDate.isAfter(startDate) || eventEndDate.isEqual(startDate))) {
                    // If overlapping, add the event to the found events list
                    allFoundEvents.add(event);
                    found = true;
                }
            }
        }

        // Inform the user if no events were found
        if (!found) {
            System.out.println("\nNo events found within the specified date range.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }
    }

    public void searchByLocation() {
        String str = eventUI.getSearchString();
        boolean found = false;
        ListInterface<Event> events = eventsMap.values();
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            if (!events.getEntry(i + 1).getIsDeleted()) {

                Event existingEvent = events.getEntry(i + 1);
                if (existingEvent.getLocation().toLowerCase().contains(str.toLowerCase())) {
                    found = true;
                    allFoundEvents.add(existingEvent);
                }
            }
        }

        if (!found) {
            System.out.println("\nNo events found.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }
    }

    public void searchByVolunteerRange() {
        int[] numVolunteers = eventUI.inputNumVolunteer();
        boolean found = false;
        ListInterface<Event> events = (ArrayList<Event>) eventsMap.values();
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            if (!events.getEntry(i + 1).getIsDeleted()) {
                Event existingEvent = events.getEntry(i + 1);

                // Check if the current number of volunteers is within the specified range
                int currentVolunteers = existingEvent.getCurrentVolunteer();
                if (currentVolunteers >= numVolunteers[0] && currentVolunteers <= numVolunteers[1]) {
                    allFoundEvents.add(existingEvent);
                    found = true;
                }
            }

        }

        if (!found) {
            System.out.println("\nNo events found within the volunteer range.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }

    }

    public void searchByAmountRaised() {
        double min = eventUI.inputMinValue();
        double max = eventUI.inputMaxValue(min);

        boolean found = false;
        ListInterface<Event> events = (ArrayList<Event>) eventsMap.values();
        SortedListInterface<Event> allFoundEvents = new SortedLinkedList<>();

        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            if (!events.getEntry(i + 1).getIsDeleted()) {
                Event existingEvent = events.getEntry(i + 1);

                //check if current amount raised is within specified range.
                if (existingEvent.getCurrentAmount() >= min && existingEvent.getCurrentAmount() <= max) {
                    allFoundEvents.add(existingEvent);
                    found = true;
                }
            }

        }

        if (!found) {
            System.out.println("\nNo events found within the amount raised range.\n");
        } else {
            eventUI.printEventHeader(allFoundEvents.getNumberOfEntries() + " event(s) found: ");
            eventUI.printAllEvents(getAllEvents(allFoundEvents));
        }

    }

    public void editEvent() {
        StackInterface<Event> undoStack = new LinkedStack<>();
        StackInterface<Event> redoStack = new LinkedStack<>();

        String id = eventUI.getEventID("edited");
        Event event = search(id);
        if (event != null) {
            eventUI.printEditHeader(id);
            eventUI.printEventDetails(event);
            boolean pastEvent = event.getEndDate().isBefore(LocalDate.now());
            boolean continueEditing = true;

            while (continueEditing) {
                int choice = eventUI.getEditChoice(!undoStack.isEmpty(), !redoStack.isEmpty(), pastEvent);
                Event originalEvent = new Event(event.getId(), event.getName(), event.getType(), event.getStartDate(), event.getEndDate(), event.getLocation(), event.getMinVolunteer(), event.getMaxVolunteer(), event.getCurrentVolunteer(), event.getCurrentAmount(), event.getGoalAmount(), event.getIsDeleted());
                

                switch (choice) {
                    case 1:
                        event.setName(eventUI.inputEventName("new"));
                        break;
                    case 2:
                        event.setType(eventUI.getNewEventType());
                        break;
                    case 3:
                        event.setStartDate(eventUI.getNewStartDate(event.getEndDate()));
                        break;

                    case 4:
                        event.setEndDate(eventUI.inputEndDate(event.getStartDate(), "new"));
                        break;
                    case 5:
                        event.setLocation(eventUI.inputLocation("new"));
                        break;
                    case 6:
                        int[] volunteers = new int[2];
                        event.setMinVolunteer(volunteers[0]);
                        event.setMaxVolunteer(volunteers[1]);
                        break;
                    case 7:
                        event.setGoalAmount(eventUI.inputGoalAmount("new"));
                        break;
                    case 8:
                        if (!undoStack.isEmpty()) {

                            // Save current state to redo stack
                            Event addToUndo = new Event(event.getId(), event.getName(), event.getType(), event.getStartDate(), event.getEndDate(), event.getLocation(), event.getMinVolunteer(), event.getMaxVolunteer(), event.getCurrentVolunteer(), event.getCurrentAmount(), event.getGoalAmount(), event.getIsDeleted());
                            redoStack.push(addToUndo);

                            // Pop the previous state from the undo stack
                            Event undoEvent = undoStack.pop();

                            
                            // Replace the current event in the eventsMap
                            eventsMap.put(id, undoEvent);

                            // Update the current event reference
                            event = new Event(undoEvent.getId(), undoEvent.getName(), undoEvent.getType(), undoEvent.getStartDate(), undoEvent.getEndDate(), undoEvent.getLocation(), undoEvent.getMinVolunteer(), undoEvent.getMaxVolunteer(), undoEvent.getCurrentVolunteer(), undoEvent.getCurrentAmount(), undoEvent.getGoalAmount(), undoEvent.getIsDeleted());

                            System.out.println("Undo successful. Current event details:");

                        } else {
                            System.out.println("No actions to undo.");
                        }

                        break;
                    case 9:
                        if (!redoStack.isEmpty()) {
                            // Save current state to undo stack
                            undoStack.push(event.deepCopy());

                            // Pop the previous state from the redo stack
                            Event redoEvent = redoStack.pop();

                            // Replace the current event in the eventsMap
                            eventsMap.put(id, redoEvent);

                            // Update the current event reference
                            event = redoEvent;
                            System.out.println("Redo successful. Current event details:");
                        } else {
                            System.out.println("No actions to undo.");
                        }

                        break;

                    default:
                        undoStack.clear();
                        redoStack.clear();
                        continueEditing = false;
                        break;

                }

                if (choice >= 1 && choice <= 7) {
                    undoStack.push(originalEvent);
                    
                    redoStack.clear();
                    eventsMap.put(id, event);
                    System.out.println("Event updated. Updated event details:");
                }

                eventUI.printEventDetails(event);
                eventDAO.saveToFile(eventsMap, "event.txt");

            }

        } else {
            MessageUI.displaySearchNotFoundMessage("Event " + id);
        }

    }

    //calling display all event function
    public void displayAllEvents() {
        eventUI.printEventHeader("Event List (Earliest to Latest):");
        eventUI.printAllEvents(getAllEvents(createSortedEventList()));
    }

    public void displayDeletedEvents() {
        eventUI.printEventHeader("Deleted Events");
        eventUI.printAllEvents(getAllDeletedEvents());

    }

    public String getAllEvents(SortedListInterface<Event> sortedEvents) {
        String str = "";

        for (int i = 0; i < sortedEvents.getNumberOfEntries(); i++) {
            if (!sortedEvents.getEntry(i + 1).getIsDeleted()) {
                str += sortedEvents.getEntry(i + 1) + "\n";
            }
        }
        return str;
    }

    public String getAllDeletedEvents() {
        String str = "";
        SortedListInterface<Event> sortedEvents = createSortedEventList();
        for (int i = 0; i < sortedEvents.getNumberOfEntries(); i++) {
            if (sortedEvents.getEntry(i + 1).getIsDeleted()) {
                str += sortedEvents.getEntry(i + 1) + "\n";
            }
        }
        return str;
    }

    private String generateEventID() {
        return String.format("E%05d", eventsMap.size() + 1);
    }

    public SortedListInterface<Event> createSortedEventList() {
        SortedListInterface<Event> sortedEvents = new SortedLinkedList<>();
        ListInterface<Event> events = eventsMap.values();
        for (int i = 0; i < events.getNumberOfEntries(); i++) {
            sortedEvents.add(events.getEntry(i + 1));
        }

        return sortedEvents;
    }
    
    public MapInterface<String, Event> getEventMap(){
        return eventsMap;
    }

    public static void main(String[] args) {
        EventMaintenance em = new EventMaintenance();
        em.runEventMaintenance();
    }
}
