/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package dao;

import adt.Heap;
import adt.ListHeap;
import entity.Donee;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DoneeInitializer {

    // Create Donee objects
    static Donee donee1 = new Donee("DN2400001", Donee.Type.INDIVIDUAL, "Alice Johnson", 18, LocalDate.of(2020, 1, 1), Donee.Gender.MALE, "0123456789", LocalDateTime.now());
    static Donee donee2 = new Donee("DN2400002", Donee.Type.ORGANISATION, "Bob Smith", 19, LocalDate.of(2020, 1, 2), Donee.Gender.MALE, "0123456780", LocalDateTime.now());
    static Donee donee3 = new Donee("DN2400003", Donee.Type.FAMILY, "Carol Davis", 20, LocalDate.of(2020, 1, 3), Donee.Gender.FEMALE, "0123456781", LocalDateTime.now());
    static Donee donee4 = new Donee("DN2400004", Donee.Type.INDIVIDUAL, "David Brown", 21, LocalDate.of(2020, 1, 4), Donee.Gender.MALE, "0123456782", LocalDateTime.now());
    static Donee donee5 = new Donee("DN2400005", Donee.Type.ORGANISATION, "Emily Wilson", 22, LocalDate.of(2020, 1, 5), Donee.Gender.FEMALE, "0123456783", LocalDateTime.now());
    static Donee donee6 = new Donee("DN2400006", Donee.Type.FAMILY, "Frank Taylor", 23, LocalDate.of(2020, 1, 6), Donee.Gender.MALE, "0123456784", LocalDateTime.now());
    static Donee donee7 = new Donee("DN2400007", Donee.Type.INDIVIDUAL, "Grace Anderson", 24, LocalDate.of(2020, 1, 7), Donee.Gender.FEMALE, "0123456785", LocalDateTime.now());
    static Donee donee8 = new Donee("DN2400008", Donee.Type.ORGANISATION, "Henry Thomas", 25, LocalDate.of(2020, 1, 8), Donee.Gender.MALE, "0123456786", LocalDateTime.now());
    static Donee donee9 = new Donee("DN2400009", Donee.Type.FAMILY, "Iris Moore", 26, LocalDate.of(2020, 1, 9), Donee.Gender.FEMALE, "0123456787", LocalDateTime.now());
    static Donee donee10 = new Donee("DN2400010", Donee.Type.INDIVIDUAL, "Jack Martin", 27, LocalDate.of(2020, 1, 10), Donee.Gender.MALE, "0123456788", LocalDateTime.now());
    static Donee donee11 = new Donee("DN2400011", Donee.Type.ORGANISATION, "Kelly Jackson", 28, LocalDate.of(2020, 1, 11), Donee.Gender.FEMALE, "0123456789", LocalDateTime.now());
    static Donee donee12 = new Donee("DN2400012", Donee.Type.FAMILY, "Leo Lim", 29, LocalDate.of(2020, 1, 12), Donee.Gender.MALE, "0123456790", LocalDateTime.now());
    static Donee donee13 = new Donee("DN2400013", Donee.Type.INDIVIDUAL, "Mia Harris", 30, LocalDate.of(2020, 1, 13), Donee.Gender.FEMALE, "0123456791", LocalDateTime.now());
    static Donee donee14 = new Donee("DN2400014", Donee.Type.ORGANISATION, "Noah Clark", 31, LocalDate.of(2020, 1, 14), Donee.Gender.MALE, "0123456792", LocalDateTime.now());
    static Donee donee15 = new Donee("DN2400015", Donee.Type.FAMILY, "Olivia Lewis", 32, LocalDate.of(2020, 1, 15), Donee.Gender.FEMALE, "0123456793", LocalDateTime.now());
    static Donee donee16 = new Donee("DN2400016", Donee.Type.INDIVIDUAL, "Peter n Jane", 33, LocalDate.of(2020, 1, 16), Donee.Gender.MALE, "0123456794", LocalDateTime.now());
    static Donee donee17 = new Donee("DN2400017", Donee.Type.ORGANISATION, "Rick Rall", 34, LocalDate.of(2020, 1, 17), Donee.Gender.FEMALE, "0123456795", LocalDateTime.now());
    static Donee donee18 = new Donee("DN2400018", Donee.Type.FAMILY, "Sam Allen", 35, LocalDate.of(2020, 1, 18), Donee.Gender.MALE, "0123456796", LocalDateTime.now());
    static Donee donee19 = new Donee("DN2400019", Donee.Type.INDIVIDUAL, "Tina Young", 36, LocalDate.of(2020, 1, 19), Donee.Gender.FEMALE, "0123456797", LocalDateTime.now());
    static Donee donee20 = new Donee("DN2400020", Donee.Type.ORGANISATION, "Mia Karris", 37, LocalDate.of(2020, 1, 20), Donee.Gender.MALE, "0123456798", LocalDateTime.now());

    // Initialize ListHeap with Donee objects
    public static ListHeap<Donee> initializeDonees() {
        ListHeap<Donee> doneeHeap = new Heap<>();
        doneeHeap.add(donee1);
        doneeHeap.add(donee2);
        doneeHeap.add(donee3);
        doneeHeap.add(donee4);
        doneeHeap.add(donee5);
        doneeHeap.add(donee6);
        doneeHeap.add(donee7);
        doneeHeap.add(donee8);
        doneeHeap.add(donee9);
        doneeHeap.add(donee10);
        doneeHeap.add(donee11);
        doneeHeap.add(donee12);
        doneeHeap.add(donee13);
        doneeHeap.add(donee14);
        doneeHeap.add(donee15);
        doneeHeap.add(donee16);
        doneeHeap.add(donee17);
        doneeHeap.add(donee18);
        doneeHeap.add(donee19);
        doneeHeap.add(donee20);
        return doneeHeap;
    }

    public static void main(String[] args) {
        DoneeInitializer initializer = new DoneeInitializer();
        ListHeap<Donee> doneeHeap = initializer.initializeDonees();

        // Print Donee objects in the heap
        System.out.println("\nDonee Heap:");
        while (!doneeHeap.isEmpty()) {
            Donee donee = doneeHeap.remove();
            // Format LocalDateTime for output
            System.out.println(donee.toString());
        }
    }
}
