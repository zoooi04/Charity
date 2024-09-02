/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author Chew Huai Ern and Andrew Pheng Qi Jinn
 * @param <T>
 * @param <N>
 */



public interface GraphInterface<T extends Comparable<T>, N extends Comparable<N>> {
    
    
    
    /**
     * Adds a vertex to the graph.
     * @param newVertex The vertex to be added.
     * @return True if the newVertex was added successfully, false if it already exists.
     */
    boolean addVertex(T newVertex);

    /**
     * Checks if a vertex exists in the graph.
     * @param vertex The vertex to check.
     * @return True if the vertex exists, false otherwise.
     */
    boolean hasVertex(T vertex);

    /**
     * Gets a vertex based on its position in the graph.
     * @param position The position of the vertex.
     * @return The vertex at the specified position.
     */
    T getVertex(int position);

    /**
     * Finds the index of a vertex.
     * @param vertex The vertex to find.
     * @return The index of the vertex, or -1 if not found.
     */
    int getIndex(T vertex);

    /**
     * Adds an edge between two vertices with a specified weight.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @param weight The weight of the edge.
     * @return True if the edge was added successfully, false otherwise.
     */
    boolean addEdge(T source, T destination, N weight);

    boolean addUndirectedEdge(T source, T destination, N weight);
    
    
    
    
    /**
     * Removes an edge between two vertices.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @return The removed edge, or null if no edge was found.
     */
    Edge<T, N> removeEdge(T source, T destination);
    
    boolean removeUndirectedEdge(T source, T destination);

    /**
     * Prints all edges in the graph.
     */
    void printEdges();

    /**
     * Gets a list of all vertices in the graph.
     * @return A list of all vertex objects.
     */
    ListInterface<T> getAllVertexObjects();

    /**
     * Gets a list of all neighbors of a specified vertex.
     * @param vertex The vertex whose neighbors are to be retrieved.
     * @return A list of neighboring vertices.
     */
    ListInterface<T> getNeighbours(T vertex);

    /**
     * Checks if an edge exists between two vertices.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @return True if the edge exists, false otherwise.
     */
    boolean hasEdge(T source, T destination);

    /**
     * Gets the weight of the edge between two vertices.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @return The weight of the edge, or null if no edge exists.
     */
    N getEdgeWeight(T source, T destination);

    /**
     * Gets the in-degree of a vertex.
     * @param vertex The vertex whose in-degree is to be retrieved.
     * @return The in-degree of the vertex.
     */
    int getIndeg(T vertex);

    /**
     * Gets the out-degree of a vertex.
     * @param vertex The vertex whose out-degree is to be retrieved.
     * @return The out-degree of the vertex.
     */
    int getOutdeg(T vertex);

    /**
     * Performs a breadth-first search (BFS) traversal starting from a vertex.
     * @param start The starting vertex for BFS.
     * @return A list of vertices in the order they were visited.
     */
    //ListInterface<T> bfs(T start);

    /**
     * Performs a depth-first search (DFS) traversal starting from a vertex.
     * @param start The starting vertex for DFS.
     * @return A list of vertices in the order they were visited.
     */
    //ListInterface<T> dfs(T start);

    /**
     * Clears all vertices and edges from the graph, effectively resetting it to
     * an empty state.
     */
    void clear();

    /**
     * Gets the number of vertices currently in the graph.
     *
     * @return The number of vertices in the graph.
     */
    int getSize();
}