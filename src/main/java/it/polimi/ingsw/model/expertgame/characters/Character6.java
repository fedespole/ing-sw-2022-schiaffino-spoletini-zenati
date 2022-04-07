package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode6;

public class Character6 extends Character{

    public Character6(){
        setCost(3);
        setId(6);
        setHasBeenUsed(false);
    }

    public GameMode6 useAbility(Game currGame) {

        playerPayment(currGame.getCurrPlayer());
        changeCost();
        return new GameMode6((ConcreteExpertGame) currGame);
    }
}
