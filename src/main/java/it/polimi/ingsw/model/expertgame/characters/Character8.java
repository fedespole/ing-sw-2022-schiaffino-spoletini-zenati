package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode8;

public class Character8 extends Character{

    public Character8(){
        setCost(2);
        setId(8);
        setHasBeenUsed(false);
    }

    public GameMode8 useAbility(Game currGame) {

        playerPayment(currGame.getCurrPlayer());
        changeCost();
        return new GameMode8( (ConcreteExpertGame) currGame);
    }
}
