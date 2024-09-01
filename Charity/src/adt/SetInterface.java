package adt;

/**
 *
 * @author TanJianHui and AndrewPhengQiJinn
 */
import java.util.Iterator;

public interface SetInterface<T> {

    public boolean add(T newElement);

    public boolean remove(T element);
    
    public boolean contains(T element);

    public boolean checkSubset(SetInterface anotherSet);

    public void union(SetInterface anotherSet);

    public SetInterface intersection(SetInterface anotherSet);

    public boolean isEmpty();
    
    public int size();
    
    public void clear();

    public Iterator<T> iterator();
}
