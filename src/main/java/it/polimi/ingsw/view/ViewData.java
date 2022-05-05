package it.polimi.ingsw.view;

import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.String;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewData implements Serializable {
    private  ArrayList<String> players;
    private int numPlayers;
    private  ArrayList<ArrayList<Island>> islands;
    private  ArrayList<Professor> professors;
    private int motherNature;
    private String currPlayer;
    private  ArrayList<Cloud> clouds;
    private  StatusGame statusGame;
    private String winner;
    private ArrayList<String> tiePlayers;

    private ArrayList<Character> characters;

    public ViewData(){;
        this.players= new ArrayList<>();
    }

    public ViewData(ArrayList<String> players, int numPlayers, ArrayList<ArrayList<Island>> islands, ArrayList<Professor> professors, int motherNature, String currPlayer, ArrayList<Cloud> clouds, StatusGame statusGame) {
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


    public ArrayList<String> getPlayers() {
        return players;
    }


    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public ArrayList<String> getTiePlayers() {
        return tiePlayers;
    }

    public void setTiePlayers(ArrayList<String> tiePlayers) {
        this.tiePlayers = tiePlayers;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public StatusGame getStatusGame() {
        return statusGame;
    }

    public String getCurrPlayer() {
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
