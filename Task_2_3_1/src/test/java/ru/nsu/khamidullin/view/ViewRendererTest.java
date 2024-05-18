package ru.nsu.khamidullin.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.model.CellType;

/**
 * Test class.
 */
class ViewRendererTest {
    @Test
    void testRender() {
        int width = 10;
        int height = 10;

        CellType[][] field = new CellType[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    field[i][j] = CellType.WALL;
                } else {
                    field[i][j] = CellType.EMPTY;
                }
            }
        }
        field[5][5] = CellType.SNAKE;
        field[3][4] = CellType.FOOD;

        ViewRenderer viewRenderer = new ViewRenderer(800, 600, width, height);
        viewRenderer.render(field);

        var gridPane = viewRenderer.getView();

        Assertions.assertNotNull(gridPane);

        Assertions.assertEquals(width * height, gridPane.getChildren().size());
    }
}