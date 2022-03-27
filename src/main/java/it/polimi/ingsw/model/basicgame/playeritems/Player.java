package it.polimi.ingsw.model.basicgame.playeritems;

import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.TEAM;
import it.polimi.ingsw.model.basicgame.Tower;

import java.util.ArrayList;

public class Player {
    private String username;
    private int coins;
    private ArrayList<Cloud> myCloud;
    private Deck myDeck;
    private SchoolBoard mySchoolBoard;
    private TEAM team;
    private int maxSteps;

    public Player(String username) {
        this.username = username;
        this.myCloud = new ArrayList<Cloud>();
        this.myDeck = new Deck();
        this.mySchoolBoard = new SchoolBoard();
        this.coins = -1;
        }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public ArrayList<Cloud> getMyCloud() {
        return myCloud;
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


