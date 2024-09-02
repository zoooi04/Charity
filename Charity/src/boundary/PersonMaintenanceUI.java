package boundary;

import entity.Person;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;
import utility.MessageUI;

/**
 * The PersonMaintenanceUI class provides an interface for managing person details.
 * It offers functionality to view, update, and input person details such as name, phone number,
 * birthday, gender, and activation status. It also provides menu options for selecting various 
 * update actions and validation for user inputs.
 *
 * This class uses Scanner for user input and provides methods to handle input and output 
 * operations related to Person objects. The inputs are validated to ensure they conform to expected formats
 * and constraints.
 * 
 * @author Ooi Choon Chong
 */
public class PersonMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    // <editor-fold defaultstate="collapsed" desc="menu">
    /**
     * Displays a menu for updating person details and prompts the user to choose an option.
     * 
     * @return The user's choice as an integer.
     */
    public int getUpdateMenuChoice() {
        System.out.println("\nUPDATE MENU");
        System.out.println("1. Name");
        System.out.println("2. Phone Number");
        System.out.println("3. Birthday");
        System.out.println("4. Gender");
        System.out.println("5. Activate / Deactivate");
        System.out.println("99. Next Page");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="output">
    /**
     * Displays a list of all persons.
     * 
     * @param outputStr The formatted string containing person details to be displayed.
     */
    public void listAllPerson(String outputStr) {
        System.out.println("\nList of Person:\n" + outputStr);
    } 
    
    /**
     * Prints detailed information of a given person.
     * 
     * @param person The Person object whose details are to be printed.
     */
    public void printPersonDetails(Person person) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        System.out.print("\n" + "=".repeat(48) + "\n");
        System.out.print("\tPerson Details");
        System.out.print("\n" + "=".repeat(48) + "\n");
        System.out.println("Person name              : " + person.getName());
        System.out.println("Person Age               : " + person.getAge());
        System.out.println("Person birthday          : " + person.getBirthday().format(dateFormatter));
        System.out.println("Person gender            : " + person.getGender());
        System.out.println("Person phoneNo           : " + person.getPhoneNo());
        System.out.println("Person registration date : " + person.getRegisterDate().format(dateTimeFormatter));
    }
    
    /**
     * Prints activation status of a person.
     * 
     * @param person The Person object whose activation status is to be printed.
     */
    public void printPersonActivate(Person person) {
        if (person.isIsActive()) {
            System.out.println("This person is now Activated");
        } else {
            System.out.println("This person is now De-activated");
        }
    }
    
    /**
     * Displays the menu for selecting gender.
    */
    public void personGenderMenu() {
        System.out.println("\nSelection of Gender");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.println("3. Other");
        System.out.print("Enter Selection: ");
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="input">
    /**
     * Prompts the user to input a person's name, ensuring it only contains alphabetic characters.
     * 
     * @return The inputted person name as a string.
    */
    public String inputPersonName() {
        Pattern pattern = Pattern.compile("[A-Za-z]*");
        System.out.print("Enter person name: ");
        while (!scanner.hasNext(pattern)) {
            scanner.next();
            System.out.print("Enter person name: ");
        }
        return scanner.nextLine();
    }
    
    /**
     * Prompts the user to input a person's age, ensuring it is a valid integer.(can be automated, not really needed)
     * 
     * @return The inputted age as an integer.
     */
    public int inputPersonAge() {
        System.out.print("Enter person Age: ");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Enter person Age: ");
        }
        return scanner.nextInt();
    }
    
    /**
     * Prompts the user to input a person's birthday, validating year, month, and day.
     * 
     * @return The inputted birthday as a LocalDate object.
    */
    public LocalDate inputPersonBirthday() {
        System.out.println("Enter person birthday:");

        int inputValueYear = inputYear();
        int inputValueMonth = inputMonth(inputValueYear);
        int inputValueDay = inputDay(inputValueYear, inputValueMonth);

        LocalDate birthday = LocalDate.of(inputValueYear, inputValueMonth, inputValueDay);

        // Check if the entered date is in the future
        while (birthday.isAfter(LocalDate.now())) {
            System.out.println("The entered date is in the future. Please enter a valid birthday.");
            inputValueYear = inputYear();
            inputValueMonth = inputMonth(inputValueYear);
            inputValueDay = inputDay(inputValueYear, inputValueMonth);
            birthday = LocalDate.of(inputValueYear, inputValueMonth, inputValueDay);
        }
        return birthday;
    }
    
    /**
    * Prompts the user to input a year for a person's birthday and validates the input.
    * The year must be within the range of 120 years before the current year to the current year.
    * 
    * @return The valid year entered by the user.
    */
    private int inputYear() {
        int minYear = LocalDate.now().getYear() - 120;
        int maxYear = LocalDate.now().getYear();
        int year;

        System.out.print("Enter person birthday (Year): ");
        do {
            while (!scanner.hasNextInt()) {
                scanner.next(); // Clear the invalid input
                System.out.print("Please enter a valid year (oldest: " + minYear + ", latest: " + maxYear + "): ");

            }
            year = scanner.nextInt();
            if (year < minYear || year > maxYear) {
                System.out.print("Please enter a valid year (oldest: " + minYear + ", latest: " + maxYear + "): ");
            }
        } while (year < minYear || year > maxYear);
        return year;
    }
    
    /**
    * Prompts the user to input a month for a person's birthday and validates the input.
    * The month must be between 1 and the current month if the year is the current year.
    * 
    * @param year The year for which the month is being entered.
    * @return The valid month entered by the user.
    */
    private int inputMonth(int year) {
        int month = 0;
        int maxMonth = 12;
        if (year == LocalDate.now().getYear()) {
            maxMonth = LocalDate.now().getMonthValue();
        }
        System.out.print("Enter person birthday (Month): ");
        do {
            while (!scanner.hasNextInt()) {
                scanner.next(); // Clear the invalid input
                System.out.print("Please enter a valid month (1-" + maxMonth + "): ");
            }
            month = scanner.nextInt();
            if (month >= 1 && month <= maxMonth) {
                if (year != LocalDate.now().getYear()) {
                    return month;
                } else {
                    if (month <= maxMonth) {
                        return month;
                    } else {
                        System.out.print("Please enter a valid month (1-" + maxMonth + "): ");
                    }
                }
            } else {
                System.out.print("Please enter a valid month (1-" + maxMonth + "): ");
            }
        } while (month < 1 || month > maxMonth);
        return month;
    }
    
    /**
    * Prompts the user to input a day for a person's birthday and validates the input.
    * The day must be between 1 and the maximum number of days in the month. Additionally,
    * it must be less than or equal to the current day if the year and month are the current year and month.
    * 
    * @param year The year for which the day is being entered.
    * @param month The month for which the day is being entered.
    * @return The valid day entered by the user.
    */
    private int inputDay(int year, int month) {
        int day = 1;
        YearMonth yearMonth = YearMonth.of(year, month);
        int maxDays = yearMonth.lengthOfMonth();
        System.out.print("Enter person birthday (Day): ");
        do {
            while (!scanner.hasNextInt()) {
                scanner.next(); // Clear the invalid input
                System.out.print("Please enter a valid day (1-" + maxDays + "): ");
            }
            day = scanner.nextInt();
            if (day >= 1 && day <= maxDays) {
                if (year != LocalDate.now().getYear()) {
                    return day;
                } else {
                    if (month != LocalDate.now().getMonthValue()) {
                        return day;
                    } else {
                        if (day < LocalDate.now().getDayOfMonth()) {
                            return day;
                        }
                    }
                }
            } else {
                System.out.print("Please enter a valid day (1-" + maxDays + "): ");
            }
        } while (day < 1 || day > maxDays);
        return day;
    }
    
    /**
     * Prompts the user to select a gender from a menu.
     * 
     * @return The selected Gender enum value.
    */
    public Person.Gender inputPersonGender() {
        personGenderMenu();
        while (!scanner.hasNextInt()) {
            scanner.next();
            personGenderMenu();
        }
        switch (scanner.nextInt()) {
            case 1:
                return Person.Gender.MALE;
            case 2:
                return Person.Gender.FEMALE;
            case 3:
                return Person.Gender.OTHER;
            default:
                MessageUI.displayInvalidChoiceMessage();
        }
        return null;
    }
    
    /**
     * Prompts the user to input a person's phone number, ensuring it is 10 or 11 digits.
     * 
     * @return The inputted phone number as a string.
    */
    public String inputPersonPhoneNo() {
        Pattern pattern = Pattern.compile("[0-9]{10,11}");
        System.out.print("Enter person phoneNo: ");

        while (!scanner.hasNext(pattern)) {
            scanner.next();
            System.out.print("Enter person phoneNo: ");

        }
        return scanner.nextLine();
    }
    // </editor-fold >
    
    /**
     * Collects all details for a new Person from user input.
     * 
     * @return A new Person object with the inputted details.
    */
    public Person inputPersonDetails() {
        String personName = inputPersonName();
        String personPhoneNo = inputPersonPhoneNo();
        LocalDate personBirthday = inputPersonBirthday();
        int personAge = LocalDate.now().getYear() - personBirthday.getYear();
        Person.Gender personGender = inputPersonGender();
        System.out.println();
        return new Person(personName, personAge, personBirthday, personGender, personPhoneNo);
    }

}
