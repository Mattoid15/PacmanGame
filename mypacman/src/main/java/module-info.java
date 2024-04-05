module com.mygame {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mygame to javafx.fxml;
    exports com.mygame;
}
