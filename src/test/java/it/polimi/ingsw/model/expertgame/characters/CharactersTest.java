package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CharactersTest extends TestCase {
    Game game;

    @BeforeEach
    public void setUp(){
        Random rand = new Random(); //instance of random class
        //int int_random = rand.nextInt(1)+2;// is 2 or 3
        int int_random=2;
        game =new BasicGame();
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test"));
        }
        game.setUp();
        this.game =new ConcreteExpertGame((BasicGame) game);

    }

}