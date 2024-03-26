package ru.nsu.khamidullin;

import javafx.scene.layout.Pane;

public class Cell extends Pane {
    public static final int SIZE = 20;

    public Cell() {
        setPrefSize(SIZE, SIZE);
        setStyle("-fx-background-color: red;");
    }
}