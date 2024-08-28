/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Ooi Choon Chong
 */
public class LinkedStack<T> implements StackInterface<T> {

    private Node<T> topNode; // Reference to the first node

    public LinkedStack() {
        topNode = null;
    }

    public void push(T newEntry) {
        Node<T> newNode = new Node<>(newEntry, topNode);
        topNode = newNode;
    }

    public T pop() {
        T top = peek();
        if (topNode != null) {
            topNode = topNode.getNextNode();
        }
        return top;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return topNode.getData();
        }
    }

    public boolean isEmpty() {
        return topNode == null;
    }

    public void clear() {
        topNode = null;
    }

    private static class Node<T> {

        private T data; // Entry in stack
        private Node<T> next; // Link to next node

        private Node(T dataPortion) {
            this(dataPortion, null);
        }

        private Node(T dataPortion, Node<T> nextNode) {
            data = dataPortion;
            next = nextNode;
        }

        private T getData() {
            return data;
        }

        private Node<T> getNextNode() {
            return next;
        }
    }
}
