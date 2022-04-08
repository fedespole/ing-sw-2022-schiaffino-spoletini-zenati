package it.polimi.ingsw.common.events.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class UseCharacter3Event extends GameEvent {

    private final int indexIsland;  // destination island

    public UseCharacter3Event(Object source, int indexIsland) {
        super(source);
        this.indexIsland = indexIsland;
    }

    public int getIndexIsland() {
        return indexIsland;
    }
}
