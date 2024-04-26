package ru.nsu.khamidullin.prime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Node {
    public static void main(String[] args) {
        int port = 0;
        try {
            byte[] receiveData = new byte[4];
            MulticastSocket clientSocket = new MulticastSocket(8888);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            clientSocket.joinGroup(group);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            port = ByteBuffer.wrap(receivePacket.getData()).getInt();
            System.out.println("Received multicast message: " + port);

            clientSocket.leaveGroup(group);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Socket socket = new Socket("localhost", port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            int[] receivedArray = (int[]) in.readObject();

            boolean result = Arrays.stream(receivedArray)
                    .anyMatch(Node::isPrime);
            System.out.println(result);


            out.writeBoolean(result);

            out.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPrime(int x) {
        if (Math.abs((long) x) <= 1) {
            return false;
        }
        int sqrt = (int) Math.floor(Math.sqrt(Math.abs((long) x)));
        for (int i = 2; i <= sqrt; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}