package it.polimi.ingsw.model.basicgame.playeritems;
import java.util.ArrayList;

public class Deck {
    private ArrayList<AssistantCard> cards;

    public Deck() {
        //costruire 10 carte come link
    }

    public ArrayList<AssistantCard> getCards() {
        return new ArrayList<AssistantCard>(cards);
    }

    public void draw(AssistantCard assistantCard){
        cards.remove(assistantCard);
    }
}
