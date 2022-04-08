package it.polimi.ingsw.common.events;

public class MoveStudentToIslandEvent extends GameEvent{

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
