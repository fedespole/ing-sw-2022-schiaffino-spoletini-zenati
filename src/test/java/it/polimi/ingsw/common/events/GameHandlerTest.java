package it.polimi.ingsw.common.events;

import it.polimi.ingsw.common.events.fromClientEvents.PlayerAccessEvent;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.network.server.RemoteView;
import it.polimi.ingsw.view.View;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class GameHandlerTest extends TestCase {

    @Test
    public void callsTest()  {
      /*  Controller controller= new Controller(new BasicGame());
        View view1 = new RemoteView();
        assertEquals(0,controller.getGame().getPlayers().size());
        GameEvent event = new PlayerAccessEvent(view1,"First Player", client);
        GameHandler.calls(event);
        assertEquals(1,controller.getGame().getPlayers().size()); */
    }

}