
package adt;
/**
 *
 * @author huaiern
 */
public class HashMap<K,V> implements MapInterface<K,V>{
    //hashmap array size (bigger size to reduce collision possibility)
    private final int SIZE = 16;
    //hahsmap array
    private Node<K,V>[] table;

    //constructor and intialize size
    public HashMap(){
        table = new Node[SIZE];
    }
    

    
    //functions
    @Override
    public V get(K key) {
        //convert key to index
        //get array index from hashcode in the range of SIZE
        int index = key.hashCode() % SIZE;
        //get node from that index
        Node<K, V> node = table[index];
        
        //if there is no node in that index then return null
        if(node == null){
            return null;
        }
        
        //while next node in that index is not null, search the next one till have the same key
        while (node.next != null) {
            if (node.getKey() == key) {
                return node.getValue();
            }
            node = node.next;
        }

        //if the end still dont have the same key then return null
        return null;
    }

    @Override
    public void put(K key, V value) {
        //convert key to index
        //get array index from hashcode in the range of SIZE
        int index = key.hashCode() % SIZE;
        //get node from that index
        Node<K,V> node = table[index];
        
        //if that index doesnt have any existing node, directly put in it
        if(node == null){
            //create a new node with the given key and value and put in the array
            table[index] = new Node<>(key,value);
        }else{
            //if have other key already occupied that index
            //By looping through the linked list in that index, check if KEY already exist, if so just update the value
            while(node.next != null){//as long as current node have linked to next node
                //check if the current node is equal to the given key
                if(node.getKey()== key){
                    //change the value only
                    node.setValue(value);
                    return;
                }
                //go to the next node and do the same thing
                node = node.next;
            }
            
            //if the last one have the same key then change value
            if(node.getKey() == key){
                node.setValue(value);
                return;
            }
            
            //if the entire linkedlist dont have the same key, create a new one and link at the end
            node.next = new Node<>(key,value);
            
        }
       
    }

    //following the original remove, it will return the removed node
    @Override
    public Node<K,V> remove(K key) {
        //convert key to index
        //get array index from hashcode in the range of SIZE
        int index = key.hashCode() % SIZE;
        //"node" points to the node to be removed
        //keep a reference to be returned
        Node<K, V> node = table[index];
        
        //if there is no node in that index then return null
        if (node == null) {
            return null;
        }

        //if the pointed node have the same key
        if (node.getKey() == key) {
            //point array index to the next node, prev node still hold by "node"
            table[index] = node.next;
            //remove the removed node "next" reference so that wont link to the orignal list of nodes
            node.next = null;
            //return removed node
            return node;
        }

        //if the remove one not the head/directly point by the array index
        //create a "prev" variable to point to every previous node
        Node<K, V> prev = node;//lets say 1st node(head) become previous node
        //go to the next node
        node = node.next;//then current node is now 2nd node(after head)

        //if current node is not null
        while (node != null) {
            //if found with the same key
            if (node.getKey() == key) {
                //prev node will skip current one and point to after current one
                prev.next = node.next;
                //remove the current next reference so that when returned wont have link to the following nodes
                node.next = null;
                return node;
            }
            //else if not same key, shift previous node to current node
            prev = node;
            //and current node to the next node
            node = node.next;
        }
        
        //if still dont find the node with the same key then return null
        return null;
    }

    @Override
    public ArrayList<K> keySet() {
        //array for storing keys
        ArrayList<K> keys = new ArrayList<>();
        
        //traverse array
        for (Node<K, V> node : table) {
            //for every position if is not pointed to null
            if (node != null) {
                //traverse the linked nodes in that position
                //if next still have node
                while (node.next != null) {
                    //store the node key
                    keys.add(node.getKey());
                    //go to the next node
                    node = node.next;
                }
                //if no next node, store the current key
                keys.add(node.getKey());
            }
        }
        
        return keys;
        
    }

    @Override
    public ArrayList<V> values() {
        //array for storing value
        ArrayList<V> values = new ArrayList<>();

        //traverse array
        for (Node<K, V> node : table) {
            //for every position if is not pointed to null
            if (node != null) {
                //traverse the linked nodes in that position
                //if next still have node
                while (node.next != null) {
                    //store the node key
                    values.add(node.getValue());
                    //go to the next node
                    node = node.next;
                }
                //if no next node, store the current key
                values.add(node.getValue());
            }
        }

        return values;
    }

    @Override
    public boolean containsKey(K key) {
        //since we know hashcode can generate the same index
        int index = key.hashCode() % SIZE;
        Node<K,V> node = table[index];
        
        //directly go to that index
        //if node at that index is not null
        if(node != null){
            //check if the head is the same key first, then check the subsequent keys
            while(node.next != null){
                if(node.getKey() == key){
                    //if key found then true
                    return true;
                }
                node = node.next;
            }
            
            //last node which next is null
            //again check for same key
            if (node.getKey() == key) {
                //if key found then true
                return true;
            }
        }
        
        //if all nodes at that index dont have the key then false
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        
        
        for(int i = 0; i<table.length;i++){
            Node<K,V> node = table[i];
            //if node at that index is not null
            if (node != null) {
                //check if the head is the same value first, then check the subsequent value
                while (node.next != null) {
                    if (node.getValue() == value) {
                        //if value found then true
                        return true;
                    }
                    node = node.next;
                }

                //last node which next is null
                //again check for same value
                if (node.getValue() == value) {
                    //if value found then true
                    return true;
                }
            }
        }
        

        //if all nodes at that index dont have the value then false
        return false;
    }

    @Override
    public void clear() {
        for(int i=0; i<table.length;i++){
            table[i] = null;
        }
    }

    @Override
    public int size() {
        int count = 0;
        
        for(int i=0; i<table.length; i++){
            Node<K,V> node = table[i];
            if (node != null) {
                while (node.next != null) {
                    count++;
                    node = node.next;
                }

                count++;

            }
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        for(int i=0; i<table.length; i++){
            if(table[i] != null){
                return false;
            }
        }
        return true;
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

    //return value if key already exist (put() is change the key value if key exist)
    @Override
    public V putIfAbsent(K key, V value) {
        //convert key to index
        //get array index from hashcode in the range of SIZE
        int index = key.hashCode() % SIZE;
        //get node from that index
        Node<K, V> node = table[index];

        //if that index doesnt have any existing node, directly put in it
        if (node == null) {
            //create a new node with the given key and value and put in the array
            table[index] = new Node<>(key, value);
            return null;
        } else {
            //By looping through the linked list in that index, check if KEY already exist, if so return the value
            while (node.next != null) {//as long as current node have linked to next node
                //check if the current node is equal to the given key
                if (node.getKey() == key) {
                    //return key value
                    
                    return node.getValue();
                }
                //go to the next node and do the same thing
                node = node.next;
            }

            //if the last one have the same key then return value
            if (node.getKey() == key) {
                
                return node.getValue();
            }

            //if the entire linkedlist dont have the same key, create a new one and link at the end
            node.next = new Node<>(key, value);
            
            return null;
        }
        
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            if (table[i] != null) {
                sb.append(i + ". " + table[i] + "\n");
            } else {
                sb.append(i + ". " + "null" + "\n");
            }
        }

        return sb.toString();
    }
    
    //Node as an inner class for encapsulation purpose, can interact with the outer class without accessor and mutator
    private class Node<K,V> implements NodeInterface<K,V>{
        private K key;
        private V value;
        private Node<K,V> next;
        
        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            Node<K, V> temp = this;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            while (temp != null) {
                sb.append(temp.key + " --> " + temp.value);
                if(temp.next != null){
                    sb.append(", ");
                }
                temp = temp.next;
            }
            sb.append("]");
            return sb.toString();
        }
        
        
    }
    
    
    

    
    

    
    
    
}
