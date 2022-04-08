package it.polimi.ingsw.common.events;

public class StartGameEvent extends GameEvent{

    private final boolean isExpert;

    public StartGameEvent(Object source, boolean isExpert) {
        super(source);
        this.isExpert = isExpert;
    }

    public boolean isExpert() {
        return isExpert;
    }
}
