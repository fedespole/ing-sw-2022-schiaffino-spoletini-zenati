package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.common.exceptions.AbilityAlreadyUsedException;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Island;

import java.util.ArrayList;

public class Character5 extends Character {

    private int noEntries;

    public Character5() {
        setCost(2);
        setId(5);
        setHasBeenUsed(false);
        noEntries = 4;
    }

    public void useAbility(Game currGame, ArrayList<Island> island) {

        if (noEntries == 0) throw new AbilityAlreadyUsedException();

        playerPayment(currGame.getCurrPlayer());
        changeCost();
        island.get(0).setNoEntry(true);//change the flag only of first island of the group
        noEntries--;
    }

    public void restoreNoEntry(ArrayList<Island> island) {

        island.get(0).setNoEntry(false);
        noEntries++;
    }

    public int getNoEntries() {
        return noEntries;
    }
}
