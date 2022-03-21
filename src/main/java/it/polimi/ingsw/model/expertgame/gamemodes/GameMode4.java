package it.polimi.ingsw.model.expertgame.gamemodes;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class GameMode4 extends ConcreteExpertGame {
    public GameMode4(ConcreteExpertGame concreteGame,Player player) {//island to call computeInfluence
        super(concreteGame);
        player.setMaxSteps(player.getMaxSteps()+2);
    }
}
