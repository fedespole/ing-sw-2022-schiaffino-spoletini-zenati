package it.polimi.ingsw.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SocketWriter<T> implements Runnable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final BlockingQueue<T> objectsToBeWritten;

    public SocketWriter(Socket socket, BlockingQueue<T> objectsToBeWritten) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.objectsToBeWritten = objectsToBeWritten;
    }

    public void run() {
        while (true) {
            try {
                T obj = objectsToBeWritten.take();
                out.writeObject(obj);
                out.flush();
            } catch (InterruptedException | IOException e) {
            }
            ;
        }
    }
}