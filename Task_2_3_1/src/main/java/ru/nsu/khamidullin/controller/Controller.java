package ru.nsu.khamidullin.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.khamidullin.model.Game;
import ru.nsu.khamidullin.model.snake.Direction;

public class Controller {
    private final Game game;

    public Controller(Game game) {
        this.game = game;
    }

    public void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code.isArrowKey()) {
            directionHandle(code);
        }
    }

    private void directionHandle(KeyCode code) {
        if (code == KeyCode.UP) {
            game.directPlayerSnake(Direction.UP);
        } else if (code == KeyCode.DOWN) {
            game.directPlayerSnake(Direction.DOWN);
        } else if (code == KeyCode.LEFT) {
            game.directPlayerSnake(Direction.LEFT);
        } else if (code == KeyCode.RIGHT) {
            game.directPlayerSnake(Direction.RIGHT);
        }
    }
}
