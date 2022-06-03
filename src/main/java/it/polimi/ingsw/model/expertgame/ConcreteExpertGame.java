package it.polimi.ingsw.model.expertgame;

import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.*;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.view.ViewData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ConcreteExpertGame extends ExpertGameDecorator {
    private Game game;
    private ArrayList<Character> characters;

    public ConcreteExpertGame(Game game) {
        this.game = game;
        characters= new ArrayList<Character>();
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
        ArrayList<Integer> randomNumbers= new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
        game.setUp();
        for(Player player : this.getPlayers()){
            player.setCoins(1);
        }
        for(int i=0;i<3;i++){
            int rand= randomNumbers.remove(new Random().nextInt(12-i));
            switch(rand){
                case 1:
                    this.characters.add(new Character1(this));
                    break;
                case 2:
                    this.characters.add(new Character2());
                    break;
                case 3:
                    this.characters.add(new Character3());
                    break;
                case 4:
                    this.characters.add(new Character4());
                    break;
                case 5:
                    this.characters.add(new Character5());
                    break;
                case 6:
                    this.characters.add(new Character6());
                    break;
                case 7:
                    this.characters.add(new Character7(this));
                    break;
                case 8:
                    this.characters.add(new Character8());
                    break;
                case 9:
                    this.characters.add(new Character9());
                    break;
                case 10:
                    this.characters.add(new Character10());
                    break;
                case 11:
                    this.characters.add(new Character11(this));
                    break;
                case 12:
                    this.characters.add(new Character12());
                    break;
            }
        }
        this.characters.remove(0);
        this.characters.add(new Character5());
    }

    @Override
    public void fillClouds() {
        game.fillClouds();
    }

    @Override
    public void chooseCard(int index) {
        game.chooseCard(index);
    }

    @Override
    public void moveStudentFromEntranceToDining(COLOR color) {
        game.moveStudentFromEntranceToDining(color);
        if(this.getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size()%3==0) {
            this.getCurrPlayer().setCoins(this.getCurrPlayer().getCoins() + 1);
        }
    }

    @Override
    public void moveStudentFromEntranceToIsland(COLOR color, Island chosenIsland) {
        game.moveStudentFromEntranceToIsland(color, chosenIsland);
    }

    @Override
    public void moveMother(int steps) {
        setMotherNature((game.getMotherNature()+steps) % this.getIslands().size());

        this.computeInfluence();
        if(getStatusGame().getOrder().get(getStatusGame().getOrder().size()-1)==getCurrPlayer()){
            if(game.getLastRound()) checkWinner();
        }
        this.getStatusGame().setStatus(STATUS.ACTION_CHOOSECLOUD);
    }

    @Override
    public void chooseCloud(int cloud) {
        game.chooseCloud(cloud);
    }

    @Override
    public void moveStudentsFromCloud(Cloud chosenCloud) {
        game.moveStudentsFromCloud(chosenCloud);
    }

    @Override
    public void computeInfluence() {
        System.out.println("Sono nel conbcreteExpert computeinfluence");
        int chr5 = -1;
        for(int i = 0;  i < 3; i++){
            if(this.getCharacters().get(i) instanceof Character5){
                System.out.println("C'è il 5");
                chr5 = i;
                break;
            }
        }

        if (chr5 != -1) {
            if (!((this.getIslands().get(this.getMotherNature())).get(0).isNoEntry())) {
                game.computeInfluence();
                System.out.println("Non c'è il divieto");
            } else {
                System.out.println("C'è il divieto");
                ((Character5)this.getCharacters().get(chr5)).restoreNoEntry(this.getIslands().get(this.getMotherNature()));
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
    public void checkWinner(){
        game.checkWinner();
    }

    @Override
    public ViewData getData(){//returns a ViewData with a list of characters
        ViewData viewData = game.getData();
        viewData.setCharacters((ArrayList<Character>) characters.clone());
        viewData.setExpert(true);
        return viewData;
    }

    @Override
    public boolean getLastRound() {
        return game.getLastRound();
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

    @Override
    public StatusGame getStatusGame() {
        return game.getStatusGame();
    }

    @Override
    public void setNumPlayers(int numPlayers) {

    }
}
