package it.polimi.ingsw.model.basicgame;
import java.util.ArrayList;

public class Island {
    private final ArrayList<Student> students;
    private Tower tower;
    private boolean noEntry;

    public Island() {
        students = new ArrayList<Student>();
        noEntry= false;
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

    public boolean isNoEntry() {
        return noEntry;
    }

    public void setNoEntry(boolean noEntry) {
        this.noEntry = noEntry;
    }
}
