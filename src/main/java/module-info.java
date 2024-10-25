module org.example.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    exports Application.backend;
    requires javafx.graphics;


<<<<<<< HEAD
    opens frontend to javafx.fxml;
    exports frontend;
    opens Application.backend to javafx.fxml;
    exports Application.backend.Controller;
    opens Application.backend.Controller to javafx.fxml;
    exports Application.backend.Connection;
    opens Application.backend.Connection to javafx.fxml;
=======
    opens Application to javafx.fxml;
    exports Application;
    exports Application.backend;
    opens Application.backend to javafx.fxml;
    exports Application.frontend;
    opens Application.frontend to javafx.fxml;
>>>>>>> b628945d9ee2db00728341637ff7208c24a388ab

}