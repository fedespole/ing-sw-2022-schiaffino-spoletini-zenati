package it.polimi.ingsw.model.basicgame.playeritems;
import it.polimi.ingsw.common.exceptions.NoMoreSpaceException;
import it.polimi.ingsw.common.exceptions.StudentNotPresentException;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Professor;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.basicgame.Tower;

import java.io.Serializable;
import java.util.ArrayList;

public class SchoolBoard implements Serializable {
    private final ArrayList<Student> entrance;
    private final ArrayList<Student>[] diningRoom;
    private final ArrayList<Tower> towers;
    private final ArrayList<Professor> professors;

    public SchoolBoard() {
        this.entrance = new ArrayList<Student>();
        this.diningRoom = new ArrayList[5];
        for(int i=0;i<5;i++){
            this.diningRoom[i]= new ArrayList<Student>();
        }
        this.towers = new ArrayList<Tower>();
        this.professors = new ArrayList<Professor>();
    }

    public void addStudentToEntrance(Student student){
        entrance.add(student);
    }

    public Student removeStudentFromEntrance(COLOR color) throws StudentNotPresentException {
        for(Student student : entrance){
            if(student.getColor().equals(color)){
                entrance.remove(student);
                return student;
            }
        }
        throw new StudentNotPresentException();
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

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public Student removeStudentFromDiningRoom(COLOR color) {
        if(diningRoom[color.ordinal()].size() == 0) throw new StudentNotPresentException();
        return diningRoom[color.ordinal()].remove(diningRoom[color.ordinal()].size()-1);
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public ArrayList<Student> getEntrance() {
        return entrance;
    }
}
