package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class Character4 extends Character{

    public Character4(){
        setCost(1);
        setId(4);
        setHasBeenUsed(false);
    }

    public void useAbility(ConcreteExpertGame currGame, Player player){
        changeCost();
        player.setMaxSteps(player.getMaxSteps()+2);
    }

}
