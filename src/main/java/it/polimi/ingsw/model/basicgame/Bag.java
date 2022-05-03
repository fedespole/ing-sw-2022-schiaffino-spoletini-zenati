package it.polimi.ingsw.model.basicgame;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Bag implements Serializable {
    private final ArrayList<Student> students;

    public Bag() {
        students = new ArrayList<Student>();
        for(COLOR c : COLOR.values()){
            for(int i = 0; i < 2; i++){
                students.add(new Student(c));
            }
        }
    }
    public Student removeStudent(){
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(students.size());
        return students.remove(int_random);
    }
    public void addStudent(Student student){
        students.add(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
