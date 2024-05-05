package ru.nsu.khamidullin.prime.primefinder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

/**
 * The NodeNotifier class periodically sends notification
 * messages to a multicast group of nodes.
 * Each notification message contains information about the port
 * on which this node is listening for requests.
 */
public class NodeNotifier extends Thread {
    private final static int SLEEP_TIME = 1000;
    private final String nodesHost;
    private final int port;
    private final int nodesPort;

    /**
     * Constructs a NodeNotifier with the specified parameters.
     *
     * @param port      the port on which this node is listening for requests
     * @param nodesHost the hostname of the multicast group
     * @param nodesPort the port of the multicast group
     */
    public NodeNotifier(int port, String nodesHost, int nodesPort) {
        this.nodesHost = nodesHost;
        this.port = port;
        this.nodesPort = nodesPort;
        this.setDaemon(true);
    }

    /**
     * Periodically sends notification messages containing
     * the port information to the multicast group.
     */
    @Override
    public void run() {
        try (MulticastSocket multicastSocket = new MulticastSocket()) {
            InetAddress group = InetAddress.getByName(nodesHost);

            byte[] sendData = ByteBuffer.allocate(Integer.BYTES).putInt(port).array();
            DatagramPacket sendPacket = new DatagramPacket(
                    sendData, sendData.length, group, nodesPort);

            while (true) {
                multicastSocket.send(sendPacket);
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException ignored) {
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}