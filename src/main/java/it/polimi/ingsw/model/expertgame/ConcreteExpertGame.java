package it.polimi.ingsw.model.expertgame;

import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.model.expertgame.characters.Character5;

import java.util.ArrayList;

public class ConcreteExpertGame extends ExpertGameDecorator {
    private Game game;
    private ArrayList<Character> characters;

    public ConcreteExpertGame(Game game) {
        this.game = game;
        for (Player player : game.getPlayers()) {
            player.setCoins(0);
        characters= new ArrayList<Character>();
        }

    }

    public ConcreteExpertGame(ConcreteExpertGame expertGame) {
        this.game = expertGame.game;
        this.characters = expertGame.characters;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
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

        Character5 character5 = null;
        for (Character character : characters) {
            if (character instanceof Character5) {
                character5 = (Character5) character;
                break;
            }
        }
        if (character5 != null) {
            if (this.getIslands().get(this.getMotherNature()).get(0).isNoEntry() == false) {
                this.computeInfluence();
            } else {
                character5.restoreNoEntry(this.getIslands().get(this.getMotherNature()));
            }
        } else this.computeInfluence();
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
