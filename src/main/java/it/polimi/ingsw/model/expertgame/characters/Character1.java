package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.Island;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

import java.util.ArrayList;

public class Character1 extends Character {

    private final ArrayList<Student> students;

    public Character1(ConcreteExpertGame currGame){
        setCost(1);
        setId(1);
        setHasBeenUsed(false);
        students = new ArrayList<Student>();
        for(int i=0; i<4; i++) {
            addStudent(currGame.getGame().getBag().removeStudent());
        }
    }

    public void useAbility(ConcreteExpertGame currGame, Student student, Island island){
        changeCost();
        removeStudent(student);
        island.addStudent(student);
        addStudent(currGame.getGame().getBag().removeStudent());
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    public void addStudent(Student student){
        students.add(student);
    }
}
