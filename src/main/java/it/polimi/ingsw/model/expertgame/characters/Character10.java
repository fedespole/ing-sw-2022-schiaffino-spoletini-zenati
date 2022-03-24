package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

import java.util.ArrayList;

public class Character10 extends Character{
//da decidere come dare l'input di useAbility
/*
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
}*/
    public Character10(ConcreteExpertGame currGame) {
        setCost(1);
        setId(10);
        setHasBeenUsed(false);
    }

    public void useAbility(ConcreteExpertGame currGame, ArrayList<COLOR> colors){  //colors è un array di color lungo 2 o 4, con colore_studente_sala1, colore_studente_ingresso1 ed eventuali 2 e 2
        //metto prima in un buffer quelli scelti, per evitare casini con la dim_max degli array destinazione
        ArrayList<Student> bufferWasInDining = new ArrayList<>();
        ArrayList<Student> bufferWasInEntrance = new ArrayList<>();
            for(int i= 0; i < colors.size(); i++){
                if(i%2==0){
                    bufferWasInDining.add(currGame.getCurrPlayer().getMySchoolBoard().removeStudentFromDiningRoom(colors.get(i)));
                }
                else{
                    bufferWasInEntrance.add(currGame.getCurrPlayer().getMySchoolBoard().removeStudentFromEntrance(colors.get(i)));
                }
            }
            for(Student student : bufferWasInDining){
                currGame.getCurrPlayer().getMySchoolBoard().addStudentToEntrance(student);
            }
        for(Student student : bufferWasInEntrance){
            currGame.getCurrPlayer().getMySchoolBoard().addStudentToDining(student);
        }
    }

}
