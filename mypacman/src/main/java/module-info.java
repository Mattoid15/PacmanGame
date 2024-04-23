module com.mygame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.mygame to javafx.fxml;
    exports com.mygame;
}
