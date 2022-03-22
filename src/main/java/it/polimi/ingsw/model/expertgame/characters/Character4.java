package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class Character4 extends Character{

    public void useAbility(ConcreteExpertGame currGame, Player player){
        player.setMaxSteps(player.getMaxSteps()+2);
    }

}
