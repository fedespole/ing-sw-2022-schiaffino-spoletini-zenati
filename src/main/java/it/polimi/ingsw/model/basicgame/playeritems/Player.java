package it.polimi.ingsw.model.basicgame.playeritems;

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

    public Player(String username, int num, TEAM team) {
        this.username = username;
        this.team = team;
        this.myCloud = new ArrayList<Cloud>();
        this.myDeck = new Deck();
        this.mySchoolBoard = new SchoolBoard();
        this.coins = -1;
        switch(num) {
            case 2:
                this.myCloud.add(new Cloud());
                this.myCloud.add(new Cloud());
                for(int i = 0; i< 8; i++){
                    this.mySchoolBoard.addTower(new Tower(this.team));
            }
            case 3:
                this.myCloud.add(new Cloud());
                for(int i = 0; i< 6; i++){
                    this.mySchoolBoard.addTower(new Tower(this.team));

                }
        }
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
}


