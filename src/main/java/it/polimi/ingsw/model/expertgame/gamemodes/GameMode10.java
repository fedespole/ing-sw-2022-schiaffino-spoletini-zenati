package it.polimi.ingsw.model.expertgame.gamemodes;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public class GameMode10 extends ConcreteExpertGame{//array lungo 4,con studenti accoppiati due a due. students[0] si trova nell'entrance,students[1] si trova nella plancia
    public GameMode10(ConcreteExpertGame concreteExpertGame, Player player, Student[] students) {
        super(concreteExpertGame);
        for(int i=0;i<2 && students[2*i]!=null;i++){
                player.getMySchoolBoard().removeStudentFromEntrance(students[2*i]);
                player.getMySchoolBoard().addStudentToDining(students[2*i]);
                player.getMySchoolBoard().removeStudentFromDiningRoom(students[2*i+1].getColor());
                player.getMySchoolBoard().addStudentToEntrance(students[2*i+1]);
        }
    }
}
