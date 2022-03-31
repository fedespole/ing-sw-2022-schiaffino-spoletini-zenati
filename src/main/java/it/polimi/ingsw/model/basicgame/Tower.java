package it.polimi.ingsw.model.basicgame;

public class Tower {
    private final TEAM color;

    public Tower(TEAM color) {
        this.color = color;
    }

    public TEAM getColor() {
        return color;
    }
}
