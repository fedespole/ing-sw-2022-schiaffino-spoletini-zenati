package it.polimi.ingsw.common.events;


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
