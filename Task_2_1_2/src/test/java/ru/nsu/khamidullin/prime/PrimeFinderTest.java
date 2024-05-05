package ru.nsu.khamidullin.prime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.prime.primefinder.Distributor;
import ru.nsu.khamidullin.prime.primefinder.NodeNotifier;

/**
 * Test class.
 */
public class PrimeFinderTest {
    private static final int PORT = 8080;
    private static final String NODES_HOST = "230.0.0.1";
    private static final int NODES_PORT = 8888;

    @Test
    public void testPrimeFinderTrue() throws Exception {
        Node node = new Node();
        node.start();

        Thread.sleep(1000);

        NodeNotifier nodeNotifier = new NodeNotifier(PORT, NODES_HOST, NODES_PORT);
        nodeNotifier.start();

        int[] array = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3};

        Distributor distributor = new Distributor(array);
        distributor.start();

        distributor.join();

        Assertions.assertTrue(distributor.getResult());

        node.interrupt();
    }


    @Test
    public void testPrimeFinderFalse() throws Exception {
        Node node = new Node();
        node.start();

        Thread.sleep(1000);

        NodeNotifier nodeNotifier = new NodeNotifier(PORT, NODES_HOST, NODES_PORT);
        nodeNotifier.start();

        int[] array = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4};

        Distributor distributor = new Distributor(array);
        distributor.start();

        distributor.join();

        Assertions.assertFalse(distributor.getResult());

        node.interrupt();
    }

    @Test
    public void testPrimeFinderEmpty() throws Exception {
        Node node = new Node();
        node.start();

        Thread.sleep(1000);

        NodeNotifier nodeNotifier = new NodeNotifier(PORT, NODES_HOST, NODES_PORT);
        nodeNotifier.start();

        int[] array = {};

        Distributor distributor = new Distributor(array);
        distributor.start();

        distributor.join();

        Assertions.assertFalse(distributor.getResult());

        node.interrupt();
    }

    @Test
    public void testPrimeFinderDistributorFirst() throws Exception {
        NodeNotifier nodeNotifier = new NodeNotifier(PORT, NODES_HOST, NODES_PORT);
        nodeNotifier.start();

        int[] array = {};

        Distributor distributor = new Distributor(array);
        distributor.start();

        Thread.sleep(1000);

        Node node = new Node();
        node.start();

        distributor.join();

        Assertions.assertFalse(distributor.getResult());

        node.interrupt();
    }

    @Test
    public void testPrimeFinderSeveralNodes() throws Exception {
        NodeNotifier nodeNotifier = new NodeNotifier(PORT, NODES_HOST, NODES_PORT);
        nodeNotifier.start();

        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};

        Distributor distributor = new Distributor(array, 10);
        distributor.start();

        Thread.sleep(1000);

        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
            nodes[i].start();
        }

        distributor.join();

        Assertions.assertTrue(distributor.getResult());

        for (Node node : nodes) {
            node.interrupt();
        }
    }
}
