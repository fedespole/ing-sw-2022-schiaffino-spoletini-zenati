package it.polimi.ingsw.view;

import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.io.Serializable;

public class ViewData implements Serializable {
    Player owner;

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
