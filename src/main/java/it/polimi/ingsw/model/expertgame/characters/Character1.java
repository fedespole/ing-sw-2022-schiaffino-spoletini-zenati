package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.COLOR;
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
            students.add(currGame.getBag().removeStudent());
        }
    }

    public void useAbility(Game currGame, int colorIndex, ArrayList<Island> island){
        COLOR color= COLOR.values()[colorIndex];
        playerPayment(currGame.getCurrPlayer());
        changeCost();
        for(int i=0;i<students.size();i++) {
            if(students.get(i).getColor().equals(color)) {
                Student student = students.get(i);
                students.remove(student);
                island.get(0).addStudent(student);
                students.add(currGame.getBag().removeStudent());
                break;
            }
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
