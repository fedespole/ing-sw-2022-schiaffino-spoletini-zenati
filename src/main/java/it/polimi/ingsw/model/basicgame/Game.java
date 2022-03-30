package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

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
}
