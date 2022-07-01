package it.polimi.ingsw.common.exceptions;

/**
 * This exception is created by the server when the current player tries to use a username which was already chosen by another player
 */
public class InvalidUserNameException extends RuntimeException{
    private final String clientThatCausedEx;

    public InvalidUserNameException(String clientThatCausedEx) {
        this.clientThatCausedEx = clientThatCausedEx;
    }

    public String getClientThatCausedEx() {
        return clientThatCausedEx;
    }
}
