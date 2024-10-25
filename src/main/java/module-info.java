module org.example.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    exports backend;
    requires javafx.graphics;


    opens frontend to javafx.fxml;
    exports frontend;
    opens backend to javafx.fxml;
    exports backend.Controller;
    opens backend.Controller to javafx.fxml;
    exports backend.Connection;
    opens backend.Connection to javafx.fxml;

}