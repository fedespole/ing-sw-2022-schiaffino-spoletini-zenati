package it.polimi.ingsw.common.events.charactersEvents;

import java.util.ArrayList;

public class UseCharacter7Event {

    private final ArrayList<Integer> IndexesColors;

    public UseCharacter7Event(ArrayList<Integer> indexesColors) {
        IndexesColors = indexesColors;
    }

    public ArrayList<Integer> getIndexesColors() {
        return IndexesColors;
    }
}
