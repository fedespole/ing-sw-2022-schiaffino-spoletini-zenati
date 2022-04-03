package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class BasicGameExceptionsTest extends TestCase {
    Game game;
    @Test
    public void setUp(){
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(1)+1;// is 1 or 2
        game =new BasicGame(new Player("Host"));
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test"));
        }
        game.setUp();
       System.out.println(game.getPlayers().size());
       assert (game.getNumPlayers()==game.getPlayers().size());
    }


}
