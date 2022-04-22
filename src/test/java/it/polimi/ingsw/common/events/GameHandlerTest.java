package it.polimi.ingsw.common.events;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class GameHandlerTest extends TestCase {

    @Test
    public void callsTest(){
        Controller controller= new Controller(new BasicGame(new Player("Host")));
        View view1 = new RemoteView();
        View view2 = new RemoteView();
        GameHandler.addEventListener(controller);
        GameHandler.addEventListener(view1);
        GameHandler.addEventListener(view2);
        assertEquals(1,controller.getGame().getPlayers().size());
        PlayerAccessEvent event = new PlayerAccessEvent(view1,"Second Player");
        GameHandler.calls(event);
        assertEquals(2,controller.getGame().getPlayers().size());
    }

}