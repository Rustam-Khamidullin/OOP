import nsu.ru.khamidullin.AdjacecyListGraph;
import nsu.ru.khamidullin.Graph;
import nsu.ru.khamidullin.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


/**
 * Test class.
 */
public class AdjacencyListGraphTest {

    @Test
    public void testSort1() {
        AdjacecyListGraph<Integer> graph = new AdjacecyListGraph<>();

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
        AdjacecyListGraph<String> graph = new AdjacecyListGraph<>();

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

    @Test
    public void testReadFile1() {
        var graph = Graph.readGraphFromFile("src/test/resources/Test1");
        Vertex<String> vertex = null;
        for (var ver : graph.getVertexes()) {
            if (ver.getValue().equals("A")) {
                vertex = ver;
                break;
            }
        }

        var sortedVertexes = graph.distanceSort(vertex);

        assertEquals(sortedVertexes.get(0).getValue(), "A");
        assertEquals(sortedVertexes.get(1).getValue(), "C");
        assertEquals(sortedVertexes.get(2).getValue(), "B");
    }

    @Test
    public void testReadFile2() {
        var graph = Graph.readGraphFromFile("src/test/resources/Test2");
        Vertex<String> vertex = null;
        for (var ver : graph.getVertexes()) {
            if (ver.getValue().equals("C")) {
                vertex = ver;
                break;
            }
        }

        var sortedVertexes = graph.distanceSort(vertex);

        assertEquals(sortedVertexes.get(0).getValue(), "C");
        assertEquals(sortedVertexes.get(1).getValue(), "D");
        assertEquals(sortedVertexes.get(2).getValue(), "E");
        assertEquals(sortedVertexes.get(3).getValue(), "F");
        assertEquals(sortedVertexes.get(4).getValue(), "G");
        assertEquals(sortedVertexes.get(5).getValue(), "B");
        assertEquals(sortedVertexes.get(6).getValue(), "A");
    }
}
