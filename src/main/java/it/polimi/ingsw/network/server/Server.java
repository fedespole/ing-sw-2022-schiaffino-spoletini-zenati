package it.polimi.ingsw.network.server;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.common.events.fromClientEvents.PlayerAccessEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewMidGamePlayerEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewPlayerCreatedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.STATUS;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private ArrayList<RemoteView> playingConnection = new ArrayList<>();

    private Controller controller;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        Game game = new BasicGame();
        controller = new Controller(game);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                    this.newClientConnection();
            } catch (IOException | InterruptedException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    public void newClientConnection() throws IOException, InterruptedException {
        Socket newSocket = serverSocket.accept();
        RemoteView remoteView = new RemoteView(newSocket);
        if(controller.getGame().getStatusGame().getStatus() != STATUS.SETUP) {
            newMidGameClientConnection(remoteView);
        }
        else {
            playingConnection.add(remoteView);
            executor.execute(remoteView);
        }
    }

    private void newMidGameClientConnection(RemoteView remoteView) throws InterruptedException, IOException {//resilience
        remoteView.update(new NewMidGamePlayerEvent(this));
        GameEvent gameEvent;
        while (true) {
            gameEvent = remoteView.getClientEvs().take();
            if (gameEvent instanceof PlayerAccessEvent)
                break;
        }
        for (RemoteView remoteView1 : playingConnection) {
            if (((PlayerAccessEvent) gameEvent).getUsername().equals(remoteView1.getData().getOwner().getUsername())) {
                //seconda condizione verifica che la vecchia remoteView sia disconessa,DA CHIEDERE
                if (remoteView1.getClientSocket().getInputStream().read() == -1) {
                    remoteView.update(new NewPlayerCreatedEvent(this,remoteView1.getData().getOwner()));
                    remoteView.update(new UpdatedDataEvent(this,remoteView1.getData()));
                    playingConnection.remove(remoteView1);
                    playingConnection.add(remoteView);
                    executor.execute(remoteView);
                    break;
                } else {
                    //TODO username non corretto,dovrebbe alzare eccezione
                }
            }
            else{
                //TODO username non corretto,dovrebbe alzare eccezione
            }
        }
    }
    public int getPort(){
        return Server.PORT;
    }

    public ArrayList<RemoteView> getPlayingConnection() {
        return playingConnection;
    }

    public Controller getController() {
        return controller;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    public void kills(){
        executor.shutdown();
    }

}