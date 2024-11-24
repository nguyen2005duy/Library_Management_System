package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {

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
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void loadTrending(MouseEvent event) {
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set("Trending");
        trending.getStylesheets().add("");
    }

    @FXML
    private void loadDiscover(MouseEvent event) {
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set("Discover");
    }

    public void loadCategory(MouseEvent mouseEvent) {
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set("Category");
    }

    public void quit(MouseEvent mouseEvent) {

    }

    public void loadLibrary(MouseEvent mouseEvent) {
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set("Library");
    }


}
