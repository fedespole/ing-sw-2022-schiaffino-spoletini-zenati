package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode2;

public class Character2 extends Character{

    public Character2(){
        setCost(2);
        setId(2);
        setHasBeenUsed(false);
    }

    public GameMode2 useAbility(Game currGame) {
        playerPayment(currGame.getCurrPlayer());
        changeCost();
        return new GameMode2((ConcreteExpertGame) currGame);
    }
}
