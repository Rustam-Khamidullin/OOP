module ru.nsu.khamidullin {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires com.almasb.fxgl.all;

    opens ru.nsu.khamidullin to javafx.fxml;
    exports ru.nsu.khamidullin;
}