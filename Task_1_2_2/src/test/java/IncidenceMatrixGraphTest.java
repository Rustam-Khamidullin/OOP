import nsu.ru.khamidullin.IncidenceMatrixGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test class.
 */
public class IncidenceMatrixGraphTest {

    @Test
    public void testSort1() {
        var graph = new IncidenceMatrixGraph<Integer>();

        var first = graph.addVertex(1);
        var second = graph.addVertex(2);
        var three = graph.addVertex(3);

        graph.addEdge(first, second, 3);
        graph.addEdge(first, three, 1);
        graph.addEdge(second, three, 1);

        var sortedVertexes = graph.distanceSort(first);

        assertSame(sortedVertexes.get(0), first);
        assertSame(sortedVertexes.get(1), three);
        assertSame(sortedVertexes.get(2), second);
    }

    @Test
    public void testSort2() {
        var graph = new IncidenceMatrixGraph<String>();

        var A = graph.addVertex("A");
        var B = graph.addVertex("B");
        var C = graph.addVertex("C");
        var D = graph.addVertex("D");
        var E = graph.addVertex("E");
        var F = graph.addVertex("F");
        var G = graph.addVertex("G");

        graph.addBidirectionalEdge(A, B, 5);
        graph.addBidirectionalEdge(A, D, 12);
        graph.addBidirectionalEdge(A, G, 25);
        graph.addBidirectionalEdge(B, D, 8);
        graph.addBidirectionalEdge(D, C, 2);
        graph.addBidirectionalEdge(C, E, 4);
        graph.addBidirectionalEdge(C, G, 10);
        graph.addBidirectionalEdge(C, F, 5);
        graph.addBidirectionalEdge(E, G, 5);
        graph.addBidirectionalEdge(G, F, 10);


        var sortedVertexes = graph.distanceSort(C);

        assertSame(sortedVertexes.get(0), C);
        assertSame(sortedVertexes.get(1), D);
        assertSame(sortedVertexes.get(2), E);
        assertSame(sortedVertexes.get(3), F);
        assertSame(sortedVertexes.get(4), G);
        assertSame(sortedVertexes.get(5), B);
        assertSame(sortedVertexes.get(6), A);
    }
}
