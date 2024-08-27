
package adt;
import java.util.Iterator;

/**
 *
 * @author huaiern
 */
public class WeightedGraph<T> implements GraphInterface<T>{
    
    //adjacency list
    private MapInterface<T,LinkedList<Edge<T>>> adj = new HashMap<>();
    
    @Override
    public void addVertex(T vertex) {
        //Put new pair of vertex and its edges
        adj.put(vertex, new LinkedList<>());
    }

    
    @Override
    public void addEdge(T source, T destination, int weight) {
        //put vertices
        adj.putIfAbsent(source, new LinkedList<>());
        adj.putIfAbsent(destination,new LinkedList<>());
        //put edges in respective linked list
        Edge<T> edge = new Edge<>(source, destination, weight);
        adj.get(source).add(edge);
        
        //annother way
        Edge<T> edge2 = new Edge<>(destination, source, weight);
        adj.get(destination).add(edge2);
    }
    
    
    
    //Remove direct connection between v1 and b
    @Override
    public boolean removeEdge(T v1, T v2) {
        if (!adj.containsKey(v1) || !adj.containsKey(v2)) //invalid input
        {
            return false;
        }
        
        //find both edges
        LinkedList<Edge<T>> list1 = adj.get(v1);
        LinkedList<Edge<T>> list2 = adj.get(v2);
        if (list1 == null || list2 == null) {
            return false;
        }
        
        //remove both side
        //v1 -> v2
        //v2 -> v1
        Edge<T> edge1 = findEdgeByNodes(v1, v2);//find destination v2 by using v1 as source/key
        if (edge1 == null) {
            return false;
        }
        list1.remove(edge1);//eventc
        
        Edge<T> edge2 = findEdgeByNodes(v2, v1);//find destination v1 by using v2 as source/key
        if (edge2 == null) {
            return false;
        }
        list2.remove(edge2);//eventa
        
        
        return true;
    }
    
    //Find the edge between two nodes
    private Edge<T> findEdgeByNodes(T v1, T v2) {
        if (!adj.containsKey(v1) || !adj.containsKey(v2)) //invalid input
        {
            return null;
        }
        //get v1 source's destinations (edges)
        LinkedList<Edge<T>> list = adj.get(v1);
        for (Edge<T> edge : list) {
            if (edge.destination.equals(v2)) {
                return edge;
            }

        }

        return null;
    }
    
    //print graph as hashmap
    public void printGraph(){
        System.out.println(adj.toString());
       
    }
    
    private class Edge<T> {
        private T source;
        private T destination;
        private int weight;
        
        public Edge(T source, T destination, int weight){
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
        
        @Override
        public String toString() {
            return  destination + " (" + weight + ")";
        }
        
        
        public boolean equals(Edge anotherEdge){
            if(!source.equals(anotherEdge.source))
                return false;
            
            
            if(!destination.equals(anotherEdge.destination))
                return false;
            
            return weight == anotherEdge.weight;
        }
    }
    
    
}
