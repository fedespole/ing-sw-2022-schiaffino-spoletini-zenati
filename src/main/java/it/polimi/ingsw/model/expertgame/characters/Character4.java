package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.playeritems.Player;

public class Character4 extends Character{

    public Character4(){
        setCost(1);
        setId(4);
        setHasBeenUsed(false);
    }

    public void useAbility(Player player){
        playerPayment(player);
        changeCost();
        player.getChosenCard().setSteps(2+player.getChosenCard().getSteps());
    }

}
