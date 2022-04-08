package it.polimi.ingsw.model.expertgame;

import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.characters.*;
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
        int int_random = rand.nextInt(2)+1;// is 1 or 2
        game =new BasicGame(new Player("Host"));
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test"));
        }
        game.setUp();
        this.game =new ConcreteExpertGame(game);

    }

    @Test
    public void Character1Test(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character1(game));
        assertEquals(4,((Character1)((ConcreteExpertGame)game).getCharacters().get(0)).getStudents().size());
        int sizeIsland= game.getIslands().get(0).get(0).getStudents().size();
        ((Character1)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,0,game.getIslands().get(0));
        assertEquals(sizeIsland+1,game.getIslands().get(0).get(0).getStudents().size());
    }

    @Test
    public void Character2Test(){
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
    public void Character3Test(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character3());
        for(int i=0;i<4;i++) {
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        }
        game.assignProfessor(COLOR.RED);
        ((Character3)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,0);
        assertEquals(game.getIslands().get(0).get(0).getTower().getColor(),game.getCurrPlayer().getTeam());
        game.getIslands().get(0).get(0).setTower(game.getPlayers().get(1).getMySchoolBoard().removeTower());
        ((Character3)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,0);
        assertEquals(game.getIslands().get(0).get(0).getTower().getColor(),game.getCurrPlayer().getTeam());
    }

    @Test
    public void Character4Test(){
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
    public void Character5Test(){
         Character5 character5 = new Character5();
        ((ConcreteExpertGame)game).getCharacters().add(character5);
        game.setCurrPlayer(game.getPlayers().get(0));
        for(int i=0;i<4;i++){
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        }
        game.assignProfessor(COLOR.RED);
        character5.useAbility(game, game.getIslands().get(0));
        assertEquals(true,game.getIslands().get(0).get(0).isNoEntry());
        assertEquals(3,character5.getNoEntries());
        game.setMotherNature(0);
        game.computeInfluence();
        assertEquals(false,game.getIslands().get(0).get(0).isNoEntry());
        assertEquals(4,character5.getNoEntries());
    }

    @Test
    public void Character6Test(){
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

    @Test
    public void CharacterTest7() {
        Character7 character7 = new Character7(game);
        ((ConcreteExpertGame) game).getCharacters().add(character7);
        int cont_pink_prior = 0, cont_yellow_prior = 0;
         ArrayList<COLOR> colors = new ArrayList<>();
        COLOR color1,color2;
        game.getCurrPlayer().getMySchoolBoard().getEntrance().remove(0);
        game.getCurrPlayer().getMySchoolBoard().getEntrance().remove(1);
        game.getCurrPlayer().getMySchoolBoard().addStudentToEntrance(new Student(COLOR.PINK));
        game.getCurrPlayer().getMySchoolBoard().addStudentToEntrance(new Student(COLOR.YELLOW));
        for (Student student : game.getCurrPlayer().getMySchoolBoard().getEntrance()) {
            if (student.getColor() == COLOR.PINK) {
                cont_pink_prior++;
            } else if (student.getColor() == COLOR.YELLOW) {
                cont_yellow_prior++;
            }
        }
        color1=character7.getStudents().get(0).getColor();
        color2=character7.getStudents().get(1).getColor();
        colors.add(COLOR.PINK);
        colors.add(color1);
        colors.add(COLOR.YELLOW);
        colors.add(color2);
        character7.useAbility(game,colors);
        int cont_pink_post=0,cont_yellow_post=0;
        for (Student student : game.getCurrPlayer().getMySchoolBoard().getEntrance()) {
            if (student.getColor() == COLOR.PINK) {
                cont_pink_post++;
            } else if (student.getColor() == COLOR.YELLOW) {
                cont_yellow_post++;
            }
        }
        if(color1==COLOR.PINK){
            cont_pink_post-=1;
        }
        else if (color1==COLOR.YELLOW){
            cont_yellow_post-=1;
        }
        if(color2==COLOR.PINK){
            cont_pink_post-=1;
        }
        else if (color2==COLOR.YELLOW){
            cont_yellow_post-=1;
        }
        assertEquals(cont_pink_prior-1,cont_pink_post);
        assertEquals(cont_yellow_prior-1,cont_yellow_post);
    }
    @Test
    public void Character8Test(){
        Character8 character8 = new Character8();
        ((ConcreteExpertGame)game).getCharacters().add(character8);
        game.setCurrPlayer(game.getPlayers().get(0));
        for(int i=0;i<4;i++){
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
            game.getIslands().get(game.getMotherNature()).get(0).addStudent(new Student(COLOR.RED));
        }
        game.assignProfessor(COLOR.RED);
        game.setCurrPlayer(game.getPlayers().get(1));
        for(int i=0;i<3;i++){
            game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.BLUE));
            game.getIslands().get(game.getMotherNature()).get(0).addStudent(new Student(COLOR.BLUE));
        }
        game.assignProfessor(COLOR.BLUE);
        game = character8.useAbility(game);
        assertEquals(null,game.getIslands().get(game.getMotherNature()).get(0).getTower());
        game.computeInfluence();
        assertEquals(game.getCurrPlayer().getTeam(),game.getIslands().get(game.getMotherNature()).get(0).getTower().getColor());
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
        int rnd= new Random().nextInt(3);
        Student student = character11.getStudents().get(rnd);
        character11.useAbility(game,rnd);
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

