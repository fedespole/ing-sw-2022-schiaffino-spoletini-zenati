package it.polimi.ingsw.model.basicgame;
import java.util.ArrayList;

public class Island {
    private ArrayList<Student> students;
    private Tower tower;

    public Island() {
        students = new ArrayList<Student>();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

}
