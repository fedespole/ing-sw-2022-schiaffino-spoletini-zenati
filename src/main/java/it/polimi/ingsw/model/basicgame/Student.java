package it.polimi.ingsw.model.basicgame;

import java.io.Serializable;

public class Student implements Serializable {
    private final COLOR color;
    public Student(COLOR color) {
        this.color = color;
    }
    public COLOR getColor() {
        return color;
    }
}
