package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.fromClientEvents.*;
import it.polimi.ingsw.common.exceptions.*;
import it.polimi.ingsw.model.basicgame.*;
import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerExceptionsTest extends TestCase {     // No more needed as are thrown as events and view handles them
 /*
    private Controller controller;

    @BeforeEach
    public void setUp(){
        Game game =new BasicGame();
        game.getPlayers().add(new Player("Host"));
        this.controller = new Controller(game);
    }


    @Test
    public void PlayerAccessEventExceptionTest() {
        PlayerAccessEvent event = new PlayerAccessEvent(this,"Test1", null);
        PlayerAccessEvent finalEvent = event;
        assertDoesNotThrow(()-> controller.update(finalEvent));
        assertEquals("Test1",controller.getGame().getPlayers().get(1).getUsername());
        event = new PlayerAccessEvent(this,"Test2", null);
        PlayerAccessEvent finalEvent1 = event;
        assertDoesNotThrow(()-> controller.update(finalEvent1));
        assertEquals("Test2",controller.getGame().getPlayers().get(2).getUsername());
        //change phase to launch InvalidPhase
        controller.getGame().getStatusGame().setStatus(STATUS.PLANNING);
        assertThrows(InvalidPhaseException.class, () -> controller.update(finalEvent1));
        //add another Test2 to launch InvalidUsername
        controller.getGame().getStatusGame().setStatus(STATUS.SETUP);
        assertThrows(InvalidUserNameException.class, ()-> controller.update(finalEvent1));
    }

    @Test
    public void DrawAssistantCardEventExceptionTest(){
        //adding 3 players and starting the game
        controller.getGame().setNumPlayers(3);
        controller.update(new PlayerAccessEvent(this, "Test1", null));
        controller.update(new PlayerAccessEvent(this, "Test2", null));
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
        controller.getGame().setNumPlayers(3);
        controller.update(new PlayerAccessEvent(this, "Test1", null));
        controller.update(new PlayerAccessEvent(this, "Test2", null));

        controller.update(new DrawAssistantCardEvent(this,1 ));
        controller.update(new DrawAssistantCardEvent(this,2 ));
        controller.update(new DrawAssistantCardEvent(this,3 ));
        //Just accessory variables
        Player currPlayer = controller.getGame().getCurrPlayer();
        COLOR color;
        MoveStudentToDiningEvent event;
        color = controller.getGame().getCurrPlayer().getMySchoolBoard().getEntrance().get(0).getColor();
        COLOR finalColor = color;
        //tests InvalidPlayer
        assertThrows(InvalidPlayerException.class, () ->controller.update(new MoveStudentToDiningEvent(new Player("WrongPlayer"), finalColor.ordinal())));
        currPlayer.getMySchoolBoard().getEntrance().remove(0);
        currPlayer.getMySchoolBoard().getEntrance().add(new Student(COLOR.PINK));
        //tests ArrayIndexOutOfBound
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> controller.update(new MoveStudentToDiningEvent(currPlayer, 5)));
        //tests NoMoreSpace
        for(int i=0;currPlayer.getMySchoolBoard().getDiningRoom()[3].size() < 10; i++){
            assertDoesNotThrow(()-> controller.getGame().getCurrPlayer().getMySchoolBoard().addStudentToDining(new Student(COLOR.PINK)));
        }
        assertThrows(NoMoreSpaceException.class, ()-> controller.update(new MoveStudentToDiningEvent(currPlayer, 3)));
        //tests StudentNotPresent
        currPlayer.getMySchoolBoard().getEntrance().removeAll(currPlayer.getMySchoolBoard().getEntrance());
        controller.getGame().getCurrPlayer().getMySchoolBoard().removeStudentFromDiningRoom(COLOR.PINK);
        assertThrows(StudentNotPresentException.class, () -> controller.update(new MoveStudentToDiningEvent(currPlayer, 3)));
    }

    @Test
    public void MoveStudentToIslandEventTest(){
        //adding 3 players and starting the game
        controller.getGame().setNumPlayers(3);
        controller.update(new PlayerAccessEvent(this, "Test1", null));
        controller.update(new PlayerAccessEvent(this, "Test2", null));

        controller.update(new DrawAssistantCardEvent(this,1 ));
        controller.update(new DrawAssistantCardEvent(this,2 ));
        controller.update(new DrawAssistantCardEvent(this,3 ));
        Player currPlayer = controller.getGame().getCurrPlayer();
        //tests InvalidIsland
        currPlayer.getMySchoolBoard().getEntrance().remove(0);
        currPlayer.getMySchoolBoard().getEntrance().add(new Student(COLOR.GREEN));
        assertThrows(InvalidIslandIndexException.class,() -> controller.update(new MoveStudentToIslandEvent(currPlayer,0, -1 )));
        assertThrows(InvalidIslandIndexException.class,() -> controller.update(new MoveStudentToIslandEvent(currPlayer,0, controller.getGame().getIslands().size()+1 )));
        //tests InvalidColor
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> controller.update(new MoveStudentToIslandEvent(currPlayer, 5, 0)));
        //tests StudentNotPresent
        currPlayer.getMySchoolBoard().getEntrance().removeAll(currPlayer.getMySchoolBoard().getEntrance());
        assertThrows(StudentNotPresentException.class, () -> controller.update(new MoveStudentToIslandEvent(currPlayer, 0,0)));

    }

    @Test
    public void MoveMotherEventTest(){
        //adding 3 players and starting the game
        controller.getGame().setNumPlayers(3);
        controller.update(new PlayerAccessEvent(this, "Test1", null));
        controller.update(new PlayerAccessEvent(this, "Test2", null));

        controller.update(new DrawAssistantCardEvent(this,1 ));
        controller.update(new DrawAssistantCardEvent(this,2 ));
        controller.update(new DrawAssistantCardEvent(this,3 ));
        Player currPlayer = controller.getGame().getCurrPlayer();
        //tests InvalidSteps
        controller.getGame().getStatusGame().setStatus(STATUS.ACTION_MOVEMN);
        assertThrows(InvalidStepsException.class,()-> controller.update(new MoveMotherEvent(currPlayer, -1)));
        assertThrows(InvalidStepsException.class, ()-> controller.update(new MoveMotherEvent(currPlayer, currPlayer.getChosenCard().getSteps()+1)));
    }

        @Test
        public void ChooseCloudTest(){
            //adding 3 players and starting the game
            controller.getGame().setNumPlayers(3);
            controller.update(new PlayerAccessEvent(this, "Test1", null));
            controller.update(new PlayerAccessEvent(this, "Test2", null));

            controller.update(new DrawAssistantCardEvent(this,1 ));
            controller.update(new DrawAssistantCardEvent(this,2 ));
            controller.update(new DrawAssistantCardEvent(this,3 ));
            Player currPlayer = controller.getGame().getCurrPlayer();
            controller.getGame().getStatusGame().setStatus(STATUS.ACTION_MOVEMN);
            controller.update(new MoveMotherEvent(currPlayer, 1));
        }
*/
}
