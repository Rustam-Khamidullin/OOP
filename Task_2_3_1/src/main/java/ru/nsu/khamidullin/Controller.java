package ru.nsu.khamidullin;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {
    private static final int MIN_BOARD_SIZE = 5;
    public static final int SELL_SIZE = 20;
    private final int width;
    private final int height;
    private final int foodNumber;
    private final int winSize;
    private final GridPane field;
    private final List<SnakeElem> snake;
    private final List<Food> food;
    private final Set<Coordinate> emptyFields;
    @Getter
    @Setter
    private Direction direction;
    @Getter
    @Setter
    private Direction prevDirection;

    public Controller(int width, int height, int foodNumber, int winSize)
            throws IllegalArgumentException {
        this.width = width;
        this.height = height;
        this.foodNumber = foodNumber;
        this.winSize = winSize;

        checkParameters();

        this.field = new GridPane(width, height);
        field.setHgap(0);
        field.setVgap(0);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Rectangle cell = new Rectangle(SELL_SIZE, SELL_SIZE);
                cell.setFill(Color.BLACK);
                field.add(cell, x, y);
            }
        }

        this.snake = new LinkedList<>();
        this.food = new LinkedList<>();
        this.emptyFields = new HashSet<>();

        initGame();
    }

    public void initGame() {
        var fieldChildren = field.getChildren();

        for (var snakeElem : snake) {
            fieldChildren.remove(snakeElem);
        }
        for (var foo : food) {
            fieldChildren.remove(foo);
        }

        snake.clear();
        food.clear();
        emptyFields.clear();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                emptyFields.add(new Coordinate(i, j));
            }
        }

        SnakeElem head = new SnakeElem();
        snake.add(head);
        field.add(head, width / 2, height / 2);
        direction = Direction.RIGHT;
        prevDirection = Direction.RIGHT;

        generateFood();
    }

    public void startGame() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            moveSnake();
            checkDefeat();
            generateFood();
            checkWin();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    void checkDefeat() {
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

    void checkWin() {
        if (snake.size() == winSize) {
            initGame();
        }
    }

    private void generateFood() {
        while (food.size() < foodNumber && !emptyFields.isEmpty()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(emptyFields.size());
            Coordinate newFoodCoordinate = emptyFields.stream().skip(randomIndex).findFirst().orElse(null);

            Food newFood = new Food();

            addElem(
                    newFoodCoordinate.x(),
                    newFoodCoordinate.y(),
                    newFood,
                    food
            );
        }
    }


    private void moveSnake() {
        SnakeElem head = snake.getFirst();

        int newHeadX = (GridPane.getColumnIndex(head) + direction.getX() + height) % height;
        int newHeadY = (GridPane.getRowIndex(head) + direction.getY() + width) % width;
        SnakeElem newHead = new SnakeElem();

        prevDirection = direction;

        Food eatenFood = isOccupied(newHeadX, newHeadY, food);

        if (eatenFood == null) {
            removeElem(snake.getLast(), snake);
        } else {
            removeElem(eatenFood, food);
        }

        addElem(newHeadX, newHeadY, newHead, snake);
    }


    private <T extends Node> T isOccupied(int x, int y, List<T> list) {
        for (T node : list) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                return node;
            }
        }
        return null;
    }


    <T extends Node> void addElem(int x, int y, T node, List<T> list) {
        list.addFirst(node);
        field.add(node, x, y);
        emptyFields.remove(new Coordinate(x, y));
    }

    <T extends Node> void removeElem(T node, List<T> list) {
        int x = GridPane.getColumnIndex(node);
        int y = GridPane.getRowIndex(node);
        list.remove(node);
        field.getChildren().remove(node);
        emptyFields.add(new Coordinate(x, y));
    }


    private void checkParameters() throws IllegalArgumentException {
        if (width < MIN_BOARD_SIZE || height < MIN_BOARD_SIZE) {
            throw new IllegalArgumentException("Incorrect board size");
        }
        if (winSize > height * width) {
            throw new IllegalArgumentException("Impossible to win");
        }
    }

    public GridPane getView() {
        return field;
    }
}
