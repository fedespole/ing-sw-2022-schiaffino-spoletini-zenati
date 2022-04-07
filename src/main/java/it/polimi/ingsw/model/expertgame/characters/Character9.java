package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode9;

public class Character9 extends Character{

    public Character9(){
        setCost(3);
        setId(9);
        setHasBeenUsed(false);
    }

    public GameMode9 useAbility(Game currGame, COLOR color) {

        playerPayment(currGame.getCurrPlayer());
        changeCost();
        return new GameMode9(currGame, color);
    }
}
