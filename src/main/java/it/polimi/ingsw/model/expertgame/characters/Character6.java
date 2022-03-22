package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode6;

public class Character6 extends Character{

    public Character6(ConcreteExpertGame currGame){

        setCost(3);
        setId(6);
    }

    @Override
    public void useAbility(ConcreteExpertGame currGame) {
        currGame = new GameMode6(currGame);
    }
}
