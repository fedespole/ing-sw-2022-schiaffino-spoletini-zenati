package it.polimi.ingsw.network.client;

import it.polimi.ingsw.common.ANSIcolors.ANSI;
import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.network.SocketReader;
import it.polimi.ingsw.network.SocketWriter;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.gui.GuiManager;
//import it.polimi.ingsw.view.gui.GuiManager;
//import it.polimi.ingsw.view.gui.GuiView;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements Runnable {
    private final LinkedBlockingQueue<GameEvent> clientEvs;
    private final LinkedBlockingQueue<GameEvent> serverEvs;
    private ExecutorService executor;
    private Socket socket;
    View view;

    /**
     * Establish a connection between the selected server using Java Socket
     * @param ip the server IP
     * @param port the server port
     * @param chosenView is 0 if the user selected CLI, 1 if he selected GUI
     * @throws IOException if the connection failed
     */
    public Client(String ip, int port, int chosenView) throws IOException {
        try{
            socket = new Socket(ip, port);
        }catch(UnknownHostException| ConnectException e){
            System.out.println(ANSI.RED + "> The connection to the server failed, please try again" + ANSI.RESET);
            Client.main(null);
        }

        clientEvs = new LinkedBlockingQueue<>();
        serverEvs = new LinkedBlockingQueue<>();
        executor = Executors.newFixedThreadPool(2);
        executor.execute(new SocketWriter<>(socket,clientEvs));
        executor.execute(new SocketReader<>(socket,serverEvs,GameEvent.class));

        if(chosenView==0)
            view = new CliView(this);
        else{
            view = GuiManager.getInstance(this);
            ((GuiManager) view).gameSetUp();
        }
    }

    /**
     * This method continuously takes the incoming server events from the queue and raises them
     * through the GameHandler
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                GameEvent currEvent = serverEvs.take();
                GameHandler.calls(currEvent);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public LinkedBlockingQueue<GameEvent> getServerEvs() {
        return serverEvs;
    }

    public LinkedBlockingQueue<GameEvent> getClientEvs() {
        return clientEvs;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Asks the user the server IP and port, the chosen interface,
     * then, it calls the constructor and the run() method
     * @param args is null
     * @throws IOException if the connection  failed
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("> Insert the server IP address");
        System.out.print(ANSI.GREEN +  "> " + ANSI.RESET);
        String ip = in.nextLine();
        System.out.println("> Insert the server port");
        System.out.print(ANSI.GREEN +  "> " + ANSI.RESET);
        int port = in.nextInt();
        System.out.println("> Choose an Interface");
        System.out.println("   -CLI");
        System.out.println("   -GUI");
        System.out.print(ANSI.GREEN +  "> " + ANSI.RESET);

        Scanner scanner = new Scanner(System.in);
        while(true){
            String choice = scanner.nextLine();
            choice = choice.toLowerCase();
            switch (choice) {
                case "cli" : {
                    Client client = new Client(ip, port, 0);
                    client.run();
                    break;
                }
                case "gui" : {
                    Client client = new Client(ip, port, 1);
                    client.run();
                    break;
                }
                default : {
                    System.out.println(ANSI.RED + "> Please type a valid interface" + ANSI.RESET);
                    System.out.print(ANSI.GREEN+  "> " + ANSI.RESET);
                }
            }
        }
    }
}

