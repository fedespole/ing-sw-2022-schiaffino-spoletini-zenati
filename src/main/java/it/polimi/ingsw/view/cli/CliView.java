package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.common.events.fromClientEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewPlayerCreatedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.exceptions.InvalidUserNameException;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
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
        this.client.getClientEvs().add(new PlayerAccessEvent(this, username, this.client.getSocket().toString()));
    }


    @Override
    public void update(NotifyExceptionEvent event) {

        // Checks the client that caused the invalidUserName using only the socket, as owner is set only with NewPlayerCreated
        if (event.getException() instanceof InvalidUserNameException
                && ((InvalidUserNameException)event.getException()).getClientThatCausedEx().equals(this.client.getSocket().toString())){
                System.err.println("> Username already chosen");
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setup();
        }
        // Notifies only player that caused exception
        else if(getData().getOwner()!=null && getData().getOwner().equals(getData().getCurrPlayer())) {

            // if(event.getException() instanceof )

        }

    }

    @Override
    public void update(NewPlayerCreatedEvent event) {
        super.update(event);
        if (getData().getOwner().equals(event.getPlayer()))
            System.out.println("You have been accepted in the game, you username is : " + getData().getOwner().getUsername());
        else
            System.out.println("Username " + event.getPlayer().getUsername() + " has been accepted in the game");
    }

    @Override
    public void update(RequestNumPlayersEvent event) {
        super.update(event);
        int numPlayers;
        if (event.getPlayer().equals(this.getData().getOwner())) {
            String input;
            System.out.println("You are the first player connected, your username is : " + getData().getOwner().getUsername());
            in.reset();
            System.out.println("Choose number of players: 2 or 3 players available");
            while(true) {
                input = in.nextLine();
                numPlayers = Integer.parseInt(input);
                in.reset();
                if(numPlayers<2 || numPlayers>3){
                    System.err.println("> Please type a valid number");
                    System.err.print("> ");
                }
                else break;
            }
            System.out.println("Choose game mode: BasicGame or ExpertGame");

            input = in.nextLine().toLowerCase();
            if (input.equals("basicgame")) {
                this.client.getClientEvs().add(new SelectedGameSetUpEvent(this.getData().getOwner(), numPlayers, false));
            } else if (input.equals("expertgame")) {
                this.client.getClientEvs().add(new SelectedGameSetUpEvent(this.getData().getOwner(), numPlayers, true));
            }
            else {
                in.reset();
                System.out.println("Invalid game mode");
            }
            System.out.println("Waiting for other " + (numPlayers-1) + " player(s) to join, " + input + " selected...");
        }
    }

    public void update(NewMidGamePlayerEvent event){
        System.out.println("The game has already started");
    }
    @Override//TODO CHECK ALL THE INPUTS
    public void update(UpdatedDataEvent event) {
        super.update(event);
        // Printing an updated game board
        printTable();
        if (getData().getOwner().equals(getData().getCurrPlayer())) {
            // Printing only the currPlayer items
            printOwnBoard();
            String input;
            if (getData().getStatusGame().getStatus().equals(STATUS.PLANNING)) {
                System.out.println("Draw assistant card from available ");
                in.reset();
                input = in.nextLine();
                int assistantCard = Integer.parseInt(input);
                this.client.getClientEvs().add(new DrawAssistantCardEvent(this.getData().getOwner(), assistantCard));
            } else if (getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD)){
                System.out.println("Choose color to move from entrance");
                in.reset();
                input=in.nextLine().toLowerCase();
                int colorIndex=-1;
                switch (input){
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
                System.out.println("Choose destination: Island or DiningRoom");
                in.reset();
                input = in.nextLine().toLowerCase();
                switch (input){
                    case "island": {
                        System.out.println("Choose island ");
                        in.reset();
                        input = in.nextLine();
                        int island = Integer.parseInt(input);
                        this.client.getClientEvs().add(new MoveStudentToIslandEvent(this.getData().getOwner(), colorIndex, island));
                        break;
                    }
                    case "diningroom": {
                        this.client.getClientEvs().add(new MoveStudentToDiningEvent(this.getData().getOwner(), colorIndex));
                        break;
                    }
                    default : System.err.println("Wrong choice");
                }
            }
            else if(getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVEMN)){
                in.reset();
                System.out.println("Choose number of jumps of mother nature");
                input = in.nextLine();
                int motherNature= Integer.parseInt(input);
                this.client.getClientEvs().add(new MoveMotherEvent(this.getData().getOwner(),motherNature));
            }
            else if(getData().getStatusGame().getStatus().equals(STATUS.ACTION_CHOOSECLOUD)){
                in.reset();
                System.out.println("Choose cloud");
                input= in.nextLine();
                int cloud = Integer.parseInt(input);
                this.client.getClientEvs().add(new ChooseCloudEvent(this.getData().getOwner(),cloud));
            }
        }
        else {
            System.out.println(getData().getOwner().getUsername() + "'s turn");
        }
    }

    private void printOwnBoard(){
        System.out.println(getData().getOwner().getUsername()+" TEAM "+getData().getOwner().getTeam());
        System.out.println("ASSISTANT CARDS");
        for(AssistantCard assistantCard:getData().getOwner().getMyDeck().getCards()){
            System.out.print(assistantCard.getValue()+"---");
        }
        System.out.print("\nSTUDENTS IN ENTRANCE\n");
        for(Student student:getData().getOwner().getMySchoolBoard().getEntrance()){
            System.out.print(student.getColor()+"---");
        }
        System.out.print("\nSTUDENTS IN DINING ROOM\n");
        for(int i=0;i<5;i++){
            System.out.println(Arrays.stream(COLOR.values()).toArray()[i]+" "+getData().getOwner().getMySchoolBoard().getDiningRoom()[i].size());
            for(Professor professor:getData().getOwner().getMySchoolBoard().getProfessors()){
                if(professor.getColor().equals(Arrays.stream(COLOR.values()).toArray()[i]))
                    System.out.println(professor.getColor()+" PROFESSOR PRESENT");
            }
        }
        System.out.println("TOWERS\n"+getData().getOwner().getMySchoolBoard().getTowers().size());

    }

    private void printTable(){
        System.out.println("--------------------------");
        System.out.println("ISLANDS");
        for(int i=0;i<getData().getIslands().size();i++){
            System.out.println("ISLAND NUMBER "+i);
            for(Island island: getData().getIslands().get(i)){
                for(Student student:island.getStudents())
                    System.out.print(student.getColor()+"---");
                System.out.print("\n");
                if(island.getTower()!=null)
                    System.out.println("TOWER PRESENT: "+ island.getTower().getColor());
            }
            if(getData().getMotherNature()==i)
                System.out.println("MOTHER NATURE PRESENT HERE");
        }
        System.out.println("--------------------------");
        System.out.println("CLOUDS");
        for(Cloud cloud:getData().getClouds()){
            for(Student student:cloud.getStudents())
                System.out.print(student.getColor()+"---");
            System.out.print("\n");
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}