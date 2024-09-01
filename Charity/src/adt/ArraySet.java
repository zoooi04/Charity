package adt;

import java.util.Iterator;

/**
 *
 * @author AndrewPhengQiJinn
 */
public class ArraySet<T> implements SetInterface<T> {

    private T[] setArray;
    private int numberOfElements;
    private static final int DEFAULT_INITIAL_CAPACITY = 25;

    public ArraySet() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ArraySet(int initial_capacity) {
        setArray = (T[]) new Object[initial_capacity];
        numberOfElements = 0;
    }

    @Override
    public boolean add(T newElement) {
        for (int i = 0; i < numberOfElements; i++) {
            if (setArray[i].equals(newElement)) {
                return false;
            }
        }
        //check if array is full if full then doubleArray
        if (setArray.length == numberOfElements) {
            doubleArray();
        }

        //add new element ion the set
        setArray[numberOfElements] = newElement;
        numberOfElements++;
        return true;
    }

    @Override
    public boolean remove(T anElement) {
        for (int i = 0; i < numberOfElements; i++) {
            if (setArray[i].equals(anElement)) {
                //revmove the element
                removeGap(i);
                numberOfElements--;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean checkSubset(SetInterface anotherSet) {
        if (anotherSet instanceof ArraySet) {
            ArraySet aSet = (ArraySet) anotherSet;

            if (aSet.numberOfElements > this.numberOfElements) {
                return false;
            }

            for (int i = 0; i < aSet.numberOfElements; i++) {
                boolean found = false;
                for (int j = 0; j < this.numberOfElements && !found; j++) {
                    if (aSet.setArray[i].equals(setArray[j])) {
                        found = true;
                    }

                }

                if (!found) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void union(SetInterface anotherSet) {
        if (anotherSet instanceof ArraySet) {
            ArraySet aSet = (ArraySet) anotherSet;
            for (int i = 0; i < aSet.numberOfElements; i++) {
                add((T) aSet.setArray[i]);
            }
        }
    }

    @Override
    public SetInterface intersection(SetInterface anotherSet) {
        SetInterface<T> resultSet = new ArraySet<>();

        if (anotherSet instanceof ArraySet) {
            ArraySet aSet = (ArraySet) anotherSet;

            for (int i = 0; i < aSet.numberOfElements; i++) {
                for (int j = 0; j < this.numberOfElements; j++) {
                    if (aSet.setArray[i].equals(setArray[j])) {

                        resultSet.add((T) aSet.setArray[i]);
                        break;
                    }
                }
            }
        }
        return resultSet;
    }

    @Override
    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    public String toString() {
        String outputStr = "";
        for (int i = 0; i < numberOfElements; i++) {
            outputStr += setArray[i] + "\n";
        }
        return outputStr;
    }

    @Override
    public boolean contains(T element) {
        for (int i = 0; i < numberOfElements; i++) {
            if (setArray[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return numberOfElements;
    }

    @Override
    public void clear() {
        setArray = (T[]) new Object[DEFAULT_INITIAL_CAPACITY];
        numberOfElements = 0;
    }

    //declare private method here...
    private void removeGap(int index) {
        for (int i = index; i < numberOfElements - 1; i++) {
            setArray[i] = setArray[i + 1];
        }

    }

    private void doubleArray() {
        T[] oldArray = setArray;
        setArray = (T[]) new Object[oldArray.length * 2];

        for (int i = 0; i < oldArray.length; i++) {
            setArray[i] = oldArray[i];
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorForArraySet();
    }

    private class IteratorForArraySet implements Iterator<T> {

        private int nextIndex;

        public IteratorForArraySet() {
            nextIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < numberOfElements;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T nextElement = (T) setArray[nextIndex++];
                return nextElement;
            }

            return null;
        }

    }
}
