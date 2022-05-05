package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

public class Character12 extends Character{

    public Character12(){
        setCost(3);
        setId(12);
        setHasBeenUsed(false);
    }

    public void useAbility(Game currGame, COLOR color) {

        playerPayment(currGame.getCurrPlayer());
        changeCost();
        for(Player player : currGame.getPlayers()){
            for(int i=0;i<3 && player.getMySchoolBoard().getDiningRoom()[color.ordinal()].size()!=0;i++){
                currGame.getBag().addStudent(player.getMySchoolBoard().removeStudentFromDiningRoom(color));
            }
        }
    }
}
