package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.BasicGame;
import it.polimi.ingsw.model.basicgame.COLOR;
import it.polimi.ingsw.model.basicgame.Game;
import it.polimi.ingsw.model.basicgame.Student;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static javax.swing.UIManager.get;

public class CharactersTest extends TestCase {
    Game game;

    @BeforeEach
    public void setUp(){
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(1)+1;// is 1 or 2
        game =new BasicGame(new Player("Host"));
        for(int i=0;i<int_random;i++){
            game.getPlayers().add(new Player("Test"));
        }
        game.setUp();
        this.game =new ConcreteExpertGame(game);

    }

    @Test
    public void CharacterTest1(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character1(game));
        assertEquals(4,((Character1)((ConcreteExpertGame)game).getCharacters().get(0)).getStudents().size());
        int sizeIsland= game.getIslands().get(0).get(0).getStudents().size();
        ((Character1)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,0,game.getIslands().get(0));
        assertEquals(sizeIsland+1,game.getIslands().get(0).get(0).getStudents().size());
    }

    @Test
    public void CharacterTest2(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character2());
        game.setCurrPlayer(game.getPlayers().get(0));
        for(int i=0;i<3;i++)
            game.getPlayers().get(0).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        game.assignProfessor(COLOR.RED);
        assertEquals(game.getProfessors().get(COLOR.RED.ordinal()).getOwner(),game.getPlayers().get(0));
        assertEquals(game.getCurrPlayer().getMySchoolBoard().getProfessors().get(0),game.getProfessors().get(COLOR.RED.ordinal()));
        for(int i=0;i<3;i++)
            game.getPlayers().get(1).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        game = ((Character2)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game);
        game.assignProfessor(COLOR.RED);
        game.setCurrPlayer(game.getPlayers().get(1));
        assertEquals(game.getProfessors().get(COLOR.RED.ordinal()).getOwner(),game.getPlayers().get(0));
    }

    @Test
    public void CharacterTest3(){
        ((ConcreteExpertGame)game).getCharacters().add(new Character3());
        for(int i=0;i<4;i++) {
            game.getIslands().get(0).get(0).addStudent(new Student(COLOR.RED));
            game.getPlayers().get(0).getMySchoolBoard().addStudentToDining(new Student(COLOR.RED));
        }
        game.assignProfessor(COLOR.RED);
        ((Character3)((ConcreteExpertGame)game).getCharacters().get(0)).useAbility(game,0);
        assertEquals(game.getIslands().get(0).get(0).getTower().getColor(),game.getPlayers().get(0).getTeam());
    }
}

