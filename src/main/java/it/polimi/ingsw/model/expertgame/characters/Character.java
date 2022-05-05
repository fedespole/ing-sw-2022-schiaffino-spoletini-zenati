package it.polimi.ingsw.model.expertgame.characters;

import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.io.Serializable;

public abstract class Character implements Serializable {
    private int cost;
    private int id;
    private boolean hasBeenUsed;

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

    public void setHasBeenUsed(boolean hasBeenUsed) {
        this.hasBeenUsed = hasBeenUsed;
    }
    public boolean isHasBeenUsed() {
        return hasBeenUsed;
    }
    public void changeCost(){
        if(!isHasBeenUsed()){
            setHasBeenUsed(true);
            setCost(getCost()+1);
        }
    }
    public void playerPayment(Player player){
        player.setCoins(player.getCoins()-this.cost);
    }
}
