package it.polimi.ingsw.common.events;

public class NotifyExceptionEvent extends GameEvent{

    private final RuntimeException exception;

    public NotifyExceptionEvent(Object source, RuntimeException exception) {
        super(source);
        this.exception = exception;
    }

    public RuntimeException getException() {
        return exception;
    }
}
