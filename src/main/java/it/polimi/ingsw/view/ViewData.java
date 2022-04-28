package it.polimi.ingsw.view;

import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewData implements Serializable {
    private  Player owner;
    private  ArrayList<Player> players;
    private int numPlayers;
    private  ArrayList<ArrayList<Island>> islands;
    private  ArrayList<Professor> professors;
    private int motherNature;
    private Player currPlayer;
    private  ArrayList<Cloud> clouds;
    private  StatusGame statusGame;
    private Player winner;
    private ArrayList<Player> tiePlayers;

    public ViewData(){}

    public ViewData(Player owner, ArrayList<Player> players, int numPlayers, ArrayList<ArrayList<Island>> islands, ArrayList<Professor> professors, int motherNature, Player currPlayer, ArrayList<Cloud> clouds, StatusGame statusGame) {
        this.owner = owner;
        this.players = players;
        this.numPlayers = numPlayers;
        this.islands = islands;
        this.professors = professors;
        this.motherNature = motherNature;
        this.currPlayer = currPlayer;
        this.clouds = clouds;
        this.statusGame = statusGame;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public ArrayList<Player> getTiePlayers() {
        return tiePlayers;
    }

    public void setTiePlayers(ArrayList<Player> tiePlayers) {
        this.tiePlayers = tiePlayers;
    }
}
