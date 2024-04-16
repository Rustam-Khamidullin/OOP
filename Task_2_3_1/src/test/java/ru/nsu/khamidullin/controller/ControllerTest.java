package ru.nsu.khamidullin.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.model.Game;
import ru.nsu.khamidullin.model.snake.Direction;
import ru.nsu.khamidullin.view.ViewRenderer;

class ControllerTest {
    private Game game;
    private Controller controller;

    @BeforeEach
    void setUp() {
        ViewRenderer viewRenderer = new ViewRenderer(800, 600, 20, 20);
        game = new Game(3, 20, 20, 10, viewRenderer);
        controller = new Controller(game);
    }

    @Test
    void testHandleKeyPressUp() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);
        controller.handleKeyPress(event);
        Assertions.assertEquals(game.getPlayerSnake().getDirection(), Direction.UP);
    }

    @Test
    void testHandleKeyPressDown() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false);
        controller.handleKeyPress(event);
        Assertions.assertEquals(Direction.DOWN, game.getPlayerSnake().getDirection());
    }

    @Test
    void testHandleKeyPressLeft() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);
        controller.handleKeyPress(event);
        Assertions.assertEquals(Direction.RIGHT, game.getPlayerSnake().getDirection());
    }

    @Test
    void testHandleKeyPressRight() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false);
        controller.handleKeyPress(event);
        Assertions.assertEquals(Direction.RIGHT, game.getPlayerSnake().getDirection());
    }

    @Test
    void testHandleKeyPressNonArrowKey() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false);
        controller.handleKeyPress(event);
        Assertions.assertNotEquals(Direction.UP, game.getPlayerSnake().getDirection());
        Assertions.assertNotEquals(Direction.DOWN, game.getPlayerSnake().getDirection());
        Assertions.assertNotEquals(Direction.LEFT, game.getPlayerSnake().getDirection());
        Assertions.assertEquals(Direction.RIGHT, game.getPlayerSnake().getDirection());
    }
}