package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

public interface Game {
    void fillCloud(Player player);
    AssistantCard chooseCard(int index,Player player);
    void moveStudentFromEntranceToDining(Player player, Student student);
    void moveStudentFromEntranceToIsland(Player player, Student student, Island chosenIsland);
    void moveMother(int steps, Player player);
    Cloud chooseCloud(Player player,int cloud);
    void moveStudentsFromCloud(Player player,Cloud chosenCloud);
    void computeInfluence();
    void assignProfessor(Player player, COLOR color);
    int getMotherNature();
}
