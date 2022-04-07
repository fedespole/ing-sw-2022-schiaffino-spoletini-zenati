package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.common.exceptions.InvalidPlayerIndexException;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Island;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class Character1 extends Character {

    private final ArrayList<Student> students;

    public Character1(Game currGame){
        setCost(1);
        setId(1);
        setHasBeenUsed(false);
        students = new ArrayList<Student>();
        for(int i=0; i<4; i++) {
            addStudent(currGame.getBag().removeStudent());
        }
    }

    public void useAbility(Game currGame, int index, ArrayList<Island> island){

        if(index < 0 || index > 3) throw new InvalidPlayerIndexException();

        changeCost();
        removeStudent(students.get(index));
        island.get(0).addStudent(students.get(index));
        addStudent(currGame.getBag().removeStudent());
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
