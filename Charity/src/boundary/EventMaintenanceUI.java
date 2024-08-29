package boundary;

/**
 *
 * @author AndrewPhengQiJinn
 */
import entity.Volunteer;
import entity.Event;
import entity.Event_Volunteer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import adt.*;

public class EventMaintenanceUI {

    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public int getChoice() {
        System.out.println("------- Event Management -------");
        System.out.println("[1] Add a new event");
        System.out.println("[2] Remove an event");
        System.out.println("[3] Restore a deleted event");
        System.out.println("[4] Search for an event");
        System.out.println("[5] Amend an event");
        System.out.println("[6] List all events");
        System.out.println("[7] Remove an event from a volunteer");
        System.out.println("[8] List all events for a volunteer");
        System.out.println("[9] Generate report");
        System.out.println("[0] Back to Main Menu");
        System.out.print("Your choice: ");
        int ch = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return ch;
    }

    public int getDeleteChoice() {
        System.out.println();
        System.out.println("------- Delete Event -------");
        System.out.println("[1] Single Delete");
        System.out.println("[2] Batch Delete");
        System.out.println("[0] Back to Event Menu");
        System.out.print("Your choice: ");
        int ch = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return ch;
    }

    public int getSearchChoice() {
        System.out.println();
        System.out.println("------- Search Event -------");
        System.out.println("[1] Single Search");
        System.out.println("[2] Batch Search");
        System.out.println("[0] Back to Event Menu");
        System.out.print("Your choice: ");
        int ch = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return ch;
    }

    public int getBatchSearchChoice() {
        System.out.println();
        System.out.println("------- Batch Search -------");
        System.out.println("[1] Name");
        System.out.println("[2] Date Range");
        System.out.println("[3] Location");
        System.out.println("[4] Current Volunteer Range");
        System.out.println("[5] Range of Amount Raised");
        System.out.println("[0] Back to Event Menu");
        System.out.print("Your choice: ");
        int ch = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return ch;
    }

    public String getSearchString() {
        System.out.println();
        System.out.print("Enter search string:");
        return scanner.nextLine().trim();
    }

    public char getDeleteConfirmation() {
        char response = ' ';

        while (response != 'Y' && response != 'N') {
            System.out.print("Confirm delete? (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1) {
                response = input.charAt(0);
            }

            if (response != 'Y' && response != 'N') {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }

        return response;
    }

    public char getRestoreConfirmation() {
        char response = ' ';

        while (response != 'Y' && response != 'N') {
            System.out.print("Confirm restore? (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1) {
                response = input.charAt(0);
            }

            if (response != 'Y' && response != 'N') {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }

        return response;
    }

    public void printEditHeader(String str) {
        System.out.println();
        System.out.println("-------" + "Editing Event " + str + "-------");
    }

    public int getEditChoice(boolean undoable, boolean redoable, boolean pastEvent) {
        System.out.println();
        System.out.println("[1] Event Name");
        System.out.println("[2] Event Type");
        System.out.println("[3] Start Date");
        System.out.println("[4] End Date");
        System.out.println("[5] Location");
        System.out.println("[6] Min and Max Volunteer");

        if (!pastEvent) {
            System.out.println("[7] Goal Amount");
        }

        if (undoable) {
            System.out.println("[8] Undo");
        }

        if (redoable) {
            System.out.println("[9] Redo");
        }
        System.out.println("[0] Complete Editing and Go Back to Main Menu");
        System.out.print("Your choice: ");
        int ch = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return ch;

    }

    public void printEventDetails(Event event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println();
        System.out.println("Event ID: " + event.getId());
        System.out.println("Event Name: " + event.getName());
        System.out.println("Event Type: " + event.getType());
        System.out.println("Start Date: " + event.getStartDate().format(formatter));
        System.out.println("End Date: " + event.getEndDate().format(formatter));
        System.out.println("Location: " + event.getLocation());
        System.out.println("Min Volunteers: " + event.getMinVolunteer());
        System.out.println("Max Volunteers: " + event.getMaxVolunteer());
        System.out.println("Current Volunteers: " + event.getCurrentVolunteer());
        System.out.println("Current Amount: RM" + String.format("%.2f", event.getCurrentAmount()));
        System.out.println("Goal Amount: RM" + String.format("%.2f", event.getGoalAmount()));
        System.out.println();
    }

    public String getEventID(String additionaltext) {
        System.out.print("Please provide eventID of the event to be " + additionaltext + ": ");
        return scanner.nextLine();
    }

    public String inputEventName(String output) {
        System.out.print("Enter " + output + " event name: ");
        return scanner.nextLine();
    }

    public String getNewEventType() {
        int ch = 0;
        String result = "";
        do {
            System.out.println("Select New Event Type:");
            System.out.println("[1] Community Service");
            System.out.println("[2] Charity");
            System.out.println("[3] Fundraising");
            System.out.print("Your choice: ");
            ch = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character
            System.out.println();

            switch (ch) {
                case 1:
                    result = "CS";  // Community Service
                    break;
                case 2:
                    result = "Charity";  // Charity
                    break;
                case 3:
                    result = "Fund";  // Fundraising
                    break;
                default:
                    System.out.println("Invalid Choice! Please select a valid event type.\n");
            }
        } while (ch < 1 || ch > 3);  // Ensure a valid choice is made

        return result;
    }

    public char inputEventType() {
        int ch = 0;
        char result = ' ';
        do {
            System.out.println("Select Event Type:");
            System.out.println("[1] Community Service");
            System.out.println("[2] Charity");
            System.out.println("[3] Fundraising");
            System.out.print("Your choice: ");
            ch = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            switch (ch) {
                case 1:
                    result = 'S';
                    break;
                case 2:
                    result = 'C';
                    break;
                case 3:
                    result = 'F';
                    break;
                default:
                    System.out.println("Invalid Choice!\n");

            }
        } while (ch < 1 && ch > 3);
        return result;
    }

   

    public String inputLocation(String output) {
        System.out.print("Enter " + output + " event location: ");
        return scanner.nextLine();
    }

    public int[] inputNumVolunteer() {

        int minVolunteers = -1;
        int maxVolunteers = -1;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                // Prompt for minimum number of volunteers
                System.out.print("Enter minimum number of volunteers: ");
                minVolunteers = Integer.parseInt(scanner.nextLine());

                if (minVolunteers < 0) {
                    System.out.println("Minimum number of volunteers must be greater than or equal to 0. Please try again.");
                    continue;
                }

                // Prompt for maximum number of volunteers
                System.out.print("Enter maximum number of volunteers: ");
                maxVolunteers = Integer.parseInt(scanner.nextLine());

                if (maxVolunteers < minVolunteers) {
                    System.out.println("Maximum number of volunteers must be greater than or equal to the minimum. Please try again.");
                } else {
                    isValidInput = true;  // Valid input received, exit loop
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer. ");
            }
        }

        return new int[]{minVolunteers, maxVolunteers};
    }

    public double inputGoalAmount(String output) {
        double goalAmount = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                // Prompt the user to enter the goal amount
                System.out.print("Enter " + output + " goal amount: RM");
                goalAmount = Double.parseDouble(scanner.nextLine().trim());

                if (goalAmount > 0) {
                    isValidInput = true;  // Valid input, exit the loop
                } else {
                    System.out.println("The goal amount must be greater than 0. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric value.");
            }
        }

        return goalAmount;
    }

    // Method to input the minimum target amount
    public double inputMinValue() {
        double minValue = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Insert minimum target amount value: ");
            if (scanner.hasNextDouble()) {
                minValue = scanner.nextDouble();
                if (minValue >= 0) {
                    validInput = true;
                } else {
                    System.out.println("The minimum target amount must be greater or equal to 0. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        return minValue;
    }

    // Method to input maximum target amount
    public double inputMaxValue(double firstThresholdValue) {
        double maxValue = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Insert maximum target amount value: ");
            if (scanner.hasNextDouble()) {
                maxValue = scanner.nextDouble();
                if (maxValue > firstThresholdValue) {
                    validInput = true;
                } else {
                    System.out.println("The maximum target amount must be greater than the minimum target amount. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        return maxValue;
    }

    public LocalDate getNewStartDate(LocalDate endDate) {
        LocalDate startDate = null;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter new event start date (dd/MM/yyyy): ");
                startDate = LocalDate.parse(scanner.nextLine(), formatter);

                //check if start date same or before end date
                if (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
                    valid = true;
                } else {
                    System.out.println("Start date must be the same or earlier than the end date, " + endDate.format(formatter) + ". Please try again.");
                }

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");

            }
        }
        return startDate;
    }

    public LocalDate inputStartDate() {
        LocalDate startDate = null;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter event start date (dd/MM/yyyy): ");
                startDate = LocalDate.parse(scanner.nextLine(), formatter);
                valid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        return startDate;
    }

    public LocalDate inputEndDate(LocalDate startDate, String output) {
        LocalDate endDate = null;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter " + output + " event end date (dd/MM/yyyy): ");
                endDate = LocalDate.parse(scanner.nextLine(), formatter);
                if (endDate.isBefore(startDate)) {
                    System.out.println("End date cannot be before start date. Please try again.");
                } else {
                    valid = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        return endDate;
    }

    public Event AddNewEventUI(String eventID) {
        System.out.println();
        System.out.println("------- Add a new event -------");
        System.out.println("Event ID: " + eventID);
        String name = inputEventName("");
        String type = "";
        type = switch (inputEventType()) {
            case 'S' ->
                "CS";
            case 'C' ->
                "Charity";
            default ->
                "Fund";    //'F'
        };
        LocalDate startDate = inputStartDate();
        LocalDate endDate = inputEndDate(startDate, "");
        String location = inputLocation("");
        int[] numVolunteers = inputNumVolunteer();
        double goalAmount = inputGoalAmount("");

        return new Event(eventID, name, type, startDate, endDate, location, numVolunteers[0], numVolunteers[1], 0, 0.00, goalAmount);

    }

    public void printEventHeader(String title) {
        String outputStr = "";
        outputStr += "\n" + title + "\n";
        outputStr += "\n" + "=".repeat(217) + "\n";
        outputStr += String.format("%-10s%-30s%-12s%-12s%-12s%-50s%-15s%-15s%-15s%-25s%-25s",
                "EventID",
                "EventName",
                "Type",
                "From",
                "To",
                "Location",
                "MinVolunteer",
                "MaxVolunteer",
                "CurrVolunteer",
                "ObtainedAmount(RM)",
                "TargetAmount(RM)"
        );
        outputStr += "\n" + "=".repeat(217) + "\n";
        System.out.print(outputStr);
    }

    public void printAllEvents(String str) {
        System.out.println(str);
    }

}
