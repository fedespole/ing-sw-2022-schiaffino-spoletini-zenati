package it.polimi.ingsw.network.client;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.network.SocketReader;
import it.polimi.ingsw.network.SocketWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private final LinkedBlockingQueue<GameEvent> clientEvs;
    private final LinkedBlockingQueue<GameEvent> serverEvs;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Socket socket;

    public Client(String ip,int port) throws IOException {
        socket = new Socket(ip,port);
        clientEvs = new LinkedBlockingQueue<>();
        serverEvs = new LinkedBlockingQueue<>();

        executor.execute(new SocketWriter<>(socket,serverEvs));
        executor.execute(new SocketReader<>(socket,clientEvs,GameEvent.class));
    }

    public LinkedBlockingQueue<GameEvent> getServerEvs() {
        return serverEvs;
    }
}

