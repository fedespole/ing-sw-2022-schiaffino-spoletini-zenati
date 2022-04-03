package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.exceptions.*;


import it.polimi.ingsw.model.basicgame.playeritems.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicGameExceptionsTest extends TestCase{

    Game game;

    @BeforeEach
    public void setUp(){
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(2)+1;// is 1 or 2
        game =new BasicGame(new Player("Host"));
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test"));
        }
        game.setUp();
    }

    @Test
    public void TestInvalidNumPlayersException(){
        if(game.getNumPlayers()==3) {
            game.getPlayers().add(new Player("Test"));
            assertThrows(InvalidNumPlayerException.class, () ->game.setUp());
        }
       game.getPlayers().removeAll(game.getPlayers());
        assertThrows(InvalidNumPlayerException.class, () ->game.setUp());
    }

    @Test
    public void TestOutOfBoundCardSelectionException(){
        assertThrows(OutOfBoundCardSelectionException.class,()->game.chooseCard(0));
        assertThrows(OutOfBoundCardSelectionException.class,()->game.chooseCard(11));
        for(int i=1; i<11;i++) {
            int finalI = i;
            assertDoesNotThrow(()->game.chooseCard(finalI));
        }
    }
    @Test
    public void TestNotAvailableCard(){
        Random rand = new Random();
        int int_random = rand.nextInt(10)+1;
        assertDoesNotThrow( ()->game.chooseCard(int_random));
        assertThrows(NotAvailableCardException.class,()->game.chooseCard(int_random));
    }
    //TODO Invalid Indexes?
}
