package boundary;

import entity.Person;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;
import java.util.regex.Pattern;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 */
public class PersonMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    // <editor-fold defaultstate="collapsed" desc="menu">
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
    public void listAllPerson(String outputStr) {
        System.out.println("\nList of Person:\n" + outputStr);
    }

    public void printPersonDetails(Person person) {
        System.out.println("Person Details");
        System.out.println("Person name: " + person.getName());
        System.out.println("Person Age: " + person.getAge());
        System.out.println("Person birthday:" + person.getBirthday());
        System.out.println("Person gender:" + person.getGender());
        System.out.println("Person phoneNo:" + person.getPhoneNo());
        System.out.println("Person egistration date:" + person.getRegisterDate());
    }

    public void printPersonActivate(Person person) {
        if (person.isIsActive()) {
            System.out.println("This person is now Activated");
        } else {
            System.out.println("This person is now De-activated");
        }
    }

    public void personGenderMenu() {
        System.out.println("\nSelection of Gender");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.println("3. Other");
        System.out.print("Enter Selection: ");
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="input">
    public String inputPersonName() {
        Pattern pattern = Pattern.compile("[A-Za-z]*");
        System.out.print("Enter person name: ");
        while (!scanner.hasNext(pattern)) {
            scanner.next();
            System.out.print("Enter person name: ");
        }
        return scanner.nextLine();
    }

    // can be automated, not really needed
    public int inputPersonAge() {
        System.out.print("Enter person Age: ");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Enter person Age: ");
        }
        return scanner.nextInt();
    }

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
