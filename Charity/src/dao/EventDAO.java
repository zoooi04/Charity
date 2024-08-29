package dao;

/**
 *
 * @author andrew
 */
import adt.*;
import entity.Event;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class EventDAO {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MapInterface<String, Event> retrieveFromFile(String fileName) {
        MapInterface<String, Event> eventList = new HashMap<>();
        //SortedListInterface<Event> eventList = new SortedLinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by the "|" delimiter
                String[] fields = line.split("\\|");

                if (fields.length == 12) { // Ensure there are enough fields for an Event
                    // Parse and create an Event object
                    Event event = parseEvent(fields);
                    // Add the event to the list
                    eventList.put(event.getId(), event);
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return eventList;
    }

    private Event parseEvent(String[] fields) {
        // Create and return an Event object from the fields
        String id = fields[0];
        String name = fields[1];
        String type = fields[2];
        LocalDate startDate = LocalDate.parse(fields[3], DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(fields[4], DATE_FORMATTER);
        String location = fields[5];
        int minVolunteer = Integer.parseInt(fields[6]);
        int maxVolunteer = Integer.parseInt(fields[7]);
        int currentVolunteer = Integer.parseInt(fields[8]);
        double currentAmount = Double.parseDouble(fields[9]);
        double goalAmount = Double.parseDouble(fields[10]);
        boolean isDeleted = fields[11].equals("true");

        return new Event(id, name, type, startDate, endDate, location, minVolunteer, maxVolunteer, currentVolunteer, currentAmount, goalAmount, isDeleted);
    }

    // Method to save Event objects to a file
    public void saveToFile(MapInterface<String, Event> eventMap, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            ArrayList<Event> eventArr = eventMap.values(); // Retrieve the list of values from the HashMap

            
            for(int i = 0; i < eventArr.getNumberOfEntries();i++){
                String line = formatEvent(eventArr.getEntry(i+1));
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /*
    public void saveToFile(SortedListInterface<Event> eventList, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < eventList.getNumberOfEntries(); i++) {
                Event event = eventList.getEntry(i + 1);
                String line = formatEvent(event);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
*/
    // Helper method to format an Event object as a "|" delimited string
    private String formatEvent(Event event) {
        return String.join("|",
                event.getId(),
                event.getName(),
                event.getType(),
                event.getStartDate().format(DATE_FORMATTER),
                event.getEndDate().format(DATE_FORMATTER),
                event.getLocation(),
                String.valueOf(event.getMinVolunteer()),
                String.valueOf(event.getMaxVolunteer()),
                String.valueOf(event.getCurrentVolunteer()),
                String.format("%.2f", event.getCurrentAmount()),
                String.format("%.2f", event.getGoalAmount()),
                String.valueOf(event.getIsDeleted())
        );
    }
}
