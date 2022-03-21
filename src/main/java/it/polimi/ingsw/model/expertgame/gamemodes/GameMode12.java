package it.polimi.ingsw.model.expertgame.gamemodes;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;


public class GameMode12 extends ConcreteExpertGame {
    public GameMode12(ConcreteExpertGame concreteGame, COLOR color){
        super(concreteGame);
        for(Player player : this.getGame().getPlayers()){
            for(int i=0;i<3 && player.getMySchoolBoard().getDiningRoom()[color.ordinal()].size()!=0;i++){
                this.getGame().getBag().addStudent(player.getMySchoolBoard().removeStudentFromDiningRoom(color));
            }
        }
    }
}
