package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.expertgame.ConcreteExpertGame;

public abstract class Character {
    private int cost;
    private int id;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void useAbility(ConcreteExpertGame currGame){}
}
