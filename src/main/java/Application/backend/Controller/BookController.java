package Application.backend.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BookController implements Initializable {

    @FXML
    private HBox category;

    @FXML
    private HBox discover;

    @FXML
    private HBox library;

    @FXML
    private HBox log_out;

    @FXML
    private HBox trending;


    @Override
    public void initialize (URL url, ResourceBundle rb) {

    }
}
