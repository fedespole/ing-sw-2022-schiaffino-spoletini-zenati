package it.polimi.ingsw.common.events;

import it.polimi.ingsw.model.basicgame.playeritems.Player;

public class ClientDisconnectedEvent extends GameEvent{
    private Player player;
    public ClientDisconnectedEvent(Object source) {
        super(source);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
