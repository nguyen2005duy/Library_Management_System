package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Librarian;
import Application.com.jmc.backend.Class.User_Information.Member;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminProfileController implements Initializable {

    //phai dang nhapp
    private final Librarian cur = (Librarian) Library.current_user;
    @FXML
    private TextField Role;

    @FXML
    private TextField about_me;

    @FXML
    private Button cancel_button;

    @FXML
    private Button change_password_button;

    @FXML
    private TextField email_field;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private Button save_changes;

    @FXML
    private Label email;

    @FXML
    private TextField user_name;

    @FXML
    private Label nickname;

    @FXML
    void change_pass(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/change_pass.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Change password");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            System.out.println("can't load new window");
        }
    }

    @FXML
    void cancel(MouseEvent event) {
        first_name_field.setText("");
        last_name_field.setText("");
        email_field.setText("");
        about_me.setText("");
        user_name.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first_name_field.setPromptText(cur.getFirstName());
        last_name_field.setPromptText(cur.getLastName());
        email_field.setPromptText(cur.getEmail());
        user_name.setPromptText(cur.getUsername());
        nickname.setText(cur.getFirstName() + " " + cur.getLastName());
        email.setText(cur.getEmail());
    }

    
}
