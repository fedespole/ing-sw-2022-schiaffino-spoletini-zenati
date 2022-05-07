package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.fromServerEvents.NotifyExceptionEvent;
import it.polimi.ingsw.common.exceptions.StudentNotPresentException;
import it.polimi.ingsw.common.exceptions.StudentNotPresentInCharacterException;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Student;

import java.util.ArrayList;

public class Character11 extends Character{

    private final ArrayList<Student> students;

    public Character11(Game currGame) {
        setCost(2);
        setId(11);
        setHasBeenUsed(false);
        students = new ArrayList<Student>();
        for(int i=0; i<4; i++) {
            addStudent(currGame.getBag().removeStudent());
        }
    }

    public void useAbility(Game currGame, int indexColor) {
        COLOR color= COLOR.values()[indexColor];
        for(int i=0;i<students.size();i++){
            if(students.get(i).getColor().equals(color)){
                playerPayment(currGame.getCurrPlayer());
                changeCost();
                Student student=students.get(i);
                removeStudent(student);
                currGame.getCurrPlayer().getMySchoolBoard().addStudentToDining(student);
                addStudent(currGame.getBag().removeStudent());
                return;
            }
        }
        GameHandler.calls(new NotifyExceptionEvent(this, new StudentNotPresentInCharacterException()));
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
