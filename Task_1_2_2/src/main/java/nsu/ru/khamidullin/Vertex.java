package nsu.ru.khamidullin;

/**
 * Represents a vertex in a graph, containing a value of generic type.
 *
 * @param <T> The type of data stored in the vertex.
 */
public class Vertex<T> {
    private T value;

    /**
     * Creates a new vertex with the specified value.
     *
     * @param value The value associated with the vertex.
     */
    public Vertex(T value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the vertex.
     *
     * @return The value of the vertex.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value associated with the vertex.
     *
     * @param value The new value to assign to the vertex.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Computes a hash code for the vertex based on its value.
     *
     * @return The computed hash code.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
