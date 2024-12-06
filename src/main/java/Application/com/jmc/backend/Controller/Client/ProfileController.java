package Application.com.jmc.backend.Controller.Client;

import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Class.User_Information.User;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import Application.com.jmc.backend.Controller.Admin.MemberSearchModel;
import Application.com.jmc.backend.Controller.Admin.MembersController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private final Member cur = (Member) Library.current_user;

    @FXML
    private Label email;

    @FXML
    private Label nickname;

    @FXML
    private TextField user_name;
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
    void save(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save changes");
        alert.setHeaderText("You are going to save changes");
        alert.setContentText("Are you sure you want to save?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();

            String sql = "UPDATE user_account SET username = ?, firstname = ?, lastname = ?, email = ? WHERE account_id = ?";

            try (PreparedStatement statement = connectDB.prepareStatement(sql)) {
                // Set the parameters
                statement.setString(1, user_name.getText());
                statement.setString(2, first_name_field.getText());
                statement.setString(3, last_name_field.getText());
                statement.setString(4, email_field.getText());
                statement.setInt(5, cur.getAccount_id());

                // Execute the update
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
