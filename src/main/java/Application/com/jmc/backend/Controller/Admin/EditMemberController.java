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

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditMemberController implements Initializable {

    Member cur = (Member) Library.current_user;

    @FXML
    private TextField email_field;

    @FXML
    private TextField firstname_field;

    @FXML
    private TextField lastname_field;

    @FXML
    private Button submit;

    @FXML
    void submit_edit(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Change Member Information");
        alert.setHeaderText("You are going to change member Information");
        alert.setContentText("Are you sure you want to change?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();

            String sql = "UPDATE user_account SET  firstname = ?, lastname = ?, email = ? WHERE account_id = ?";

            try (PreparedStatement statement = connectDB.prepareStatement(sql)) {
                // Set the parameters
                statement.setString(1, firstname_field.getText());
                statement.setString(2, lastname_field.getText());
                statement.setString(3, email_field.getText());
                statement.setInt(4, cur.getAccount_id());

                // Execute the update
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
