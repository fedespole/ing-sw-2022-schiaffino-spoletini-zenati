package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.Player;

import java.util.ArrayList;

public class StatusGame {
    private STATUS status;
    private ArrayList<Player> order;


    public StatusGame() {
        status = STATUS.SETUP;
        order = new ArrayList<>();
    }

    public STATUS getStatus() {
        return status;
    }

    public ArrayList<Player> getOrder() {
        return order;
    }

    public void setStatus(STATUS status) {
        status = status;
    }

    public void setOrder(ArrayList<Player> order) {
        this.order = order;
    }
}
