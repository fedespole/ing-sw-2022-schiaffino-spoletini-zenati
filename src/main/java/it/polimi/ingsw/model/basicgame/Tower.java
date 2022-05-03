package it.polimi.ingsw.model.basicgame;

import java.io.Serializable;

public class Tower implements Serializable {
    private final TEAM color;

    public Tower(TEAM color) {
        this.color = color;
    }

    public TEAM getColor() {
        return color;
    }
}
