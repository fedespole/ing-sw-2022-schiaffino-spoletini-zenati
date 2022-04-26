package it.polimi.ingsw.controller;

import it.polimi.ingsw.common.events.*;
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

public class ControllerExceptionsTest extends TestCase {
    private Controller controller;

    @BeforeEach
    public void setUp(){
        Game game =new BasicGame();
        game.getPlayers().add(new Player("Host"));
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
        //tests InvalidColor and ArrayIndexOutOfBound
        assertThrows(InvalidColorException.class, () -> controller.update(new MoveStudentToDiningEvent(currPlayer, -1)));
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
        controller.update(new PlayerAccessEvent(this, "Test1"));
        controller.update(new PlayerAccessEvent(this, "Test2"));
        controller.update(new StartGameEvent(this, false));
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
        assertThrows(InvalidColorException.class, () -> controller.update(new MoveStudentToIslandEvent(currPlayer, -1, 0)));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> controller.update(new MoveStudentToIslandEvent(currPlayer, 5, 0)));
        //tests StudentNotPresent
        currPlayer.getMySchoolBoard().getEntrance().removeAll(currPlayer.getMySchoolBoard().getEntrance());
        assertThrows(StudentNotPresentException.class, () -> controller.update(new MoveStudentToIslandEvent(currPlayer, 0,0)));

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
        Player currPlayer = controller.getGame().getCurrPlayer();
        //tests InvalidSteps
        assertThrows(InvalidStepsException.class,()-> controller.update(new MoveMotherEvent(currPlayer, -1)));
        assertThrows(InvalidStepsException.class, ()-> controller.update(new MoveMotherEvent(currPlayer, currPlayer.getChosenCard().getSteps()+1)));
    }

        @Test
        public void ChooseCloudTest(){
            //adding 3 players and starting the game
            controller.update(new PlayerAccessEvent(this, "Test1"));
            controller.update(new PlayerAccessEvent(this, "Test2"));
            controller.update(new StartGameEvent(this, false));
            controller.update(new DrawAssistantCardEvent(this,1 ));
            controller.update(new DrawAssistantCardEvent(this,2 ));
            controller.update(new DrawAssistantCardEvent(this,3 ));
            Player currPlayer = controller.getGame().getCurrPlayer();
            controller.update(new MoveMotherEvent(currPlayer, 1));
            //tests InvalidCloudIndex
            assertThrows(InvalidCloudIndexException.class, () -> controller.update(new ChooseCloudEvent(currPlayer, -1)));
            assertThrows(InvalidCloudIndexException.class, () -> controller.update(new ChooseCloudEvent(currPlayer, controller.getGame().getClouds().size())));
        }
}
