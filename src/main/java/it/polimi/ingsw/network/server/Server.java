package it.polimi.ingsw.network.server;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.PlayerAccessEvent;
import it.polimi.ingsw.common.exceptions.InvalidUserNameException;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.STATUS;
import it.polimi.ingsw.view.RemoteView;

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
        while (controller.getGame().getStatusGame().getOrder().equals(STATUS.SETUP)) {
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
}