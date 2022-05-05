package it.polimi.ingsw.model.expertgame.gamemodes;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class GameMode2 extends ConcreteExpertGame {
    public GameMode2(ConcreteExpertGame concreteExpertGame) {
        super(concreteExpertGame);

        //When the card is selected, checks immediately if the player can steal professors from other players

        for(COLOR color : COLOR.values()) {
            if(this.getGame().getProfessors().get(color.ordinal()).getOwner()==null && this.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size() != 0) {
                this.getGame().getProfessors().get(color.ordinal()).setOwner(getGame().getCurrPlayer());           // assignProf
                this.getGame().getCurrPlayer().getMySchoolBoard().addProfessor(this.getGame().getProfessors().get(color.ordinal()));
            }
            // Compare influence of this player and last owner, if higher or equal, the owner changes
            else if(this.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size() != 0){
                int influenceContender = getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
                int influenceLastOwner = this.getGame().getProfessors().get(color.ordinal()).getOwner().getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
                if(influenceContender>=influenceLastOwner){
                    this.getGame().getProfessors().get(color.ordinal()).setOwner(getGame().getCurrPlayer());       // assignProf
                    this.getGame().getCurrPlayer().getMySchoolBoard().addProfessor( this.getGame().getProfessors().get(color.ordinal()).getOwner().getMySchoolBoard().removeProfessor( this.getGame().getProfessors().get(color.ordinal())));
                }
            }
        }
    }

    //Whenever the current player moves students in the diningRoom, this overwritten method is called
    @Override
    public void assignProfessor(COLOR color){

        // Check if professor is not owned
        if(this.getGame().getProfessors().get(color.ordinal()).getOwner()==null){
            this.getGame().getProfessors().get(color.ordinal()).setOwner(getGame().getCurrPlayer());           // assignProf
            this.getGame().getCurrPlayer().getMySchoolBoard().addProfessor(this.getGame().getProfessors().get(color.ordinal()));
        }
        // Compare influence of this player and last owner, if higher or equal, the owner changes
        else{
            int influenceContender = getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
            int influenceLastOwner = this.getGame().getProfessors().get(color.ordinal()).getOwner().getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
            if(influenceContender>=influenceLastOwner){
                this.getGame().getCurrPlayer().getMySchoolBoard().addProfessor( this.getGame().getProfessors().get(color.ordinal()).getOwner().getMySchoolBoard().removeProfessor( this.getGame().getProfessors().get(color.ordinal())));
                this.getGame().getProfessors().get(color.ordinal()).setOwner(getGame().getCurrPlayer());       // assignProf
            }
        }
    }
}