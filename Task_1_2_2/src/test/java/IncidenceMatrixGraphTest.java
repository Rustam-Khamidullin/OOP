import nsu.ru.khamidullin.Graph;
import nsu.ru.khamidullin.IncidenceMatrixGraph;
import nsu.ru.khamidullin.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        var a = graph.addVertex("A");
        var b = graph.addVertex("B");
        var c = graph.addVertex("C");
        var d = graph.addVertex("D");
        var e = graph.addVertex("E");
        var f = graph.addVertex("F");
        var g = graph.addVertex("G");

        graph.addBidirectionalEdge(a, b, 5);
        graph.addBidirectionalEdge(a, d, 12);
        graph.addBidirectionalEdge(a, g, 25);
        graph.addBidirectionalEdge(b, d, 8);
        graph.addBidirectionalEdge(d, c, 2);
        graph.addBidirectionalEdge(c, e, 4);
        graph.addBidirectionalEdge(c, g, 10);
        graph.addBidirectionalEdge(c, f, 5);
        graph.addBidirectionalEdge(e, g, 5);
        graph.addBidirectionalEdge(g, f, 10);


        var sortedVertexes = graph.distanceSort(c);

        assertSame(sortedVertexes.get(0), c);
        assertSame(sortedVertexes.get(1), d);
        assertSame(sortedVertexes.get(2), e);
        assertSame(sortedVertexes.get(3), f);
        assertSame(sortedVertexes.get(4), g);
        assertSame(sortedVertexes.get(5), b);
        assertSame(sortedVertexes.get(6), a);
    }

    @Test
    public void testReadFile1() {
        var graph = new IncidenceMatrixGraph<String>();
        Graph.readGraphFromFile(graph, "src/test/resources/Test1");

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
        var graph = new IncidenceMatrixGraph<String>();
        Graph.readGraphFromFile(graph, "src/test/resources/Test2");
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

    @Test
    public void testDeleteVertex() {
        var graph = new IncidenceMatrixGraph<Integer>();

        var first = graph.addVertex(1);
        var second = graph.addVertex(2);
        var three = graph.addVertex(3);

        graph.addEdge(first, second, 3);
        graph.addEdge(first, three, 1);
        graph.addEdge(second, three, 1);

        graph.deleteVertex(first);

        assertEquals(2, graph.getVertexes().size());
        assertEquals(1, graph.getEdges(second).size());
        assertEquals(0, graph.getEdges(three).size());
    }

    @Test
    public void testDeleteEdge() {
        var graph = new IncidenceMatrixGraph<Integer>();

        var first = graph.addVertex(1);
        var second = graph.addVertex(2);
        var three = graph.addVertex(3);

        graph.addEdge(first, second, 3);
        graph.addEdge(first, three, 1);
        graph.addEdge(second, three, 1);

        graph.deleteEdge(first, second);

        assertEquals(3, graph.getVertexes().size());
        assertEquals(1, graph.getEdges(first).size());
        assertEquals(1, graph.getEdges(second).size());
        assertEquals(0, graph.getEdges(three).size());
    }

}
