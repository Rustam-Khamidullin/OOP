package ru.nsu.khamidullin;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {
    private final int width;
    private final int height;
    private final GridPane field;
    private final LinkedList<SnakeElem> snake;
    private final Food food;
    private Direction direction = Direction.RIGHT;

    public Controller(int width, int height) {
        this.width = width;
        this.height = height;
        this.field = new GridPane(width, height);
        this.snake = new LinkedList<>();
        this.food = new Food();
        field.setHgap(0);
        field.setVgap(0);
        initGame();
    }

    public void initGame() {
        field.getChildren().clear();
        snake.clear();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Rectangle cell = new Rectangle(SnakeElem.SIZE, SnakeElem.SIZE);
                cell.setFill(Color.BLACK);
                field.add(cell, x, y);
            }
        }

        SnakeElem head = new SnakeElem();
        snake.add(head);

        field.add(head, width / 2, height / 2);
        generateFood();
    }

    public void startGame() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            moveSnake();
            checkCollisions();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    void checkCollisions() {
        SnakeElem head = snake.getFirst();
        int headX = GridPane.getColumnIndex(head);
        int headY = GridPane.getRowIndex(head);

        for (var elem : snake) {
            if (elem != head) {
                int elemX = GridPane.getColumnIndex(elem);
                int elemY = GridPane.getRowIndex(elem);

                if (headY == elemY && headX == elemX) {
                    initGame();
                }
            }
        }
    }

    public void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.UP && direction != Direction.DOWN) {
            direction = Direction.UP;
        } else if (code == KeyCode.DOWN && direction != Direction.UP) {
            direction = Direction.DOWN;
        } else if (code == KeyCode.LEFT && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        } else if (code == KeyCode.RIGHT && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
    }

    private void generateFood() {
        int x = ThreadLocalRandom.current().nextInt(0, width);
        int y = ThreadLocalRandom.current().nextInt(0, height);

        if (!isCellOccupied(x, y)) {
            field.add(food, x, y);
        } else {
            generateFood();
        }
    }

    public GridPane getView() {
        return field;
    }

    private void moveSnake() {
        SnakeElem head = snake.getFirst();
        int newHeadX = (GridPane.getColumnIndex(head) + direction.getX() + height) % height;
        int newHeadY = (GridPane.getRowIndex(head) + direction.getY() + width) % width;

        SnakeElem newHead = new SnakeElem();
        snake.addFirst(newHead);
        field.add(newHead, newHeadX, newHeadY);

        if (GridPane.getColumnIndex(food) == newHeadX && GridPane.getRowIndex(food) == newHeadY) {
            field.getChildren().remove(food);
            generateFood();
        } else {
            var last = snake.removeLast();
            field.getChildren().remove(last);
        }
    }

    private boolean isCellOccupied(int x, int y) {
        for (Node node : snake) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                return true;
            }
        }
        return false;
    }
}
