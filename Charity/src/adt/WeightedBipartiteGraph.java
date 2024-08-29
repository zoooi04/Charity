/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import entity.Donor;
import entity.Event;

/**
 *
 * @author huaiern
 */
public class WeightedBipartiteGraph<T, N>{
    //adjacency list
    private MapInterface<Vertex<T>, LinkedList<Edge<Vertex<T>,Vertex<N>>>> adj1 = new HashMap<>();
    private MapInterface<Vertex<N>, LinkedList<Edge<Vertex<N>,Vertex<T>>>> adj2 = new HashMap<>();
    
    //entity class in this graph (T or N)
    private final Class<T> clazz1;
    private final Class<N> clazz2;
    
    public WeightedBipartiteGraph(Class<T> clazz1, Class<N> clazz2){
        this.clazz1 = clazz1;
        this.clazz2 = clazz2;
    }
    
    public boolean addVertex(Object object) {
        if(clazz1.isInstance(object)){
            //Put new pair of vertex and its edges
            Vertex<T> vertexT = (Vertex<T>) new Vertex<>((T)object);
            adj1.put(vertexT, new LinkedList<>());
        }else if(clazz2.isInstance(object)){
            Vertex<N> vertexN = (Vertex<N>) new Vertex<>((N) object);
            adj2.put(vertexN, new LinkedList<>());
        }else {
            // Handle the case where v is neither type
            //System.out.println("Neither type");
            return false;
        }
        return true;
    }
    
    public void test(T key){
//        Event event = new Event();
//        Vertex<Event> vertex = new Vertex<>(event);
//        System.out.println(vertex.getClass().getName());
        System.out.println(adj1.containsKey(new Vertex<>(key)));
    }
    
//    public Vertex<?> constructVertex(Object object){
//        if(clazz1.isInstance(object)){
//            return (Vertex<T>) new Vertex<>((T)object);
//        }else if(clazz2.isInstance(object)){
//            return (Vertex<N>) new Vertex<>((N)object);
//        }
//        return null;
//    }
    
    public boolean addEdge(Object source, Object destination, int weight) {
        if((!clazz1.isInstance(source) && !clazz2.isInstance(source))|| (!clazz1.isInstance(destination) && !clazz2.isInstance(destination))){
            return false;
        }

        
        //put vertices
        if (clazz1.isInstance(source) && clazz2.isInstance(destination)) {
            Vertex<T> vertexT = (Vertex<T>) new Vertex<>((T) source);
            Vertex<N> vertexN = (Vertex<N>) new Vertex<>((N) destination);
            
            adj1.putIfAbsent(vertexT, new LinkedList<>());
            adj2.putIfAbsent( vertexN, new LinkedList<>());
            
            //put edges in respective linked list
            Edge<Vertex<T>, Vertex<N>> edge = new Edge<>(vertexT, vertexN, weight);
            adj1.get(vertexT).add(edge);
            
            Edge<Vertex<N>, Vertex<T>> edge2 = new Edge<>(vertexN, vertexT, weight);
            adj2.get(vertexN).add(edge2);
            
        } else if (clazz1.isInstance(destination) && clazz2.isInstance(source)) {
            Vertex<T> vertexT = (Vertex<T>) new Vertex<>((T) destination);
            Vertex<N> vertexN = (Vertex<N>) new Vertex<>((N) source);

            adj1.putIfAbsent(vertexT, new LinkedList<>());
            adj2.putIfAbsent(vertexN, new LinkedList<>());

            //put edges in respective linked list
            Edge<Vertex<T>, Vertex<N>> edge = new Edge<>(vertexT, vertexN, weight);
            adj1.get(vertexT).add(edge);

            Edge<Vertex<N>, Vertex<T>> edge2 = new Edge<>(vertexN, vertexT, weight);
            adj2.get(vertexN).add(edge2);
        }
        
//        if (clazz1.isInstance(destination)) {
//            adj1.putIfAbsent((Vertex<T>) destination, new LinkedList<>());
//        } else if (clazz2.isInstance(source)) {
//            adj2.putIfAbsent((Vertex<N>) destination, new LinkedList<>());
//        }
        

        

        return true;
    }
    
    //Remove direct connection between v1 and b

    public boolean removeEdge(Object v1, Object v2) {

        //if not those 2 entity
        if ((!clazz1.isInstance(v1) && !clazz2.isInstance(v1)) || (!clazz1.isInstance(v2) && !clazz2.isInstance(v2))) {
            return false;
        }
        

//        //validate vertex
//        if (!adj1.containsKey(vertexT) || !adj1.containsKey(vertexN) || !adj2.containsKey(vertexT) || !adj2.containsKey(vertexN)) {
//            return false;
//        }

        Vertex<T> vertexT=null;
        Vertex<N> vertexN=null;
        if (clazz1.isInstance(v1) && clazz2.isInstance(v2)) {
            
            vertexT = (Vertex<T>) new Vertex<>((T) v1);
            vertexN = (Vertex<N>) new Vertex<>((N) v2);
        } else if (clazz1.isInstance(v2) && clazz2.isInstance(v1)) {
            vertexT = (Vertex<T>) new Vertex<>((T) v2);
            vertexN = (Vertex<N>) new Vertex<>((N) v1);
        }
        
        if(vertexT==null && vertexN==null){
            return false;
        }
        
        //get list(valeus) in both map
        LinkedList<Edge<Vertex<T>, Vertex<N>>> list1 = adj1.get(vertexT);
        LinkedList<Edge<Vertex<N>, Vertex<T>>> list2 = adj2.get(vertexN);
        //validate list availability
        if (list1 == null || list2 == null) {

            return false;
        }

        //put edges in respective linked list
        Edge<Vertex<T>, Vertex<N>> edge1 = findEdgeByNodes(vertexT, vertexN);
        if (edge1 == null) {
            System.out.println("hi");

            return false;
        }
        list1.remove(edge1);

        Edge<Vertex<N>, Vertex<T>> edge2 = findEdgeByNodes(vertexN, vertexT);
        if (edge2 == null) {
            return false;
        }
        list2.remove(edge2);

        return true;
    }
    
    //Find the edge between two nodes
    private <S extends Vertex, D extends Vertex> Edge<S,D> findEdgeByNodes(Vertex v1, Vertex v2) {
        if (!adj1.containsKey(v1) || !adj1.containsKey(v2) || !adj2.containsKey(v1) || !adj2.containsKey(v2)){
            return null;
        }
        
        Edge<S,D> edgeResult = null;
        boolean found = false;
        //get vertex edges
        LinkedList<Edge<S,D>> list = adj1.get(v1);
        for (Edge<S,D> edge: list) {
            if (edge.destination.equals(v2)) {
                edgeResult = edge;
                found = true;
                break;
            }
        }
        
        //if not found in first map find in second map
        if(!found){
            list = adj1.get(v2);
            for(Edge<S,D> edge: list){
                if(edge.destination.equals(v2)){
                    edgeResult = edge;
                    break;
                }
            }
        }

        return edgeResult;
    }
    
    //print graph as hashmap
    //depends on ur entity toString()
    public void printGraph() {
        System.out.println(adj1.toString());
        System.out.println(adj2.toString());

    }
    
    public class Vertex<V>{
        V data;
        int degree; //number of edges connected to this vertex
        
        public Vertex(){
            data=null;
        }
        
        public Vertex(V data){
            this.data = data;
        }
        
        public V getData(){
            return data;
        }
        
        public int getDegree(){
            return degree;
        }
        
        public void setDegree(int degree){
            this.degree = degree;
        }
        
        public void incrementDegree(){
            degree++;
        }
        
        @Override
        public String toString(){
            if(data instanceof Event){
                return ((Event)data).getName();
            }else if(data instanceof Donor){
                return ((Donor)data).getName();
            }
            
            return data.getClass().getName();
        }
        
        @Override
        public boolean equals(Object obj){
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Vertex<?> vertex = (Vertex<?>) obj;
            return data.equals(vertex.data);
        }
        
        @Override
        public int hashCode(){
            return data.hashCode();
        }
    }
    
    private class Edge<S,D> {
        private S source;
        private D destination;
        private int weight;

        public Edge(S source, D destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return destination + " (" + weight + ")";
        }

        public boolean equals(Edge anotherEdge) {
            if (!source.equals(anotherEdge.source)) {
                return false;
            }

            if (!destination.equals(anotherEdge.destination)) {
                return false;
            }

            return weight == anotherEdge.weight;
        }
    }
}


