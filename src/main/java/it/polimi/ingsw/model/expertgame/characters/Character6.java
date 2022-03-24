package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode6;

public class Character6 extends Character{

    public Character6(ConcreteExpertGame currGame){
        setCost(3);
        setId(6);
        setHasBeenUsed(false);
    }

    @Override
    public void useAbility(ConcreteExpertGame currGame) {
        changeCost();
        currGame = new GameMode6(currGame);
    }
}
