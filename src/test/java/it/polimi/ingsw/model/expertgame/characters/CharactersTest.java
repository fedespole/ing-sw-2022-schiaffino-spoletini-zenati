package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static javax.swing.UIManager.get;

public class CharactersTest extends TestCase {
    Game game;

    @BeforeEach
    public void setUp(){
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(1)+1;// is 1 or 2
        game =new BasicGame(new Player("Host"));
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test"));
        }
        game.setUp();
        this.game =new ConcreteExpertGame(game);

    }

    @Test
    public void CharacterTest1(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character1(game));
        assertEquals(4,((Character1)((ConcreteExpertGame)game).getCharacters().get(0)).getStudents().size());
        int sizeIsland= game.getIslands().get(0).get(0).getStudents().size();
        ((Character1)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,0,game.getIslands().get(0));
        assertEquals(sizeIsland+1,game.getIslands().get(0).get(0).getStudents().size());
    }

    @Test
    public void CharacterTest2(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character2());
        game.setCurrPlayer(game.getPlayers().get(0));
        for(int i=0;i<3;i++)
            game.getPlayers().get(0).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        game.assignProfessor(COLOR.RED);
        assertEquals(game.getProfessors().get(COLOR.RED.ordinal()).getOwner(),game.getPlayers().get(0));
        assertEquals(game.getCurrPlayer().getMySchoolBoard().getProfessors().get(0),game.getProfessors().get(COLOR.RED.ordinal()));
        for(int i=0;i<3;i++)
            game.getPlayers().get(1).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        game = ((Character2)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game);
        game.assignProfessor(COLOR.RED);
        game.setCurrPlayer(game.getPlayers().get(1));
        assertEquals(game.getProfessors().get(COLOR.RED.ordinal()).getOwner(),game.getPlayers().get(0));
    }

    @Test
    public void CharacterTest3(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character3());
        for(int i=0;i<4;i++) {
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
            game.getPlayers().get(0).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        }
        game.assignProfessor(COLOR.RED);
        ((Character3)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,0);
        assertEquals(game.getIslands().get(0).get(0).getTower().getColor(),game.getPlayers().get(0).getTeam());
    }

    @Test
    public void CharacterTest4(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character4());
        game.setCurrPlayer(game.getPlayers().get(0));
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(9);// from 0 to 9
        AssistantCard chosenCard = game.getCurrPlayer().getMyDeck().draw(game.getCurrPlayer().getMyDeck().getCards().get(int_random));
        game.getCurrPlayer().setChosenCard(chosenCard);
        int old_steps= chosenCard.getSteps();
        ((Character4)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game.getCurrPlayer());
        assertEquals(old_steps+2,game.getCurrPlayer().getChosenCard().getSteps());
    }

    @Test
    public void CharacterTest5(){
         Character5 character5 = new Character5();
        ((ConcreteExpertGame)game).getCharacters().add(character5);
        game.setCurrPlayer(game.getPlayers().get(0));
        for(int i=0;i<4;i++){
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        }
        game.assignProfessor(COLOR.RED);
        character5.useAbility(game.getIslands().get(0));
        assertEquals(1,character5.getIslandsWithNoEntries().size());
        assertEquals(3,character5.getNoEntries());
        game.setMotherNature(0);
        game.computeInfluence();
        assertEquals(null,game.getIslands().get(0).get(0).getTower());
        assertEquals(4,character5.getNoEntries());
    }

    @Test
    public void CharacterTest6(){
        Character6 character6 = new Character6();
        ((ConcreteExpertGame)game).getCharacters().add(character6);
        game.setCurrPlayer(game.getPlayers().get(0));
        game.setMotherNature(0);
        for(int i=0;i<4;i++){
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        }
        game.assignProfessor(COLOR.RED);
        game.computeInfluence();
        assertEquals(game.getIslands().get(0).get(0).getTower().getColor(),game.getPlayers().get(0).getTeam());
        game.setCurrPlayer(game.getPlayers().get(1));
        for(int i=0;i<7;i++){
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.YELLOW));
            game.getPlayers().get(1).getMySchoolBoard().addStudentToDining(new Student(COLOR.YELLOW));
        }
        game.assignProfessor(COLOR.YELLOW);
        game = character6.useAbility(game);
        game.computeInfluence();
        assertEquals(game.getIslands().get(0).get(0).getTower().getColor(),game.getPlayers().get(1).getTeam());
    }

   /* @Test
    public void CharacterTest7(){
        Character7 character7 = new Character7(game);

    }*/

    @Test
    public void CharacterTest8(){
        Character8 character8 = new Character8();
        ((ConcreteExpertGame)game).getCharacters().add(character8);
        game.setMotherNature(0);
        game.setCurrPlayer(game.getPlayers().get(0));
        for(int i=0;i<4;i++){
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
        }
        game.assignProfessor(COLOR.RED);
        game.setCurrPlayer(game.getPlayers().get(1));
        for(int i=0;i<3;i++){
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.BLUE));
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.BLUE));
        }
        game.assignProfessor(COLOR.BLUE);
        game = character8.useAbility(game);
        assertEquals(null,game.getIslands().get(0).get(0).getTower());
        game.computeInfluence();
        assertEquals(game.getCurrPlayer().getTeam(),game.getIslands().get(0).get(0).getTower().getColor());
    }

    @Test
    public void Character9Test(){
        Character9 character9 = new Character9();
        ((ConcreteExpertGame)game).getCharacters().add(character9);
        game.setMotherNature(0);
        game.setCurrPlayer(game.getPlayers().get(0));
        for(int i=0;i<4;i++){
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
        }
        game.assignProfessor(COLOR.RED);
        game.computeInfluence();
        assertEquals(game.getIslands().get(0).get(0).getTower().getColor(),game.getCurrPlayer().getTeam());
        game.setCurrPlayer(game.getPlayers().get(1));
        game = character9.useAbility(game,COLOR.RED);
        for(int i=0;i<2;i++) {
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.BLUE));
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.BLUE));
        }
        game.assignProfessor(COLOR.BLUE);
        game.computeInfluence();
        assertEquals(game.getIslands().get(0).get(0).getTower().getColor(),game.getCurrPlayer().getTeam());
    }

    @Test
    public void Character10Test(){
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
        character10.useAbility(game,students);
        assertEquals(true,game.getCurrPlayer().getMySchoolBoard().getEntrance().contains(student_1));
        assertEquals(true,game.getCurrPlayer().getMySchoolBoard().getEntrance().contains(student_3));
        assertEquals(1, game.getCurrPlayer().getMySchoolBoard().getDiningRoom()[COLOR.YELLOW.ordinal()].size());
        assertEquals(1, game.getCurrPlayer().getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size());
    }

    @Test
    public void Character11Test(){
        Character11 character11 = new Character11(game);
        ((ConcreteExpertGame)game).getCharacters().add(character11);
        Student student = character11.getStudents().get(new Random().nextInt(3));
        character11.useAbility(game,student);
        for(COLOR color:COLOR.values()){
            if(color == student.getColor()){
                assertEquals(1,game.getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size());
            }
            else{
                assertEquals(0,game.getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size());
            }
        }
        assertEquals(4,character11.getStudents().size());
    }


    @Test
    public void Character12Test(){
        Character12 character12 = new Character12();
        ((ConcreteExpertGame)game).getCharacters().add(character12);
        for(int i=0;i<5;i++){
            game.getPlayers().get(0).getMySchoolBoard().addStudentToDining(new Student(COLOR.PINK));
        }
        for(int i=0;i<2;i++){
            game.getPlayers().get(1).getMySchoolBoard().addStudentToDining(new Student(COLOR.PINK));
        }
        for(int i=0;i<4 && game.getNumPlayers()==3;i++){
            game.getPlayers().get(2).getMySchoolBoard().addStudentToDining(new Student(COLOR.PINK));
        }
        assertEquals(5,game.getPlayers().get(0).getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size());
        assertEquals(2,game.getPlayers().get(1).getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size());
        if(game.getNumPlayers()==3){
            assertEquals(4,game.getPlayers().get(2).getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size());
        }
        character12.useAbility(game,COLOR.PINK);
        assertEquals(2,game.getPlayers().get(0).getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size());
        assertEquals(0,game.getPlayers().get(1).getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size());
        if(game.getNumPlayers()==3){
            assertEquals(1,game.getPlayers().get(2).getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size());
        }
    }
}

