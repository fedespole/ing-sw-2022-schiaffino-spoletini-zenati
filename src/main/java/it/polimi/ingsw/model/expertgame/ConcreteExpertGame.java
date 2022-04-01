package it.polimi.ingsw.model.expertgame;

import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.model.expertgame.characters.Character5;

import java.util.ArrayList;

public class ConcreteExpertGame extends ExpertGameDecorator {
    private BasicGame game;
    private Character[] characters;

    public ConcreteExpertGame(BasicGame game) {
        this.game = game;
        for (Player player : game.getPlayers()) {
            player.setCoins(0);
        }

    }

    public ConcreteExpertGame(ConcreteExpertGame expertGame) {
        this.game = expertGame.game;
        this.characters = expertGame.characters;
    }

    public BasicGame getGame() {
        return game;
    }

    public void setGame(BasicGame game) {
        this.game = game;
    }

    public Character[] getCharacters() {
        return characters;
    }

    public void setCharacters(Character[] characters) {
        this.characters = characters;
    }

    @Override
    public void setUp() {
        game.setUp();
    }

    @Override
    public void fillCloud() {
        game.fillCloud();
    }

    @Override
    public void chooseCard(int index) {
        game.chooseCard(index);
    }

    @Override
    public void moveStudentFromEntranceToDining(COLOR color) {
        game.moveStudentFromEntranceToDining(color);
    }

    @Override
    public void moveStudentFromEntranceToIsland(COLOR color, Island chosenIsland) {
        game.moveStudentFromEntranceToIsland(color, chosenIsland);
    }

    @Override
    public void moveMother(int steps) {
        game.moveMother(steps);
    }

    @Override
    public Cloud chooseCloud(int cloud) {
        return game.chooseCloud(cloud);
    }

    @Override
    public void moveStudentsFromCloud(Cloud chosenCloud) {
        game.moveStudentsFromCloud(chosenCloud);
    }

    @Override
    public void computeInfluence() {

        int char5index = -1, i = 0;

        for (Character character : characters) {
            if (character instanceof Character5) char5index = i;
            i++;
        }

        // if expertgame has character5, the influence is computed only if the island doesn't have a noEntry
        if (char5index != -1) {
            // Checking if island has a noEntry status
            if (!((Character5) characters[char5index]).getIslandsWithNoEntries().contains(game.getIslands().get(getMotherNature()))) {
                game.computeInfluence();
            } else {
                ((Character5) characters[char5index]).restoreNoEntry(game.getIslands().get(getMotherNature()));
            }
        } else game.computeInfluence();
    }

    @Override
    public void assignProfessor(COLOR color) {
        game.assignProfessor(color);
    }

    @Override
    public void mergeIslands() {
        game.mergeIslands();
    }

    @Override
    public int getMotherNature() {
        return game.getMotherNature();
    }

    public Player getCurrPlayer() {
        return game.getCurrPlayer();
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return game.getPlayers();
    }

    @Override
    public int getNumPlayers() {
        return game.getNumPlayers();
    }

    @Override
    public Bag getBag() {
        return game.getBag();
    }

    @Override
    public ArrayList<Professor> getProfessors() {
        return game.getProfessors();
    }

    @Override
    public ArrayList<ArrayList<Island>> getIslands() {
        return game.getIslands();
    }

    @Override
    public void setPlayers(ArrayList<Player> players) {
        game.setPlayers(players);
    }

    @Override
    public void setNumPlayers(int numPlayers) {
        game.setNumPlayers(numPlayers);
    }

    @Override
    public void setBag(Bag bag) {
        game.setBag(bag);
    }

    @Override
    public void setIslands(ArrayList<ArrayList<Island>> islands) {
        game.setIslands(islands);
    }

    @Override
    public void setProfessors(ArrayList<Professor> professors) {
        game.setProfessors(professors);
    }

    @Override
    public void setMotherNature(int motherNature) {
        game.setMotherNature(motherNature);
    }

    @Override
    public void setCurrPlayer(Player currPlayer) {
        game.setCurrPlayer(currPlayer);
    }

    @Override
    public ArrayList<Cloud> getClouds() {
        return game.getClouds();
    }
}
