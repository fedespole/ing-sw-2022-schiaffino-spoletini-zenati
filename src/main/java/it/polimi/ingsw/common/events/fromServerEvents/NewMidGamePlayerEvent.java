package it.polimi.ingsw.common.events.fromServerEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class NewMidGamePlayerEvent extends GameEvent {
    public NewMidGamePlayerEvent(Object source) {
        super(source);
    }
}
