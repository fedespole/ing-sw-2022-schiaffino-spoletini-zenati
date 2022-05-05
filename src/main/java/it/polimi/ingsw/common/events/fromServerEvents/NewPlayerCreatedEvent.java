package it.polimi.ingsw.common.events.fromServerEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class NewPlayerCreatedEvent extends GameEvent {

    private final String username;

    public NewPlayerCreatedEvent(Object source, String player) {
        super(source);
        this.username = player;
    }

    public String getUsername() {
        return username;
    }
}
