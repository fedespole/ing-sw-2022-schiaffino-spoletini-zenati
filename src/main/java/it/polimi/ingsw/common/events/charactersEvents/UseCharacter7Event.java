package it.polimi.ingsw.common.events.charactersEvents;

import it.polimi.ingsw.common.events.GameEvent;

import java.util.ArrayList;

public class UseCharacter7Event extends GameEvent {

    private final ArrayList<Integer> IndexesColors;

    public UseCharacter7Event(Object source,ArrayList<Integer> indexesColors) {
        super(source);
        IndexesColors = indexesColors;
    }

    public ArrayList<Integer> getIndexesColors() {
        return IndexesColors;
    }
}
