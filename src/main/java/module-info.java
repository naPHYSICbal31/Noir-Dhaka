module com.example.noir {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.noir to javafx.fxml;
    exports com.example.noir;
}