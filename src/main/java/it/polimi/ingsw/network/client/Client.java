package it.polimi.ingsw.network.client;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.network.SocketReader;
import it.polimi.ingsw.network.SocketWriter;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.gui.GuiView;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private final LinkedBlockingQueue<GameEvent> clientEvs;
    private final LinkedBlockingQueue<GameEvent> serverEvs;
    private ExecutorService executor;
    private Socket socket;
    View view;

    public Client(String ip, int port, int chosenView) throws IOException {
        socket = new Socket(ip, port);

        clientEvs = new LinkedBlockingQueue<>();
        serverEvs = new LinkedBlockingQueue<>();
        executor = Executors.newFixedThreadPool(2);
        executor.execute(new SocketWriter<>(socket,clientEvs));
        executor.execute(new SocketReader<>(socket,serverEvs,GameEvent.class));

        switch(chosenView) {
            case 0 : view = new CliView(this);
            case 1 : view = new GuiView();
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

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        System.out.println("> Insert the server IP address");
        System.out.print("> ");
        String ip = in.nextLine();
        System.out.println("> Insert the server port");
        System.out.print("> ");
        int port = in.nextInt();
        System.out.println("> Choose an Interface");

        System.out.println(" Type CLI or GUI:");
        System.out.print("> ");

        Scanner scanner = new Scanner(System.in);
        while(true){
            String choice = scanner.nextLine();
            choice.toLowerCase();
            switch (choice) {
                case "cli" : {
                    Client client = new Client(ip, port, 0);
                    break;
                }
                case "gui" : {
                    Client client = new Client(ip, port, 1);
                    break;
                }
                default : {
                    System.err.println("> Please type a valid interface");
                    System.err.print("> ");
                }
            }
        }
    }
}

