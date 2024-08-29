package adt;

import java.util.Iterator;

/**
 *
 * @author Ooi Choon Chong
 */
public interface BinarySearchTreeInterface<T> extends Iterable<T> {

    boolean insert(T element); // Inserts an element into the tree

    boolean delete(T element); // Deletes an element from the tree

    boolean contains(T element); // Checks if the tree contains a particular element

    T findMin(); // Finds the minimum element in the tree

    T findMax(); // Finds the maximum element in the tree

    boolean isEmpty(); // Checks if the tree is empty

    void clear(); // Clears the tree

    @Override
    Iterator<T> iterator(); // Provides an iterator for in-order traversal
}
