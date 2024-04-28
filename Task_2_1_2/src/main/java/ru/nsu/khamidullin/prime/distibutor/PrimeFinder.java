package ru.nsu.khamidullin.prime.distibutor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class PrimeFinder {
    private static final int PORT = 8080;
    private static final String NODES_HOST = "230.0.0.1";
    private static final int NODES_PORT = 8888;

    public static void main(String[] args) throws InterruptedException {
        int[] arr = {1, 1, 4, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};

        Distributor distributor = new Distributor(arr);

        distributor.start();

        Thread.sleep(1000);

        notifyNode();

        distributor.join();

        System.out.println(distributor.getResult());
    }

    private static void notifyNode() {
        try {
            MulticastSocket multicastSocket = new MulticastSocket();
            InetAddress group = InetAddress.getByName(NODES_HOST);

            byte[] sendData = ByteBuffer.allocate(Integer.BYTES).putInt(PORT).array();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, NODES_PORT);
            multicastSocket.send(sendPacket);
            multicastSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}