package nsu.ru.khamidullin;

/**
 * Represents an edge in a graph connecting two vertices with a distance.
 *
 * @param <T> The type of data stored in the vertices.
 */
public class Edge<T> implements Comparable<Edge<T>> {
    private final Vertex<T> vertex;
    private long distance;

    /**
     * Creates a new edge with the specified target vertex and distance.
     *
     * @param vertex   The target vertex connected by the edge.
     * @param distance The distance associated with the edge.
     */
    public Edge(Vertex<T> vertex, long distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    /**
     * Computes a hash code for the edge based on the target vertex and distance.
     *
     * @return The computed hash code.
     */
    @Override
    public int hashCode() {
        return vertex.hashCode() + (int) distance;
    }

    /**
     * Retrieves the target vertex connected by the edge.
     *
     * @return The target vertex.
     */
    public Vertex<T> getVertex() {
        return vertex;
    }

    /**
     * Retrieves the distance associated with the edge.
     *
     * @return The distance value.
     */
    public long getDistance() {
        return distance;
    }

    /**
     * Sets the distance associated with the edge.
     *
     * @param distance The new distance value to set.
     */
    public void setDistance(long distance) {
        this.distance = distance;
    }

    /**
     * Compares this edge to another edge based on their distances.
     *
     * @param o The other edge to compare to.
     * @return 0 if the edges are the same, 1 if the distances are the same, or the result of the distance comparison.
     */
    @Override
    public int compareTo(Edge<T> o) {
        if (this == o) {
            return 0;
        }
        if (o == null || distance == o.distance) {
            return 1;
        }
        return Long.compare(distance, o.distance);
    }
}