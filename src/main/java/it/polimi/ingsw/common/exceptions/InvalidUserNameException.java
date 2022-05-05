package it.polimi.ingsw.common.exceptions;

public class InvalidUserNameException extends RuntimeException{
    private final String clientThatCausedEx;

    public InvalidUserNameException(String clientThatCausedEx) {
        this.clientThatCausedEx = clientThatCausedEx;
    }

    public String getClientThatCausedEx() {
        return clientThatCausedEx;
    }
}
