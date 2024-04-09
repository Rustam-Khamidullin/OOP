package ru.nsu.khamidullin.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.khamidullin.model.Direction;

public class KeyHandler {
    private final Controller controller;

    public KeyHandler(Controller controller) {
        this.controller = controller;
    }


    public void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code.isArrowKey()) {
            directionHandle(code);
        }
    }

    private void directionHandle(KeyCode code) {
        Direction prevDirection = controller.getPrevDirection();

        if (code == KeyCode.UP && prevDirection != Direction.DOWN) {
            controller.setDirection(Direction.UP);
        } else if (code == KeyCode.DOWN && prevDirection != Direction.UP) {
            controller.setDirection(Direction.DOWN);
        } else if (code == KeyCode.LEFT && prevDirection != Direction.RIGHT) {
            controller.setDirection(Direction.LEFT);
        } else if (code == KeyCode.RIGHT && prevDirection != Direction.LEFT) {
            controller.setDirection(Direction.RIGHT);
        }
    }
}
