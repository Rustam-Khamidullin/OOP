package ru.nsu.khamidullin.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.model.snake.Direction;
import ru.nsu.khamidullin.model.snake.Snake;
import ru.nsu.khamidullin.view.ViewRenderer;

/**
 * Test class.
 */
class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        ViewRenderer viewRenderer = new ViewRenderer(800, 600, 20, 20);
        game = new Game(3, 20, 20, 10, viewRenderer);
    }

    @Test
    void testDirectPlayerSnake() {
        game.directPlayerSnake(Direction.DOWN);
        Assertions.assertEquals(Direction.DOWN, game.getPlayerSnake().getDirection());
    }

    @Test
    void testSpawnFood() {
        game.spawnFood();
        int foodCount = 0;
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                if (game.getField()[i][j] == CellType.FOOD) {
                    foodCount++;
                }
            }
        }
        Assertions.assertEquals(3, foodCount);
    }

    @Test
    void testCheckWin() {
        Assertions.assertFalse(game.isDefeat());
        Snake snake = game.getPlayerSnake();
        while (snake.size() < game.getWinSize()) {
            snake.getSnake().add(new Coordinate(0, 0));
            game.checkWin();
            Assertions.assertFalse(game.isDefeat());
        }
        snake.getSnake().add(new Coordinate(0, 0));
        game.checkWin();
        Assertions.assertFalse(game.isDefeat());
    }
}