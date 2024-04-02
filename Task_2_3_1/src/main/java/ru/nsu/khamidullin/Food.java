package ru.nsu.khamidullin;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Food extends Circle {
    public Food() {
        super(Controller.SELL_SIZE / 2., Color.RED);
    }
}