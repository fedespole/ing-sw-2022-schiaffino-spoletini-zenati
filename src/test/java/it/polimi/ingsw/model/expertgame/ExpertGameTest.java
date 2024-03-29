package it.polimi.ingsw.model.expertgame;

import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ExpertGameTest extends TestCase {
        Game game;

        @BeforeEach
        public void setUp(){
            Random rand = new Random(); //instance of random class
            int int_random = rand.nextInt(2)+1;// is 1 or 2
            game = new BasicGame();
            game.getPlayers().add(new Player("Host"));
            game = new ConcreteExpertGame(game);
            for(int i=0;i<int_random;i++){
                game.getPlayers().add(new Player("Test"));
            }
            game.setUp();
        }

        @Test
        public void TestFillCloud(){
            int size= game.getClouds().size();
            switch(size){
                case 2:
                    assertEquals(3,game.getClouds().get(0).getStudents().size());
                    break;
                case 3:
                    assertEquals(4,game.getClouds().get(0).getStudents().size());
                    break;
            }
        }

        @Test
        public void TestComputeInfluence(){
            //check without towers in the islands
            assertEquals(TEAM.WHITE, game.getPlayers().get(0).getTeam());
            assertEquals(TEAM.GREY,game.getPlayers().get(1).getTeam());
            game.setMotherNature(0);
            if(game.getNumPlayers()==3)
                assertEquals(TEAM.BLACK,game.getPlayers().get(2).getTeam());
            for(int i=0;i<4;i++) {
                game.getIslands().get(0).get(0).getStudents().add(new Student(COLOR.YELLOW));
            }
            for(int i=0;i<6;i++){
                game.getIslands().get(0).get(0).getStudents().add(new Student(COLOR.BLUE));
            }
            game.getPlayers().get(0).getMySchoolBoard().addProfessor(game.getProfessors().get(COLOR.YELLOW.ordinal()));
            game.getPlayers().get(1).getMySchoolBoard().addProfessor(game.getProfessors().get(COLOR.BLUE.ordinal()));
            game.getProfessors().get(COLOR.YELLOW.ordinal()).setOwner(game.getPlayers().get(0));
            game.getProfessors().get(COLOR.BLUE.ordinal()).setOwner(game.getPlayers().get(1));
            game.computeInfluence();
            assertEquals(game.getPlayers().get(1).getTeam(),game.getIslands().get(0).get(0).getTower().getColor());
            int towers_0 = game.getPlayers().get(0).getMySchoolBoard().getTowers().size();
            int towers_1 = game.getPlayers().get(1).getMySchoolBoard().getTowers().size();
            //check with towers
            for(int i=0;i<6;i++) {
                game.getIslands().get(0).get(0).getStudents().add(new Student(COLOR.YELLOW));
            }
            game.computeInfluence();
            assertEquals(towers_0-1, game.getPlayers().get(0).getMySchoolBoard().getTowers().size());
            assertEquals(towers_1+1, game.getPlayers().get(1).getMySchoolBoard().getTowers().size());
        }

        @Test//works
        public void TestAssignProfessor(){
            game.setCurrPlayer(game.getPlayers().get(0));
            for(int i=0;i<6;i++)
                game.getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));//dining room del currPlayer con 6 studenti rossi
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

        @Test
        public void TestMergeIslands(){
            game.setMotherNature(0);
            game.getIslands().get(0).get(0).setTower(new Tower(TEAM.WHITE));
            game.getIslands().get(1).get(0).setTower(new Tower(TEAM.WHITE));
            assertEquals(12,game.getIslands().size());
            game.mergeIslands();
            assertEquals(11,game.getIslands().size());
            assertEquals(2,game.getIslands().get(0).size());
            game.getIslands().get(game.getIslands().size()-1).get(0).setTower(new Tower(TEAM.WHITE));
            game.mergeIslands();
            assertEquals(10,game.getIslands().size());
            assertEquals(3,game.getIslands().get(0).size());
        }
    }