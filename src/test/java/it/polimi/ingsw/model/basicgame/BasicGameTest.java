package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class BasicGameTest extends TestCase {
    BasicGame game;

    @BeforeEach
    public void setUp(){
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(1)+2;// is 2 or 3
        game =new BasicGame(int_random);
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test",int_random,TEAM.values()[i],game));
        }
    }
    @Test
    public void TestFillCloud(){
        game.setCurrPlayer(game.getPlayers().get(0));
        Student student;
        for(int i=0,size=game.getCurrPlayer().getMyCloud().get(0).getStudents().size();i<size;i++){
            student = game.getCurrPlayer().getMyCloud().get(0).getStudents().remove(0);
        }
        assertEquals(0,game.getCurrPlayer().getMyCloud().get(0).getStudents().size());
        game.fillCloud();
        for(Cloud cloud:game.getPlayers().get(0).getMyCloud()){
            if(game.getNumPlayers()==2)
                assertEquals(3,cloud.getStudents().size());
            else
                assertEquals(4,cloud.getStudents().size());
        }
    }

}