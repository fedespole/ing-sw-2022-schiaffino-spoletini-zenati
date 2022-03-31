package it.polimi.ingsw.model.basicgame.playeritems;

import it.polimi.ingsw.model.basicgame.*;

import java.util.ArrayList;

public class Player {
    private final String username;
    private int coins;
    private final Deck myDeck;
    private final SchoolBoard mySchoolBoard;
    private TEAM team;
    private int maxSteps;

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

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public void setTeam(TEAM team) {
        this.team = team;
    }
}


