package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

import java.util.ArrayList;

public class Character7 extends Character{

    private final ArrayList<Student> students;

    public Character7(ConcreteExpertGame currGame){

        setCost(1);
        setId(7);
        setHasBeenUsed(false);

        students = new ArrayList<Student>();

        for(int i=0; i<6; i++) {
            addStudent(currGame.getGame().getBag().removeStudent());
        }
    }

    public void useAbility(ConcreteExpertGame currGame, ArrayList<Student> students) {
        changeCost();
        for(int i=0; i<students.size()/2; i++){
            removeStudent(students.get(2*i));
            // player.
        }

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
