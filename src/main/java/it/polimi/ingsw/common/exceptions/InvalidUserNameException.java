package it.polimi.ingsw.common.exceptions;

import it.polimi.ingsw.network.client.Client;

public class InvalidUserNameException extends RuntimeException{
    private final String clientThatCausedEx;

    public InvalidUserNameException(String clientThatCausedEx) {
        this.clientThatCausedEx = clientThatCausedEx;
    }

    public String getClientThatCausedEx() {
        return clientThatCausedEx;
    }
}
