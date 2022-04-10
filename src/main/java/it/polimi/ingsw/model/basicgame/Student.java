package it.polimi.ingsw.model.basicgame;

import java.util.Objects;

public class Student {
    private final COLOR color;
    public Student(COLOR color) {
        this.color = color;
    }
    public COLOR getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
