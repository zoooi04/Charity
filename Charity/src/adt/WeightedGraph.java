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
public class WeightedGraph<T extends Comparable<T>, N extends Comparable<N>> implements GraphInterface<T, N> {

    Vertex<T, N> head;
    int size;

    public WeightedGraph() {
        head = null;
        size = 0;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getIndeg(T v) {
        if (hasVertex(v) == true) {
            Vertex<T, N> temp = head;
            while (temp != null) {
                //is the vertex we looking for?
                if (temp.vertexInfo.compareTo(v) == 0) {
                    return temp.indeg;
                }
                temp = temp.nextVertex;
            }
        }
        return -1; //return -1 if cannot find
    }

    @Override
    public int getOutdeg(T v) {
        if (hasVertex(v) == true) {
            Vertex<T, N> temp = head;
            while (temp != null) {
                if (temp.vertexInfo.compareTo(v) == 0) {
                    return temp.outdeg;
                }
                temp = temp.nextVertex;
            }
        }
        return -1;
    }

    @Override
    public boolean hasVertex(T v) {
        if (head == null) {
            return false;
        }
        Vertex<T, N> temp = head;
        while (temp != null) {
            //is the vertex we looking for?
            if (temp.vertexInfo.compareTo(v) == 0) {
                return true;
            }
            temp = temp.nextVertex;
        }
        return false;
    }

    @Override
    public boolean addVertex(T v) {
        if (hasVertex(v) == false) {
            //Vertex not in graph
            Vertex<T, N> temp = head;
            Vertex<T, N> newVertex = new Vertex<>(v, null);

            if (head == null)//Graph is empty, point to this head
            {
                head = newVertex;
            } else {

                Vertex<T, N> previous = head;
                //use previous to move to the last vertex
                while (temp != null) {
                    previous = temp;
                    temp = temp.nextVertex;//add the vertex as last in the list
                }
                previous.nextVertex = newVertex;
            }
            size++;
            return true;
        } else {
            //Vertex already in graph
            return false;
        }

    }

    //With node information, find the index of the vertex
    @Override
    public int getIndex(T v) {
        Vertex<T, N> temp = head;
        int pos = 0;
        while (temp != null) {
            if (temp.vertexInfo.compareTo(v) == 0) //vertex is found
            {
                return pos;
            }

            temp = temp.nextVertex;//move to the next vertex
            pos += 1;
        }
        return -1;
    }

    @Override
    //Return ArrayList that stores T
    public ListInterface<T> getAllVertexObjects() {
        ListInterface<T> list = new ArrayList<>();
        Vertex<T, N> temp = head;
        while (temp != null) {
            //use add method of ArrayList to add each vertexInfo
            list.add(temp.vertexInfo);
            temp = temp.nextVertex;
        }
        return list;
    }

    @Override
    //Get vertex info at a specific index/position
    public T getVertex(int pos) {
        if (pos > size - 1 || pos < 0)//if position is not valid
        {
            return null;
        }

        Vertex<T, N> temp = head;

        for (int i = 0; i < pos; i++) {
            temp = temp.nextVertex;
        }
        return temp.vertexInfo;
    }

    //Add a new edge from source to destination with a weight
    @Override
    public boolean addEdge(T source, T destination, N w) {
        if (head == null) {
            return false;
        }
        if (!hasVertex(source) || !hasVertex(destination)) {
            return false;
        }
        Vertex<T, N> sourceVertex = head;
        while (sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                //Reached source vertex, look for destination now
                Vertex<T, N> destinationVertex = head;
                while (destinationVertex != null) {
                    if (destinationVertex.vertexInfo.compareTo(destination) == 0) {
                        //Reached destination vertex, add edge here
                        Edge<T, N> currentEdge = sourceVertex.firstEdge;
                        Edge<T, N> newEdge = new Edge<>(destinationVertex, w, currentEdge);
                        sourceVertex.firstEdge = newEdge;
                        sourceVertex.outdeg++;
                        destinationVertex.indeg++;
                        return true;
                    }
                    destinationVertex = destinationVertex.nextVertex;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
        return false;
    }

//    public ArrayList<Edge<T, N>> getEdges(T v) {
//        ArrayList<Edge<T, N>> edges = new ArrayList<>();
//        Vertex<T, N> vertex = head;
//
//        while (vertex != null) {
//            if (vertex.vertexInfo.compareTo(v) == 0) {
//                Edge<T, N> currentEdge = vertex.firstEdge;
//                while (currentEdge != null) {
//                    edges.add(currentEdge);
//                    currentEdge = currentEdge.nextEdge;
//                }
//                return edges; // Found the vertex and collected all edges
//            }
//            vertex = vertex.nextVertex;
//        }
//
//        return edges; // Return empty list if vertex is not found
//    }
    @Override
    public boolean addUndirectedEdge(T source, T destination, N weight) {
        return addEdge(source, destination, weight) && addEdge(destination, source, weight);
    }

    @Override
    public Edge<T, N> removeEdge(T source, T destination) {
        Edge<T, N> removedEdge = null;

        // Check if the edge exists
        if (!hasEdge(source, destination)) {
            return removedEdge;
        }

        Vertex<T, N> sourceVertex = head;
        while (sourceVertex != null) {
            // Find the source vertex
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                Edge<T, N> currentEdge = sourceVertex.firstEdge;

                // If the edge to remove is the first edge
                if (currentEdge != null && currentEdge.toVertex.vertexInfo.compareTo(destination) == 0) {
                    removedEdge = currentEdge;
                    sourceVertex.firstEdge = currentEdge.nextEdge;
                } else {
                    // Traverse the edges
                    Edge<T, N> prevEdge = null;
                    while (currentEdge != null) {
                        if (currentEdge.toVertex.vertexInfo.compareTo(destination) == 0) {
                            removedEdge = currentEdge;
                            if (prevEdge != null) {
                                prevEdge.nextEdge = currentEdge.nextEdge;
                            } else {
                                sourceVertex.firstEdge = currentEdge.nextEdge;
                            }
                            break;
                        }
                        prevEdge = currentEdge;
                        currentEdge = currentEdge.nextEdge;
                    }
                }

                // If we found and removed an edge, update the degree counts and size
                if (removedEdge != null) {
                    sourceVertex.outdeg--;
                    removedEdge.toVertex.indeg--;
                    size--;
                }
                break; // Exit the loop after processing the source vertex
            }
            sourceVertex = sourceVertex.nextVertex;
        }

        return removedEdge;
    }

    @Override
    //Check whether there is an edge
    public boolean hasEdge(T source, T destination) {
        //Graph is empty
        if (head == null) {
            return false;
        }
        //No such vertices
        if (!hasVertex(source) || !hasVertex(destination)) {
            return false;
        }
        Vertex<T, N> sourceVertex = head;
        while (sourceVertex != null) {
            //Search for the edge in valid condition
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                //Reached source vertex, look for destination now
                Edge<T, N> currentEdge = sourceVertex.firstEdge;
                while (currentEdge != null) {
                    if (currentEdge.toVertex.vertexInfo.compareTo(destination) == 0) //destination vertex found
                    {
                        return true;
                    }
                    currentEdge = currentEdge.nextEdge;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
        return false;//Find no such edge in previous loop
    }

    @Override
    public N getEdgeWeight(T source, T destination) {
        N notFound = null;
        if (head == null) {
            return notFound;
        }
        Vertex<T, N> sourceVertex = head;
        while (sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                //Reached source vertex, look for destination now
                Edge<T, N> currentEdge = sourceVertex.firstEdge;
                while (currentEdge != null) {
                    //Edge found return weight
                    if (currentEdge.toVertex.vertexInfo.compareTo(destination) == 0) //destination vertex found
                    {
                        return currentEdge.weight;
                    }
                    currentEdge = currentEdge.nextEdge;

                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
        return notFound;
    }

    @Override
    public ListInterface<T> getNeighbours(T v) {
        if (!hasVertex(v)) {
            return null;
        }
        ListInterface<T> list = new ArrayList<>();
        Vertex<T, N> temp = head;
        //loop to find vertex and create a reference to edge if found
        while (temp != null) {
            if (temp.vertexInfo.compareTo(v) == 0) {
                //Reached vertex, look for destination now
                Edge<T, N> currentEdge = temp.firstEdge;
                //read edges and add to ArrayList
                while (currentEdge != null) {
                    list.add(currentEdge.toVertex.vertexInfo);
                    currentEdge = currentEdge.nextEdge;
                }
            }
            temp = temp.nextVertex;
        }
        return list;
    }

    @Override
    public void printEdges() {
        Vertex<T, N> temp = head;
        while (temp != null) {
            //Print a vertex
            System.out.println("# " + temp.vertexInfo + " : ");
            Edge<T, N> currentEdge = temp.firstEdge;
            while (currentEdge != null) {
                //Print edges in a nested loop
                System.out.print("[" + temp.vertexInfo + "," + currentEdge.toVertex.vertexInfo + "] ");
                currentEdge = currentEdge.nextEdge;
            }
            System.out.println();
            temp = temp.nextVertex;
        }
    }

}
