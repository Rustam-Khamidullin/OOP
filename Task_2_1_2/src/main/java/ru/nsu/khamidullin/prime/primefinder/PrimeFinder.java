package ru.nsu.khamidullin.prime.primefinder;

/**
 * Main class.
 */
public class PrimeFinder {
    private static final int PORT = 8080;
    private static final String NODES_HOST = "230.0.0.1";
    private static final int NODES_PORT = 8888;

    /**
     * The main method of the PrimeFinder application.
     *
     * @param args the command-line arguments (not used)
     * @throws InterruptedException if any thread interrupts the current thread
     * while waiting for the Distributor to finish
     */
    public static void main(String[] args) throws InterruptedException {
        int[] arr = {1, 1, 4, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};

        NodeNotifier nodeNotifier = new NodeNotifier(PORT, NODES_HOST, NODES_PORT);

        Distributor distributor = new Distributor(arr);

        distributor.start();
        nodeNotifier.start();

        distributor.join();

        System.out.println(distributor.getResult());
    }
}
