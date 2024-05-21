package ru.nsu.khamidullin.prime.primefinder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The RequestManager class handles incoming requests from clients
 * to check whether a given array contains prime numbers.
 * It listens for client connections on a specified ServerSocket
 * and communicates with clients using Object streams.
 */
public class RequestManager extends Thread {
    private final ServerSocket serverSocket;
    private final int[] array;
    private Boolean result = null;

    /**
     * Constructs a RequestManager with the specified array of integers and ServerSocket.
     *
     * @param array        the array of integers to be checked for prime numbers
     * @param serverSocket the ServerSocket on which to listen for client connections
     */
    public RequestManager(int[] array, ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.array = array;
    }

    /**
     * Listens for incoming client connections and communicates with clients to process requests.
     * Updates the result field when a response is received from the client.
     */
    @Override
    public void run() {
        while (result == null) {
            try (Socket socket = serverSocket.accept()) {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                out.writeObject(array);

                result = in.readBoolean();

                out.close();
                in.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Gets the result of the prime number check.
     *
     * @return true if the array contains prime numbers,
     * false otherwise null if the result is not yet available
     */
    public Boolean getResult() {
        return result;
    }
}
