package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

import static org.junit.Assert.*;

public class ControllerTest {

    private Game game;

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


}