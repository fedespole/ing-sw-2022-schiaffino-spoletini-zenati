package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.fromClientEvents.PlayerAccessEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.common.exceptions.*;

import java.io.PrintStream;
import java.util.Scanner;

public class CliView extends View implements Runnable{

    private final Scanner in;
    private final PrintStream out;
    private boolean activeGame;
    private final Client client;

    public CliView(Client client) {
        this.client = client;
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
        activeGame = true;
        this.run();
    }

    public void setup() {
        String username;
        System.out.println("> Insert your nickname: ");
        System.out.print("> ");
        username = in.nextLine();

        this.client.getClientEvs().add(new PlayerAccessEvent(this, username));
    }

    @Override
    public void run() {
        setup();
        while (activeGame) {
            // Listens the Events
            while(!Thread.currentThread().isInterrupted()){
                try {
                    GameEvent currEvent = client.getClientEvs().take();
                    GameHandler.calls(currEvent);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        in.close();
        out.close();
    }

    @Override
    public void update(NotifyExceptionEvent event) {
        switch (event.getException().toString()){
            case "InvalidUserNameException" : {
                System.err.println("> Username already chosen");
                setup();
            }
        }
    }
}
