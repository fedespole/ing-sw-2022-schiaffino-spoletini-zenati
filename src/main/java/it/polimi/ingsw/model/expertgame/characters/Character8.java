package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode8;

public class Character8 extends Character{

    public Character8(){
        setCost(2);
        setId(8);
        setHasBeenUsed(false);
    }

    public void useAbility(ConcreteExpertGame currGame, Player player) {
        changeCost();
        currGame = new GameMode8(currGame, player);
    }
}
