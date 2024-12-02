package Application.com.jmc.backend.Controller.Client;
import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.GoogleBooksAPI;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.ClientMenuOptions;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DiscoverController implements Initializable {

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private Text text3;

    @FXML
    private Text text4;

    @FXML
    private TextField search_bar;

    @FXML
    private Button search_button;

    @FXML
    private MenuButton search_menu;

    @FXML
    void search(MouseEvent event) {
        Model.getInstance().setSearchString(search_bar.getText());
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.SEARCH);
    }


    @Override
    public void initialize (URL url, ResourceBundle rb) {
     /*   try {
            String[] fav = Library.get_user_favourite();
            text1.setData(fav[0]);
            text2.setData(fav[1]);
        } catch(IOException e){

        }*/
    }

}
