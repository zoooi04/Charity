/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Chew Huai Ern & Andrew
 * @param <T>
 * @param <N>
 */
public class Edge<T extends Comparable<T>, N extends Comparable<N>> {
    //Edge nodes
    Vertex<T,N> toVertex;   //Reference to adjacent vertex
    N weight;               //Weight of the edge
    Edge<T,N> nextEdge;     //Reference to the next edge node
    
    public Edge(Vertex<T,N> destination, N w, Edge<T,N> a) {
        toVertex = destination;
        weight = w;
        nextEdge = a;
    }


}
