package it.polimi.ingsw.common.events.fromClientEvents.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class UseCharacter4Event extends GameEvent {

    private final int indexPlayer; // index of player that uses this character

    public UseCharacter4Event(Object source, int indexPlayer) {
        super(source);
        this.indexPlayer = indexPlayer;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }
}
