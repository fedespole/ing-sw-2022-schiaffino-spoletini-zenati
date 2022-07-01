package it.polimi.ingsw.common.events;

/**
 * Handles resilience, when the socket reader finds out that a client is
 * no longer connected, this event is launched directly to the controller.
 */
public class ClientDisconnectedEvent extends GameEvent{
    private String username;
    public ClientDisconnectedEvent(Object source) {
        super(source);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSource(Object source){
        this.source=source;
    }
}
