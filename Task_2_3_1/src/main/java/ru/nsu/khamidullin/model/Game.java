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


/**
 * Represents the game logic and state.
 */
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

    /**
     * Constructs a Game object with the specified parameters.
     *
     * @param foodNumber   the number of food items in the game.
     * @param width        the width of the game field.
     * @param height       the height of the game field.
     * @param winSize      the size required to win the game.
     * @param viewRenderer the view renderer for displaying the game.
     */
    public Game(int foodNumber, int width, int height, int winSize, ViewRenderer viewRenderer) {
        this.winSize = winSize;
        this.foodNumber = foodNumber;
        this.viewRenderer = viewRenderer;
        this.width = width;
        this.height = height;

        field = new CellType[width][height];

        initGame();
    }

    /**
     * Initialization.
     */
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

    /**
     * Main loop.
     */
    private void loop() {
        moveSnake(playerSnake);
        checkDefeat();
        checkWin();

        spawnFood();

        viewRenderer.render(field);
    }

    /**
     * Directs the player-controlled snake in the specified direction.
     *
     * @param direction the direction to move the snake.
     */
    public void directPlayerSnake(Direction direction) {
        playerSnake.setDirection(direction);
    }

    /**
     * Moving.
     *
     * @param snake move snake.
     */
    private void moveSnake(Snake snake) {
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

    /**
     * Add new food.
     */
    public void spawnFood() {
        var generatedFood = foodManager.generateFood(getEmptyCoordinates());

        for (var newFood : generatedFood) {
            place(newFood, CellType.FOOD);
        }
    }

    /**
     * Check if player defeat.
     */
    private void checkDefeat() {
        defeat |= playerSnake.checkSelfCollision();

        if (defeat) {
            initGame();
        }
    }

    /**
     * Check if player win.
     */
    public void checkWin() {
        if (playerSnake.size() == winSize) {
            initGame();
        }
    }

    /**
     * Empty coordinates.
     *
     * @return empty coordinates.
     */
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

    /**
     * Places the specified cell type at the given coordinate in the game field.
     *
     * @param coordinate the coordinate where the cell type will be placed.
     * @param cellType   the type of cell to be placed.
     * @return the previous cell type at the specified coordinate.
     */
    private CellType place(Coordinate coordinate, CellType cellType) {
        var result = field[coordinate.getX()][coordinate.getY()];
        field[coordinate.getX()][coordinate.getY()] = cellType;
        return result;
    }

    /**
     * Retrieves the cell type at the specified coordinate in the game field.
     *
     * @param coordinate the coordinate of the cell to retrieve.
     * @return the cell type at the specified coordinate.
     */
    private CellType getCell(Coordinate coordinate) {
        return field[coordinate.getX()][coordinate.getY()];
    }

    /**
     * Cleans the game field by setting all cells to EMPTY.
     *
     * @param field the field to be cleaned.
     */
    private void cleanField(CellType[][] field) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = CellType.EMPTY;
            }
        }
    }

    /**
     * Sets the outer walls of the game field to WALL.
     */
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