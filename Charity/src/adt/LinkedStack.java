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

    @Override
    public void push(T newEntry) {
        Node<T> newNode = new Node<>(newEntry, topNode);
        topNode = newNode;
    }

    @Override
    public T pop() {
        T top = peek();
        if (topNode != null) {
            topNode = topNode.next;
        }
        return top;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return topNode.data;
        }
    }

    @Override
    public boolean isEmpty() {
        return topNode == null;
    }

    @Override
    public void clear() {
        topNode = null;
    }

    private static class Node<T> {

        private T data; // Entry in stack
        private Node<T> next; // Link to next node

        private Node(T data) {
            this(data, null);
        }

        private Node(T data, Node<T> nextNode) {
            this.data = data;
            this.next = nextNode;
        }
    }
}
