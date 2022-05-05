package it.polimi.ingsw.common.events.fromServerEvents;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

public class VictoryEvent extends GameEvent {

    private final String winningPlayer;

    public VictoryEvent(Object source, String winningPlayer){
        super(source);
        this.winningPlayer = winningPlayer;
    }

    public String getWinningPlayer(){return winningPlayer;}
}
