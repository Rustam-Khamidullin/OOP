package ru.nsu.khamidullin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.khamidullin.controller.Controller;
import ru.nsu.khamidullin.controller.KeyHandler;

public class SnakeGame extends Application {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int FOOD_NUMBER = 3;
    private static final int WIN_SIZE = 4;

    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller(WIDTH, HEIGHT, FOOD_NUMBER, WIN_SIZE);
        KeyHandler keyHandler = new KeyHandler(controller);

        Scene scene = new Scene(controller.getView(), WIDTH * Controller.SELL_SIZE, HEIGHT * Controller.SELL_SIZE);
        scene.setOnKeyPressed(keyHandler::handleKeyPress);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();

        controller.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}