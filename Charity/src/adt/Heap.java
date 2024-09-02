/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package adt;

import java.io.Serializable;

/**
 *
 * @author BEH JING HEN
 */

/**
 * Heap is a class that implements the ListHeap interface.
 * It represents a max-heap where the largest element is always at the root.
 * 
 * @param <T> The type of elements stored in the heap, which must be comparable.
 */
public class Heap<T extends Comparable<T>> implements ListHeap<T>, Serializable {

    private T[] heap; // Array to hold heap elements
    private int size; // Number of elements in the heap
    private static final int DEFAULT_CAPACITY = 5; // Default capacity of the heap

    
    /**
     * Default constructor that initializes the heap with the default capacity.
     */
    public Heap() {
        this(DEFAULT_CAPACITY);
    }
    
    
    /**
     * Constructor that initializes the heap with a specified capacity.
     * 
     * @param capacity The initial capacity of the heap.
     */
    public Heap(int capacity) {
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    @Override
    public int size() {
        return size; // Returns the number of elements in the heap
    }

    @Override
    public void add(T newEntry) {
        // Adds a new entry to the heap
        if (isFull()) {
            doubleHeapCapacity(); // Doubles the capacity if the heap is full
        }
        
        heap[size] = newEntry;
        size++;
        heapifyUp(size - 1); // Restores the heap property by shifting up

    }

    @Override
    public T remove() {
        // Removes and returns the root (max element) of the heap
        T maxValue = null;
        if (!isEmpty()) {
            maxValue = heap[0];
            heap[0] = heap[size - 1];
            heap[size - 1] = null; // Clear the last element
            size--;
            heapifyDown(0); // Restores the heap property by shifting down
        } else {
            return null;
        }

        return maxValue; // Returns the removed max element or null if the heap was empty
    }

    @Override
    public T peekMaxValue() {
        // Returns the root (max element) without removing it
        T maxValue = null;
        if (!isEmpty()) {
            maxValue = heap[0];
        }
        return maxValue;
    }

    @Override
    public T getAnyValue(int index) {
        // Returns the element at a specified index
        T anyValue = null;
        if (!isEmpty()) {
            anyValue = heap[index];
        }
        return anyValue;
    }

    @Override
    public void clear() {
    // Clears the heap by setting all elements to null
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        // Checks if the heap is empty
        return size == 0;
    }
    
     /**
     * Restores the heap property by shifting the element at index up.
     * 
     * @param index The index of the element to shift up.
     */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            // Change comparison to maintain max-heap property
            if (heap[index].compareTo(heap[parentIndex]) < 0) {
                swap(index, parentIndex);  // Swap if the current element is greater than its parent
                index = parentIndex;
            } else {
                break;
            }
        }
    }
    
    
    /**
     * Restores the heap property by shifting the element at index down.
     * 
     * @param index The index of the element to shift down.
     */
    private void heapifyDown(int index) {
        while (index < size) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
            int largestIndex = index;

            //Compare if the left child is greater than the current largest
            if (leftChildIndex < size && heap[leftChildIndex].compareTo(heap[largestIndex]) > 0) {
                largestIndex = leftChildIndex;
            }

            //Compare if the right child is greater than the current largest
            if (rightChildIndex < size && heap[rightChildIndex].compareTo(heap[largestIndex]) > 0) {
                largestIndex = rightChildIndex;
            }

            // If the largest is not the current index, swap and continue heapifying down
            if (largestIndex != index) {
                swap(index, largestIndex);
                index = largestIndex;
            } else {
                break;
            }
        }
    }
    
     /**
     * Swaps the elements at two indices in the heap.
     * 
     * @param i Index of the first element.
     * @param j Index of the second element.
     */
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
     /**
     * Checks if the heap is full.
     * 
     * @return true if the heap is full, false otherwise.
     */
    private boolean isFull() {
        return size == heap.length;
    }
    
     /**
     * Doubles the capacity of the heap when it is full.
     */
    private void doubleHeapCapacity() {
        T[] newHeap = (T[]) new Comparable[heap.length * 2];

        //Copy elements from the old heap to the new heap
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }

}
