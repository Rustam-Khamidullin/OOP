package ru.nsu.khamidullin.prime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Node class.
 */
public class Node extends Thread {
    private static final int NODES_PORT = 8888;
    private static final String NODES_HOST = "230.0.0.1";

    /**
     * The main method of the Node application.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        Node node = new Node();
        node.start();
    }

    /**
     * Receives port information from multicast messages sent by NodeNotifiers.
     *
     * @return the port number received from the multicast message
     */
    private static int receivePort() {
        try {
            int port;

            MulticastSocket clientSocket = new MulticastSocket(NODES_PORT);
            InetAddress group = InetAddress.getByName(NODES_HOST);
            clientSocket.joinGroup(group);

            byte[] receiveData = new byte[Integer.BYTES];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            clientSocket.receive(receivePacket);
            port = ByteBuffer.wrap(receivePacket.getData()).getInt();
            System.out.println("Received multicast message: " + port);

            clientSocket.leaveGroup(group);
            clientSocket.close();

            return port;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks whether the received array contains prime numbers.
     *
     * @param array the array of integers to be checked for prime numbers
     * @return true if the array contains prime numbers, false otherwise
     */
    private static boolean hasPrime(int[] array) {
        for (int x : array) {
            if (Math.abs((long) x) <= 1) {
                continue;
            }
            boolean res = true;
            int sqrt = (int) Math.floor(Math.sqrt(Math.abs((long) x)));
            for (int i = 2; i <= sqrt; i++) {
                if (x % i == 0) {
                    res = false;
                    break;
                }
            }
            if (res) {
                return true;
            }
        }
        return false;
    }

    /**
     * Listens for multicast messages to receive port information
     * and establishes connections with other nodes.
     */
    @Override
    public void run() {
        while (true) {
            int port = receivePort();

            while (true) {
                try (Socket socket = new Socket("localhost", port)) {
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    int[] receivedArray = (int[]) in.readObject();

                    boolean result = hasPrime(receivedArray);
                    System.out.println(result);

                    out.writeBoolean(result);

                    out.close();
                    in.close();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Stop connection with " + port);
                    break;
                }
            }
        }
    }
}