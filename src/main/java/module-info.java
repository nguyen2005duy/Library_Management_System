module org.example.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;


    opens frontend to javafx.fxml;
    exports frontend;
}