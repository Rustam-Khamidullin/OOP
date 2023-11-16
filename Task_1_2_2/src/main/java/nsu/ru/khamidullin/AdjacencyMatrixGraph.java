package nsu.ru.khamidullin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Class representing a graph using an adjacency matrix.
 *
 * @param <T> The type of data stored in the graph's vertices.
 */
public class AdjacencyMatrixGraph<T> extends Graph<T> {
    private final ArrayList<ArrayList<Long>> adjacencyMatrix;
    private final ArrayList<Vertex<T>> vertexFromIndex;
    private final HashMap<Vertex<T>, Integer> indexFromVertex;

    /**
     * Creates a new instance of the AdjacencyMatrixGraph.
     */
    public AdjacencyMatrixGraph() {
        super();
        adjacencyMatrix = new ArrayList<>();
        vertexFromIndex = new ArrayList<>();
        indexFromVertex = new HashMap<>();
    }

    /**
     * Adds a vertex with the specified value to the graph.
     *
     * @param value The value of the vertex to be added.
     * @return The newly added vertex.
     * @throws NullPointerException if the value is null.
     */
    @Override
    public Vertex<T> addVertex(T value) {
        if (value == null) {
            throw new NullPointerException();
        }

        var newVertex = new Vertex<>(value);
        int index = adjacencyMatrix.size();

        for (var line : adjacencyMatrix) {
            line.add(0L);
        }

        vertexFromIndex.add(newVertex);
        indexFromVertex.put(newVertex, index);
        adjacencyMatrix.add(new ArrayList<>(Collections.nCopies(index + 1, 0L)));

        vertexes.add(newVertex);

        return newVertex;
    }


    /**
     * Deletes a vertex from the graph, including all associated edges.
     *
     * @param vertex The vertex to be deleted.
     * @throws NullPointerException if the vertex is null.
     */
    @Override
    public void deleteVertex(Vertex<T> vertex) {
        if (vertex == null) {
            throw new NullPointerException();
        }
        if (!vertexes.contains(vertex)) {
            return;
        }

        int index = indexFromVertex.get(vertex);

        adjacencyMatrix.remove(index);

        for (var line : adjacencyMatrix) {
            line.remove(index);
        }

        for (int i = index; i < vertexFromIndex.size(); i++) {
            var curVertex = vertexFromIndex.get(i);
            indexFromVertex.put(curVertex, indexFromVertex.get(curVertex) - 1);
        }
    }

    /**
     * Adds an edge between two vertices with the specified weight.
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
        if (!vertexes.contains(from) || !vertexes.contains(to)) {
            return;
        }
        var indexFrom = indexFromVertex.get(from);
        var indexTo = indexFromVertex.get(to);
        adjacencyMatrix.get(indexFrom).set(indexTo, weight);
    }

    /**
     * Deletes an edge between two vertices in the graph.
     *
     * @param from The source vertex.
     * @param to   The target vertex.
     */
    @Override
    public void deleteEdge(Vertex<T> from, Vertex<T> to) {
        addEdge(from, to, 0L);
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
        if (adjacencyMatrix.get(indexFromVertex.get(from)).get(indexFromVertex.get(to)) != 0) {
            adjacencyMatrix.get(indexFromVertex.get(from)).set(indexFromVertex.get(to), weight);
        }
    }

    /**
     * Retrieves a list of edges originating from the specified vertex.
     *
     * @param vertex The vertex for which to retrieve edges.
     * @return A list of edges originating from the vertex.
     * @throws NullPointerException if the vertex is null.
     */
    @Override
    public List<Edge<T>> getEdges(Vertex<T> vertex) {
        if (vertex == null) {
            throw new NullPointerException();
        }
        if (!vertexes.contains(vertex)) {
            return null;
        }

        var line = adjacencyMatrix.get(indexFromVertex.get(vertex));

        var result = new ArrayList<Edge<T>>();

        for (int i = 0; i < line.size(); i++) {
            var distance = line.get(i);
            if (distance != 0) {
                result.add(new Edge<>(vertexFromIndex.get(i), distance));
            }
        }
        return result;
    }
}
