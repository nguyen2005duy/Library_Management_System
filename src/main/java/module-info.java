module org.example.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    requires javafx.graphics;


    opens frontend to javafx.fxml;
    exports frontend;

}