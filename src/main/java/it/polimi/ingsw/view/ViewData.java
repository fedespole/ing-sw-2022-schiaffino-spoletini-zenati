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

    private ArrayList<Character> characters;

    public ViewData(){
        this.owner = null;
        this.players= new ArrayList<>();
    }

    public ViewData( ArrayList<Player> players, int numPlayers, ArrayList<ArrayList<Island>> islands, ArrayList<Professor> professors, int motherNature, Player currPlayer, ArrayList<Cloud> clouds, StatusGame statusGame) {
        this.players = players;
        this.numPlayers = numPlayers;
        this.islands = islands;
        this.professors = professors;
        this.motherNature = motherNature;
        this.currPlayer = currPlayer;
        this.clouds = clouds;
        this.statusGame = statusGame;
        //owner == null when we send a viewData to View
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Player getOwner() {
        return owner;
    }

    public ArrayList<Player> getPlayers() {
        return players;
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

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public StatusGame getStatusGame() {
        return statusGame;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public ArrayList<ArrayList<Island>> getIslands() {
        return islands;
    }

    public int getMotherNature() {
        return motherNature;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }
}
