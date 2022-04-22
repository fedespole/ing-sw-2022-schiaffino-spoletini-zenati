package it.polimi.ingsw.view;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.util.EventListener;

public abstract class View implements EventListener {
    private Player player;

    public View() {
        GameHandler.addEventListener(this);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    //TODO: CREATE EVENTS FOR VIEW
    public void update(GameEvent event){

    }
}
