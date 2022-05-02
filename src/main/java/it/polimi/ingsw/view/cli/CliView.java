package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.common.events.fromClientEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewPlayerCreatedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.STATUS;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.Scanner;

public class CliView extends View {

    private final Scanner in;
    private final PrintStream out;
    private boolean activeGame;
    private final Client client;

    public CliView(Client client) {
        this.client = client;
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
        activeGame = true;
        setup();
    }

    public void setup() {
        String username;
        System.out.println("> Insert your nickname: ");
        System.out.print("> ");
        username = in.nextLine();

        this.client.getClientEvs().add(new PlayerAccessEvent(this, username));
    }


    @Override
    public void update(NotifyExceptionEvent event) {
        switch (event.getException().toString()) {
            case "InvalidUserNameException": {
                System.err.println("> Username already chosen");
                setup();
            }
        }
    }

    @Override
    public void update(NewPlayerCreatedEvent event) {
        super.update(event);
        if (getData().getOwner().equals(event.getPlayer()))
            System.out.println("You have been accepted in the game, you username is : " + getData().getOwner().getUsername());
        else
            System.out.println("Username " + event.getPlayer().getUsername() + "has been accepted in the game");
    }

    @Override
    public void update(RequestNumPlayersEvent event) {
        super.update(event);
        if (event.getPlayer().equals(this.getData().getOwner())) {
            System.out.println("You are the first player connected, your username is : " + getData().getOwner().getUsername());
            in.reset();
            System.out.println("Choose number of players: 2 or 3 players available");
            int numPlayers = in.nextInt();
            in.reset();
            System.out.println("Choose game mode: BasicGame or ExpertGame");
            while (true) {
                String gameMode = in.nextLine().toLowerCase();
                if (gameMode.equals("basicgame")) {
                    this.client.getClientEvs().add(new SelectedGameSetUpEvent(this, numPlayers, false));
                    break;
                } else if (gameMode.equals("expertgame")) {
                    this.client.getClientEvs().add(new SelectedGameSetUpEvent(this, numPlayers, true));
                    break;
                } else {
                    in.reset();
                    System.out.println("Invalid game mode");
                }
            }
        }
    }

    @Override//TODO CHECK ALL THE INPUTS
    public void update(UpdatedDataEvent event) {
        super.update(event);
        //stampare una board aggiornata in qualche modo
        if (getData().getOwner().equals(getData().getCurrPlayer())) {
            if (getData().getStatusGame().getStatus().equals(STATUS.PLANNING)) {
                in.reset();
                System.out.println("Draw assistant card from available ");
                //stampare assistant cards available
                int assistantCard = in.nextInt();
                this.client.getClientEvs().add(new DrawAssistantCardEvent(this, assistantCard));
            } else if (getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD)){
                in.reset();
                System.out.println("Choose color to move from entrance");
                String color=in.nextLine().toLowerCase();
                int colorIndex=-1;
                switch (color){
                    case "green":
                        colorIndex = COLOR.GREEN.ordinal();
                        break;
                    case "red":
                        colorIndex = COLOR.RED.ordinal();
                        break;
                    case "yellow":
                        colorIndex = COLOR.YELLOW.ordinal();
                        break;
                    case "pink":
                        colorIndex = COLOR.PINK.ordinal();
                        break;
                    case "blue":
                        colorIndex = COLOR.BLUE.ordinal();
                        break;
                }
                in.reset();
                System.out.println("Choose destination: Island or DiningRoom");
                String destination = in.nextLine().toLowerCase();
                switch (destination){
                    case "island":
                        in.reset();
                        System.out.println("Choose island ");
                        int island = in.nextInt();
                        this.client.getClientEvs().add(new MoveStudentToIslandEvent(this, colorIndex,island));
                        break;
                    case "diningroom":
                        this.client.getClientEvs().add(new MoveStudentToDiningEvent(this, colorIndex));
                        break;
                }
            }
            else if(getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVEMN)){
                in.reset();
                System.out.println("Choose number of jumps of mother nature");
                int motherNature = in.nextInt();
                this.client.getClientEvs().add(new MoveMotherEvent(this,motherNature));
            }
            else if(getData().getStatusGame().getStatus().equals(STATUS.ACTION_CHOOSECLOUD)){
                in.reset();
                System.out.println("Choose cloud");
                int cloud= in.nextInt();
                this.client.getClientEvs().add(new MoveMotherEvent(this,cloud));
            }
        }
    }
}