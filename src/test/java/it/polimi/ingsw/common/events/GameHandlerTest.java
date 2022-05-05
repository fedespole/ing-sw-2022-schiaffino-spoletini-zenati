package it.polimi.ingsw.common.events;

import it.polimi.ingsw.common.events.fromClientEvents.PlayerAccessEvent;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.network.server.RemoteView;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.View;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

public class GameHandlerTest extends TestCase {

    @Test
    public void callsTest() throws IOException {
        Controller controller= new Controller(new BasicGame());
        View view1 = new RemoteView();
        assertEquals(0,controller.getGame().getPlayers().size());
        GameEvent event = new PlayerAccessEvent(view1,"First Player", null);
        GameHandler.calls(event);
        assertEquals(1,controller.getGame().getPlayers().size());
    }

}