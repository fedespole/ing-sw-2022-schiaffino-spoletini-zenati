package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.String;

import java.io.Serializable;

public class Professor implements Serializable {
    private final COLOR color;
    private String owner;

    public Professor(COLOR color) {
        this.color = color;
    }

    public COLOR getColor() {
        return color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
