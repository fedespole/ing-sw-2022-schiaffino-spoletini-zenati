package it.polimi.ingsw.model.expertgame;

import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Island;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.Character;

import java.util.Collections;
import java.util.Random;

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
    public void setUp(){
        game.setUp();
    }

    @Override
    public void fillCloud() {
        game.fillCloud();
    }

    @Override
    public AssistantCard chooseCard(int index) {
        return game.chooseCard(index);
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
        game.computeInfluence();
    }

    @Override
    public void assignProfessor(COLOR color) {
        game.assignProfessor(color);
    }

    @Override
    public int getMotherNature() {
        return game.getMotherNature();
    }

    public Player getCurrPlayer() {
        return game.getCurrPlayer();
    }
}
