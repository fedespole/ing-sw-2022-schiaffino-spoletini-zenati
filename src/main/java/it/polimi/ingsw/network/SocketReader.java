package it.polimi.ingsw.network;

import it.polimi.ingsw.common.events.ClientDisconnectedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
/**
 * This class has the function of creating an input queue for the events received by the socket.
 * If it catches an exception, it closes the socket and it raises an event that notifies the server of this action.
 */
public class SocketReader<T> implements Runnable{

    private final Socket socket;
    private  ObjectInputStream in;
    private final BlockingQueue<T> objectsToBeRead;
    private final Class<T> objClass;

    public SocketReader(Socket socket, BlockingQueue<T> retrievedObjects, Class<T> objClass) throws IOException {
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.objectsToBeRead = retrievedObjects;
        this.objClass = objClass;
    }

    public void run() {
        while (true) {
            try {
                Object receivedObj = in.readObject();
                if (objClass.isAssignableFrom(receivedObj.getClass())) {
                    objectsToBeRead.put(objClass.cast(receivedObj));
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                try {
                    socket.close();
                    objectsToBeRead.put(objClass.cast(new ClientDisconnectedEvent(this)));
                    break;
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
