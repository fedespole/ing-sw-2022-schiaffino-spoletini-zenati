package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class Character11 extends Character{
    public Character11() {
        setCost(2);
        setId(11);
        setHasBeenUsed(false);
    }

    @Override
    public void useAbility(ConcreteExpertGame currGame) {
        changeCost();
        //more
    }
}
