package it.polimi.ingsw.common.events;

import java.io.Serializable;
import java.net.ServerSocket;
import java.util.EventObject;

abstract public class GameEvent extends EventObject implements Serializable {
    public GameEvent(Object source) {
        super(source);
    }

}
