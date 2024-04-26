package ru.nsu.khamidullin.prime.distibutor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestHandler extends Thread {
    private final ServerSocket serverSocket;
    private final int[] array;
    private Boolean result = null;


    public RequestHandler(int[] array, ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.array = array;

    }

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

    public Boolean getResult() {
        return result;
    }
}
