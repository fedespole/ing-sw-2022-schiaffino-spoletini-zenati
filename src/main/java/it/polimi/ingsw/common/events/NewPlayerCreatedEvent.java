package it.polimi.ingsw.common.events;

import it.polimi.ingsw.model.basicgame.playeritems.Player;

public class NewPlayerCreatedEvent extends GameEvent {

    private final Player player;

    public NewPlayerCreatedEvent(Object source, Player player) {
        super(source);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
