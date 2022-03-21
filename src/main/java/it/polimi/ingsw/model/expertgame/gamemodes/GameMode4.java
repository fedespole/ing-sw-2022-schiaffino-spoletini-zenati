package it.polimi.ingsw.model.expertgame.gamemodes;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class GameMode4 extends ConcreteExpertGame {
    public GameMode4(ConcreteExpertGame concreteGame) {//island to call computeInfluence
        super(concreteGame);
        //missing +two steps in the maxSteps of the card
    }

    @Override
    public void moveMother(int steps, Player player) {
        super.moveMother(steps);
    }
}
