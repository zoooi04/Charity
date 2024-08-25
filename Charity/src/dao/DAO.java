package dao;

import adt.*;
import entity.Donor;
import java.io.*;

/**
 *
 * @author Ooi Choon Chong
 * @param <T>
 */
public class DAO<T> {

    public void saveToFile(ListInterface<T> entityList, String fileName) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(entityList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        } catch (Exception ex) {
            System.err.println("An unexpected error occurred: " + ex.getMessage());
        }
    }

    public ListInterface<T> retrieveFromFile(String fileName) {
        File file = new File(fileName);
        ListInterface<T> entityList = new ArrayList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            entityList = (ArrayList<T>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } catch (Exception ex) {
            System.err.println("An unexpected error occurred: " + ex.getMessage());
        } finally {
            return entityList;
        }
    }
}
