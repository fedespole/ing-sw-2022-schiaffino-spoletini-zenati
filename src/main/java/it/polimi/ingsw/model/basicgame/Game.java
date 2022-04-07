package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.model.expertgame.characters.Character;

import java.util.ArrayList;

public interface Game {
    void setUp();

    void fillCloud();

    void chooseCard(int index);

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

    void setMotherNature(int motherNature);

    Player getCurrPlayer();

    void setCurrPlayer(Player currPlayer);

    ArrayList<Cloud> getClouds();

    StatusGame getStatusGame();
}