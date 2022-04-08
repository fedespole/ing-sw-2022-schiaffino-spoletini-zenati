package it.polimi.ingsw.common.events.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class UseCharacter11Event extends GameEvent {
    private final int indexStudent;

    public UseCharacter11Event(Object source,int indexStudent) {
        super(source);
        this.indexStudent = indexStudent;
    }

    public int getIndexStudent() {
        return indexStudent;
    }
}
