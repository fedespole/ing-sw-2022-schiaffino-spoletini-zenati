package it.polimi.ingsw.model;
import java.util.ArrayList;

public class BasicGame implements Game{
    private ArrayList<Player> players;
    private Bag bag;
    private ArrayList<ArrayList<Island>> islands;
    private ArrayList<Professor> professors;
    private int motherNature;

    @Override
    public void fillCloud() {

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
}
