package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Class.User_Information.User;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class New_memberController implements Initializable {



    @FXML
    private TextField email_field;

    @FXML
    private TextField firstname_field;

    @FXML
    private TextField lastname_field;

    @FXML
    private Button submit;

    @FXML
    void submit_user(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add New Member");
        alert.setHeaderText("You are going to add a new member");
        alert.setContentText("Are you sure you want to add?");

        if (alert.showAndWait().get() == ButtonType.OK) {


            Connection connectDB = DatabaseConnection.getConnection();
            int lastInsertId = 1;
            while (Library.usersList.get(lastInsertId)!=null) {
                lastInsertId++;
            }
            String default_username = "user" + String.valueOf(lastInsertId), default_password = "12345";

            String insertFields = "INSERT INTO user_account(firstname, lastname, username, password, email, role) VALUES ('";
            String insertValues = firstname_field.getText() + "','" + lastname_field.getText() + "','" + default_username  +  "','" +
                    default_password + "','" + email_field.getText() + "', 'Member')";
            String insertToRegister = insertFields + insertValues;
            try {
                Statement stmt = connectDB.createStatement();
                stmt.executeUpdate(insertToRegister);
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }



            MemberSearchModel mem = new MemberSearchModel(lastInsertId, firstname_field.getText() + " " + lastname_field.getText(), email_field.getText());
            MembersController.getMemberSearchModelObservableList().add(mem);
            User user = new Member(default_username, default_password, firstname_field.getText(), lastname_field.getText(), email_field.getText(), "Member");
            user.setAccount_id(lastInsertId);
            Library.add_member(user);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
