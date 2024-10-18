module org.example.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    exports backend;
    requires javafx.graphics;


    opens Application to javafx.fxml;
    exports Application;
    exports Application.backend;
    opens Application.backend to javafx.fxml;
    exports Application.frontend;
    opens Application.frontend to javafx.fxml;

}