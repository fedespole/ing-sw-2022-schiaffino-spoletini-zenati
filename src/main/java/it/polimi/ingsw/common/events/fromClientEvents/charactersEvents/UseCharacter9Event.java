package it.polimi.ingsw.common.events.fromClientEvents.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class UseCharacter9Event extends GameEvent {
    private final int indexColor;

    public UseCharacter9Event(Object source, int indexColor) {
        super(source);
        this.indexColor = indexColor;
    }

    public int getIndexColor() {
        return indexColor;
    }
}
