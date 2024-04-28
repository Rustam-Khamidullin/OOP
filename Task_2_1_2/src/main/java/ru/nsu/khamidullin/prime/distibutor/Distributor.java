package ru.nsu.khamidullin.prime.distibutor;

import java.net.ServerSocket;
import java.util.Arrays;

public class Distributor extends Thread {
    private static final int PORT = 8080;
    private static final int PARTS = 5;
    private final int[] array;
    private boolean result = false;

    public Distributor(int[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            int blockSize = array.length / PARTS;
            int start = 0;
            int end = blockSize;

            var requestHandlers = new RequestManager[PARTS];

            for (int i = 0; i < PARTS; i++) {
                var newTask = new RequestManager(Arrays.copyOfRange(array, start, end), serverSocket);
                newTask.start();
                requestHandlers[i] = newTask;

                start = end;
                end = i == PARTS - 2 ? array.length : end + blockSize;
            }

            for (var request : requestHandlers) {
                request.join();
                result |= request.getResult();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getResult() {
        return result;
    }

}
