package ru.nsu.khamidullin.model;

import javafx.application.Platform;
import lombok.Getter;
import lombok.SneakyThrows;
import ru.nsu.khamidullin.model.food.FoodManager;
import ru.nsu.khamidullin.model.snake.Direction;
import ru.nsu.khamidullin.model.snake.Snake;
import ru.nsu.khamidullin.view.ViewRenderer;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Game extends Thread {
    private final CellType[][] field;
    private final int width;
    private final int height;
    private final int foodNumber;
    private final int winSize;
    private final ViewRenderer viewRenderer;
    private Snake playerSnake;
    private FoodManager foodManager;
    private boolean defeat;

    public Game(int foodNumber, int width, int height, int winSize, ViewRenderer viewRenderer) {
        this.winSize = winSize;
        this.foodNumber = foodNumber;
        this.viewRenderer = viewRenderer;
        this.width = width;
        this.height = height;

        field = new CellType[width][height];

        initGame();
    }

    private void initGame() {
        cleanField(field);
        setWall();

        Coordinate snakeCoordinate = new Coordinate(width / 2, height / 2);
        playerSnake = new Snake(snakeCoordinate, width, height);
        place(snakeCoordinate, CellType.SNAKE);

        defeat = false;

        foodManager = new FoodManager(foodNumber);
        spawnFood();
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            Platform.runLater(this::loop);

            Thread.sleep(100);
        }
    }

    private void loop() {
        moveSnake(playerSnake);
        checkDefeat();
        checkWin();

        spawnFood();

        viewRenderer.render(field);
    }

    public void directPlayerSnake(Direction direction) {
        playerSnake.setDirection(direction);
    }

    public void moveSnake(Snake snake) {
        for (var coordinate : snake.getSnake()) {
            place(coordinate, CellType.EMPTY);
        }
        var newHead = snake.move();

        if (getCell(newHead) == CellType.FOOD) {
            foodManager.eat();
        } else {
            snake.removeTail();
        }

        for (var coordinate : snake.getSnake()) {
            defeat |= place(coordinate, CellType.SNAKE) == CellType.WALL;
        }
    }

    public void spawnFood() {
        var generatedFood = foodManager.generateFood(getEmptyCoordinates());

        for (var newFood : generatedFood) {
            place(newFood, CellType.FOOD);
        }
    }

    public void checkDefeat() {
        defeat |= playerSnake.checkSelfCollision();

        if (defeat) {
            initGame();
        }
    }

    public void checkWin() {
        if (playerSnake.size() == winSize) {
            initGame();
        }
    }

    private List<Coordinate> getEmptyCoordinates() {
        List<Coordinate> emptyCoordinates = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (field[i][j] == CellType.EMPTY) {
                    emptyCoordinates.add(new Coordinate(i, j));
                }
            }
        }
        return emptyCoordinates;
    }

    private CellType place(Coordinate coordinate, CellType cellType) {
        var result = field[coordinate.getX()][coordinate.getY()];
        field[coordinate.getX()][coordinate.getY()] = cellType;
        return result;
    }

    private CellType getCell(Coordinate coordinate) {
        return field[coordinate.getX()][coordinate.getY()];
    }

    private void cleanField(CellType[][] field) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = CellType.EMPTY;
            }
        }
    }

    private void setWall() {
        for (int i = 0; i < width; i++) {
            field[i][0] = CellType.WALL;
            field[i][height - 1] = CellType.WALL;
        }
        for (int i = 0; i < height; i++) {
            field[0][i] = CellType.WALL;
            field[width - 1][i] = CellType.WALL;
        }
    }
}
