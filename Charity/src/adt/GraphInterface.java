/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author huaiern
 * @param <T>
 * @param <N>
 */
public interface GraphInterface<T extends Comparable<T>,N extends Comparable<N>> {
    boolean addVertex(T vertex);                        //add vertex
    boolean hasVertex(T vertex);                        //check has vertex
    T getVertex(int position);                          //get vertex based on position
    int getIndex(T vertex);                             //with node information, find the index of the vertex
    boolean addEdge(T source, T destination, N weight); // add edge
    boolean removeEdge(T v1, T v2);                     //remove edge between 2 vertex
    void printEdges();                                  //print edges
    ArrayList<T> getAllVertexObjects();                 //get all vertex objects
    ArrayList<T> getNeighbours(T vertex);               //get neighbours of given vertex


}
