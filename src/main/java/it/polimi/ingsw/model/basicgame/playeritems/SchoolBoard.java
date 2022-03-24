package it.polimi.ingsw.model.basicgame.playeritems;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.basicgame.Tower;

import java.util.ArrayList;

public class SchoolBoard {
    private ArrayList<Student> entrance;
    private ArrayList<Student>[] diningRoom;
    private ArrayList<Tower> towers;
    private ArrayList<Professor> professors;

    public void addStudentToEntrance(Student student){
        entrance.add(student);
    }

    public void removeStudentFromEntrance(Student student) {
            entrance.remove(student);
        }

    public void addStudentToDining(Student student){
        diningRoom[student.getColor().ordinal()].add(student);
    }

    public void addTower(Tower tower){
        towers.add(tower);
    }

    public Tower removeTower(){
        return towers.remove(towers.size()-1);
    }

    public void addProfessor(Professor prof){
        professors.add(prof);
    }

    public Professor removeProfessor(Professor prof){
        return professors.remove(professors.indexOf(prof));
    }

    public ArrayList<Student>[] getDiningRoom(){
        return diningRoom;
    }


    public Student removeStudentFromDiningRoom(COLOR color) {
       return diningRoom[color.ordinal()].remove(diningRoom[color.ordinal()].size()-1);
    }
    public Student removeStudentFromEntrance(COLOR color){
        for (int i = 0, entranceSize = entrance.size(); i < entranceSize; i++) {
            if(entrance.get(i).getColor().equals(color))
                return entrance.get(i);
        }
        return null;
    }
}
