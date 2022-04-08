package it.polimi.ingsw.common.events;

import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.util.ArrayList;

public class TieEvent extends GameEvent{

    private final ArrayList<Player> tiePlayers;

    public TieEvent(Object source, ArrayList<Player> tiePlayers){
        super(source);
        this.tiePlayers = tiePlayers;
    }

    public ArrayList<Player> getTiePlayers(){return tiePlayers;}
}
