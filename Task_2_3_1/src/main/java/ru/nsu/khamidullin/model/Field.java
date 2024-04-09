package ru.nsu.khamidullin.model;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Field {
    private final Set<Coordinate> emptyFields;
    private final GridPane field;
    private final Snake playerSnake;

    public Field(int width, int height) {
        field = new GridPane(width, height);
        playerSnake = new Snake(this);
        emptyFields = new HashSet<>();
    }


    private <T extends Node> T isOccupied(int x, int y, List<T> list) {
        for (T node : list) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                return node;
            }
        }
        return null;
    }

    <T extends Node> void addElem(int x, int y, T node, List<T> list) {
        list.addFirst(node);
        field.add(node, x, y);
        emptyFields.remove(new Coordinate(x, y));
    }

    <T extends Node> void removeElem(T node, List<T> list) {
        int x = GridPane.getColumnIndex(node);
        int y = GridPane.getRowIndex(node);
        list.remove(node);
        field.getChildren().remove(node);
        emptyFields.add(new Coordinate(x, y));
    }
}
