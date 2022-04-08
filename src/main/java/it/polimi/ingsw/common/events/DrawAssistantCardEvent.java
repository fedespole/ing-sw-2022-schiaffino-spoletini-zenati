package it.polimi.ingsw.common.events;

public class DrawAssistantCardEvent extends GameEvent{

    private final int index; // View selects index of card from deck

    public DrawAssistantCardEvent(Object source, int index) {
        super(source);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
