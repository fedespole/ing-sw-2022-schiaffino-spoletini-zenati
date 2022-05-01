package it.polimi.ingsw.common.events.fromClientEvents.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class UseCharacter5Event extends GameEvent {

    private final int indexIsland;

    public UseCharacter5Event(Object source, int indexIsland) {
        super(source);
        this.indexIsland=indexIsland;
    }

    public int getIndexIsland() {
        return indexIsland;
    }
}
