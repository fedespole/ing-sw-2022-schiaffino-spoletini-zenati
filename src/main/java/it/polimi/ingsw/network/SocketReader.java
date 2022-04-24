package it.polimi.ingsw.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SocketReader<T> implements Runnable {
    Socket socket;
    private final ObjectInputStream in;

    private final BlockingQueue<T> retrievedObjects;

    private final Class<T> objClass;

    public SocketReader(Socket socket, BlockingQueue<T> retrievedObjects, Class<T> objClass) throws IOException {
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.retrievedObjects = retrievedObjects;
        this.objClass = objClass;
    }

    public void run() {
        while (true) {
            try {
                Object receivedObj = in.readObject();
                if (objClass.isAssignableFrom(receivedObj.getClass())) {
                    retrievedObjects.put(objClass.cast(receivedObj));
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
            }
            ;
        }
    }
}
