package it.polimi.ingsw.model.expertgame;


import it.polimi.ingsw.common.exceptions.*;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CharactersExceptionsTest extends TestCase{
    Game game;

    @BeforeEach
    public void setUp(){
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(2)+1;// is 1 or 2
        game =new BasicGame();
        game.getPlayers().add(new Player("Host"));
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test"));
        }
        game.setUp();
        this.game =new ConcreteExpertGame(game);

    }

    @Test
    public void Character1InvalidPlayerIndexTest(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character1(game));
        int sizeIsland= game.getIslands().get(0).get(0).getStudents().size();
        assertDoesNotThrow( ()->((Character1)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,1,game.getIslands().get(0)));
        assertThrows(IndexOutOfBoundsException.class, () -> ((Character1)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,-1,game.getIslands().get(0)));
        assertThrows(IndexOutOfBoundsException.class, () -> ((Character1)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,4,game.getIslands().get(0)));
    }

    @Test
    public void Character3InvalidIslandIndexException(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character3());
        for(int i=0;i<4;i++) {
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
            game.getPlayers().get(0).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        }
        assertDoesNotThrow( () ->((Character3)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,0));
        assertThrows(InvalidIslandIndexException.class, () ->((Character3)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,-1));
        assertThrows(InvalidIslandIndexException.class, () ->((Character3)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,game.getIslands().size()+1));
    }

    @Test
    public void Character5AbilityAlreadyUsedException(){
        Character5 character5 = new Character5();
        ((ConcreteExpertGame)game).getCharacters().add(character5);
        game.setCurrPlayer(game.getPlayers().get(0));
        for(int i=0;i<4;i++){
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        }
        for(int i= 0; i<4; i++)
            assertDoesNotThrow(() ->character5.useAbility(game, game.getIslands().get(0)));
        assertThrows(AbilityAlreadyUsedException.class, () -> character5.useAbility(game, game.getIslands().get(0)));
    }

    @Test
    public void Character7AbilityStudentNotPresent(){
        Character7 character7 = new Character7(game);
        ((ConcreteExpertGame) game).getCharacters().add(character7);
        for(int i=0;i<character7.getStudents().size();i++){
            if(character7.getStudents().get(i).getColor()==COLOR.PINK){
                character7.getStudents().remove(i);
                character7.getStudents().add(new Student(COLOR.YELLOW));
                i--;
            }
        }
        assertThrows(StudentNotPresentException.class, () -> character7.removeStudent(COLOR.PINK));
    }

    @Test
    public void Character10InvalidNumStudentsException(){
        Character10 character10 = new Character10();
        ((ConcreteExpertGame)game).getCharacters().add(character10);
        game.setCurrPlayer(game.getPlayers().get(0));
        Student student_1 = new Student(COLOR.RED);
        Student student_2 = new Student(COLOR.YELLOW);
        Student student_3 = new Student(COLOR.GREEN);
        Student student_4 = new Student(COLOR.PINK);
        game.getCurrPlayer().getMySchoolBoard().addStudentToDining(student_1);
        game.getCurrPlayer().getMySchoolBoard().addStudentToEntrance(student_2);
        game.getCurrPlayer().getMySchoolBoard().addStudentToDining(student_3);
        game.getCurrPlayer().getMySchoolBoard().addStudentToEntrance(student_4);
        ArrayList<COLOR> students = new ArrayList<>();
        students.add(student_1.getColor());
        students.add(student_2.getColor());
        students.add(student_3.getColor());
        students.add(student_4.getColor());
        assertEquals(0, game.getCurrPlayer().getMySchoolBoard().getDiningRoom()[COLOR.YELLOW.ordinal()].size());
        assertEquals(0, game.getCurrPlayer().getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size());
        assertDoesNotThrow(()->character10.useAbility(game,students));
        students.remove(students.size()-1);
        assertThrows(InvalidNumStudentsException.class, ()->character10.useAbility(game,students));
    }
}
