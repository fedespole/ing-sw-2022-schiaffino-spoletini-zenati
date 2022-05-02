package it.polimi.ingsw.model.basicgame.playeritems;

import java.io.Serializable;

public class AssistantCard implements Serializable {
    private final int value;
    private int steps;

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

    public void setSteps(int steps){
        this.steps= steps;
    }
}
