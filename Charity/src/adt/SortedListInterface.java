package adt;

/**
 *
 * @author AndrewPhengQiJinn, referenced and modified from sample code
 * @param <T>
 */
public interface SortedListInterface<T> {
    
    public boolean add(T newEntry);
    
    public boolean remove(T anEntry);
    
    public boolean contains(T anEntry);
    
    public void clear();
    
    public T getEntry(int givenPosition);
    
    public int getNumberOfEntries();
    
    public boolean isEmpty();
    
}
