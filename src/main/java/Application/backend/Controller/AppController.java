package Application.backend.Controller;

import Application.backend.Class.Library.GoogleBooksAPI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;


import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private ImageView book1;

    @FXML
    private ImageView book2;

    @FXML
    private ImageView book3;

    @FXML
    private ImageView book4;

    @FXML
    private HBox category;

    @FXML
    private HBox discover;

    @FXML
    private HBox library;

    @FXML
    private HBox log_out;

    @FXML
    private TextField search_bar;

    @FXML
    private Button search_button;

    @FXML
    private MenuButton search_menu;

    @FXML
    private HBox trending;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GoogleBooksAPI api = new GoogleBooksAPI();

    }
}
