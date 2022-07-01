package it.polimi.ingsw.common.exceptions;

/**
 * This exception is created by the server when the current player tries to move a student from
 * his entrance to his dining room, but a student of the chosen color is not present in the entrance
 */
public class StudentNotPresentException extends RuntimeException{
}
