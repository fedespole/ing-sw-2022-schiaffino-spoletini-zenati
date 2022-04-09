package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.DrawAssistantCardEvent;
import it.polimi.ingsw.common.events.PlayerAccessEvent;
import it.polimi.ingsw.common.events.StartGameEvent;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ControllerTest {

    private Controller controller;

    @BeforeEach
    public void setUp(){
        Game game =new BasicGame(new Player("Host"));
        this.controller = new Controller(game);
    }

    @Test
    public void PlayerAccessEventTest() {
        PlayerAccessEvent event = new PlayerAccessEvent(this,"Test1");
        controller.update(event);
        assertEquals("Test1",controller.getGame().getPlayers().get(1).getUsername());
        event = new PlayerAccessEvent(this,"Test2");
        controller.update(event);
        assertEquals("Test2",controller.getGame().getPlayers().get(2).getUsername());

    }

    @Test
    public void StartBasicGameEventTest(){
        this.PlayerAccessEventTest();
        StartGameEvent event= new StartGameEvent(this,false);
        controller.update(event);
        assertTrue(controller.getGame() instanceof BasicGame);
    }

    @Test
    public void StartExpertGameEventTest(){
        this.PlayerAccessEventTest();
        StartGameEvent event= new StartGameEvent(this,true);
        controller.update(event);
        assertTrue(controller.getGame() instanceof ConcreteExpertGame);
    }
    @Test
    public void DrawAssistantCardEventTest(){
        this.StartBasicGameEventTest();
        DrawAssistantCardEvent event = new DrawAssistantCardEvent(this,3);
        Player firstPlayer = controller.getGame().getCurrPlayer();
        controller.update(event);
        assertEquals(3,firstPlayer.getChosenCard().getValue());
        Player second_player = controller.getGame().getCurrPlayer();
        event = new DrawAssistantCardEvent(this,5);
        controller.update(event);
        assertEquals(5,second_player.getChosenCard().getValue());
    }
}