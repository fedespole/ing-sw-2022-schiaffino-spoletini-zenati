package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.charactersEvents.UseCharacter1Event;
import it.polimi.ingsw.common.exceptions.TooPoorException;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import it.polimi.ingsw.model.expertgame.characters.Character1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    public void UseCharacter1Event() {
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
}