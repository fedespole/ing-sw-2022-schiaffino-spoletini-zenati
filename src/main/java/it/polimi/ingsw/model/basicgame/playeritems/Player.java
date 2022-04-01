package it.polimi.ingsw.model.basicgame.playeritems;

import it.polimi.ingsw.model.basicgame.*;

import java.util.ArrayList;

public class Player {
    private final String username;
    private int coins;
    private final Deck myDeck;
    private final SchoolBoard mySchoolBoard;
    private TEAM team;
    private AssistantCard chosenCard;

    public Player(String username) {
        this.username = username;
        this.myDeck = new Deck();
        this.mySchoolBoard = new SchoolBoard();
        this.coins = -1;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Deck getMyDeck() {
        return myDeck;
    }

    public SchoolBoard getMySchoolBoard() {
        return mySchoolBoard;
    }

    public String getUsername() {
        return username;
    }

    public TEAM getTeam() {
        return team;
    }

    public int getCoins() {
        return coins;
    }

    public void setTeam(TEAM team) {
        this.team = team;
    }

    public AssistantCard getChosenCard() {
        return chosenCard;
    }

    public void setChosenCard(AssistantCard chosenCard) {
        this.chosenCard = chosenCard;
    }
}


