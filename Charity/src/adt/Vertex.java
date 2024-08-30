/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Chew Huai Ern & Andrew
 * @param <T>
 */
public class Vertex<T extends Comparable<T>, N extends Comparable<N>> {
    //Vertex Info
    T vertexInfo;
    int indeg; 
    int outdeg;
    
    //Reference
    Vertex<T,N> nextVertex;  //to the next Vertex
    Edge<T,N> firstEdge;    //to the first Edge node
    
    public Vertex() {
        vertexInfo=null;
        indeg=0;
        outdeg=0;
        nextVertex=null;
        firstEdge=null;
    }

    public Vertex(T vInfo, Vertex<T,N> next) {
        vertexInfo = vInfo;
        indeg = 0;
        outdeg = 0;
        nextVertex=next;
        firstEdge=null;
    }


}
