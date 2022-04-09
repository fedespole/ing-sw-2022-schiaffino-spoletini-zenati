package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.common.exceptions.InvalidPlayerIndexException;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Island;
import it.polimi.ingsw.model.basicgame.Student;
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

        playerPayment(currGame.getCurrPlayer());
        changeCost();
        Student student = students.get(index);
        students.remove(index);
       // removeStudent(student);
        island.get(0).addStudent(student);
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
