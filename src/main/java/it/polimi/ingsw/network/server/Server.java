package it.polimi.ingsw.network.server;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;
import it.polimi.ingsw.common.events.fromClientEvents.PlayerAccessEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewMidGamePlayerEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewPlayerCreatedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.exceptions.InvalidUserNameException;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.STATUS;

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
    Game game;
    private Controller controller;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        game = new BasicGame();
        controller = new Controller(game);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
        System.out.println("Waiting for clients to connect...");
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                    this.newClientConnection();
                    System.out.println("Connection ok!");

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
        GameEvent gameEvent;
        while (true) {
            gameEvent = remoteView.getClientEvs().take();
            if (gameEvent instanceof PlayerAccessEvent)
                break;
        }
        String username = ((PlayerAccessEvent)gameEvent).getUsername();
        // Checks if username given in PlayerAccessEvent matches with one of the usernames in the game, and it is actually disconnected
        if (controller.getDisconnectedPlayers().containsKey(username)) {
            for(RemoteView oldView: this.playingConnection) {
                if (oldView.getOwner().equals(username)) {
                    remoteView.update(new NewPlayerCreatedEvent(this, username));
                    remoteView.update(new UpdatedDataEvent(this, game.getData()));
                    playingConnection.remove(oldView);
                    playingConnection.add(remoteView);
                    executor.execute(remoteView);
                    controller.getDisconnectedPlayers().replace(username,true);
                    System.out.println("RECONNECTED:"+username);
                    break;
                } else {
                    remoteView.update(new NotifyExceptionEvent(this, new InvalidUserNameException(null)));
                }
                return;
            }
        }
        // If username is not matched
        remoteView.update(new NotifyExceptionEvent(this, new InvalidUserNameException(null)));
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