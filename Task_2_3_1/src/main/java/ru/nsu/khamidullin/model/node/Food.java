package ru.nsu.khamidullin.model.node;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ru.nsu.khamidullin.controller.Controller;

public class Food extends Circle {
    public Food() {
        super(Controller.SELL_SIZE / 2., Color.RED);
    }
}