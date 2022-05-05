package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.common.ANSIcolors.ANSI;
import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.fromClientEvents.*;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.*;
import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewPlayerCreatedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.exceptions.*;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.ArrayList;
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
        java.lang.String username;
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
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setup();
        }
        // Notifies only player that caused exception
        else if(getOwner()!=null && getOwner().equals(getData().getCurrPlayer().getUsername())) {

            if(event.getException() instanceof AlreadyUsedCardException || event.getException() instanceof NotAvailableCardException) {
                System.out.println(ANSI.RED + "> Card already drawn" + ANSI.RESET);
                drawAssistantCard();
            }
            else if(event.getException() instanceof StudentNotPresentException){
                System.out.println(ANSI.RED + "> Student not present in Entrance" + ANSI.RESET);
                moveStudent();
            }
            else if(event.getException() instanceof NoMoreSpaceException){
                System.out.println(ANSI.RED + "> Dining room of student is already full, redo the move" + ANSI.RESET);
                moveStudent();
            }
            else if(event.getException() instanceof InvalidIslandIndexException){
                System.out.println(ANSI.RED + "> Island no longer exists, redo the move" + ANSI.RESET);
                moveStudent();
            }
            else if(event.getException() instanceof InvalidStepsException){
                System.out.println(ANSI.RED + "> Not allowed to move mother nature this much" + ANSI.RESET);
                moveMother();
            }
            else if(event.getException() instanceof InvalidPhaseException){
                System.out.println(ANSI.RED + "> Anomaly" + ANSI.RESET);
            }
        }

    }

    @Override
    public void update(NewPlayerCreatedEvent event) {
        super.update(event);
        if (this.getOwner().equals(event.getUsername()))
            System.out.println("You have been accepted in the game, you username is : " + getOwner());
        else
            System.out.println("Player " + event.getUsername() + " has been accepted in the game");
    }

    @Override
    public void update(RequestNumPlayersEvent event) {
        super.update(event);
        int numPlayers = 0;
        if (event.getUsername().equals(this.getOwner())) {
            java.lang.String input;
            System.out.println("You are the first player connected, your username is : " + getOwner());
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
                this.client.getClientEvs().add(new SelectedGameSetUpEvent(this.getOwner(), numPlayers, false));
                break;
            } else if (input.equals("expert")) {
                this.getData().setExpert(true);
                System.out.println("Whenever is your turn, to use an ability type: character");
                this.client.getClientEvs().add(new SelectedGameSetUpEvent(this.getOwner(), numPlayers, true));
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

    @Override
    public void update(UpdatedDataEvent event) {
        super.update(event);
        // Printing an updated game board
        printTable();
        if (getOwner().equals(getData().getCurrPlayer().getUsername())) {
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

    @Override
    public void update(VictoryEvent event){
        if(event.getWinningPlayer().equals(this.getOwner()))
            System.out.println("YOU WON THE GAME");
        else{
            System.out.println("YOU LOST THE GAME");
        }
    }

    public void update(TieEvent event){
        if(event.getTiePlayers().contains(this.getOwner()))
            System.out.println("YOU WON THE GAME (TIE)");
        else{
            System.out.println("YOU LOST THE GAME (TIE)");
        }
    }

    private void drawAssistantCard() {
        java.lang.String input;
        int assistantCard;
        System.out.println("> Draw assistant card from those available by typing its value");
        System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
        while (true) {
            input = in.nextLine();
            useCharacter(input);
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
        this.client.getClientEvs().add(new DrawAssistantCardEvent(this.getOwner(), assistantCard));
    }

    private void moveStudent(){
        System.out.println("> Choose the color of the student to move from entrance:");
        System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
        in.reset();
        java.lang.String input;
        int colorIndex=-1;
        while (colorIndex < 0) {
            input = in.nextLine().toLowerCase();
            useCharacter(input);
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
            useCharacter(input);
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
                    this.client.getClientEvs().add(new MoveStudentToIslandEvent(this.getOwner(), colorIndex, island));
                    thisFlag = true;
                    break;
                }
                case "diningroom": {
                    this.client.getClientEvs().add(new MoveStudentToDiningEvent(this.getOwner(), colorIndex));
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
        System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
        while(true){
            in.reset();
            java.lang.String input = in.nextLine();
            useCharacter(input);
            try{
                motherNature= Integer.parseInt(input);
                break;
            }catch(NumberFormatException e){
                System.out.println(ANSI.RED + "> Please enter a number" + ANSI.RESET);
                System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
            }
        }
        this.client.getClientEvs().add(new MoveMotherEvent(this.getOwner(),motherNature));
    }

    public void chooseCloud() {
        int cloud = 0;
        java.lang.String input;
        System.out.println("> Choose cloud");
        System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
        while (true) {
            in.reset();
            input = in.nextLine();
            useCharacter(input);
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
        this.client.getClientEvs().add(new ChooseCloudEvent(this.getOwner(), cloud));
    }

    private void useCharacter(String input){
        if(this.getData().isExpert()){
            if(input.equals("character")) {
                System.out.println("> Choose which character to activate");
                System.out.print(ANSI.BLUE + "> " + ANSI.RESET);
                int charIn;
                while (true) {
                    in.reset();
                    String inputChar = in.nextLine();
                    try {
                        charIn = Integer.parseInt(inputChar);
                        if (charIn < 1 || charIn > 12) {
                            System.out.println(ANSI.RED + "> Please type a number between 1 and 12" + ANSI.RESET);
                            System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                        } else break;
                    } catch (NumberFormatException e) {
                        System.out.println(ANSI.RED + "> Please enter a number" + ANSI.RESET);
                        System.out.print(ANSI.GREEN + "> " + ANSI.RESET);
                    }
                }
                String inputIsland;
                int islandInt;
                switch (charIn){
                    case 1:
                        System.out.println("> Pick a student");
                        System.out.print(ANSI.BLUE + "> " + ANSI.RESET);
                        in.reset();
                        String inputStud = in.nextLine();
                        int studInt = Integer.parseInt(inputStud);
                        System.out.println("> Choose an island");
                        System.out.print(ANSI.BLUE + "> " + ANSI.RESET);
                        in.reset();
                        inputIsland = in.nextLine();
                        islandInt = Integer.parseInt(inputIsland);
                        this.client.getClientEvs().add(new UseCharacter1Event(this.getOwner(), studInt, islandInt));
                        break;
                    case 2:
                        this.client.getClientEvs().add(new UseCharacter2Event(this.getOwner()));
                        break;
                    case 3:
                        System.out.println("> Choose an island");
                        System.out.print(ANSI.BLUE + "> " + ANSI.RESET);
                        in.reset();
                        inputIsland = in.nextLine();
                        islandInt = Integer.parseInt(inputIsland);
                        this.client.getClientEvs().add(new UseCharacter3Event(this.getOwner(), islandInt));
                        break;
                    case 4:
                        System.out.println("> Choose a player");
                        System.out.print(ANSI.BLUE + "> " + ANSI.RESET);
                        in.reset();
                        String inputPlayer = in.nextLine();
                        int playerInt = Integer.parseInt(inputPlayer);
                        this.client.getClientEvs().add(new UseCharacter3Event(this.getOwner(), playerInt));
                        break;
                    /*case 5:

                        break;
                    case 6:
                        colorIndex = COLOR.BLUE.ordinal();
                        break;
                    case 7:
                        colorIndex = COLOR.GREEN.ordinal();
                        break;
                    case 8:
                        colorIndex = COLOR.RED.ordinal();
                        break;
                    case 9:
                        colorIndex = COLOR.YELLOW.ordinal();
                        break;
                    case 10:
                        colorIndex = COLOR.PINK.ordinal();
                        break;
                    case 11:
                        colorIndex = COLOR.BLUE.ordinal();
                        break;
                    case 12:
                        charIn=0;
                        break; */
                }
            }
        }
    }

    private void printOwnBoard(){
        Player playerOwner=null;
        for(Player player: getData().getPlayers()){
            if(player.getUsername().equals(getOwner())){
                playerOwner=player;
                break;
            }
        }
        ANSI.writeTitle(getOwner()+" TEAM "+playerOwner.getTeam());
        ANSI.writeTitle("ASSISTANT CARDS");
        for(AssistantCard assistantCard:playerOwner.getMyDeck().getCards()){
            System.out.print(assistantCard.getValue()+"   ");
        }
        ANSI.writeTitle("\nSTUDENTS IN ENTRANCE: ");
        for(Student student:playerOwner.getMySchoolBoard().getEntrance()){
            ANSI.writeInColor(student.getColor(),student.getColor()+"   ");
        }
        ANSI.writeTitle("\nSTUDENTS IN DINING ROOM");
        for(int i=0;i<5;i++){
            ANSI.writeInColor(COLOR.values()[i], "  -" + COLOR.values()[i].toString() + ": " );
            System.out.print(playerOwner.getMySchoolBoard().getDiningRoom()[i].size());
            for(Professor professor:playerOwner.getMySchoolBoard().getProfessors()){
                if(professor.getColor().equals(Arrays.stream(COLOR.values()).toArray()[i]))
                    ANSI.writeInColor(professor.getColor(),"   " + professor.getColor()+" PROFESSOR HERE ");
            }
            System.out.println("");
        }
        ANSI.writeTitle("TOWERS: " + playerOwner.getMySchoolBoard().getTowers().size());

    }

    private void printTable(){
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        ANSI.writeTitle("ISLANDS");
        for(int i=0;i<getData().getIslands().size();i++){
            System.out.print("Island "+i + ": ");
            for(Island island: getData().getIslands().get(i)){
                for(Student student:island.getStudents())
                    ANSI.writeInColor(student.getColor(), student.getColor()+"   ");
                if(island.getTower()!=null)
                    System.out.println("Tower team: "+ island.getTower().getColor());
            }
            if(getData().getMotherNature()==i)
                System.out.println( "  " + ANSI.WHITE_UNDERLINED + "MOTHER NATURE PRESENT HERE" + ANSI.RESET);
            else
                System.out.println("");
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        ANSI.writeTitle("CLOUDS");
        ArrayList<Cloud> clouds = getData().getClouds();
        for (int i = 0, cloudsSize = clouds.size(); i < cloudsSize; i++) {
            Cloud cloud = clouds.get(i);
            System.out.print("Cloud "+ i + ": ");
            for (Student student : cloud.getStudents())
                ANSI.writeInColor(student.getColor(),student.getColor() + "   ");
            System.out.print("\n");
        }
        if(this.getData().isExpert()){
            ANSI.writeTitle("CHARACTERS");
            for (int i = 0; i < 3; i++) {
                Character character = getData().getCharacters().get(i);
                System.out.print(character.getId() + "(cost: " + character.getCost() + ")");
                System.out.print("\t");
            }
            System.out.print("\n");
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}