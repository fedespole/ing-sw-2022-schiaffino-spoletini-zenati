package it.polimi.ingsw.common.events.fromClientEvents.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

import java.util.ArrayList;

public class UseCharacter10Event extends GameEvent {
    private final ArrayList<Integer> IndexesColors;

    public UseCharacter10Event(Object source, ArrayList<Integer> indexesColors) {
        super(source);
        IndexesColors = indexesColors;
    }

    public ArrayList<Integer> getIndexesColors() {
        return IndexesColors;
    }
}
