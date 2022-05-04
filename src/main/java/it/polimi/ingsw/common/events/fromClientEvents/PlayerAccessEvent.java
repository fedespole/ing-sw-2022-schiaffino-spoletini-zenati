package it.polimi.ingsw.common.events.fromClientEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class PlayerAccessEvent extends GameEvent {

    private final String username;
    private final String client;

    public PlayerAccessEvent(Object source, String username, String client) {
        super(source);
        this.username = username;
        this.client = client;
    }

    public String getUsername() {
        return username;
    }

    public String getClient() {
        return client;
    }
}
