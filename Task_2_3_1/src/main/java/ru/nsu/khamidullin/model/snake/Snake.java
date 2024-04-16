package ru.nsu.khamidullin.model.snake;

import lombok.Getter;
import ru.nsu.khamidullin.model.Coordinate;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Class representing the snake entity in the game.
 */
public class Snake {
    private static final Direction DEFAULT_DIRECTION = Direction.RIGHT;
    private final int width;
    private final int height;
    @Getter
    private final Deque<Coordinate> snake;
    @Getter
    private Direction direction;
    private Direction previousDirection;


    /**
     * Constructs a Snake object with the specified starting coordinate, width, and height.
     *
     * @param startCoordinate the starting coordinate of the snake.
     * @param width           the width of the game field.
     * @param height          the height of the game field.
     */
    public Snake(Coordinate startCoordinate, int width, int height) {
        this.width = width;
        this.height = height;

        direction = DEFAULT_DIRECTION;
        previousDirection = DEFAULT_DIRECTION;

        snake = new LinkedList<>();
        snake.add(startCoordinate);
    }

    /**
     * Moves the snake in its current direction and returns the new head coordinate.
     *
     * @return the new head coordinate of the snake after moving.
     */
    public Coordinate move() {
        Coordinate head = head();
        Coordinate newHead = new Coordinate(
                (head.getX() + direction.getX() + width) % width,
                (head.getY() + direction.getY() + height) % height);

        snake.addFirst(newHead);

        previousDirection = direction;
        return newHead;
    }

    /**
     * Retrieves the current head coordinate of the snake.
     *
     * @return the coordinate of the snake's head.
     */
    public Coordinate head() {
        return snake.getFirst();
    }

    /**
     * Removes the last segment of the snake's tail.
     */
    public void removeTail() {
        if (!snake.isEmpty()) {
            snake.removeLast();
        }
    }

    /**
     * Checks if the snake has collided with itself.
     *
     * @return true if the snake has collided with itself, otherwise false.
     */
    public boolean checkSelfCollision() {
        var head = head();
        for (var elem : snake) {
            if (elem != head && elem.equals(head)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the direction of the snake's movement.
     *
     * @param newDirection the new direction to set for the snake.
     */
    public void setDirection(Direction newDirection) {
        if (newDirection == Direction.UP && previousDirection != Direction.DOWN) {
            direction = Direction.UP;
        } else if (newDirection == Direction.DOWN && previousDirection != Direction.UP) {
            direction = Direction.DOWN;
        } else if (newDirection == Direction.LEFT && previousDirection != Direction.RIGHT) {
            direction = Direction.LEFT;
        } else if (newDirection == Direction.RIGHT && previousDirection != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
    }

    /**
     * Retrieves the size of the snake (number of segments).
     *
     * @return the size of the snake.
     */
    public int size() {
        return snake.size();
    }
}