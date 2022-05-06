package it.polimi.ingsw.common.events.fromClientEvents.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class UseCharacter11Event extends GameEvent {
    private final int indexStudent;
//TODO cambiare da index a color(o color.index)
    public UseCharacter11Event(Object source,int indexStudent) {
        super(source);
        this.indexStudent = indexStudent;
    }

    public int getIndexStudent() {
        return indexStudent;
    }
}
