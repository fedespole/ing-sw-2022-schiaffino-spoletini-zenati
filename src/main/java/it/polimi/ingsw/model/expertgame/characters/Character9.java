package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode9;

public class Character9 extends Character{

    public Character9(){}

    public void useAbility(ConcreteExpertGame currGame, COLOR color) {
        currGame = new GameMode9(currGame, color);
    }
}
