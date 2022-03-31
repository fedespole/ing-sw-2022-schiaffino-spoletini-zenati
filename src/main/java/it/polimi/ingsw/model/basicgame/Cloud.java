package it.polimi.ingsw.model.basicgame;
import it.polimi.ingsw.model.basicgame.Bag;
import it.polimi.ingsw.model.basicgame.Student;
import java.util.ArrayList;

public class Cloud {
    private final ArrayList<Student> students;

    public Cloud() {
        students = new ArrayList<Student>();
    }

    public void addStudent(Bag bag){
        students.add(bag.removeStudent());
    }
    public Student removeStudent(int index){
        return students.remove(index);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
