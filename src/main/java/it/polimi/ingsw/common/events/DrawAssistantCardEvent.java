package it.polimi.ingsw.common.events;

public class DrawAssistantCardEvent extends GameEvent{

    private final int value; // View selects value of card from deck

    public DrawAssistantCardEvent(Object source, int value) {
        super(source);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
