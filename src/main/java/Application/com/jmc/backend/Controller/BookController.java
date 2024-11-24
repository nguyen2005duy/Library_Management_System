package Application.com.jmc.backend.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class BookController implements Initializable {

    @FXML
    private Label Page;

    @FXML
    private Label Rating;

    @FXML
    private Label author;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;

    @FXML
    private TextArea description;

    @FXML
    private Button favourite_button;

    @FXML
    private Button read_button;

    @FXML
    private Label status;


    @Override
    public void initialize (URL url, ResourceBundle rb) {

    }
}
