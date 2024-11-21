module org.example.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    requires de.jensd.fx.glyphs.fontawesome;
    requires mysql.connector.j;

    // Export and open the backend packages
    opens Application to javafx.fxml;
    exports Application.com.jmc.backend.Controller;
    opens Application.com.jmc.backend.Controller to javafx.fxml;
    exports Application.com.jmc.backend.Connection;
    opens Application.com.jmc.backend.Connection to javafx.fxml;
    exports Application.com.jmc.backend.Class.Library;
    exports Application.com.jmc.backend.Class.Exceptions;
    exports Application.frontend;
    opens Application.frontend to javafx.fxml;
    exports Application.com.jmc.backend.Class.Books;
    exports Application.com.jmc.backend.Class.User_Information;
    // Export and open the frontend packages
}
