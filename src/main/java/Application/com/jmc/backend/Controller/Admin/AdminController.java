package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Model.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getFactoryViews().getAdminSelectedMenuItem().addListener((observable, oldVal, newVal) -> {

        });
    }
}
