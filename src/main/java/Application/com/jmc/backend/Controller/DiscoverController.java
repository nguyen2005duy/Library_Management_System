package Application.com.jmc.backend.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class DiscoverController implements Initializable {

    @FXML
    private ImageView book1;

    @FXML
    private ImageView book2;

    @FXML
    private ImageView book3;

    @FXML
    private ImageView book4;

    @FXML
    private TextField search_bar;

    @FXML
    private Button search_button;

    @FXML
    private MenuButton search_menu;

    @Override
    public void initialize (URL url, ResourceBundle rb) {

    }

}
