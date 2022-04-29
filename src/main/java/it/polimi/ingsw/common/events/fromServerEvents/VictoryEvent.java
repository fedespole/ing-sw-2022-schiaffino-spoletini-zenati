package it.polimi.ingsw.common.events.fromServerEvents;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

public class VictoryEvent extends GameEvent {

    private final Player winningPlayer;

    public VictoryEvent(Object source, Player winningPlayer){
        super(source);
        this.winningPlayer = winningPlayer;
    }

    public Player getWinningPlayer(){return winningPlayer;}
}
