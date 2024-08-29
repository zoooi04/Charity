/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author Ooi Choon Chong
 * @param <T>
 */
public class LinkedQueue<T> implements QueueInterface<T>, Serializable {

    private Node<T> front; // References the first node in the queue
    private Node<T> back;  // References the last node in the queue
    private int size;      // Tracks the number of elements in the queue

    public LinkedQueue() {
        front = null;
        back = null;
        size = 0;
    }

    @Override
    public void enqueue(T newEntry) {
        Node<T> newNode = new Node<>(newEntry, null);

        if (isEmpty()) {
            front = newNode;
        } else {
            back.next = newNode;
        }

        back = newNode;
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;  // Return null if the queue is empty
        }

        T frontData = front.data;
        front = front.next;

        if (front == null) { // Queue is now empty
            back = null;
        }

        size--;
        return frontData;
    }

    @Override
    public T getFront() {
        if (isEmpty()) {
            return null;  // Return null if the queue is empty
        }

        return front.data;
    }

    @Override
    public boolean isEmpty() {
        return front == null;
    }

    @Override
    public void clear() {
        front = null;
        back = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> getIterator() {
        return new LinkedQueueIterator();
    }

    private class LinkedQueueIterator implements Iterator<T> {

        private Node currentNode;

        public LinkedQueueIterator() {
            currentNode = front;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T returnData = (T) currentNode.data;
                currentNode = currentNode.next;
                return returnData;
            } else {
                return null;
            }
        }
    }

    private static class Node<T> {

        private T data;
        private Node<T> next;

        private Node(T data) {
            this(data, null);
        }

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
}
