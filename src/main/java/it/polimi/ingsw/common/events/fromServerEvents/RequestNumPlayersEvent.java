package it.polimi.ingsw.common.events.fromServerEvents;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

public class RequestNumPlayersEvent extends GameEvent {
    private final Player player;

    public RequestNumPlayersEvent(Object source, Player player) {
        super(source);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
