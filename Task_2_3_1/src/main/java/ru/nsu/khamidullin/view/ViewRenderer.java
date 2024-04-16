package ru.nsu.khamidullin.view;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ru.nsu.khamidullin.model.CellType;

public class ViewRenderer {
    private final GridPane gridPane;
    private final int width;
    private final int height;
    private final int cellSize;

    public ViewRenderer(int winWidth, int winHeight, int width, int height) {
        gridPane = new GridPane(width, height);
        gridPane.setBackground(Background.fill(Color.BLACK));
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        gridPane.getChildren().clear();
        gridPane.setAlignment(Pos.CENTER);

        this.width = width;
        this.height = height;

        cellSize = Math.min(winHeight / height, winWidth / width);
    }

    public void render(CellType[][] field) {
        gridPane.getChildren().clear();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Shape newCell = null;
                if (field[i][j] == CellType.EMPTY) {
                    newCell = new Rectangle(cellSize, cellSize, Color.BLACK);
                } else if (field[i][j] == CellType.SNAKE) {
                    newCell = new Circle(cellSize / 2., Color.RED);
                } else if (field[i][j] == CellType.FOOD) {
                    newCell = new Circle(cellSize / 2., Color.GREEN);
                } else if (field[i][j] == CellType.WALL) {
                    newCell = new Rectangle(cellSize, cellSize, Color.GRAY);
                }

                gridPane.add(newCell, i, j);
            }
        }
    }

    public GridPane getView() {
        return gridPane;
    }
}
