package it.polimi.ingsw.model.basicgame.playeritems;
import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable {
    private final ArrayList<AssistantCard> cards;

    public Deck() {
        cards = new ArrayList<AssistantCard>();
        for(int i=1;i<11;i++){
            if(i%2==0){
                cards.add(new AssistantCard(i,i/2));
            }
            else{
                cards.add(new AssistantCard(i,i/2+1));
            }
        }
    }

    public ArrayList<AssistantCard> getCards() {
        return cards;
    }

    public AssistantCard draw(AssistantCard assistantCard){
        cards.remove(assistantCard);
        return assistantCard;
    }
}
