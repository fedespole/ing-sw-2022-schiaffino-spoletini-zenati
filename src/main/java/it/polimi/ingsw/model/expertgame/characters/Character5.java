package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.exceptions.AbilityAlreadyUsedException;
import it.polimi.ingsw.model.basicgame.Island;

import java.util.ArrayList;

public class Character5 extends Character{

    private int noEntries;
    private final ArrayList<ArrayList<Island>> islandsWithNoEntries;

    public Character5(){
        setCost(2);
        setId(5);
        setHasBeenUsed(false);
        noEntries = 4;
        islandsWithNoEntries = new ArrayList<ArrayList<Island>>();
    }

    public void useAbility(ArrayList<Island> island) {

        if(noEntries==0) throw new AbilityAlreadyUsedException();

        changeCost();
        islandsWithNoEntries.add(island);
        noEntries--;
    }

    public void restoreNoEntry(ArrayList<Island> island){

        islandsWithNoEntries.remove(island);
        noEntries++;
    }

    public int getNoEntries() {
        return noEntries;
    }

    public ArrayList<ArrayList<Island>> getIslandsWithNoEntries() {
        return islandsWithNoEntries;
    }
}
