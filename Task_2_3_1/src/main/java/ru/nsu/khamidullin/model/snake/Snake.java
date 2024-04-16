package ru.nsu.khamidullin.model.snake;

import lombok.Getter;
import ru.nsu.khamidullin.model.Coordinate;

import java.util.Deque;
import java.util.LinkedList;

public class Snake {
    private static final Direction DEFAULT_DIRECTION = Direction.RIGHT;
    private final int width;
    private final int height;
    @Getter
    private final Deque<Coordinate> snake;
    @Getter
    private Direction direction;
    private Direction previousDirection;


    public Snake(Coordinate startCoordinate, int width, int height) {
        this.width = width;
        this.height = height;

        direction = DEFAULT_DIRECTION;
        previousDirection = DEFAULT_DIRECTION;

        snake = new LinkedList<>();
        snake.add(startCoordinate);
    }

    public Coordinate move() {
        Coordinate head = head();
        Coordinate newHead = new Coordinate(
                (head.getX() + direction.getX() + width) % width,
                (head.getY() + direction.getY() + height) % height);

        snake.addFirst(newHead);

        previousDirection = direction;
        return newHead;
    }

    public Coordinate head() {
        return snake.getFirst();
    }

    public void removeTail() {
        if (!snake.isEmpty()) {
            snake.removeLast();
        }
    }

    public boolean checkSelfCollision() {
        var head = head();
        for (var elem : snake) {
            if (elem != head && elem.equals(head)) {
                return true;
            }
        }
        return false;
    }

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

    public int size() {
        return snake.size();
    }
}
