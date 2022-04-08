package it.polimi.ingsw.common.events;

public class ChooseCharacterEvent extends GameEvent{

    private final int index;

    ChooseCharacterEvent(Object source, int index){

        super(source);
        this.index = index;

    }

    public int getIndex() {
        return index;
    }

}
