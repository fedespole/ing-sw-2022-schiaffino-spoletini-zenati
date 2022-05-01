package it.polimi.ingsw.common.events.fromServerEvents;

import it.polimi.ingsw.common.events.GameEvent;

public class NotifyExceptionEvent extends GameEvent {

    private final RuntimeException exception;

    public NotifyExceptionEvent(Object source, RuntimeException exception) {
        super(source);
        this.exception = exception;
    }

    public RuntimeException getException() {
        return exception;
    }
}
