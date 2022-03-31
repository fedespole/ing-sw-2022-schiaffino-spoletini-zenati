package it.polimi.ingsw.model.basicgame.playeritems;

public class AssistantCard {
    private final int value;
    private final int steps;

    public AssistantCard(int value, int steps) {
        this.value = value;
        this.steps = steps;
    }

    public int getValue(){
        return value;
    }

    public int getSteps() {
        return steps;
    }
}
