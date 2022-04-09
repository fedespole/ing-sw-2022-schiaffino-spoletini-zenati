package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.PlayerAccessEvent;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ControllerTest {

    private Game game;
    private Controller controller;

    @BeforeEach
    public void setUp(){
        game =new BasicGame(new Player("Host"));
        this.controller = new Controller(game);
    }

    @Test
    public void PlayerAccessEventTest() {
        PlayerAccessEvent event = new PlayerAccessEvent(this,"Test1");
        controller.update(event);
        assertEquals("Test1",game.getPlayers().get(1).getUsername());
        event = new PlayerAccessEvent(this,"Test2");
        controller.update(event);
        assertEquals("Test2",game.getPlayers().get(2).getUsername());

    }
}