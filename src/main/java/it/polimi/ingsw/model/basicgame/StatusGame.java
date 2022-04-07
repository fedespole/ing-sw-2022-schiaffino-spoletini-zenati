package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import java.util.ArrayList;

public class StatusGame {
    private STATUS status;
    private ArrayList<Player> order;        // action phase order


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

    public void addSort(Player currPlayer){

        for(int i=0; i< order.size(); i++){
            // If currPlayer has chosen a lower value card, the order is sorted
            if (order.get(i).getChosenCard().getValue() > currPlayer.getChosenCard().getValue()){
                order.add(i, currPlayer);
            }
        }
    }

}
