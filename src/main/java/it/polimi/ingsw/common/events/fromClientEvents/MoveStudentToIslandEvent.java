package it.polimi.ingsw.common.events.fromClientEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class MoveStudentToIslandEvent extends GameEvent {

    private final int colorIndex;
    private final int islandIndex;

    public MoveStudentToIslandEvent(Object source, int colorIndex, int islandIndex) {
        super(source);
        this.colorIndex = colorIndex;
        this.islandIndex = islandIndex;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public int getIslandIndex() {
        return islandIndex;
    }
}
