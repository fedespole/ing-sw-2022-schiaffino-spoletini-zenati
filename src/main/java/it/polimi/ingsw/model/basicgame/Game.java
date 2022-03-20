package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.AssistantCard;
import it.polimi.ingsw.model.basicgame.playeritems.Cloud;

public interface Game {
    void fillCloud();
    AssistantCard chooseCard();
    void movestudentsfromEntrance();
    void moveMother(int steps);
    Cloud chooseCloud();
    void moveStudentsFromCloud(Cloud chosenCloud);
    void computeInfluence();
}
