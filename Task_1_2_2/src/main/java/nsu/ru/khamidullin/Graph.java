package nsu.ru.khamidullin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract class representing a graph data structure.
 *
 * @param <T> The type of data stored in the graph's vertices.
 */
public abstract class Graph<T> {
    private static final Pattern EDGE_RE = Pattern.compile("^([A-Za-z0-9 _]+)(<?)-(\\d+)->([A-Za-z0-9 _]+)$");
    protected final HashSet<Vertex<T>> vertexes;

    /**
     * Constructs a new graph with an empty set of vertices.
     */
    public Graph() {
        vertexes = new HashSet<>();
    }

    /**
     * Reads a graph from a text file and constructs an instance of the Adjacency List Graph.
     *
     * @param file The path to the input file containing graph data.
     * @throws RuntimeException if an I/O error occurs during file reading.
     */
    public static void readGraphFromFile(Graph<String> graph, String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            var existingVertexes = new HashMap<String, Vertex<String>>();

            String line;
            Matcher matcher;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                matcher = EDGE_RE.matcher(line);
                if (matcher.find()) {
                    String firstVertex = matcher.group(1);
                    String bidirectional = matcher.group(2);
                    long weight = Long.parseLong(matcher.group(3));
                    String secondVertex = matcher.group(4);

                    if (!existingVertexes.containsKey(firstVertex)) {
                        existingVertexes.put(firstVertex, graph.addVertex(firstVertex));
                    }
                    if (!existingVertexes.containsKey(secondVertex)) {
                        existingVertexes.put(secondVertex, graph.addVertex(secondVertex));
                    }
                    if (bidirectional.isEmpty()) {
                        graph.addEdge(existingVertexes.get(firstVertex), existingVertexes.get(secondVertex), weight);
                    } else {
                        graph.addBidirectionalEdge(existingVertexes.get(firstVertex), existingVertexes.get(secondVertex), weight);
                    }
                } else {
                    throw new IOException();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new vertex with the given value to the graph.
     *
     * @param value The value of the vertex to be added.
     * @return The newly added vertex.
     * @throws NullPointerException if the value is null.
     */
    public abstract Vertex<T> addVertex(T value);

    /**
     * Deletes a vertex from the graph, including all associated edges.
     *
     * @param vertex The vertex to be deleted.
     * @throws NullPointerException if the vertex is null.
     */
    public abstract void deleteVertex(Vertex<T> vertex);

    /**
     * Adds an edge between two vertices with the specified weight.
     *
     * @param from   The source vertex.
     * @param to     The target vertex.
     * @param weight The weight of the edge.
     * @throws NullPointerException if from or to is null.
     */
    public abstract void addEdge(Vertex<T> from, Vertex<T> to, long weight);

    /**
     * Deletes an edge between two vertices in the graph.
     *
     * @param from The source vertex.
     * @param to   The target vertex.
     */
    public abstract void deleteEdge(Vertex<T> from, Vertex<T> to);

    /**
     * Retrieves a list of edges originating from the specified vertex.
     *
     * @param vertex The vertex for which to retrieve edges.
     * @return A list of edges originating from the vertex.
     * @throws NullPointerException if the vertex is null.
     */
    public abstract List<Edge<T>> getEdges(Vertex<T> vertex);

    /**
     * Adds a bidirectional edge between two vertices with the specified weight.
     * The edge is added in both directions, from 'from' to 'to' and from 'to' to 'from'.
     *
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     * @param weight  The weight of the edge.
     * @throws NullPointerException if vertex1 or vertex2 is null.
     */
    public void addBidirectionalEdge(Vertex<T> vertex1, Vertex<T> vertex2, long weight) {
        addEdge(vertex1, vertex2, weight);
        addEdge(vertex2, vertex1, weight);
    }

    /**
     * Deletes a bidirectional edge between two vertices in the graph.
     * The edge is deleted in both directions, from 'from' to 'to' and from 'to' to 'from'.
     *
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     */
    public void deleteBidirectionalEdge(Vertex<T> vertex1, Vertex<T> vertex2) {
        deleteEdge(vertex1, vertex2);
        deleteEdge(vertex2, vertex1);
    }

    /**
     * Retrieves the value associated with a vertex.
     *
     * @param vertex The vertex for which to retrieve the value.
     * @return The value of the vertex or null if the vertex is not in the graph.
     * @throws NullPointerException if the vertex is null.
     */
    public T getVertexValue(Vertex<T> vertex) {
        if (vertex == null) {
            throw new NullPointerException();
        }
        if (!vertexes.contains(vertex)) {
            return null;
        }
        return vertex.getValue();
    }

    /**
     * Sets the value associated with a vertex.
     *
     * @param vertex   The vertex for which to set the new value.
     * @param newValue The new value to assign to the vertex.
     * @throws NullPointerException if the vertex is null.
     */
    public void setVertexValue(Vertex<T> vertex, T newValue) {
        if (vertex == null) {
            throw new NullPointerException();
        }
        if (!vertexes.contains(vertex)) {
            return;
        }
        vertex.setValue(newValue);
    }

    /**
     * Performs a distance-based sorting of the vertices from the specified starting vertex.
     *
     * @param distanceFromVertex The vertex from which to measure distances and sort.
     * @return A list of vertices sorted by their distance from the starting vertex.
     * @throws NullPointerException if the starting vertex is null.
     */
    public ArrayList<Vertex<T>> distanceSort(Vertex<T> distanceFromVertex) {
        if (!vertexes.contains(distanceFromVertex)) {
            return null;
        }

        var result = new ArrayList<Vertex<T>>(vertexes.size());

        NavigableSet<Edge<T>> vertexAndDistances = new TreeSet<>();
        HashMap<Vertex<T>, Edge<T>> vertexToVertexAndDistances = new HashMap<>();

        for (var vertex : vertexes) {
            var newVertexAndDistance = new Edge<>(vertex, Long.MAX_VALUE);
            if (vertex == distanceFromVertex) {
                newVertexAndDistance.setDistance(0);
            }

            vertexAndDistances.add(newVertexAndDistance);
            vertexToVertexAndDistances.put(vertex, newVertexAndDistance);
        }

        while (!vertexAndDistances.isEmpty()) {
            var currentVertex = vertexAndDistances.pollFirst();

            result.add(currentVertex.getVertex());
            vertexToVertexAndDistances.remove(currentVertex.getVertex());

            for (var neighbourEdge : getEdges(currentVertex.getVertex())) {
                if (!vertexToVertexAndDistances.containsKey(neighbourEdge.getVertex())) {
                    continue;
                }

                var tmp = vertexToVertexAndDistances.get(neighbourEdge.getVertex());

                if (tmp.getDistance() > (neighbourEdge.getDistance() + currentVertex.getDistance())) {
                    vertexAndDistances.remove(tmp);
                    tmp.setDistance(neighbourEdge.getDistance() + currentVertex.getDistance());
                    vertexAndDistances.add(tmp);
                }
            }
        }
        return result;
    }

    /**
     * Retrieves a copy of the set of vertices in the graph.
     *
     * @return A copy of the set of vertices.
     */
    public HashSet<Vertex<T>> getVertexes() {
        return (HashSet<Vertex<T>>) vertexes.clone();
    }
}
