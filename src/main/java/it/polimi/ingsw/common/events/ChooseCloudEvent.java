package it.polimi.ingsw.common.events;

public class ChooseCloudEvent extends GameEvent{

    private final int index;

    public ChooseCloudEvent(Object source, int index) {
        super(source);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
