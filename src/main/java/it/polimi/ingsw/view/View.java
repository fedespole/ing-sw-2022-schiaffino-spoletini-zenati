package it.polimi.ingsw.view;

import it.polimi.ingsw.common.events.*;
import it.polimi.ingsw.common.events.fromServerEvents.RequestNumPlayersEvent;
import it.polimi.ingsw.common.events.fromServerEvents.*;


import java.util.EventListener;

public abstract class View implements EventListener {
    private String owner;
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
        if(this.owner==null)
           this.owner=event.getUsername();
    }

    public void update(UpdatedDataEvent event){
        data = event.getViewData();
    }

    public void update(VictoryEvent event){
        data.setWinner(event.getWinningPlayer());
    }

    public void update(TieEvent event){
        data.setTiePlayers(event.getTiePlayers());
    }

    public void update(RequestNumPlayersEvent event){
        if(this.owner==null) {
            this.owner=event.getUsername();
        }
    }

    public void update(NewMidGamePlayerEvent event){}

    public void update(NotifyExceptionEvent event){}

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
