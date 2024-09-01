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

public class EventMaintenanceUI {

    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public int getChoice(boolean emptyUndo, boolean emptyRedo) {
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
        if (!emptyUndo) {
            System.out.println("[10] Undo: Readding removed volunteers back to their initially assigned events");
        }

        if (!emptyRedo) {
            System.out.println("[11] Redo: Removing readded volunteers from their initially assigned events");
        }
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
    
    public int getReportChoice(){
        System.out.println();
        System.out.println("------- Report -------");
        System.out.println("[1] Event Performance Report");
        System.out.println("[2] Top 5 Donors in Events Report");
        System.out.println("[3] Event Contribution by Top Donors Report");
        System.out.println("[0] Back");
        System.out.print("Your choice: ");
        int ch = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return ch;
    }
    
    public int getEventPerformanceReportChoice(){
        System.out.println();
        System.out.println("------- Event Performance Report -------");
        System.out.println("[1] Specific Event");
        System.out.println("[2] Events Occuring in Monthly Range");
        System.out.println("[0] Back");
        System.out.print("Your choice: ");
        int ch = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return ch;
    }
    
    public int getTopFiveDonorReportChoice(){
        System.out.println();
        System.out.println("------- Top 5 Donors in Event Report -------");
        System.out.println("[1] Specific Event");
        System.out.println("[2] Across All Events in Monthly Range");
        System.out.println("[3] Across All Events in a Year");
        System.out.println("[0] Back");
        System.out.print("Your choice: ");
        int ch = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return ch;
    }
    
    

    public int getVolunteerListingChoice() {
        System.out.println();
        System.out.println("------- List All Events For Volunteer -------");
        System.out.println("[1] Single Volunteer");
        System.out.println("[2] Multiple Volunteers");
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
        return scanner.nextLine().trim();
    }

    public String getEventIDForVolunteer(String additionaltext) {
        System.out.print("Please provide eventID of your desired event " + additionaltext + ": ");
        return scanner.nextLine().trim();
    }

    public String getVolunteerID(String additionaltext) {
        System.out.print("Please provide volunteerID of your desired volunteer " + additionaltext + ": ");
        return scanner.nextLine().trim();
    }

    public String inputEventName() {
        System.out.print("Enter event name (type 'cancel' to cancel adding): ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("cancel")) {
            return null;
        }

        return input;
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
        boolean validInput = false;

        do {
            System.out.println("Select Event Type:");
            System.out.println("[1] Community Service");
            System.out.println("[2] Charity");
            System.out.println("[3] Fundraising");
            System.out.println("[4] Cancel Adding Event");
            System.out.println("[0] Go Back to Previous Step");
            System.out.print("Your choice: ");
            ch = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            System.out.println();

            switch (ch) {
                case 1:
                    result = 'S';
                    validInput = true;
                    break;
                case 2:
                    result = 'C';
                    validInput = true;
                    break;
                case 3:
                    result = 'F';
                    validInput = true;
                    break;
                case 0:
                    result = 'B'; // 'B' for back
                    validInput = true;
                    break;
                case 4:
                    result = 'X'; // 'X' for cancel
                    validInput = true;
                    break;
                default:
                    System.out.println("Invalid Choice!\n");
            }
        } while (!validInput);

        return result;
    }

    public String inputLocation(String output) {
        System.out.print("Enter " + output + " event location or type 'cancel' to cancel, 'back' to go to the previous step: ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("cancel")) {
            return null;  // Return null to indicate cancellation
        } else if (input.equalsIgnoreCase("back")) {
            return "back";  // Return "back" to indicate going back
        }

        return input;  // Return the entered location
    }

    public int[] inputNumVolunteer() {
        int minVolunteers = -1;
        int maxVolunteers = -1;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.print("Enter minimum number of volunteers (or type '-1' for back or '-2' for cancel): ");
                String minInput = scanner.nextLine().trim();

                // Handle "-1" and "-2" inputs
                if (minInput.equals("-1")) {
                    return new int[]{-1};  // -1 to represent "back"
                } else if (minInput.equals("-2")) {
                    return null;  // null to represent "cancel"
                }

                minVolunteers = Integer.parseInt(minInput);
                if (minVolunteers < 0) {
                    System.out.println("Minimum number of volunteers must be greater than or equal to 0. Please try again.");
                    continue;
                }

                System.out.print("Enter maximum number of volunteers (or type '-1' for back or '-2' for cancel): ");
                String maxInput = scanner.nextLine().trim();

                // Handle "-1" and "-2" inputs for maximum volunteers
                if (maxInput.equals("-1")) {
                    return new int[]{-1};  // -1 to represent "back"
                } else if (maxInput.equals("-2")) {
                    return null;  // null to represent "cancel"
                }

                maxVolunteers = Integer.parseInt(maxInput);
                if (maxVolunteers < minVolunteers) {
                    System.out.println("Maximum number of volunteers must be greater than or equal to the minimum. Please try again.");
                } else {
                    isValidInput = true;  // Valid input received, exit loop
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }

        return new int[]{minVolunteers, maxVolunteers};
    }

    public Double inputGoalAmount(String output) {
        Double goalAmount = null;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print("Enter " + output + " goal amount (or type 'back' to go back, 'cancel' to cancel): RM");
            String input = scanner.nextLine().trim();

            // Handle "back" and "cancel" inputs
            if (input.equalsIgnoreCase("back")) {
                return -1.0;  // Use -1.0 to indicate going back
            } else if (input.equalsIgnoreCase("cancel")) {
                return null;  // Use null to indicate cancellation
            }

            try {
                goalAmount = Double.parseDouble(input);
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
            System.out.print("Enter event start date (dd/MM/yyyy), or type 'cancel' to cancel adding, 'back' to go back to previous step: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("cancel")) {
                // Convert "cancel" to a specific date to indicate cancellation
                return LocalDate.of(1, 1, 1);
            } else if (input.equals("back")) {
                // Convert "back" to a specific date to indicate going back
                return LocalDate.of(1, 1, 2);
            } else {
                try {
                    // Attempt to parse the date input
                    startDate = LocalDate.parse(input, formatter);
                    valid = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please try again.");
                }
            }
        }

        return startDate;
    }

    public LocalDate inputEndDate(LocalDate startDate, String output) {
        LocalDate endDate = null;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter " + output + " event end date (dd/MM/yyyy) or type 'cancel' to cancel, 'back' to go to the previous step: ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("cancel")) {
                    return LocalDate.of(1, 1, 1); // Represent 'cancel' with 01/01/0001
                } else if (input.equalsIgnoreCase("back")) {
                    return LocalDate.of(1, 1, 2); // Represent 'back' with 01/01/0002
                }

                endDate = LocalDate.parse(input, formatter);
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

        String name = "";
        String type = "";
        LocalDate startDate = null;
        LocalDate endDate = null;
        String location = "";
        int[] numVolunteers = new int[2];
        Double goalAmount = 0.0;

        int step = 1;
        boolean isComplete = false;

        while (!isComplete) {
            switch (step) {
                case 1:
                    name = inputEventName();
                    if (name == null) {
                        return null;  // Cancel the process
                    }
                    if (name.equalsIgnoreCase("back")) {
                        System.out.println("Already at the first step. Cannot go back.");
                    } else {
                        step++;
                    }
                    break;

                case 2:
                    char typeChoice = inputEventType();
                    if (typeChoice == 'X') {
                        return null;  // Cancel the process
                    }
                    if (typeChoice == 'B') {
                        step--;
                    } else {
                        type = switch (typeChoice) {
                            case 'S' ->
                                "CS";
                            case 'C' ->
                                "Charity";
                            default ->
                                "Fund";    //'F'
                        };
                        step++;
                    }
                    break;

                case 3:
                    startDate = inputStartDate();
                    if (startDate.equals(LocalDate.of(1, 1, 1))) {
                        // Handle cancellation
                        return null;  // Cancel the process
                    } else if (startDate.equals(LocalDate.of(1, 1, 2))) {
                        // Handle going back to the previous step
                        step--;  // Go back to the previous step
                    } else {
                        // Proceed to the next step if a valid date is entered
                        step++;
                    }
                    break;

                case 4:
                    endDate = inputEndDate(startDate, "");
                    if (endDate.equals(LocalDate.of(1, 1, 1))) {
                        return null;  // Cancel the process
                    }
                    if (endDate.equals(LocalDate.of(1, 1, 2))) {
                        step--;  // Go back to the previous step
                    } else {
                        step++;  // Proceed to the next step
                    }
                    break;

                case 5:
                    location = inputLocation("");
                    if (location == null) {
                        return null;  // Cancel the process
                    }
                    if (location.equalsIgnoreCase("back")) {
                        step--;
                    } else {
                        step++;
                    }
                    break;

                case 6:
                    numVolunteers = inputNumVolunteer();
                    if (numVolunteers == null) {
                        return null;  // Cancel the process
                    }
                    if (numVolunteers.length > 0 && numVolunteers[0] == -1) {
                        step--;  // Go back to the previous step
                    } else {
                        step++;  // Proceed to the next step
                    }
                    break;

                case 7:
                    goalAmount = inputGoalAmount("");
                    if (goalAmount == null) {
                        return null;  // Cancel the process
                    }
                    if (goalAmount == -1.0) {
                        step--;  // Go back to the previous step
                    } else {
                        isComplete = true;
                    }
                    break;

            }
        }

        return new Event(eventID, name, type, startDate, endDate, location, numVolunteers[0], numVolunteers[1], 0, 0.00, goalAmount);
    }

    //for edit
    public String inputEditEventName() {
        System.out.print("Enter new event name: ");
        return scanner.nextLine();
    }

    public String inputEditLocation() {
        System.out.print("Enter new event location: ");
        return scanner.nextLine();
    }

    public LocalDate inputEditEndDate(LocalDate startDate) {
        LocalDate endDate = null;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter new event end date (dd/MM/yyyy): ");
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

    public int[] inputEditNumVolunteer() {

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

    public double inputEditGoalAmount() {
        double goalAmount = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                // Prompt the user to enter the goal amount
                System.out.print("Enter new goal amount: RM");
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
