package it.polimi.ingsw.model.basicgame;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.util.ArrayList;

public class BasicGame implements Game{
    private ArrayList<Player> players;
    private int numPlayers;
    private Bag bag;
    private ArrayList<ArrayList<Island>> islands;
    private ArrayList<Professor> professors;
    private int motherNature;

    public BasicGame(int numPlayers) {
        this.numPlayers = numPlayers;
        this.bag = new Bag();
        this.islands = new ArrayList<ArrayList<Island>>();
        for(int i=0; i<12; i++){
            islands.add(new ArrayList<Island>());
        }
        this.professors = new ArrayList<Professor>();
        for(COLOR color : COLOR.values()){
            professors.add(new Professor(color));
        }

    }

    @Override
    public void fillCloud() {
        if(players.size()==2){

        }
    }

    @Override
    public AssistantCard chooseCard() {
        return null;
    }

    @Override
    public void movestudentsfromEntrance() {

    }

    @Override
    public void moveMother(int steps) {

    }

    @Override
    public Cloud chooseCloud() {
        return null;
    }

    @Override
    public void moveStudentsFromCloud(Cloud chosenCloud) {

    }

    @Override
    public void computeInfluence() {

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
