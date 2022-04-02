package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

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

    public void useAbility(Game currGame, Student student) {
        changeCost();
        removeStudent(student);
        currGame.getCurrPlayer().getMySchoolBoard().addStudentToDining(student);
        addStudent(currGame.getBag().removeStudent());
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
