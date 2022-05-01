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
            // Checks if username given in PlayerAccessEvent matches with one of the usernames in the game
            if (((PlayerAccessEvent) gameEvent).getUsername().equals(remoteView1.getData().getOwner().getUsername())) {

                // Checks if player with this username is actually disconnected
                if (remoteView1.getClientSocket().getInputStream().read() == -1) {
                    remoteView.update(new NewPlayerCreatedEvent(this,remoteView1.getData().getOwner()));
                    remoteView.update(new UpdatedDataEvent(this, game.getData()));
                    playingConnection.remove(remoteView1);
                    playingConnection.add(remoteView);
                    executor.execute(remoteView);
                } else {
                    remoteView.update(new NotifyExceptionEvent(this, new InvalidUserNameException()));
                }
                return;
            }
        }
        // If username is not matched
        remoteView.update(new NotifyExceptionEvent(this, new InvalidUserNameException()));
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