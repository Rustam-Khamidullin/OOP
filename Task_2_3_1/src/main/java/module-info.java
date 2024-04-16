module ru.nsu.khamidullin {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires com.almasb.fxgl.all;

    opens ru.nsu.khamidullin to javafx.fxml;
    exports ru.nsu.khamidullin;
    exports ru.nsu.khamidullin.model;
    opens ru.nsu.khamidullin.model to javafx.fxml;
    exports ru.nsu.khamidullin.controller;
    opens ru.nsu.khamidullin.controller to javafx.fxml;
    exports ru.nsu.khamidullin.model.snake;
    opens ru.nsu.khamidullin.model.snake to javafx.fxml;
    exports ru.nsu.khamidullin.model.food;
    opens ru.nsu.khamidullin.model.food to javafx.fxml;
    exports ru.nsu.khamidullin.view;
    opens ru.nsu.khamidullin.view to javafx.fxml;
}