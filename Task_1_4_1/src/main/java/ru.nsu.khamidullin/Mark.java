package ru.nsu.khamidullin;

/**
 * The {@code Mark} enum represents the possible academic marks for a student.
 * It includes values for the grades: TWO, THREE, FOUR, and FIVE.
 * Each mark has an associated numeric value, accessible through the {@code getMark} method.
 */
public enum Mark {
    TWO(2), THREE(3), FOUR(4), FIVE(5);

    private final int mark;

    Mark(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }
}
