package ru.nsu.khamidullin;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Food extends Pane {
    public static final int SIZE = 20;

    public Food() {
        setPrefSize(SIZE, SIZE);
        setStyle("-fx-background-color: green;");
    }
}