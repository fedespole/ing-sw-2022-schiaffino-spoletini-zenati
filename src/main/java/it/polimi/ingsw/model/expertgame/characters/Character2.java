package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode2;

public class Character2 extends Character{

    public Character2(ConcreteExpertGame currGame){
        currGame = new GameMode2(currGame);
    }
}
