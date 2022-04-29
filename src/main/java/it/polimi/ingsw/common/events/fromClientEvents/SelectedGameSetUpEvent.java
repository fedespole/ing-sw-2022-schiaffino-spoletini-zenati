package it.polimi.ingsw.common.events.fromClientEvents;

import it.polimi.ingsw.common.events.GameEvent;

import java.util.Objects;

public class SelectedGameSetUpEvent extends GameEvent {
    private final int numPlayers;
    private final boolean isExpert;
    public SelectedGameSetUpEvent(Object source, int numPlayers, boolean isExpert){
        super(source);
        this.numPlayers = numPlayers;
        this.isExpert = isExpert;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public boolean isExpert() {
        return isExpert;
    }
}
