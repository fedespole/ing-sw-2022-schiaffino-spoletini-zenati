package it.polimi.ingsw.view;

import it.polimi.ingsw.common.events.*;
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
        data = event.getViewData();
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
