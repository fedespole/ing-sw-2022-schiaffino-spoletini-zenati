package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class BasicGameTest extends TestCase {
    Game game;

    @BeforeEach
    public void setUp(){
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(2)+2;// is 2 or 3
        game =new BasicGame();
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test"));
        }
        game.setUp();
    }

    @Test
     public void TestFillClouds(){
        for(Cloud cloud:game.getClouds()){
            switch(game.getNumPlayers()) {
                case 2:
                    assertEquals(3, cloud.getStudents().size());
                    break;
                case 3:
                    assertEquals(4, cloud.getStudents().size());
                    break;
            }
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
        for(int i=0;i<6;i++)
            game.getPlayers().get(0).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));//dining room del primo con 6 studenti rossi
        for(int i=0;i<4;i++)
            game.getPlayers().get(1).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));//dining room del 2 con 4 studenti rossi
        game.assignProfessor(COLOR.RED);
        assertEquals( game.getProfessors().get(COLOR.RED.ordinal()).getOwner(),game.getCurrPlayer());
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

    @Test
    public void TestMoveStudentsFromCloud(){
        ArrayList<Student> studentsCloud = new ArrayList<>();
        for(Student student:game.getClouds().get(0).getStudents()){
            studentsCloud.add(student);
        }
        game.moveStudentsFromCloud(game.getClouds().get(0));
        assertEquals(true,game.getCurrPlayer().getMySchoolBoard().getEntrance().containsAll(studentsCloud));
    }

    @Test
    public void TestPhaseFlow() {//to check if the automized action of the game work
        if (game.getNumPlayers() == 3) {
            assertEquals(3,game.getClouds().size());
            assertEquals(STATUS.PLANNING, game.getStatusGame().getStatus());
            for (Cloud cloud : game.getClouds()) {
                assertNotSame(0, cloud.getStudents().size());
            }
            assertEquals(0, game.getStatusGame().getOrder().size());
            game.setCurrPlayer(game.getPlayers().get(0));
            game.chooseCard(6);    //first player chooses card 6
            assertEquals(1, game.getPlayers().indexOf(game.getCurrPlayer()));
            assertEquals(1, game.getStatusGame().getOrder().size());
            game.chooseCard(5);    //second player chooses card 5
            assertEquals(2, game.getStatusGame().getOrder().size());
            assertEquals(2, game.getPlayers().indexOf(game.getCurrPlayer()));
            game.chooseCard(4);    //third player chooses card 4
            assertEquals(3, game.getStatusGame().getOrder().size());
            assertEquals(STATUS.ACTION_MOVESTUD, game.getStatusGame().getStatus());
            assertEquals(2, game.getPlayers().indexOf(game.getCurrPlayer()));
            game.getStatusGame().setStatus(STATUS.ACTION_MOVEMN);
            game.moveMother(1);

            game.chooseCloud(2);
            assertEquals(0, game.getClouds().get(2).getStudents().size());
            assertEquals(1, game.getPlayers().indexOf(game.getCurrPlayer()));
            game.moveMother(2);
            game.chooseCloud(1);
            assertEquals(0, game.getClouds().get(1).getStudents().size());
            assertEquals(0, game.getPlayers().indexOf(game.getCurrPlayer()));
            game.moveMother(2);
            game.chooseCloud(0);
            for(Cloud cloud: game.getClouds()){
                assertEquals(4,cloud.getStudents().size());
            }
            assertEquals(STATUS.PLANNING, game.getStatusGame().getStatus());
            assertEquals(game.getCurrPlayer(),game.getPlayers().get(2));
        }
    }
}