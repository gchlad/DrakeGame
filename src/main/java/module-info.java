module com.game.drake {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens drake to javafx.fxml;
    exports drake;
}