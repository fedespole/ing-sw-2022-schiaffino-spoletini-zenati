package it.polimi.ingsw.view;

import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.Character;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains the current state of the game in the client-side, it is sent from the server to the client
 * in an UpdatedDataEvent
 */
public class ViewData implements Serializable {
    private  ArrayList<Player> players;
    private int numPlayers;
    private  ArrayList<ArrayList<Island>> islands;
    private  ArrayList<Professor> professors;
    private int motherNature;
    private Player currPlayer;
    private  ArrayList<Cloud> clouds;
    private  StatusGame statusGame;
    private String winner;
    private ArrayList<String> tiePlayers;

    private boolean isExpert;
    private ArrayList<Character> characters;

    private int indexCharacterUsed=-1;

    public ViewData(){
        this.players= new ArrayList<>();
    }

    public ViewData(ArrayList<Player> players, int numPlayers, ArrayList<ArrayList<Island>> islands, ArrayList<Professor> professors, int motherNature, Player currPlayer, ArrayList<Cloud> clouds, StatusGame statusGame,boolean isExpert) {
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

    public boolean isExpert() {
        return isExpert;
    }

    public void setExpert(boolean expert) {
        isExpert = expert;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {
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

    public ArrayList<Character> getCharacters() {
        return characters;
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

    public int getIndexCharacterUsed() {
        return indexCharacterUsed;
    }

    public void setIndexCharacterUsed(int indexCharacterUsed) {
        this.indexCharacterUsed = indexCharacterUsed;
    }
}
