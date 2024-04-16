package ru.nsu.khamidullin.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.khamidullin.model.Game;
import ru.nsu.khamidullin.model.snake.Direction;
/**
 * Class responsible for controlling the game process.
 */
public class Controller {
    private final Game game;

    /**
     * Constructor for the Controller class.
     *
     * @param game the instance of the game to interact with.
     */
    public Controller(Game game) {
        this.game = game;
    }

    /**
     * Method to handle key press events.
     *
     * @param event the key event.
     */
    public void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code.isArrowKey()) {
            directionHandle(code);
        }
    }

    /**
     * Method to handle arrow key presses.
     *
     * @param code the code of the key pressed by the user.
     */
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