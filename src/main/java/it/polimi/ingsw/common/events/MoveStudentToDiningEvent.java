package it.polimi.ingsw.common.events;

public class MoveStudentToDiningEvent extends GameEvent{

    private final int colorIndex;

    public MoveStudentToDiningEvent(Object source, int colorIndex) {
        super(source);
        this.colorIndex = colorIndex;
    }

    public int getColorIndex() {
        return colorIndex;
    }
}
