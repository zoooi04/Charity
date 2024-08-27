package dao;

import adt.Heap;
import adt.ListHeap;
import entity.Donee;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DoneeInitializer {

    // Create Donee objects
    private Donee donee1 = new Donee("DD2400001", Donee.Type.INDIVIDUAL, "Name 1", 18, LocalDate.of(2020, 1, 1), Donee.Gender.MALE, "0123456789", LocalDateTime.now());
    private Donee donee2 = new Donee("DD2400002", Donee.Type.ORGANISATION, "Name 2", 19, LocalDate.of(2020, 1, 2), Donee.Gender.MALE, "0123456780", LocalDateTime.now());
    private Donee donee3 = new Donee("DD2400003", Donee.Type.FAMILY, "Name 3", 20, LocalDate.of(2020, 1, 3), Donee.Gender.FEMALE, "0123456781", LocalDateTime.now());

    // Initialize ListHeap with Donee objects
    public ListHeap<Donee> initializeDonees() {
        ListHeap<Donee> doneeHeap = new Heap<>();
        doneeHeap.add(donee1);
        doneeHeap.add(donee2);
        doneeHeap.add(donee3);
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
