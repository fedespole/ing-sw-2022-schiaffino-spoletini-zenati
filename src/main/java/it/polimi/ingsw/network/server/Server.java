package it.polimi.ingsw.network.server;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.PlayerAccessEvent;
import it.polimi.ingsw.common.exceptions.InvalidUserNameException;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;
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
    //  private Map<String, RemoteView> waitingConnection = new HashMap<>();
    private ArrayList<RemoteView> playingConnection = new ArrayList<>();

    private Game game;
    private Controller controller;

    public void Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        game = new BasicGame();
        controller = new Controller(game);
    }

    public void run() {
        int connections = 0;
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                connections++;
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }

    }

}