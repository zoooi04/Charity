package dao;

import adt.ListHeap;
import java.io.*;

public class DAO_Heap<T extends Comparable<T>> {

    public void saveHeapToFile(ListHeap<T> heap, String fileName) {
        File file = new File(fileName);
        try (ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file))) {
            ooStream.writeObject(heap);
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        } catch (Exception ex) {
            System.err.println("An unexpected error occurred: " + ex.getMessage());
        }
    }

    // Retrieve Heap from file
    @SuppressWarnings("unchecked")
    public ListHeap<T> retrieveFromHeapFile(String fileName) {
        File file = new File(fileName);
        ListHeap<T> heap = null;
        try (ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file))) {
            heap = (ListHeap<T>) oiStream.readObject();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } catch (Exception ex) {
            
            System.err.println("An unexpected error occurred: " + ex.getMessage());
        }
        return heap;
    }
}

