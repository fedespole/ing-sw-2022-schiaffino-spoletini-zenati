package it.polimi.ingsw.common.events.fromClientEvents.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class UseCharacter1Event extends GameEvent {

    private final int indexColor; // to select one of the colors of the students placed on character1 card
    private final int indexIsland;  // destination island

    public UseCharacter1Event(Object source, int indexStudent, int indexIsland){

        super(source);
        this.indexColor = indexStudent;
        this.indexIsland = indexIsland;
    }

    public int getIndexColor() {
        return indexColor;
    }
    public int getIndexIsland() {
        return indexIsland;
    }

}
