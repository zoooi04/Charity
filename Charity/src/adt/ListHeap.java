/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author HP
 * @param <T>
 */
public interface ListHeap<T extends Comparable<T>> {

    int size();            // Returns the number of elements in the heap

    void add(T newEntry);  // Adds a new entry to the heap

    T remove();            // Removes and returns the root (max element) of the heap

    T peekMaxValue();      // Returns the root (max element) without removing it

    T getAnyValue(int index);  // Returns the element at a specified index

    void clear();          // Clears the heap

    boolean isEmpty();     // Checks if the heap is empty
}
