package ru.nsu.khamidullin.model.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.model.Coordinate;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class.
 */
public class SnakeTest {

    @Test
    void testMove() {
        Snake snake = new Snake(new Coordinate(0, 0), 10, 10);
        snake.move();
        Assertions.assertEquals(new Coordinate(1, 0), snake.head());
    }

    @Test
    void testRemoveTail() {
        Snake snake = new Snake(new Coordinate(0, 0), 10, 10);
        snake.move();
        snake.move();
        snake.removeTail();
        Assertions.assertEquals(2, snake.size());
        snake.removeTail();
        Assertions.assertEquals(1, snake.size());
    }

    @Test
    void testCheckSelfCollision() {
        Snake snake = new Snake(new Coordinate(0, 0), 10, 10);
        Assertions.assertFalse(snake.checkSelfCollision());
        snake.move();
        Assertions.assertFalse(snake.checkSelfCollision());
        snake.setDirection(Direction.UP);
        snake.move();
        assertFalse(snake.checkSelfCollision());
    }

    @Test
    void testSetDirection() {
        Snake snake = new Snake(new Coordinate(0, 0), 10, 10);
        snake.setDirection(Direction.UP);
        Assertions.assertEquals(Direction.UP, snake.getDirection());
        snake.setDirection(Direction.RIGHT);
        Assertions.assertEquals(Direction.RIGHT, snake.getDirection());
        snake.move();
        snake.setDirection(Direction.LEFT);
        Assertions.assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    void testSize() {
        Snake snake = new Snake(new Coordinate(0, 0), 10, 10);
        Assertions.assertEquals(1, snake.size());
        snake.move();
        Assertions.assertEquals(2, snake.size());
        snake.removeTail();
        Assertions.assertEquals(1, snake.size());
    }
}
