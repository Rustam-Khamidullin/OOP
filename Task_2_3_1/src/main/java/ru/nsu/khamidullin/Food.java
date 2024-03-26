package ru.nsu.khamidullin;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Food extends Circle {
    public static final int SIZE = SnakeElem.SIZE;

    public Food() {
        super(SIZE/2., Color.RED);
    }
}