package nsu.ru.khamidullin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a graph using an adjacency list.
 *
 * @param <T> The type of data stored in the graph's vertices.
 */
public class AdjacecyListGraph<T> extends Graph<T> {
    private final HashMap<Vertex<T>, LinkedList<Edge<T>>> adjacencyList;
    private final HashMap<Vertex<T>, HashSet<Vertex<T>>> reversedAdjacencyList;


    /**
     * Constructor
     */
    public AdjacecyListGraph() {
        super();
        adjacencyList = new HashMap<>();
        reversedAdjacencyList = new HashMap<>();
    }

    /**
     * Creates a new vertex object with the given value.
     *
     * @param value The value of the vertex.
     * @return The graph vertex.
     * @throws NullPointerException if the value is null.
     */
    @Override
    public Vertex<T> addVertex(T value) {
        if (value == null) {
            throw new NullPointerException();
        }

        Vertex<T> vertex = new Vertex<>(value);

        adjacencyList.put(vertex, new LinkedList<>());
        reversedAdjacencyList.put(vertex, new HashSet<>());
        vertexes.add(vertex);

        return vertex;
    }

    /**
     * Deletes a vertex from the graph.
     *
     * @param vertex The vertex to be deleted.
     */
    @Override
    public void deleteVertex(Vertex<T> vertex) {
        for (var verToVertex : reversedAdjacencyList.get(vertex)) {
            deleteLink(verToVertex, vertex);
        }
        vertexes.remove(vertex);
        adjacencyList.remove(vertex);
        reversedAdjacencyList.remove(vertex);
    }

    /**
     * Adds an edge between vertices with a specified weight.
     *
     * @param from   The source vertex.
     * @param to     The target vertex.
     * @param weight The weight of the edge.
     * @throws NullPointerException if from or to is null.
     */
    @Override
    public void addEdge(Vertex<T> from, Vertex<T> to, long weight) {
        if (from == null || to == null) {
            throw new NullPointerException();
        }

        adjacencyList.get(from).add(new Edge<>(to, weight));
        reversedAdjacencyList.get(to).add(from);
    }

    /**
     * Deletes an edge between vertices in the graph.
     *
     * @param from The source vertex.
     * @param to   The target vertex.
     */
    @Override
    public void deleteEdge(Vertex<T> from, Vertex<T> to) {
        deleteLink(from, to);
        reversedAdjacencyList.get(to).remove(from);
    }

    /**
     * Chang edge weight.
     *
     * @param from   The source vertex.
     * @param to     The target vertex.
     * @param weight New weight.
     */
    @Override
    public void changeEdge(Vertex<T> from, Vertex<T> to, long weight) {
        if (!vertexes.contains(from) || !vertexes.contains(to)) {
            throw new IllegalArgumentException();
        }
        for (var edge : adjacencyList.get(from)) {
            if (edge.getVertex() == to) {
                edge.setDistance(weight);
                break;
            }
        }
    }

    /**
     * Retrieves a list of edges originating from the specified vertex.
     *
     * @param vertex The vertex for which to retrieve edges.
     * @return A list of edges originating from the vertex.
     */
    @Override
    public List<Edge<T>> getEdges(Vertex<T> vertex) {

        return (List<Edge<T>>) adjacencyList.get(vertex).clone();
    }

    /**
     * Deletes a link (edge) between two vertices in the adjacency list.
     *
     * @param from The source vertex from which the link is removed.
     * @param to   The target vertex to which the link points.
     */
    private void deleteLink(Vertex<T> from, Vertex<T> to) {
        for (var edge : adjacencyList.get(from)) {
            if (edge.getVertex() == to) {
                adjacencyList.get(from).remove(edge);
                break;
            }
        }
    }
}
