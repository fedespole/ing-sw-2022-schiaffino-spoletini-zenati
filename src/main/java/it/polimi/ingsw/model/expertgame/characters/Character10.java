package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.exceptions.InvalidNumStudentsException;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Student;

import java.util.ArrayList;

public class Character10 extends Character {

    public Character10() {
        setCost(1);
        setId(10);
        setHasBeenUsed(false);
    }

    public void useAbility(Game currGame, ArrayList<COLOR> colors) {


        if (colors.size() == 2 || colors.size() == 4) {

            // Colors is an array composed by one or two pairs of students, otherwise an exception is raised:
            // (student_color_room1 and student_color_entrance1 + eventually 2 and 2)
            // Using auxiliary buffers to preserve the length of the target arrays.

            ArrayList<Student> bufferWasInDining = new ArrayList<>();
            ArrayList<Student> bufferWasInEntrance = new ArrayList<>();
            for (int i = 0; i < colors.size(); i++) {
                if (i % 2 == 0) {
                    bufferWasInDining.add(currGame.getCurrPlayer().getMySchoolBoard().removeStudentFromDiningRoom(colors.get(i)));
                } else {
                    bufferWasInEntrance.add(currGame.getCurrPlayer().getMySchoolBoard().removeStudentFromEntrance(colors.get(i)));
                }
            }
            for (Student student : bufferWasInDining) {
                currGame.getCurrPlayer().getMySchoolBoard().addStudentToEntrance(student);
            }
            for (Student student : bufferWasInEntrance) {
                currGame.getCurrPlayer().getMySchoolBoard().addStudentToDining(student);
            }
        }
        else throw new InvalidNumStudentsException();

    }

}
