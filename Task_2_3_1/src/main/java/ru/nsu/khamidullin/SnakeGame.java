package ru.nsu.khamidullin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.nsu.khamidullin.controller.Controller;
import ru.nsu.khamidullin.model.Game;
import ru.nsu.khamidullin.view.ViewRenderer;

public class SnakeGame extends Application {
    private static final int WIN_WIDTH = 800;
    private static final int WIN_HEIGHT = 600;
    private static final int WIDTH = 32;
    private static final int HEIGHT = 24;
    private static final int FOOD_NUMBER = 3;
    private static final int MAX_SNAKE_SIZE = 20;

    @Override
    public void start(Stage primaryStage) {
        ViewRenderer viewRenderer = new ViewRenderer(WIN_WIDTH, WIN_HEIGHT, WIDTH, HEIGHT);
        Game game = new Game(FOOD_NUMBER, WIDTH, HEIGHT, MAX_SNAKE_SIZE, viewRenderer);
        Controller controller = new Controller(game);

        GridPane root = viewRenderer.getView();
        Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
        scene.setOnKeyPressed(controller::handleKeyPress);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Updating Scene");
        primaryStage.show();

        game.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}