package it.polimi.ingsw.view;

import it.polimi.ingsw.common.events.GameEvent;
import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.NewPlayerCreatedEvent;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.network.client.Client;


import java.util.EventListener;

public abstract class View implements EventListener {
    private Client client;
    private ViewData data;

    public View() {
        GameHandler.addEventListener(this);
        data = new ViewData();
    }

    public ViewData getData() {
        return data;
    }

    //TODO: CREATE EVENTS FOR VIEW
    public void update(NewPlayerCreatedEvent event){
        if(data.getOwner()==null)
            data.setOwner(event.getPlayer());
    }
}
