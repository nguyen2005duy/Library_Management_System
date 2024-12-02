package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.AdminMenuOptions;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
public class AdminMenuController implements Initializable {
    @FXML
    private HBox book;

    @FXML
    private HBox dashboard;

    @FXML
    private HBox log_out;

    @FXML
    private HBox members;

    @FXML
    private HBox profile;

    @FXML
    void loadBook(MouseEvent event) {
        Model.getInstance().getFactoryViews().getAdminSelectedMenuItem().set(AdminMenuOptions.CHECK_OUT);
        clear();
        book.getStylesheets().add(getClass().getResource("/Styles/admin_current.css").toExternalForm());
        members.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
        profile.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
        dashboard.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
    }

    @FXML
    void loadDashboard(MouseEvent event) {
        Model.getInstance().getFactoryViews().getAdminSelectedMenuItem().set(AdminMenuOptions.DASHBOARD);
        clear();
        book.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
        members.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
        profile.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
        dashboard.getStylesheets().add(getClass().getResource("/Styles/admin_current.css").toExternalForm());
    }

    @FXML
    void loadMember(MouseEvent event) {
        Model.getInstance().getFactoryViews().getAdminSelectedMenuItem().set(AdminMenuOptions.MEMBERS);
        clear();
        book.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
        members.getStylesheets().add(getClass().getResource("/Styles/admin_current.css").toExternalForm());
        profile.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
        dashboard.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
    }

    @FXML
    void loadProfile(MouseEvent event) {
        Model.getInstance().getFactoryViews().getAdminSelectedMenuItem().set(AdminMenuOptions.PROFILE);
        clear();
        book.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
        members.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
        profile.getStylesheets().add(getClass().getResource("/Styles/admin_current.css").toExternalForm());
        dashboard.getStylesheets().add(getClass().getResource("/Styles/admin_pane.css").toExternalForm());
    }

    @FXML
    void quit(MouseEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void clear(){
        book.getStylesheets().clear();
        dashboard.getStylesheets().clear();
        members.getStylesheets().clear();
        profile.getStylesheets().clear();
    }
}
