
package adt;

/**
 *
 * @author huaiern
 */
public interface MapInterface<K,V> {
    //get value based on key given
    V get(K key);
    //insert key and value
    void put(K key, V value);
    //remove key and value
    NodeInterface<K,V> remove(K key);
    //get all key
    ArrayList<K> keySet();
    //get all values
    ArrayList<V> values();
    //check if have key
    boolean containsKey(K key);
    //check if have value
    boolean containsValue(V value);
    //clear all key and values
    void clear();
    //return number of pair of key and value
    int size();
    //check if map is empty
    boolean isEmpty();
    //put other map's pairs of key and value to this map
    //return true if same generic type
    void putAll(MapInterface mapToCopy);
    //if key doesn't exist then insert, if existed then return existing key's value
    //if put successful return null
    V putIfAbsent(K key,V value);
    
    interface NodeInterface<K,V>{};
    
}
