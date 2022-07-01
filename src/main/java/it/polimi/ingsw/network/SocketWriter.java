package it.polimi.ingsw.network;

import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
/**
 * This class has the function of creating an output queue for the events about to be sent by the socket.
 * If it catches an exception, it closes the socket.
 */
public class SocketWriter<T> implements Runnable {

    private final Socket socket;
    private ObjectOutputStream out;
    private final BlockingQueue<T> objectsToBeWritten;

    public SocketWriter(Socket socket, BlockingQueue<T> objectsToBeWritten) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.objectsToBeWritten = objectsToBeWritten;
    }
    public void run() {
        while (!socket.isClosed()) {
            try {
                T obj = objectsToBeWritten.take();
                out.writeObject(obj);
                out.flush();
                out.reset();
            } catch (InterruptedException | IOException e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}