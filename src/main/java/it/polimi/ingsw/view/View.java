package it.polimi.ingsw.view;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.fromClientEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.*;
import it.polimi.ingsw.model.basicgame.playeritems.Player;
import it.polimi.ingsw.network.client.Client;


import java.util.EventListener;

public abstract class View implements EventListener {
    private ViewData data;

    public View(){
        GameHandler.addEventListener(this);
        data = new ViewData();
    }

    public ViewData getData() {
        return data;
    }

    public void setData(ViewData data) {
        this.data = data;
    }

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

    public void update(NewMidGamePlayerEvent event){}

    public void update(NotifyExceptionEvent event){}
}
