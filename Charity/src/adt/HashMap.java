package adt;

import java.io.Serializable;

/**
 *
 * @author huaiern & Ooi Choon Chong
 */
public class HashMap<K, V> implements MapInterface<K, V>, Serializable {
    private final int SIZE = 16;  // HashMap array size to reduce collision possibility
    private Node<K, V>[] table;  // HashMap array
    private int size;  // To keep track of the number of key-value pairs

    // Constructor and initialize size
    public HashMap() {
        table = new Node[SIZE];
        size = 0;
    }
    
    

    @Override
    public V get(K key) {
        int index = Math.abs(key.hashCode() % SIZE);  // Convert key to index
        Node<K, V> node = table[index];

        while (node != null) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
            node = node.next;
        }

        return null;  // Return null if the key is not found
    }

    @Override
    public void put(K key, V value) {
        int index = Math.abs(key.hashCode() % SIZE);
        Node<K, V> node = table[index];

        if (node == null) {
            table[index] = new Node<>(key, value);
            size++;
        } else {
            while (node != null) {
                if (node.getKey().equals(key)) {
                    node.setValue(value);  // Update value if key exists
                    return;
                }
                if (node.next == null) break;
                node = node.next;
            }
            node.next = new Node<>(key, value);  // Add new node if key doesn't exist
            size++;
        }
    }

    @Override
    public Node<K, V> remove(K key) {
        int index = Math.abs(key.hashCode() % SIZE);
        Node<K, V> node = table[index];
        Node<K, V> prev = null;

        while (node != null) {
            if (node.getKey().equals(key)) {
                if (prev == null) {
                    table[index] = node.next;  // If head node is to be removed
                } else {
                    prev.next = node.next;  // If a middle node is to be removed
                }
                node.next = null;  // Remove next reference
                size--;
                return node;
            }
            prev = node;
            node = node.next;
        }

        return null;  // Return null if the key is not found
    }

    @Override
    public ArrayList<K> keySet() {
        ArrayList<K> keys = new ArrayList<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                keys.add(node.getKey());
                node = node.next;
            }
        }
        return keys;
    }

    @Override
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                values.add(node.getValue());
                node = node.next;
            }
        }
        return values;
    }

    @Override
    public boolean containsKey(K key) {
        int index = Math.abs(key.hashCode() % SIZE);
        Node<K, V> node = table[index];

        while (node != null) {
            if (node.getKey().equals(key)) {
                return true;
            }
            node = node.next;
        }

        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (Node<K, V> node : table) {
            while (node != null) {
                if (node.getValue().equals(value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < SIZE; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void putAll(MapInterface mapToCopy) {
        
        HashMap<K,V> anotherMap = (HashMap<K,V>) mapToCopy;
        //traverse the other map to copy
        for (int i = 0; i < anotherMap.table.length; i++) {
            Node<K,V> node = anotherMap.table[i];
            //if current position not null
            
            //traverse linked nodes
            while(node != null){
                this.put(node.getKey(), node.getValue());
                node = node.next;
            }
            
            //continue to the next position
        }

        
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            put(key, value);
            return null;
        } else {
            return get(key);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            sb.append(i).append(". ").append(table[i] != null ? table[i] : "null").append("\n");
        }
        return sb.toString();
    }

    // Node as an inner class for encapsulation purpose
    private class Node<K, V> implements NodeInterface<K, V>, Serializable{
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
        
        @Override
        public boolean equals(Object obj){
            return this == obj;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node<K, V> temp = this;
            while (temp != null) {
                sb.append(temp.key).append(" --> ").append(temp.value);
                if (temp.next != null) {
                    sb.append(", ");
                }
                temp = temp.next;
            }
            return sb.toString();
        }
    }
}
