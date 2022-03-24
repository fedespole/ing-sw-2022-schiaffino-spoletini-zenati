package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class Character12 extends Character{

    public Character12(ConcreteExpertGame currGame){
        setCost(3);
        setId(12);
        setHasBeenUsed(false);
    }

    public void useAbility(ConcreteExpertGame currGame, COLOR color) {
        changeCost();
        for(Player player : currGame.getGame().getPlayers()){
            for(int i=0;i<3 && player.getMySchoolBoard().getDiningRoom()[color.ordinal()].size()!=0;i++){
                currGame.getGame().getBag().addStudent(player.getMySchoolBoard().removeStudentFromDiningRoom(color));
            }
        }
    }
}
