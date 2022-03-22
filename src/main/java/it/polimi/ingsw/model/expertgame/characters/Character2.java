package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode2;

public class Character2 extends Character{

    public Character2(){
        setCost(2);
        setId(2);
    }

    @Override
    public void useAbility(ConcreteExpertGame currGame) {
        currGame = new GameMode2(currGame);
    }
}
