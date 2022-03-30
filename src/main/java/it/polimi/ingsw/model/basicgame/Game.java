package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.util.ArrayList;

public interface Game {
    void setUp();
    void fillCloud();
    AssistantCard chooseCard(int index);
    void moveStudentFromEntranceToDining(COLOR color);
    void moveStudentFromEntranceToIsland(COLOR color, Island chosenIsland);
    void moveMother(int steps);
    Cloud chooseCloud(int cloud);
    void moveStudentsFromCloud(Cloud chosenCloud);
    void computeInfluence();
    void assignProfessor(COLOR color);
    void mergeIslands();
    int getMotherNature();
    ArrayList<Player> getPlayers();
    int getNumPlayers();
    Bag getBag();
    ArrayList<Professor> getProfessors();
    ArrayList<ArrayList<Island>> getIslands();
    void setPlayers(ArrayList<Player> players);
    void setNumPlayers(int numPlayers);
    void setBag(Bag bag);
    void setIslands(ArrayList<ArrayList<Island>> islands);
    void setProfessors(ArrayList<Professor> professors);
    void setMotherNature(int motherNature);
    Player getCurrPlayer();
    void setCurrPlayer(Player currPlayer);
}
