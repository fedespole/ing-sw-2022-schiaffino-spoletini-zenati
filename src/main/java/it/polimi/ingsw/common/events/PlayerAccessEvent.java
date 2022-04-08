package it.polimi.ingsw.common.events;

public class PlayerAccessEvent extends GameEvent{

    private final String username;

    public PlayerAccessEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
