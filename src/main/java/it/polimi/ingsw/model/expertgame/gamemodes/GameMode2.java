package it.polimi.ingsw.model.expertgame.gamemodes;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class GameMode2 extends ConcreteExpertGame {
    public GameMode2(ConcreteExpertGame concreteExpertGame) {
        super(concreteExpertGame);
    }
    public void assignProfessor(Player player, COLOR color){

        // Check if professor is not owned
        if(this.getGame().getProfessors().get(color.ordinal()).getOwner()==null){
            this.getGame().getProfessors().get(color.ordinal()).setOwner(player);           // assignProf
        }
        // Compare influence of this player and last owner, if higher, the owner changes
        else{
            int influenceContender = player.getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
            int influenceLastOwner = this.getGame().getProfessors().get(color.ordinal()).getOwner().getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
            if(influenceContender>=influenceLastOwner){
                this.getGame().getProfessors().get(color.ordinal()).setOwner(player);       // assignProf
            }
        }
    }
}