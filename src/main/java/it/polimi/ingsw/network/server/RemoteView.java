package it.polimi.ingsw.network.server;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.fromClientEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewPlayerCreatedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.TieEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
import it.polimi.ingsw.network.SocketReader;
import it.polimi.ingsw.network.SocketWriter;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

public class RemoteView extends View implements Runnable{
    private final LinkedBlockingQueue<GameEvent> clientEvs;
    private final LinkedBlockingQueue<GameEvent> serverEvs;
    private ExecutorService executor;
    private Socket clientSocket;

    public RemoteView(Socket clientSocket) throws IOException {
        super();
        this.clientSocket = clientSocket;
        clientEvs = new LinkedBlockingQueue<>();
        serverEvs = new LinkedBlockingQueue<>();
        executor = Executors.newFixedThreadPool(128);
        executor.execute(new SocketWriter<>(clientSocket,serverEvs));
        executor.execute(new SocketReader<>(clientSocket,clientEvs,GameEvent.class));
    }

    public RemoteView() {//only for tests
        super();
        clientEvs = new LinkedBlockingQueue<>();
        serverEvs = new LinkedBlockingQueue<>();
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


    public void update(NewPlayerCreatedEvent event){
        super.update(event);
        serverEvs.add(event);
    }

    public void update(UpdatedDataEvent event) {
        super.update(event);
        serverEvs.add(event);
    }

    public void update(NotifyExceptionEvent event){
         serverEvs.add(event);
    }

    public void update(VictoryEvent event){
        super.update(event);
        serverEvs.add(event);
    }

    public void update(TieEvent event){
        super.update(event);
        serverEvs.add(event);
    }
    public void update(RequestNumPlayersEvent event){
        super.update(event);
        serverEvs.add(event);
    }

    public LinkedBlockingQueue<GameEvent> getClientEvs() {
        return clientEvs;
    }

    public LinkedBlockingQueue<GameEvent> getServerEvs() {
        return serverEvs;
    }

}
