package it.polimi.ingsw.common.events.fromServerEvents;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.util.ArrayList;

public class TieEvent extends GameEvent {

    private final ArrayList<String> tiePlayers;

    public TieEvent(Object source, ArrayList<String> tiePlayers){
        super(source);
        this.tiePlayers = tiePlayers;
    }

    public ArrayList<String> getTiePlayers(){return tiePlayers;}
}
