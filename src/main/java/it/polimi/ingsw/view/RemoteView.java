package it.polimi.ingsw.view;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.network.SocketReader;
import it.polimi.ingsw.network.SocketWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

public class RemoteView extends View implements Runnable{
    private final LinkedBlockingQueue<GameEvent> clientEvs;
    private final LinkedBlockingQueue<GameEvent> serverEvs;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Socket clientSocket;

    public RemoteView(Socket clientSocket) throws IOException {
        super();
        this.clientSocket = clientSocket;
        clientEvs = new LinkedBlockingQueue<>();
        serverEvs = new LinkedBlockingQueue<>();

        executor.execute(new SocketWriter<>(clientSocket,serverEvs));
        executor.execute(new SocketReader<>(clientSocket,clientEvs,GameEvent.class));
    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try {
                GameEvent currEvent = clientEvs.take();
                GameHandler.calls(currEvent);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
