package it.polimi.ingsw.model.basicgame;

import java.io.Serializable;

public enum STATUS implements Serializable {
    SETUP,
    PLANNING,
    ACTION_MOVESTUD,
    ACTION_MOVEMN,
    ACTION_CHOOSECLOUD,
}