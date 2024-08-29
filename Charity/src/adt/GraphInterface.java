/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author huaiern
 */
public interface GraphInterface<T> {
    void addVertex(T vertex);                           //add vertex
    void addEdge(T source, T destination, int weight); // add edge
    boolean removeEdge(T v1, T v2);    //remove edge between 2 vertex
    void printGraph();              //print graph as hashmap
}
