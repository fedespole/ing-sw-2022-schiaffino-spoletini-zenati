package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.io.Serializable;

public class Professor implements Serializable {
    private final COLOR color;
    private Player owner;

    public Professor(COLOR color) {
        this.color = color;
    }

    public COLOR getColor() {
        return color;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
