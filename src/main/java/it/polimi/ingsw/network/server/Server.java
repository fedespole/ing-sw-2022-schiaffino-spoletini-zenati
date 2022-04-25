package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;

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
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    public void newClientConnection() throws IOException{
        Socket newSocket = serverSocket.accept();
        RemoteView remoteView = new RemoteView(newSocket);
        playingConnection.add(remoteView);
        executor.execute(remoteView);
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