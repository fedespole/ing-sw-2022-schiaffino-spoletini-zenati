package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.common.exceptions.*;


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
    public void TestInvalidCloudIndexException(){
        int sizeClouds = game.getClouds().size();
        assertDoesNotThrow(()-> game.chooseCloud(sizeClouds-1));
        assertThrows(InvalidCloudIndexException.class, ()->game.chooseCloud(sizeClouds));
        assertThrows(InvalidCloudIndexException.class, ()->game.chooseCloud(-1));
    }

    @Test
    public void TestInvalidStepsException(){
        game.chooseCard(1);
        int steps = game.getCurrPlayer().getChosenCard().getSteps();
        assertDoesNotThrow(()-> game.moveMother(steps));
        assertThrows(InvalidStepsException.class, ()->game.moveMother(steps+1));
        assertThrows(InvalidStepsException.class, ()->game.moveMother(-1));
    }

}
