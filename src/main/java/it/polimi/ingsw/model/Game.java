package it.polimi.ingsw.model;

public interface Game {
    void fillCloud();
    AssistantCard chooseCard();
    void movestudentsfromEntrance();
    void moveMother(int steps);
    Cloud chooseCloud();
    void moveStudentsFromCloud(Cloud chosenCloud);
    void computeInfluence();
}
