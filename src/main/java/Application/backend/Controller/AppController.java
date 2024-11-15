package Application.backend.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;


import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML
    private Label Title;
    @FXML
    private Label menu;
    @FXML
    private Button discover;
    @FXML
    private Button category;
    @FXML
    private Button library;
    @FXML
    private Button favourite;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
