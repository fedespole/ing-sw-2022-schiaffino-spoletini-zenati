package it.polimi.ingsw.common.events.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class UseCharacter1Event extends GameEvent {

    private final int indexStudent; // to select one of the 4 students placed on character1 card
    private final int indexIsland;  // destination island

    public UseCharacter1Event(Object source, int indexStudent, int indexIsland){

        super(source);
        this.indexStudent = indexStudent;
        this.indexIsland = indexIsland;
    }

    public int getIndexStudent() {
        return indexStudent;
    }
    public int getIndexIsland() {
        return indexIsland;
    }

}
