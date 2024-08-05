package boundary;

import entity.Person;
import java.time.LocalDate;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author Ooi Choon Chong
 */
public class PersonMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

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

    public String inputPersonName() {
        System.out.print("Enter person name: ");
        String inputValue = scanner.nextLine();
        return inputValue;
    }

    // can be automated, not really needed
    public int inputPersonAge() {
        System.out.print("Enter person Age: ");
        int inputValue = scanner.nextInt();
        scanner.nextLine();
        return inputValue;
    }

    public LocalDate inputPersonBirthday() {
        System.out.print("Enter person birthday: \n");
        System.out.print("Enter person birthday (Day): ");
        int inputValueDay = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter person birthday (Month): ");
        int inputValueMonth = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter person birthday (Year): ");
        int inputValueYear = Integer.parseInt(scanner.nextLine());
        LocalDate birthday = LocalDate.of(inputValueYear, inputValueMonth, inputValueDay);
        return birthday;
    }

    public Person.Gender inputPersonGender() {
        Person.Gender inputGender = null;

        do {
            System.out.print("Enter person gender: ");
            String inputValue = scanner.nextLine();
            switch (inputValue) {
                case "m", "M" -> inputGender = Person.Gender.MALE;
                case "f", "F" -> inputGender = Person.Gender.FEMALE;
                case "o", "O" -> inputGender = Person.Gender.OTHER;
                default -> MessageUI.displayInvalidChoiceMessage();
            }
        } while (inputGender == null);
        return inputGender;
    }

    public String inputPersonPhoneNo() {
        System.out.print("Enter person phoneNo: ");
        String inputValue = scanner.nextLine();
        return inputValue;
    }

    public Person inputPersonDetails() {
        String personName = inputPersonName();
        int personAge = inputPersonAge();
        LocalDate personBirthday = inputPersonBirthday();
        Person.Gender personGender = inputPersonGender();
        String personPhoneNo = inputPersonPhoneNo();
        System.out.println();
        return new Person(personName, personAge, personBirthday, personGender, personPhoneNo);
    }
}
