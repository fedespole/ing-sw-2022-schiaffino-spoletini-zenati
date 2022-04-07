package it.polimi.ingsw.common.events;

import java.util.EventObject;

abstract public class GameEvent extends EventObject {
    public GameEvent(Object source) {
        super(source);
    }



}
