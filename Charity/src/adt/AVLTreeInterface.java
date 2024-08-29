package adt;

/**
 *
 * @author AndrewPhengQiJinn
 * @param <T>
 */
public interface AVLTreeInterface<T extends Comparable<T>> {

    public void insert(T data);

    public void delete(T data);

    public boolean contains(T data);

    public T findMin();

    public T findMax();

    public boolean isEmpty();

    public void clear();
}
