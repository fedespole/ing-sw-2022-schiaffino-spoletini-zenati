package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class BasicGameTest extends TestCase {
    BasicGame game;

    @BeforeEach
    public void setUp(){
        Random rand = new Random(); //instance of random class
        //int int_random = rand.nextInt(1)+2;// is 2 or 3
        int int_random=2;
        game =new BasicGame(int_random);
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test",int_random,TEAM.values()[i],game));
        }
    }
    @Test
    public void TestFillCloud(){
        game.setCurrPlayer(game.getPlayers().get(0));
        Student student;
        for(int i=0,size=game.getCurrPlayer().getMyCloud().get(0).getStudents().size();i<size;i++){
            student = game.getCurrPlayer().getMyCloud().get(0).getStudents().remove(0);
        }
        assertEquals(0,game.getCurrPlayer().getMyCloud().get(0).getStudents().size());
        game.fillCloud();
        for(Cloud cloud:game.getPlayers().get(0).getMyCloud()){
            if(game.getNumPlayers()==2)
                assertEquals(3,cloud.getStudents().size());
            else
                assertEquals(4,cloud.getStudents().size());
        }
    }

    @Test
    public void TestComputeInfluence(){
        game.setMotherNature(0);
        for(int i=0;i<4;i++) {
            game.getIslands().get(0).get(0).getStudents().add(new Student(COLOR.YELLOW));
        }
        for(int i=0;i<3;i++){
            game.getIslands().get(0).get(0).getStudents().add(new Student(COLOR.BLUE));
        }
        game.getPlayers().get(0).getMySchoolBoard().addProfessor(game.getProfessors().get(COLOR.YELLOW.ordinal()));
        game.getPlayers().get(1).getMySchoolBoard().addProfessor(game.getProfessors().get(COLOR.BLUE.ordinal()));
        game.getProfessors().get(COLOR.YELLOW.ordinal()).setOwner(game.getPlayers().get(0));
        game.getProfessors().get(COLOR.BLUE.ordinal()).setOwner(game.getPlayers().get(1));
        assertEquals(game.getPlayers().get(0).getTeam(),game.getIslands().get(0).get(0).getTower().getColor());//non inserisce
    }

    @Test//works
    public void TestAssignProfessor(){
        game.setCurrPlayer(game.getPlayers().get(0));//setta current player al primo
        for(int i=0;i<6;i++)
            game.getPlayers().get(0).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));//dining room del primo con 6 studenti rossi
        for(int i=0;i<4;i++)
            game.getPlayers().get(1).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));//dining room del 2 con 4 studenti rossi
        game.assignProfessor(COLOR.RED);
        assertEquals( game.getProfessors().get(COLOR.RED.ordinal()).getOwner(),game.getPlayers().get(0));
        game.setCurrPlayer(game.getPlayers().get(1));//setta player 2 come current
        for(int i=0;i<3;i++)
            game.getPlayers().get(1).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));//dining room del 2 con 7 studenti rossi
        game.assignProfessor(COLOR.RED);
        assertEquals( game.getProfessors().get(COLOR.RED.ordinal()).getOwner(),game.getPlayers().get(1));
    }
}