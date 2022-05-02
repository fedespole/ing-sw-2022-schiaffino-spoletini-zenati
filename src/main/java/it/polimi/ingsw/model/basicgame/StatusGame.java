package it.polimi.ingsw.model.basicgame;

import it.polimi.ingsw.model.basicgame.playeritems.Player;
import java.util.ArrayList;

public class StatusGame {
    private STATUS status;    //Saves the next correct move to be made by currPlayer
    private ArrayList<Player> order;        // action phase order


    public StatusGame() {
        this.status = STATUS.SETUP;
        this.order = new ArrayList<>();
    }

    public STATUS getStatus() {
        return status;
    }

    public ArrayList<Player> getOrder() {
        return order;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public void setOrder(ArrayList<Player> order) {
        this.order = order;
    }

    public void addSort(Player currPlayer){

        for(int i=0; i< order.size(); i++){
            // If currPlayer has chosen a lower value card, the order is sorted
            if (order.get(i).getChosenCard().getValue() > currPlayer.getChosenCard().getValue()){
                order.add(i, currPlayer);
                return;
            }
        }
        order.add(currPlayer);
    }

}
