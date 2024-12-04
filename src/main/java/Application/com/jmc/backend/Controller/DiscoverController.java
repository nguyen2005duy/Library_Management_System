package Application.com.jmc.backend.Controller;
import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.ClientMenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import java.net.URL;
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
