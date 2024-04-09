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
    exports ru.nsu.khamidullin.model.node;
    opens ru.nsu.khamidullin.model.node to javafx.fxml;
}