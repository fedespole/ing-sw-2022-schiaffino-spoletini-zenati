package it.polimi.ingsw.view;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.fromClientEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.NewPlayerCreatedEvent;
import it.polimi.ingsw.common.events.fromServerEvents.TieEvent;
import it.polimi.ingsw.common.events.fromServerEvents.UpdatedDataEvent;
import it.polimi.ingsw.common.events.fromServerEvents.VictoryEvent;
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

    public void setData(ViewData data) {
        this.data = data;
    }

    //TODO: CREATE EVENTS FOR VIEW
    public void update(NewPlayerCreatedEvent event){
        if(data.getOwner()==null)
            data.setOwner(event.getPlayer());
    }
    public void update(UpdatedDataEvent event){
        Player owner = data.getOwner();
        data = event.getViewData();
        data.setOwner(owner);//viewData has owner== null
    }
    public void update(VictoryEvent event){
        data.setWinner(event.getWinningPlayer());
    }

    public void update(TieEvent event){
        data.setTiePlayers(event.getTiePlayers());
    }
    public void update(RequestNumPlayersEvent event){
        if(data.getOwner()==null)
            data.setOwner(event.getPlayer());
    }
}
