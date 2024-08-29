/**
 *
 * @author AndrewPhengQiJinn, referenced from sample code and modified.
 */
package adt;

public class SortedLinkedList<T extends Comparable<T>> implements SortedListInterface<T> {

    private Node firstNode;
    private int numberOfEntries;

    public SortedLinkedList() {
        firstNode = null;
        numberOfEntries = 0;
    }

    @Override
    public boolean add(T newEntry) {
        Node newNode = new Node(newEntry);

        Node nodeBefore = null;
        Node currentNode = firstNode;
        while (currentNode != null && newEntry.compareTo(currentNode.data) > 0) {
            nodeBefore = currentNode;
            currentNode = currentNode.next;
        }

        if (isEmpty() || (nodeBefore == null)) { // CASE 1: add at beginning
            newNode.next = firstNode;
            firstNode = newNode;
        } else {	// CASE 2: add in the middle or at the end, i.e. after nodeBefore
            newNode.next = currentNode;
            nodeBefore.next = newNode;
        }
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean remove(T anEntry) {
        // Check if the list is empty
        if (isEmpty()) {
            return false; // Nothing to remove
        }

        Node nodeBefore = null;
        Node currentNode = firstNode;

        // Traverse the list to find the node containing anEntry
        while (currentNode != null && anEntry.compareTo(currentNode.data) > 0) {
            nodeBefore = currentNode;
            currentNode = currentNode.next;
        }

        // Check if the currentNode contains the entry to remove
        if (currentNode != null && currentNode.data.equals(anEntry)) {
            // CASE 1: Removing the first node
            if (nodeBefore == null) {
                firstNode = currentNode.next;
            } // CASE 2: Removing a node from the middle or end
            else {
                nodeBefore.next = currentNode.next;
            }

            numberOfEntries--;
            return true; // Removal was successful
        } else {
            return false; // anEntry not found
        }
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        Node tempNode = firstNode;

        while (!found && (tempNode != null)) {
            if (anEntry.compareTo(tempNode.data) <= 0) {
                found = true;
            } else {
                tempNode = tempNode.next;
            }
        }
        if (tempNode != null && tempNode.data.equals(anEntry)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final void clear() {
        firstNode = null;
        numberOfEntries = 0;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            Node currentNode = firstNode;
            for (int i = 0; i < givenPosition - 1; ++i) {
                currentNode = currentNode.next;		// advance currentNode to next node
            }
            result = currentNode.data;	// currentNode is pointing to the node at givenPosition
        }

        return result;
    }

    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return (numberOfEntries == 0);
    }

    private class Node {

        private T data;
        private Node next;

        private Node(T data) {
            this.data = data;
            next = null;
        }

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
