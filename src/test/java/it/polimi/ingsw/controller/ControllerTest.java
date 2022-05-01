package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.fromClientEvents.*;
import it.polimi.ingsw.common.events.fromClientEvents.charactersEvents.*;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.characters.*;
import it.polimi.ingsw.model.expertgame.characters.Character;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode2;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode6;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode8;
import it.polimi.ingsw.model.expertgame.gamemodes.GameMode9;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerTest {

    private Controller controller;

    @BeforeEach
    public void setUp() {
        Game game = new BasicGame();
        this.controller = new Controller(game);
    }
    @Test
    public void FirstPlayerAccessEventTest(){
        PlayerAccessEvent event = new PlayerAccessEvent(this, "Host");
        controller.update(event);
        SelectedGameSetUpEvent event1= new SelectedGameSetUpEvent(this,3,false);
        controller.update(event1);
        assertTrue(controller.getGame() instanceof BasicGame);
        assertEquals(3,controller.getGame().getNumPlayers());
    }
    @Test
    public void ExpertGameFirstPlayerAccessEventTest(){
        PlayerAccessEvent event = new PlayerAccessEvent(this, "Host");
        controller.update(event);
        SelectedGameSetUpEvent event1= new SelectedGameSetUpEvent(this,3,true);
        controller.update(event1);
        assertTrue(controller.getGame() instanceof ConcreteExpertGame);
        assertEquals(3,controller.getGame().getNumPlayers());
    }
    @Test
    public void PlayerAccessEventTest() {
        this.ExpertGameFirstPlayerAccessEventTest();
        PlayerAccessEvent event = new PlayerAccessEvent(this, "Test1");
        controller.update(event);
        assertEquals("Test1", controller.getGame().getPlayers().get(1).getUsername());
        event = new PlayerAccessEvent(this, "Test2");
        controller.update(event);
        assertEquals("Test2", controller.getGame().getPlayers().get(2).getUsername());

    }

    @Test
    public void DrawAssistantCardEventTest() {
        this.PlayerAccessEventTest();
        DrawAssistantCardEvent event = new DrawAssistantCardEvent(this, 3);
        Player firstPlayer = controller.getGame().getCurrPlayer();
        controller.update(event);
        assertEquals(3, firstPlayer.getChosenCard().getValue());
        Player second_player = controller.getGame().getCurrPlayer();
        event = new DrawAssistantCardEvent(this, 5);
        controller.update(event);
        assertEquals(5, second_player.getChosenCard().getValue());
        Player third_player = controller.getGame().getCurrPlayer();
        event = new DrawAssistantCardEvent(this, 8);
        controller.update(event);
        assertEquals(8, third_player.getChosenCard().getValue());
        assertEquals(STATUS.ACTION, controller.getGame().getStatusGame().getStatus());
    }

    @Test
    public void MoveStudentToDiningEventTest() {//test for one player only
        this.DrawAssistantCardEventTest();
        Player currPlayer = controller.getGame().getCurrPlayer();
        COLOR color;
        MoveStudentToDiningEvent event;
        int size;
        for (int j = 0; j < 2; j++) {
            color = controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().get(0).getColor();
            event = new MoveStudentToDiningEvent(currPlayer, color.ordinal());
            size = controller.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size();
            controller.update(event);
            assertEquals(size + 1, currPlayer.getMySchoolBoard().getDiningRoom()[color.ordinal()].size());
        }
        assertEquals(STATUS.ACTION, controller.getGame().getStatusGame().getStatus());
        assertEquals(7, currPlayer.getMySchoolBoard().getEntrance().size());
    }

    @Test
    public void MoveStudentToIslandEventTest() {
        this.MoveStudentToDiningEventTest();
        Player currPlayer = controller.getGame().getCurrPlayer();
        Student student;
        MoveStudentToIslandEvent event;
        for (int i = 0; i < 2; i++) {
            student = controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().get(0);
            event = new MoveStudentToIslandEvent(currPlayer, student.getColor().ordinal(), 3);
            controller.update(event);
            assertTrue(controller.getGame().getIslands().get(3).get(0).getStudents().contains(student));
        }
        assertEquals(5, currPlayer.getMySchoolBoard().getEntrance().size());
    }

    @Test
    public void MoveMotherEventTest() {
        this.MoveStudentToIslandEventTest();
        Player currPlayer = controller.getGame().getCurrPlayer();
        int oldIsland = controller.getGame().getMotherNature();
        MoveMotherEvent event = new MoveMotherEvent(currPlayer, 1);
        controller.update(event);
        assertEquals(controller.getGame().getMotherNature(), oldIsland + 1);
    }

    @Test
    public void ChooseCloudEventTest() {
        this.MoveMotherEventTest();
        Player currPlayer = controller.getGame().getCurrPlayer();
        int cloud = 0;
        for (int i = 0; i < 3; i++) {
            if (controller.getGame().getClouds().get(i).getStudents().size() != 0) {
                cloud = i;
                break;
            }
        }
        ChooseCloudEvent event = new ChooseCloudEvent(currPlayer, cloud);
        controller.update(event);
        assertEquals(9, currPlayer.getMySchoolBoard().getEntrance().size());
        assertNotEquals(currPlayer, controller.getGame().getCurrPlayer());
    }

    @Test
    public void UseCharacter1EventTest() {
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character1) {
                int rnd = new Random().nextInt(4);
                COLOR color = ((Character1)character).getStudents().get(rnd).getColor();
                int old_size = 0;
                for (Student student : controller.getGame().getIslands().get(3).get(0).getStudents())
                    if (student.getColor() == color)
                        old_size++;
                UseCharacter1Event event = new UseCharacter1Event(controller.getGame().getCurrPlayer(), rnd, 3);
                controller.update(event);
                int new_size = 0;
                for (Student student : controller.getGame().getIslands().get(3).get(0).getStudents())
                    if (student.getColor() == color)
                        new_size++;
                assertEquals(old_size+1,new_size);
            }
        }

    }

    @Test
    public void UseCharacter2EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character2) {
                controller.getGame().getCurrPlayer().setCoins(6);
                UseCharacter2Event event = new UseCharacter2Event(controller.getGame().getCurrPlayer());
                controller.update(event);
                assertTrue(controller.getGame() instanceof GameMode2);
                ChooseCloudEvent event1 = new ChooseCloudEvent(controller.getGame().getCurrPlayer(), 0);
                controller.update(event1);
                assertFalse(controller.getGame() instanceof GameMode2);
            }
        }

    }

    @Test
    public void UseCharacter3EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character3) {
                controller.getGame().getCurrPlayer().setCoins(6);
                for(int i=0;i<4;i++) {
                    controller.getGame().getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
                    controller.getGame().getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
                }
                controller.getGame().assignProfessor(COLOR.RED);
                UseCharacter3Event event = new UseCharacter3Event(controller.getGame().getCurrPlayer(),0);
                controller.update(event);
                assertEquals(controller.getGame().getIslands().get(0).get(0).getTower().getColor(),controller.getGame().getCurrPlayer().getTeam());
            }
        }
    }

    @Test
    public void UseCharacter4EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character4) {
                controller.getGame().getCurrPlayer().setCoins(6);
                int index = controller.getGame().getPlayers().indexOf(controller.getGame().getCurrPlayer());
                int old_steps = controller.getGame().getCurrPlayer().getChosenCard().getSteps();
                UseCharacter4Event event = new UseCharacter4Event(controller.getGame().getCurrPlayer(),index);
                controller.update(event);
                int new_steps = controller.getGame().getCurrPlayer().getChosenCard().getSteps();
                assertEquals(old_steps+2,new_steps);
            }
        }
    }

    @Test
    public void UseCharacter5EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character5) {
                controller.getGame().getCurrPlayer().setCoins(6);
                assertFalse(controller.getGame().getIslands().get(0).get(0).isNoEntry());
                UseCharacter5Event event = new UseCharacter5Event(controller.getGame().getCurrPlayer(),0);
                controller.update(event);
                assertTrue(controller.getGame().getIslands().get(0).get(0).isNoEntry());
            }
        }
    }

    @Test
    public void UseCharacter6EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character6) {
                controller.getGame().getCurrPlayer().setCoins(6);
                UseCharacter6Event event = new UseCharacter6Event(controller.getGame().getCurrPlayer());
                controller.update(event);
                assertTrue(controller.getGame() instanceof GameMode6);
                ChooseCloudEvent event1 = new ChooseCloudEvent(controller.getGame().getCurrPlayer(), 0);
                controller.update(event1);
                assertFalse(controller.getGame() instanceof GameMode6);
            }
        }
    }

    @Test
    public void UseCharacter7EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character7) {
                controller.getGame().getCurrPlayer().setCoins(6);
                ArrayList<Integer> colors = new ArrayList<>();
                Student studentEntrance = controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().get(0);
                colors.add(studentEntrance.getColor().ordinal());
                Student studentCard = ((Character7)character).getStudents().get(0);
                colors.add(studentCard.getColor().ordinal());
                assertFalse(controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().contains(studentCard));
                assertFalse(((Character7)character).getStudents().contains(studentEntrance));
                UseCharacter7Event event = new UseCharacter7Event(controller.getGame().getCurrPlayer(),colors);
                controller.update(event);
                assertTrue(controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().contains(studentCard));
                assertTrue(((Character7)character).getStudents().contains(studentEntrance));
            }
        }

    }

    @Test
    public void UseCharacter8EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character8) {
                controller.getGame().getCurrPlayer().setCoins(6);
                UseCharacter8Event event = new UseCharacter8Event(controller.getGame().getCurrPlayer());
                controller.update(event);
                assertTrue(controller.getGame() instanceof GameMode8);
                ChooseCloudEvent event1 = new ChooseCloudEvent(controller.getGame().getCurrPlayer(), 0);
                controller.update(event1);
                assertFalse(controller.getGame() instanceof GameMode8);
            }
        }
    }

    @Test
    public void UseCharacter9EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character9) {
                controller.getGame().getCurrPlayer().setCoins(6);
                UseCharacter9Event event = new UseCharacter9Event(controller.getGame().getCurrPlayer(),0);
                controller.update(event);
                assertTrue(controller.getGame() instanceof GameMode9);
                ChooseCloudEvent event1 = new ChooseCloudEvent(controller.getGame().getCurrPlayer(), 0);
                controller.update(event1);
                assertFalse(controller.getGame() instanceof GameMode9);
            }
        }
    }

    @Test
    public void UseCharacter10EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character10) {
                controller.getGame().getCurrPlayer().setCoins(6);
                Student student_1 = new Student(COLOR.RED);
                Student student_2 = new Student(COLOR.YELLOW);
                controller.getGame().getCurrPlayer().getMySchoolBoard().addStudentToDining(student_1);
                controller.getGame().getCurrPlayer().getMySchoolBoard().addStudentToEntrance(student_2);
                ArrayList<Integer> students = new ArrayList<>();
                students.add(student_1.getColor().ordinal());
                students.add(student_2.getColor().ordinal());
                assertEquals(0, controller.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[COLOR.YELLOW.ordinal()].size());
                UseCharacter10Event event = new UseCharacter10Event(controller.getGame().getCurrPlayer(),students);
                controller.update(event);
                assertTrue(this.controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().contains(student_1));
                assertEquals(1, controller.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[COLOR.YELLOW.ordinal()].size());
            }
        }
    }

    @Test
    public void UseCharacter11EventTest(){
        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character11) {
                controller.getGame().getCurrPlayer().setCoins(6);
                int rnd= new Random().nextInt(3);
                Student student = ((Character11)character).getStudents().get(rnd);
                UseCharacter11Event event = new UseCharacter11Event(controller.getGame().getCurrPlayer(),rnd);
                controller.update(event);
                for(COLOR color:COLOR.values()){
                    if(color == student.getColor()){
                        assertEquals(1,controller.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size());
                    }
                    else{
                        assertEquals(0,controller.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[color.ordinal()].size());
                    }
                }
                assertEquals(4,((Character11)character).getStudents().size());
            }
        }
    }

    @Test
    public void UseCharacter12EventTest(){

        this.DrawAssistantCardEventTest();

        for (Character character : ((ConcreteExpertGame)controller.getGame()).getCharacters()) {
            if (character instanceof Character12) {

                int old_size=0,new_size=0;
                controller.getGame().getCurrPlayer().setCoins(6);
                for(int i=0;i<3;i++){
                    controller.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].add(new Student(COLOR.PINK));
                }
                old_size=controller.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size();
                UseCharacter12Event event = new UseCharacter12Event(controller.getGame().getCurrPlayer(),COLOR.PINK.ordinal());
                controller.update(event);
                new_size=controller.getGame().getCurrPlayer().getMySchoolBoard().getDiningRoom()[COLOR.PINK.ordinal()].size();
                assertEquals(old_size-3,new_size);

            }
        }
    }
}