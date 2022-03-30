package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.Island;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

import java.util.ArrayList;

public class Character5 extends Character{

    private int noEntries;
    private ArrayList<ArrayList<Island>> islandsWithNoEntries;

    public Character5(){
        setCost(2);
        setId(5);
        setHasBeenUsed(false);
        noEntries = 4;
        islandsWithNoEntries = new ArrayList<ArrayList<Island>>();
    }

    public void useAbility(ConcreteExpertGame currGame, ArrayList<Island> island) {
        changeCost();
        if(noEntries>0) {
            islandsWithNoEntries.add(island);
            noEntries--;
        }
    }

    public void restoreNoEntry(ArrayList<Island> island){
        islandsWithNoEntries.remove(island);
        noEntries++;
    }

    public ArrayList<ArrayList<Island>> getIslandsWithNoEntries() {
        return islandsWithNoEntries;
    }
}
