package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.common.ANSIcolors.ANSI;
import it.polimi.ingsw.common.events.fromClientEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewPlayerCreatedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.exceptions.*;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class CliView extends View {

    private final Scanner in;
    private final PrintStream out;
    private final Client client;

    public CliView(Client client) {
        this.client = client;
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
        setup();
    }

    public void setup() {
        String username;
        System.out.println("> Insert your nickname: ");
        System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
        username = in.nextLine();
        this.client.getClientEvs().add(new PlayerAccessEvent(this, username, this.client.getSocket().toString()));
    }


    @Override
    public void update(NotifyExceptionEvent event) {

        // Checks the client that caused the invalidUserName using only the socket, as owner is set only with NewPlayerCreated
        if (event.getException() instanceof InvalidUserNameException
                && ((InvalidUserNameException)event.getException()).getClientThatCausedEx().equals(this.client.getSocket().toString())){
            System.out.println(ANSI.RED + "> Username already chosen" + ANSI.RESET);
            try {
                Thread.currentThread().sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setup();
        }
        // Notifies only player that caused exception
        else if(getData().getOwner()!=null && getData().getOwner().equals(getData().getCurrPlayer())) {

            if(event.getException() instanceof AlreadyUsedCardException || event.getException() instanceof NotAvailableCardException) {
                System.out.println(ANSI.RED + "> Card already drawn" + ANSI.RESET);
                drawAssistantCard();
            }
            else if(event.getException() instanceof StudentNotPresentException){
                System.out.println(ANSI.RED + "> Student not present in Entrance" + ANSI.RESET);
                moveStudent();
            }
            else if(event.getException() instanceof NoMoreSpaceException){             // Potrebbe essere spacchettata
                System.out.println(ANSI.RED + "> Dining room of student is already full, redo the move" + ANSI.RESET);
                moveStudent();
            }
            else if(event.getException() instanceof InvalidIslandIndexException){      // Potrebbe essere spacchettata
                System.out.println(ANSI.RED + "> Island no longer exists, redo the move" + ANSI.RESET);
                moveStudent();
            }
            else if(event.getException() instanceof InvalidStepsException){
                System.out.println(ANSI.RED + "> Not allowed to move mother nature this much" + ANSI.RESET);
                moveMother();
            }
            else if(event.getException() instanceof InvalidPhaseException || event.getException() instanceof InvalidPlayerException){
                System.out.println(ANSI.RED + "> Anomaly" + ANSI.RESET);    // Questo non dovrebbe mai capire se cli passiva
            }
        }

    }

    @Override
    public void update(NewPlayerCreatedEvent event) {
        super.update(event);
        if (getData().getOwner().equals(event.getPlayer()))
            System.out.println("You have been accepted in the game, you username is : " + getData().getOwner().getUsername());
        else
            System.out.println("Player " + event.getPlayer().getUsername() + " has been accepted in the game");
    }

    @Override
    public void update(RequestNumPlayersEvent event) {
        super.update(event);
        int numPlayers = 0;
        if (event.getPlayer().equals(this.getData().getOwner())) {
            String input;
            System.out.println("You are the first player connected, your username is : " + getData().getOwner().getUsername());
            in.reset();
            System.out.println("> Choose number of players: 2 or 3 players available");
            System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
            while(true) {
                input = in.nextLine();
                try {
                    numPlayers = Integer.parseInt(input);
                }catch(NumberFormatException e){
                    //goes to else
                    }
                in.reset();
                if(numPlayers<2 || numPlayers>3){
                    in.reset();
                    System.out.println(ANSI.RED + "> Please choose between 2 or 3 players" + ANSI.RESET);
                    System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                }
                else break;
            }
            System.out.println("> Choose the Game Mode: Basic or Expert?");
            System.out.print(ANSI.GREEN + "> " + ANSI.RESET);

            while(true){
                input = in.nextLine().toLowerCase();
            if (input.equals("basic")) {
                this.client.getClientEvs().add(new SelectedGameSetUpEvent(this.getData().getOwner(), numPlayers, false));
                break;
            } else if (input.equals("expert")) {
                this.client.getClientEvs().add(new SelectedGameSetUpEvent(this.getData().getOwner(), numPlayers, true));
                break;
            }
            else {
                in.reset();
                System.out.println(ANSI.RED + "> Please type a valid Game Mode" + ANSI.RESET);
                System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                }
            }
            System.out.println("Waiting for other " + (numPlayers-1) + " player(s) to join, " + input + " selected...");
        }
    }

    public void update(NewMidGamePlayerEvent event){
        System.out.println("The game has already started");
    }

    @Override
    public void update(UpdatedDataEvent event) {
        super.update(event);
        // Printing an updated game board
        printTable();
        if (getData().getOwner().equals(getData().getCurrPlayer())) {
            // Printing only the currPlayer items
            printOwnBoard();
            if (getData().getStatusGame().getStatus().equals(STATUS.PLANNING)) {
                drawAssistantCard();
            } else if (getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVESTUD)){
                moveStudent();
            }
            else if(getData().getStatusGame().getStatus().equals(STATUS.ACTION_MOVEMN)){
                moveMother();
            }
            else if(getData().getStatusGame().getStatus().equals(STATUS.ACTION_CHOOSECLOUD)){
                chooseCloud();
            }
        }
        else {
            System.out.println(getData().getCurrPlayer().getUsername() + "'s turn");
        }
    }

    private void drawAssistantCard() {
        String input;
        int assistantCard;
        System.out.println("> Draw assistant card from those available by typing its value");
        System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
        while (true) {
            input = in.nextLine();
            try {
                assistantCard = Integer.parseInt(input);
                if(assistantCard < 1 || assistantCard > 10){
                    System.out.println(ANSI.RED + "> Please type a number between 1 and 10" + ANSI.RESET);
                    System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                    in.reset();
                }
                else break;
            } catch (NumberFormatException e) {
                System.out.println(ANSI.RED + "> Please type a number" + ANSI.RESET);
                System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                in.reset();
            }
        }
        this.client.getClientEvs().add(new DrawAssistantCardEvent(this.getData().getOwner(), assistantCard));
    }

    private void moveStudent(){
        System.out.println("> Choose the color of the student to move from entrance:");
        System.out.println("   -Green ");
        System.out.println("   -Red ");
        System.out.println("   -Yellow ");
        System.out.println("   -Pink ");
        System.out.println("   -Blue ");
        in.reset();
        String input;
        int colorIndex=-1;
        while (colorIndex < 0) {
            input = in.nextLine().toLowerCase();
            switch (input) {
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
                default:
                    System.out.println(ANSI.RED + "> Please type a color from those above" + ANSI.RESET);
                    System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                    in.reset();
            }
        }
        System.out.println("> Choose destination: Island or DiningRoom");
        System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
        boolean thisFlag = false;
        int island = 0;
        while(!thisFlag) {
            in.reset();
            input = in.nextLine().toLowerCase();
            switch (input) {
                case "island" : {
                    System.out.println("Choose island ");
                    in.reset();
                    while (true) {
                        input = in.nextLine();
                        try {
                            island = Integer.parseInt(input);
                            if(island < 0 || island > 11) {
                                System.out.println(ANSI.RED + "> Please type a number between 0 and 11" + ANSI.RESET);
                                System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                            }
                            else break;
                        } catch (NumberFormatException e) {
                            System.out.println(ANSI.RED + "> Please type a number" + ANSI.RESET);
                            System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                        }
                    }
                    this.client.getClientEvs().add(new MoveStudentToIslandEvent(this.getData().getOwner(), colorIndex, island));
                    thisFlag = true;
                    break;
                }
                case "diningroom": {
                    this.client.getClientEvs().add(new MoveStudentToDiningEvent(this.getData().getOwner(), colorIndex));
                    thisFlag = true;
                    break;
                }
                default:
                    System.out.println(ANSI.RED + "> Please type 'Island' or 'DiningRoom'" + ANSI.RESET);
                    System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
            }
        }
    }

    private void moveMother(){
        int motherNature = 0;
        System.out.println("> Choose number of jumps of mother nature");
        while(true){
            in.reset();
            String input = in.nextLine();
            try{
                motherNature= Integer.parseInt(input);
                break;
            }catch(NumberFormatException e){
                System.out.println(ANSI.RED + "> Please enter a number" + ANSI.RESET);
                System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
            }
        }
        this.client.getClientEvs().add(new MoveMotherEvent(this.getData().getOwner(),motherNature));
    }

    public void chooseCloud() {
        int cloud = 0;
        String input;
        System.out.println("> Choose cloud");
        while (true) {
            in.reset();
            input = in.nextLine();
            try {
                cloud = Integer.parseInt(input);
                if (cloud < 0 || cloud > (getData().getNumPlayers() - 1)) {
                    System.out.println(ANSI.RED + "> Please enter a number between 0 and " + (getData().getNumPlayers() - 1) + ANSI.RESET);
                    System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                }
                else break;
            } catch (NumberFormatException e) {
                System.out.println(ANSI.RED + "> Please enter a number" + ANSI.RESET);
                System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
            }

        }
        this.client.getClientEvs().add(new ChooseCloudEvent(this.getData().getOwner(), cloud));
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