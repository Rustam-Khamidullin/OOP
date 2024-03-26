package ru.nsu.khamidullin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeGame extends Application {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller(WIDTH, HEIGHT);
        Scene scene = new Scene(controller.getView(), WIDTH * SnakeElem.SIZE, HEIGHT * SnakeElem.SIZE);

        scene.setOnKeyPressed(controller::handleKeyPress);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();

        controller.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}