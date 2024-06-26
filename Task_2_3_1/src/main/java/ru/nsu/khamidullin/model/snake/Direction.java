package ru.nsu.khamidullin.model.snake;

import lombok.Getter;

/**
 * Direction.
 */
@Getter
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);
    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
