package ru.nsu.khamidullin.model;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.khamidullin.model.node.SnakeElem;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final Field field;
    private final List<SnakeElem> snake;
    @Setter
    @Getter
    private Direction direction;

    public Snake(Field field) {
        this.field = field;

        direction = Direction.RIGHT;

        snake = new LinkedList<>();
        var head = new SnakeElem();
        snake.add(head);
    }
}
