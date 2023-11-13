package nsu.ru.khamidullin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a graph using an incidence matrix.
 *
 * @param <T> The type of data stored in the graph's vertices.
 */
public class IncidenceMatrixGraph<T> extends Graph<T> {
    private final ArrayList<LinkedList<Long>> incidenceMatrix;
    private final ArrayList<Vertex<T>> vertexFromIndex;
    private final HashMap<Vertex<T>, Integer> indexFromVertex;
    private int cntEdges;

    /**
     * Creates a new instance of the IncidenceMatrixGraph with an empty graph.
     */
    public IncidenceMatrixGraph() {
        super();
        incidenceMatrix = new ArrayList<>();
        vertexFromIndex = new ArrayList<>();
        indexFromVertex = new HashMap<>();
        cntEdges = 0;
    }

    /**
     * Adds a new vertex with the given value to the graph.
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
        var newLine = new LinkedList<Long>();
        for (int i = 0; i < cntEdges; i++) {
            newLine.add(0L);
        }

        incidenceMatrix.add(newLine);
        vertexFromIndex.add(newVertex);
        indexFromVertex.put(newVertex, incidenceMatrix.size() - 1);
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
        int index = indexFromVertex.get(vertex);
        var line = incidenceMatrix.get(index);

        var toDelete = new ArrayList<Integer>();
        for (int i = 0; i < cntEdges; i++) {
            if (line.get(i) != 0) {
                toDelete.add(i);
            }
        }

        incidenceMatrix.remove(index);
        vertexFromIndex.remove(index);
        indexFromVertex.remove(vertex);
        vertexes.remove(vertex);
        for (var v : indexFromVertex.keySet()) {
            if (indexFromVertex.get(v) > index) {
                indexFromVertex.put(v, indexFromVertex.get(v) - 1);
            }
        }


        for (var l : incidenceMatrix) {
            for (int i = toDelete.size() - 1; i >= 0; i--) {
                l.remove(i);
            }
        }
        cntEdges -= toDelete.size();
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

        for (int i = 0; i < incidenceMatrix.size(); i++) {
            if (i == indexFrom) {
                incidenceMatrix.get(i).add(weight);
            } else if (i == indexTo && from != to) {
                incidenceMatrix.get(i).add(-weight);
            } else {
                incidenceMatrix.get(i).add(0L);
            }
        }
        cntEdges++;
    }


    /**
     * Deletes an edge between two vertices in the graph.
     *
     * @param from The source vertex.
     * @param to   The target vertex.
     */
    @Override
    public void deleteEdge(Vertex<T> from, Vertex<T> to) {
        if (from == null || to == null) {
            throw new NullPointerException();
        }
        if (!vertexes.contains(from) || !vertexes.contains(to)) {
            return;
        }
        var lineFrom = incidenceMatrix.get(indexFromVertex.get(from));
        var lineTo = incidenceMatrix.get(indexFromVertex.get(to));

        if (from == to) {
            for (int i = 0; i < cntEdges; i++) {
                boolean loop = true;
                if (lineFrom.get(i) > 0) {
                    for (int j = 0; j < incidenceMatrix.size(); j++) {
                        if (incidenceMatrix.get(j).get(i) < 0) {
                            loop = false;
                            break;
                        }
                    }
                    if (loop) {
                        for (var line : incidenceMatrix) {
                            line.remove(i);
                        }
                        return;
                    }
                }
            }
            return;
        }

        for (int i = 0; i < cntEdges; i++) {
            if (lineFrom.get(i) > 0 && lineTo.get(i) < 0) {
                for (var line : incidenceMatrix) {
                    line.remove(i);
                }
                cntEdges--;
                return;
            }
        }
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
        var lineFrom = incidenceMatrix.get(indexFromVertex.get(from));
        var lineTo = incidenceMatrix.get(indexFromVertex.get(to));

        for (int i = 0; i < cntEdges; i++) {
            if (lineFrom.get(i) != 0 && lineTo.get(i) != 0) {
                lineTo.set(i, weight);
                lineFrom.set(i, weight);
            }
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

        int index = indexFromVertex.get(vertex);
        var line = incidenceMatrix.get(index);
        var result = new ArrayList<Edge<T>>();

        for (int i = 0; i < cntEdges; i++) {
            if (line.get(i) > 0) {
                boolean loop = true;
                for (int j = 0; j < incidenceMatrix.size(); j++) {
                    if (incidenceMatrix.get(j).get(i) < 0) {
                        loop = false;
                        result.add(new Edge<>(vertexFromIndex.get(j), line.get(i)));
                    }
                }
                if (loop) {
                    result.add(new Edge<>(vertex, line.get(i)));
                }
            }
        }
        return result;
    }
}
