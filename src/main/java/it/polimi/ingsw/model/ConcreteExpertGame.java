package it.polimi.ingsw.model;

public class ConcreteExpertGame extends ExpertGameDecorator{
    private BasicGame game;
    private Character[] characters;

    public ConcreteExpertGame(BasicGame game) {
        this.game = game;
        //qui vengono scelti tre personaggi a caso
    }

    @Override
    public void fillCloud() {
        game.fillCloud();
    }

    @Override
    public AssistantCard chooseCard() {
        return game.chooseCard();
    }

    @Override
    public void movestudentsfromEntrance() {
        game.movestudentsfromEntrance();
    }

    @Override
    public void moveMother(int steps) {
        game.moveMother(steps);
    }

    @Override
    public Cloud chooseCloud() {
        return game.chooseCloud();
    }

    @Override
    public void moveStudentsFromCloud(Cloud chosenCloud) {
        game.moveStudentsFromCloud(chosenCloud);
    }

    @Override
    public void computeInfluence() {
        game.computeInfluence();
    }
}
