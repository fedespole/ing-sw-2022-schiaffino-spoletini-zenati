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
        for(Player player : game.getPlayers()){
            player.setCoins(0);
        }

    }
    public ConcreteExpertGame(ConcreteExpertGame expertGame){
        this.game=expertGame.game;
        this.characters=expertGame.characters;
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
    public void fillCloud(Player player) {
        game.fillCloud(player);
    }

    @Override
    public AssistantCard chooseCard(int index,Player player) {
        return game.chooseCard(index,player);
    }

    @Override
    public void moveStudentFromEntranceToDining(Player player, Student student) {
        game.moveStudentFromEntranceToDining(player, student);
    }

    @Override
    public void moveStudentFromEntranceToIsland(Player player, Student student, Island chosenIsland){
        game.moveStudentFromEntranceToIsland(player, student, chosenIsland);
    }

    @Override
    public void moveMother(int steps, Player player) {
        game.moveMother(steps, player);
    }

    @Override
    public Cloud chooseCloud(Player player,int cloud) {
        return game.chooseCloud(player,cloud);
    }

    @Override
    public void moveStudentsFromCloud(Player player,Cloud chosenCloud) {
        game.moveStudentsFromCloud(player,chosenCloud);
    }

    @Override
    public void computeInfluence() {
        game.computeInfluence();
    }

    @Override
    public void assignProfessor(Player player, COLOR color){
        game.assignProfessor(player, color);
    }

    @Override
    public int getMotherNature(){ return game.getMotherNature(); }
}
