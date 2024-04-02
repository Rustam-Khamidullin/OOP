package ru.nsu.khamidullin;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SnakeElem extends Circle {
    public SnakeElem() {
        super(Controller.SELL_SIZE / 2., Color.GREEN);
    }
}