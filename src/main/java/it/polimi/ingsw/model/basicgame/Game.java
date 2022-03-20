package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Cloud;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

public interface Game {
    void fillCloud(Player player);
    AssistantCard chooseCard(int index,Player player);
    void movestudentsfromEntrance();
    void moveMother(int steps);
    Cloud chooseCloud(Player player,int cloud);
    void moveStudentsFromCloud(Player player,Cloud chosenCloud);
    void computeInfluence();
}
