package Application.com.jmc.backend.Controller.Client;

import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private final Member cur = (Member) Library.current_user;
    @FXML
    private Button cancel;

    @FXML
    private TextField Role;

    @FXML
    private Button change_password_button;

    @FXML
    private TextField email_field;

    @FXML
    private TextField about_me;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private Button save_changes;
    @FXML
    void cancel(MouseEvent event) {
        first_name_field.setText("");
        last_name_field.setText("");
        email_field.setText("");
        about_me.setText("");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first_name_field.setPromptText(cur.getFirstName());
        last_name_field.setPromptText(cur.getLastName());
        email_field.setPromptText(cur.getEmail());

    }

    
}
