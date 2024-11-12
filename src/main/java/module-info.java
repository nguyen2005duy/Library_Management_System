module org.example.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;

    // Export and open the backend packages
    opens Application to javafx.fxml;
    exports Application.backend.Controller;
    opens Application.backend.Controller to javafx.fxml;
    exports Application.backend.Connection;
    opens Application.backend.Connection to javafx.fxml;
    exports  Application.backend.Class.Library;
    exports Application.backend.Class.Exceptions;
    exports Application.frontend;
    opens Application.frontend to javafx.fxml;
    // Export and open the frontend packages
}
