package adt;

import java.io.Serializable;

public class Heap<T extends Comparable<T>> implements ListHeap<T>, Serializable {

    private T[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 5;

    public Heap() {
        this(DEFAULT_CAPACITY);
    }

    public Heap(int capacity) {
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T newEntry) {

        if (isFull()) {
            doubleHeapCapacity();
        }
        heap[size] = newEntry;
        size++;
        heapifyUp(size - 1);

    }

    @Override
    public T remove() {
        T maxValue = null;
        if (!isEmpty()) {
            maxValue = heap[0];
            heap[0] = heap[size - 1];
            heap[size - 1] = null; // Clear the last element
            size--;
            heapifyDown(0);
        } else {
            return null;
        }

        return maxValue;
    }

    @Override
    public T peekMaxValue() {
        T maxValue = null;
        if (!isEmpty()) {
            maxValue = heap[0];
        }
        return maxValue;
    }

    @Override
    public T getAnyValue(int index) {
        T anyValue = null;
        if (!isEmpty()) {
            anyValue = heap[index];
        }
        return anyValue;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            // Change comparison to maintain max-heap property
            if (heap[index].compareTo(heap[parentIndex]) < 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

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

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private boolean isFull() {
        return size == heap.length;
    }

    private void doubleHeapCapacity() {
        T[] newHeap = (T[]) new Comparable[heap.length * 2];

        // Manually copy elements from the old heap to the new heap
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }

}
