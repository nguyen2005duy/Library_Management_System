package Application.com.jmc.backend.Controller.Client;

import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.ClientMenuOptions;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {

    @FXML
    private HBox discover;

    @FXML
    private FontAwesomeIconView discover_icon;

    @FXML
    private Pane discover_pane;

    @FXML
    private HBox favourite;

    @FXML
    private FontAwesomeIconView favourite_icon;

    @FXML
    private Pane favourite_pane;

    @FXML
    private HBox library;

    @FXML
    private FontAwesomeIconView library_icon;

    @FXML
    private Pane library_pane;

    @FXML
    private HBox log_out;

    @FXML
    private HBox profile;

    @FXML
    private HBox trending;

    @FXML
    private FontAwesomeIconView trending_icon;

    Stage stage;

    @FXML
    private Pane trending_pane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void loadTrending(MouseEvent event) {
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.TRENDING);
        clear();
        trending.getStylesheets().add(getClass().getResource("/Styles/current.css").toExternalForm());
        discover.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        library.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        profile.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        favourite.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());

    }

    @FXML
    private void loadDiscover(MouseEvent event) {
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.DISCOVER);
        clear();
        discover.getStylesheets().add(getClass().getResource("/Styles/current.css").toExternalForm());
        library.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        trending.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        profile.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        favourite.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());

    }

    @FXML
    void loadProfile(MouseEvent event) {
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.PROFILE);
        clear();
        trending.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        discover.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        library.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        profile.getStylesheets().add(getClass().getResource("/Styles/current.css").toExternalForm());
        favourite.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());

    }

    public void loadFavourite(MouseEvent mouseEvent) {
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.FAVOURITE);
        clear();
        trending.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        discover.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        library.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        profile.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        favourite.getStylesheets().add(getClass().getResource("/Styles/current.css").toExternalForm());
    }


    public void quit(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit!");
        alert.setContentText("Are you sure you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) trending.getScene().getWindow();
            stage.close();
        }
    }

    public void loadLibrary(MouseEvent mouseEvent) {
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.LIBRARY);
        clear();
        trending.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        discover.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        library.getStylesheets().add(getClass().getResource("/Styles/current.css").toExternalForm());
        profile.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
        favourite.getStylesheets().add(getClass().getResource("/Styles/pane.css").toExternalForm());
    }

    public void clear(){
        trending.getStylesheets().clear();
        discover.getStylesheets().clear();
        library.getStylesheets().clear();
        trending.getStylesheets().clear();
        profile.getStylesheets().clear();
        favourite.getStylesheets().clear();

    }


}
