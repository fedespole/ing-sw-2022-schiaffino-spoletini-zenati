package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

import java.util.ArrayList;

public class Character7 extends Character{

    private final ArrayList<Student> students;

    public Character7(Game currGame){
        setCost(1);
        setId(7);
        setHasBeenUsed(false);
        students = new ArrayList<Student>();
        for(int i=0; i<6; i++) {
            addStudent(currGame.getBag().removeStudent());
        }
    }

    public void useAbility(Game currGame,ArrayList<COLOR> colors) {//even are from the entrance,odd are from the card
        changeCost();
        Student support;
        for(int i=0; i<colors.size(); i++){
            if(i%2==0){
                support = currGame.getCurrPlayer().getMySchoolBoard().removeStudentFromEntrance(colors.get(i));
                addStudent(support);
            }
            else{
                support = this.removeStudent(colors.get(i));
                currGame.getCurrPlayer().getMySchoolBoard().addStudentToEntrance(support);
            }
        }

    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public Student removeStudent(COLOR color){
        for(int i=0;i<this.students.size();i++){
            if(this.students.get(i).getColor()==color){
                return students.remove(i);
            }
        }
        return null;//exception non ce colore
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    public void addStudent(Student student){
        students.add(student);
    }

}
