package dao;

/**
 *
 * @author andrew
 */
import adt.*;
import entity.Event;
import entity.Person.Gender;
import entity.Volunteer;
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
            ListInterface<Event> eventArr = eventMap.values(); // Retrieve the list of values from the HashMap

            for (int i = 0; i < eventArr.getNumberOfEntries(); i++) {
                String line = formatEvent(eventArr.getEntry(i + 1));
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

    public MapInterface<String, Volunteer> retrieveVolunteers(String fileName) {
        MapInterface<String, Volunteer> volunteersMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line based on the delimiter '|'
                String[] parts = line.split("\\|");
                String id = parts[0];
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                LocalDate birthday = LocalDate.parse(parts[3]);
                Gender gender = Gender.valueOf(parts[4].toUpperCase());
                String phoneNo = parts[5];

                // Create a new Volunteer object
                Volunteer volunteer = new Volunteer(id, name, age, birthday, gender, phoneNo);

                // Add to the HashMap using the id as the key
                volunteersMap.put(id, volunteer);
            }
        } catch (IOException e) {
            System.out.println("Error reading volunteers from file: " + e.getMessage());
        }

        return volunteersMap;
    }

    public GraphInterface<String, Integer> createGraphOfAssignedVolunteersToEvent(String fileName) {
        GraphInterface<String, Integer> graph = new WeightedGraph<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                //split line into components bassed on the delimiter '|'
                String[] parts = line.split("\\|");
                String eventID = parts[0];
                String volunteerID = parts[1];
                Integer availabilityUnit = Integer.parseInt(parts[2]);

                //create a vertex of corresponding eventID
                graph.addVertex(eventID);

                //create a vertex of corresponding volunteerID
                graph.addVertex(volunteerID);

                // Add an edge between the event and the volunteer with the availability unit as the weight
                graph.addUndirectedEdge(eventID, volunteerID, availabilityUnit);

            }
        } catch (IOException e) {
            System.out.println("Error reading eventVolunteers from file: " + e.getMessage());
        }

        return graph;
    }

    public void saveGraphToFile(GraphInterface<String, Integer> eventGraph, String filename) {
     try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
         ListInterface<String> allVertices = eventGraph.getAllVertexObjects();
         
         for(int i = 0 ; i < allVertices.getNumberOfEntries(); i++){
             if(allVertices.getEntry(i+1).startsWith("E")){
                 ListInterface <String> allNeighbours = eventGraph.getNeighbours(allVertices.getEntry(i+1));
                 
                 for(int j = 0; j < allNeighbours.getNumberOfEntries();j++){
                     String output = allVertices.getEntry(i+1) + "|" + allNeighbours.getEntry(j+1) + "|" + eventGraph.getEdgeWeight(allVertices.getEntry(i+1), allNeighbours.getEntry(j+1));
                     writer.write(output);
                     writer.newLine();
                 }
                 
             }
         }
     }catch(IOException e){
         System.out.println("Error writing graph to file: "+ e.getMessage());
     }
        
    }

}
