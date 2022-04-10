package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.DrawAssistantCardEvent;
import it.polimi.ingsw.common.events.MoveStudentToDiningEvent;
import it.polimi.ingsw.common.events.PlayerAccessEvent;
import it.polimi.ingsw.common.events.StartGameEvent;
import it.polimi.ingsw.common.exceptions.*;
import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.STATUS;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerExceptionsTest extends TestCase {
    private Controller controller;

    @BeforeEach
    public void setUp(){
        Game game =new BasicGame(new Player("Host"));
        this.controller = new Controller(game);
    }

    @Test
    public void PlayerAccessEventExceptionTest() {
        PlayerAccessEvent event = new PlayerAccessEvent(this,"Test1");
        PlayerAccessEvent finalEvent = event;
        assertDoesNotThrow(()-> controller.update(finalEvent));
        assertEquals("Test1",controller.getGame().getPlayers().get(1).getUsername());
        event = new PlayerAccessEvent(this,"Test2");
        PlayerAccessEvent finalEvent1 = event;
        assertDoesNotThrow(()-> controller.update(finalEvent1));
        assertEquals("Test2",controller.getGame().getPlayers().get(2).getUsername());
        //change phase to launch InvalidPhase
        controller.getGame().getStatusGame().setStatus(STATUS.ACTION);
        assertThrows(InvalidPhaseException.class, () -> controller.update(finalEvent1));
        //add another Test2 to launch InvalidUsername
        controller.getGame().getStatusGame().setStatus(STATUS.SETUP);
        event = new PlayerAccessEvent(this,"Test2");
        assertThrows(InvalidUserNameException.class, ()-> controller.update(finalEvent1));
        //add another player to launch TooManyPlayers
        assertThrows(TooManyPlayersException.class, ()-> controller.update(new PlayerAccessEvent(this, "Test3")));
    }

    @Test
    public void StartGameEventExceptionTest(){
        //launch TooLittlePlayers
        StartGameEvent event = new StartGameEvent(this,false);
        assertThrows(TooLittlePlayersException.class, ()->controller.update(event));
        //add 1 player and change phase to launch InvalidPhase
        controller.update( new PlayerAccessEvent(this,"Test1"));
        controller.getGame().getStatusGame().setStatus(STATUS.ACTION);
        assertThrows(InvalidPhaseException.class, () -> controller.update(event));
    }

    @Test
    public void DrawAssistantCardEventExceptionTest(){
        //TODO come fare controllo currPlayer
        //adding 3 players and starting the game
        controller.update(new PlayerAccessEvent(this, "Test1"));
        controller.update(new PlayerAccessEvent(this, "Test2"));
        controller.update(new StartGameEvent(this, false));
        //Launching OutOfBoundCardSelection with 0 and
        DrawAssistantCardEvent event3 = new DrawAssistantCardEvent(this, 0);
        assertThrows(OutOfBoundCardSelectionException.class, ()-> controller.update(event3));
        DrawAssistantCardEvent event4 = new DrawAssistantCardEvent(this, 11);
        assertThrows(OutOfBoundCardSelectionException.class, ()-> controller.update(event4));
        //Change phase to launch InvalidPhase
        controller.getGame().getStatusGame().setStatus(STATUS.SETUP);
        assertThrows(InvalidPhaseException.class, () -> controller.update(new DrawAssistantCardEvent(this, 2)));
        //Test AlreadyUsedCard with 2/3 players
        controller.getGame().getStatusGame().setStatus(STATUS.PLANNING);
        controller.update(new DrawAssistantCardEvent(this, 3));
        assertThrows(AlreadyUsedCardException.class, () -> controller.update(new DrawAssistantCardEvent(this, 3)));
        controller.update(new DrawAssistantCardEvent(this, 4));
        assertThrows(AlreadyUsedCardException.class, () -> controller.update(new DrawAssistantCardEvent(this, 4)));
        //Test NotAvailableCard
        AssistantCard card = controller.getGame().getCurrPlayer().getMyDeck().getCards().remove(0);
        assertThrows(NotAvailableCardException.class, () -> controller.update(new DrawAssistantCardEvent(this, card.getValue())));
    }

    @Test
    public void MoveStudentToDiningEventExceptionTest(){
        //adding 3 players and starting the game
        controller.update(new PlayerAccessEvent(this, "Test1"));
        controller.update(new PlayerAccessEvent(this, "Test2"));
        controller.update(new StartGameEvent(this, false));
        controller.update(new DrawAssistantCardEvent(this,1 ));
        controller.update(new DrawAssistantCardEvent(this,2 ));
        controller.update(new DrawAssistantCardEvent(this,3 ));

        Player currPlayer = controller.getGame().getCurrPlayer();
        COLOR color;
        MoveStudentToDiningEvent event;
        color = controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().get(0).getColor();
        COLOR finalColor = color;

        assertThrows(InvalidPlayerException.class, () ->controller.update(new MoveStudentToDiningEvent(new Player("WrongPlayer"), finalColor.ordinal())));

    }



    @Test
    public void MoveMotherEventTest(){
        //adding 3 players and starting the game
        controller.update(new PlayerAccessEvent(this, "Test1"));
        controller.update(new PlayerAccessEvent(this, "Test2"));
        controller.update(new StartGameEvent(this, false));
        controller.update(new DrawAssistantCardEvent(this,1 ));
        controller.update(new DrawAssistantCardEvent(this,2 ));
        controller.update(new DrawAssistantCardEvent(this,3 ));
        //
    }
}
