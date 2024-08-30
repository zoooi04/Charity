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
 * @param <T>
 * @param <N>
 */
public class WeightedBipartiteGraph<T, N>{
    //adjacency list
    private MapInterface<Vertex<T>, LinkedList<Edge<Vertex<T>,Vertex<N>>>> adj1 = new HashMap<>();
    private MapInterface<Vertex<N>, LinkedList<Edge<Vertex<N>,Vertex<T>>>> adj2 = new HashMap<>();
    private int numberOfVertices;
    
    
    //entity class in this graph (T or N)
    private final Class<T> clazz1;
    private final Class<N> clazz2;
    
    public WeightedBipartiteGraph(Class<T> clazz1, Class<N> clazz2){
        this.clazz1 = clazz1;
        this.clazz2 = clazz2;
        numberOfVertices = 0;
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
        numberOfVertices++;
        return true;
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

        Vertex<T> vertexT = null;
        Vertex<N> vertexN = null;
        //put vertices
        //check each data type first
        if (clazz1.isInstance(source) && clazz2.isInstance(destination)) {
            vertexT = (Vertex<T>) new Vertex<>((T) source);
            vertexN = (Vertex<N>) new Vertex<>((N) destination); 
        } else if (clazz1.isInstance(destination) && clazz2.isInstance(source)) {
            vertexT = (Vertex<T>) new Vertex<>((T) destination);
            vertexN = (Vertex<N>) new Vertex<>((N) source);
        }
        
        //1. put vertex
        //2. if previously does not have vertex then size++
        if (adj1.putIfAbsent(vertexT, new LinkedList<>()) == null) {
            numberOfVertices++;
        }
        if (adj2.putIfAbsent(vertexN, new LinkedList<>()) == null)
            numberOfVertices++;
        
        //put edges in respective linked list
        Edge<Vertex<T>, Vertex<N>> edge = new Edge<>(vertexT, vertexN, weight);
        adj1.get(vertexT).add(edge);

        Edge<Vertex<N>, Vertex<T>> edge2 = new Edge<>(vertexN, vertexT, weight);
        adj2.get(vertexN).add(edge2);
        
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
        
        //two different scenario where T and N are flipped around
        if (clazz1.isInstance(v1) && clazz2.isInstance(v2)) { 
            vertexT = (Vertex<T>) new Vertex<>((T) v1);
            vertexN = (Vertex<N>) new Vertex<>((N) v2);
            
            //get list(values) in both map
            LinkedList<Edge<Vertex<T>, Vertex<N>>> list1 = adj1.get(vertexT);
            LinkedList<Edge<Vertex<N>, Vertex<T>>> list2 = adj2.get(vertexN);
            //validate list availability
            if (list1 == null || list2 == null) {

                return false;
            }
            
            //remove edges in respective linked list
            //find edge 1 in the T map first
            Edge<Vertex<T>, Vertex<N>> edge1 = (Edge<Vertex<T>, Vertex<N>>) findEdgeByNodes(v1, v2);
            if (edge1 == null) {
                return false;
            }
            
            //try to remove first edge1                    
            if(!list1.remove(edge1))   
                return false;
            
            //find edge 2 in the N map
            Edge<Vertex<N>, Vertex<T>> edge2 = (Edge<Vertex<N>, Vertex<T>>) findEdgeByNodes(v2, v1);
            if (edge2 == null) {
                return false;
            }
            
            //ttry to remove edge2
            if(!list2.remove(edge2)){
                return false;
            }
            
        } else if (clazz1.isInstance(v2) && clazz2.isInstance(v1)) {
            vertexT = (Vertex<T>) new Vertex<>((T) v2);
            vertexN = (Vertex<N>) new Vertex<>((N) v1);
            
            //get list(values) in both map
            LinkedList<Edge<Vertex<T>, Vertex<N>>> list1 = adj1.get(vertexT);
            LinkedList<Edge<Vertex<N>, Vertex<T>>> list2 = adj2.get(vertexN);
            //validate list availability
            if (list1 == null || list2 == null) {

                return false;
            }

            //remove edges in respective linked list
            //find edge 1 in the T map first
            Edge<Vertex<T>, Vertex<N>> edge1 = (Edge<Vertex<T>, Vertex<N>>) findEdgeByNodes(v2, v1);
            if (edge1 == null) {
                return false;
            }

            //try to remove first edge1                    
            if (!list1.remove(edge1)) {
                return false;
            }

            //find edge 2 in the N map
            Edge<Vertex<N>, Vertex<T>> edge2 = (Edge<Vertex<N>, Vertex<T>>) findEdgeByNodes(v1, v2);
            if (edge2 == null) {
                return false;
            }

            //ttry to remove edge2
            if (!list2.remove(edge2)) {
                return false;
            }
        }else{
            return false;
        }
        


        return true;
    }
    
 
    //Find the edge between two nodes
    //source and destination in this case is T or N which is vertex data (different from remove)
    private Edge<?,?> findEdgeByNodes(Object sourceData, Object destinationData) {

        Vertex<T> vertexT = null;
        Vertex<N> vertexN = null;
  
        //two different scenario where T and N are flipped around
        if (clazz1.isInstance(sourceData) && clazz2.isInstance(destinationData)) {
            //when source is T and destination is N
            vertexT = (Vertex<T>) new Vertex<>((T) sourceData);
            //vertexN = (Vertex<N>) new Vertex<>((N) destination); not used
            
            Edge<Vertex<T>, Vertex<N>> edgeResult = null;
            //boolean found = false;
            
            //get edge list with edges containing destination N using T key
            LinkedList<Edge<Vertex<T>, Vertex<N>>> list = adj1.get(vertexT);
            for (Edge<Vertex<T>, Vertex<N>> edge : list) {
                //cast to respective data class
                T edgeSourceData = (T)edge.getSource().data;
                N edgeDestinationData = (N)edge.getDestination().data;
                if (edgeSourceData.equals((T)sourceData) && edgeDestinationData.equals((N)destinationData)) {
                    edgeResult = edge;
                    //found = true;
                    break;
                }
            }
            
            return edgeResult; //as Edge<<VertexT>,<VertexN>>
        } else if (clazz1.isInstance(destinationData) && clazz2.isInstance(sourceData)) {
            //when source is T and destination is N
            vertexN = (Vertex<N>) new Vertex<>((N) sourceData);
            //vertexN = (Vertex<N>) new Vertex<>((N) destination); not used

            Edge<Vertex<N>, Vertex<T>> edgeResult = null;
            //boolean found = false;

            //get edge list with edges containing destination N using T key
            LinkedList<Edge<Vertex<N>, Vertex<T>>> list = adj2.get(vertexN);
            for (Edge<Vertex<N>, Vertex<T>> edge : list) {
                //cast to respective data class
                N edgeSourceData = (N) edge.getSource().data;
                T edgeDestinationData = (T) edge.getDestination().data;
                if (edgeSourceData.equals((N) sourceData) && edgeDestinationData.equals((T) destinationData)) {
                    edgeResult = edge;
                    //found = true;
                    break;
                }
            }

            return edgeResult; //as Edge<<VertexT>,<VertexN>>
        }else{
            return null;
        }    
    }
    
    //print graph as hashmap
    public void printGraph() {
        System.out.println(adj1.toString());
        System.out.println(adj2.toString());

    }
    
    public int size(){
        return numberOfVertices;
    }
    
    
    

}


