package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.charactersEvents.*;
import it.polimi.ingsw.common.exceptions.TooPoorException;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.characters.*;
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
        Game game = new BasicGame(new Player("Host"));
        this.controller = new Controller(game);
    }

    @Test
    public void PlayerAccessEventTest() {
        PlayerAccessEvent event = new PlayerAccessEvent(this, "Test1");
        controller.update(event);
        assertEquals("Test1", controller.getGame().getPlayers().get(1).getUsername());
        event = new PlayerAccessEvent(this, "Test2");
        controller.update(event);
        assertEquals("Test2", controller.getGame().getPlayers().get(2).getUsername());

    }

    @Test
    public void StartBasicGameEventTest() {
        this.PlayerAccessEventTest();
        StartGameEvent event = new StartGameEvent(this, false);
        controller.update(event);
        assertTrue(controller.getGame() instanceof BasicGame);
    }

    @Test
    public void StartExpertGameEventTest() {
        this.PlayerAccessEventTest();
        StartGameEvent event = new StartGameEvent(this, true);
        controller.update(event);
        assertTrue(controller.getGame() instanceof ConcreteExpertGame);
    }

    @Test
    public void DrawAssistantCardEventTest() {
        this.StartExpertGameEventTest();
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

    //TODO Correggere algoritmo,il 20% delle volte non sposta lo studente
    @Test
    public void UseCharacter1EventTest() {
        this.DrawAssistantCardEventTest();
        Character1 character1 = new Character1(controller.getGame());
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character1);
        COLOR color = character1.getStudents().get(2).getColor();
        int old_size = 0;
        for (Student student : controller.getGame().getIslands().get(3).get(0).getStudents())
            if (student.getColor() == color)
                old_size++;
        UseCharacter1Event event = new UseCharacter1Event(controller.getGame().getCurrPlayer(), 2, 3);
        controller.update(event);
        int new_size = 0;
        for (Student student : controller.getGame().getIslands().get(3).get(0).getStudents())
            if (student.getColor() == color)
                new_size++;
        assertEquals(old_size+1,new_size);
    }

    @Test
    public void UseCharacter2EventTest(){
        this.DrawAssistantCardEventTest();
        Character2 character2 = new Character2();
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character2);
        UseCharacter2Event event = new UseCharacter2Event(controller.getGame().getCurrPlayer());
        controller.update(event);
        assertTrue(controller.getGame() instanceof GameMode2);
        ChooseCloudEvent event1 = new ChooseCloudEvent(controller.getGame().getCurrPlayer(), 0);
        controller.update(event1);
        assertFalse(controller.getGame() instanceof GameMode2);
    }

    @Test
    public void UseCharacter3EventTest(){
        this.DrawAssistantCardEventTest();
        Character3 character3 = new Character3();
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character3);
        for(int i=0;i<4;i++) {
            controller.getGame().getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
            controller.getGame().getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        }
        controller.getGame().assignProfessor(COLOR.RED);
        UseCharacter3Event event = new UseCharacter3Event(controller.getGame().getCurrPlayer(),0);
        controller.update(event);
        assertEquals(controller.getGame().getIslands().get(0).get(0).getTower().getColor(),controller.getGame().getCurrPlayer().getTeam());
    }

    @Test
    public void UseCharacter4EventTest(){
        this.DrawAssistantCardEventTest();
        Character4 character4 = new Character4();
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character4);
        int index = controller.getGame().getPlayers().indexOf(controller.getGame().getCurrPlayer());
        int old_steps = controller.getGame().getCurrPlayer().getChosenCard().getSteps();
        UseCharacter4Event event = new UseCharacter4Event(controller.getGame().getCurrPlayer(),index);
        controller.update(event);
        int new_steps = controller.getGame().getCurrPlayer().getChosenCard().getSteps();
        assertEquals(old_steps+2,new_steps);
    }

    @Test
    public void UseCharacter5EventTest(){
        this.DrawAssistantCardEventTest();
        Character5 character5 = new Character5();
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character5);
        assertFalse(controller.getGame().getIslands().get(0).get(0).isNoEntry());
        UseCharacter5Event event = new UseCharacter5Event(controller.getGame().getCurrPlayer(),0);
        controller.update(event);
        assertTrue(controller.getGame().getIslands().get(0).get(0).isNoEntry());
    }

    @Test
    public void UseCharacter6EventTest(){
        this.DrawAssistantCardEventTest();
        Character6 character6 = new Character6();
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character6);
        UseCharacter6Event event = new UseCharacter6Event(controller.getGame().getCurrPlayer());
        controller.update(event);
        assertTrue(controller.getGame() instanceof GameMode6);
        ChooseCloudEvent event1 = new ChooseCloudEvent(controller.getGame().getCurrPlayer(), 0);
        controller.update(event1);
        assertFalse(controller.getGame() instanceof GameMode6);
    }

//TODO Correggere algoritmo,il 20% delle volte non sposta lo studente
    @Test
    public void UseCharacter7EventTest(){
        this.DrawAssistantCardEventTest();
        Character7 character7 = new Character7(controller.getGame());
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character7);
        ArrayList<Integer> colors = new ArrayList<>();
        Student studentEntrance = controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().get(0);
        colors.add(studentEntrance.getColor().ordinal());
        Student studentCard = character7.getStudents().get(0);
        colors.add(studentCard.getColor().ordinal());
        assertFalse(controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().contains(studentCard));
        assertFalse(character7.getStudents().contains(studentEntrance));
        UseCharacter7Event event = new UseCharacter7Event(controller.getGame().getCurrPlayer(),colors);
        controller.update(event);
        assertTrue(controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().contains(studentCard));
        assertTrue(character7.getStudents().contains(studentEntrance));
    }

    @Test
    public void UseCharacter8EventTest(){
        this.DrawAssistantCardEventTest();
        Character8 character8 = new Character8();
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character8);
        UseCharacter8Event event = new UseCharacter8Event(controller.getGame().getCurrPlayer());
        controller.update(event);
        assertTrue(controller.getGame() instanceof GameMode8);
        ChooseCloudEvent event1 = new ChooseCloudEvent(controller.getGame().getCurrPlayer(), 0);
        controller.update(event1);
        assertFalse(controller.getGame() instanceof GameMode8);
    }

    @Test
    public void UseCharacter9EventTest(){
        this.DrawAssistantCardEventTest();
        Character9 character9 = new Character9();
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character9);
        UseCharacter9Event event = new UseCharacter9Event(controller.getGame().getCurrPlayer(),0);
        controller.update(event);
        assertTrue(controller.getGame() instanceof GameMode9);
        ChooseCloudEvent event1 = new ChooseCloudEvent(controller.getGame().getCurrPlayer(), 0);
        controller.update(event1);
        assertFalse(controller.getGame() instanceof GameMode9);
    }

    @Test
    public void UseCharacter10EventTest(){
        this.DrawAssistantCardEventTest();
        Character10 character10 = new Character10();
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character10);
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

    @Test
    public void UseCharacter11EventTest(){
        this.DrawAssistantCardEventTest();
        Character11 character11 = new Character11(controller.getGame());
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character11);
        int rnd= new Random().nextInt(3);
        Student student = character11.getStudents().get(rnd);
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
        assertEquals(4,character11.getStudents().size());
    }

    @Test
    public void UseCharacter12EventTest(){
        int old_size=0,new_size=0;
        this.DrawAssistantCardEventTest();
        Character12 character12 = new Character12();
        controller.getGame().getCurrPlayer().setCoins(6);
        ((ConcreteExpertGame) controller.getGame()).getCharacters().add(character12);
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