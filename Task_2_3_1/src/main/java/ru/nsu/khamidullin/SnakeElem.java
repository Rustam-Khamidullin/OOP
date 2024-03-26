package ru.nsu.khamidullin;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SnakeElem extends Circle {
    public static final int SIZE = 20;

    public SnakeElem() {
        super(SIZE/2., Color.GREEN);
    }
}