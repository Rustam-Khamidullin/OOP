package ru.nsu.khamidullin.prime.primefinder;

import java.net.ServerSocket;
import java.util.Arrays;

/**
 * The Distributor class divides an array of integers into parts and distributes
 * them among multiple RequestManager instances
 * to check for prime numbers concurrently.
 */
public class Distributor extends Thread {
    private static final int PORT = 8080;
    private static final int DEFAULT_PARTS = 5;
    private final int[] array;
    private final int parts;
    private boolean result = false;

    /**
     * Constructs a Distributor with the specified array of integers,
     * dividing it into default parts.
     *
     * @param array the array of integers to be distributed among RequestManagers
     */
    public Distributor(int[] array) {
        this(array, DEFAULT_PARTS);
    }

    /**
     * Constructs a Distributor with the specified array of integers, dividing it
     * into the specified number of parts.
     *
     * @param array the array of integers to be distributed among RequestManagers
     * @param parts the number of parts to divide the array into
     * @throws IllegalArgumentException if parts is less than or equal to 0
     */
    public Distributor(int[] array, int parts) {
        if (parts <= 0) {
            throw new IllegalArgumentException("Number of parts must be greater than 0");
        }
        this.array = array;
        this.parts = parts;
    }

    /**
     * Divides the array into parts, creates RequestManager instances for each part, and starts them.
     * Joins all RequestManager threads and computes the final result.
     */
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            int blockSize = array.length / parts;
            int start = 0;
            int end = blockSize;

            var requestHandlers = new RequestManager[parts];

            for (int i = 0; i < parts; i++) {
                var newTask = new RequestManager(Arrays.copyOfRange(array, start, end), serverSocket);
                newTask.start();
                requestHandlers[i] = newTask;

                start = end;
                end = i == parts - 2 ? array.length : end + blockSize;
            }

            for (var request : requestHandlers) {
                request.join();
                result |= request.getResult();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the result of the prime number check.
     *
     * @return true if the array contains prime numbers in any part, false otherwise
     */
    public boolean getResult() {
        return result;
    }
}
