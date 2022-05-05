package it.polimi.ingsw.common.events.fromServerEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class RequestNumPlayersEvent extends GameEvent {
    private final String username;

    public RequestNumPlayersEvent(Object source, String player) {
        super(source);
        this.username = player;
    }

    public String getUsername() {
        return username;
    }
}
