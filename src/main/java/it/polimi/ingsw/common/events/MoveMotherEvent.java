package it.polimi.ingsw.common.events;

public class MoveMotherEvent extends GameEvent{

    private final int index;

    public MoveMotherEvent(Object source, int index) {
        super(source);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
