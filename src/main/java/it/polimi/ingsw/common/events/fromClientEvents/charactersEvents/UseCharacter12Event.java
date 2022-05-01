package it.polimi.ingsw.common.events.fromClientEvents.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class UseCharacter12Event extends GameEvent {
    private final int indexColor;

    public UseCharacter12Event(Object source, int indexColor){

        super(source);
        this.indexColor = indexColor;

    }

    public int getIndexColor() {
        return indexColor;
    }
}
