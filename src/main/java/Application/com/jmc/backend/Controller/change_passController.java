package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class change_passController implements Initializable {

    Member cur = (Member) Library.current_user;
    @FXML
    private Label confirm_message;

    @FXML
    private Label new_pass_message;
    @FXML
    private Label old_pass_message;

    @FXML
    private TextField confirm;

    @FXML
    private TextField new_pass;

    @FXML
    private TextField old_pass;

    @FXML
    private Button submit;

    @FXML
    void submit_pass(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Change password");
        alert.setHeaderText("You are going to change your password");
        alert.setContentText("Are you sure you want to change?");
        if(alert.showAndWait().get() == ButtonType.OK) {
            if (old_pass.getText().isBlank()) {
                old_pass_message.setText("please enter old password");
                old_pass_message.setTextFill(Color.RED);
                return;
            }
            else {
                if (!old_pass.getText().equals(cur.getPassword())) {
                    old_pass_message.setText("Not Correct!");
                    old_pass_message.setTextFill(Color.RED);
                    return;
                }
            }
            if (new_pass.getText().isBlank()) {
                new_pass_message.setText("please enter new password");
                new_pass_message.setTextFill(Color.RED);
                old_pass_message.setText("");
                return;

            }
            else {
                if(new_pass.getText().length()<8) {
                    new_pass_message.setText("Password must have at least 8 characters");
                    new_pass_message.setTextFill(Color.RED);
                    old_pass_message.setText("");

                    return;
                }
                else {
                    if (new_pass.getText().equals(cur.getPassword())) {
                        new_pass_message.setText("Change to new password");
                        new_pass_message.setTextFill(Color.RED);
                        old_pass_message.setText("");

                        return;
                    }
                }
            }
            if (confirm.getText().isBlank()) {
                confirm_message.setText("please enter confirm password");
                confirm_message.setTextFill(Color.RED);
                old_pass_message.setText("");
                new_pass_message.setText("");
                return;
            }
            else {
                if (!confirm.getText().equals(new_pass.getText())) {
                    confirm_message.setText("Not Correct!");
                    confirm_message.setTextFill(Color.RED);
                    old_pass_message.setText("");
                    new_pass_message.setText("");
                    return;
                }
            }

            update();




        }
    }


    public void update(){
        DatabaseConnection db = new DatabaseConnection();
        Connection con = db.getConnection();

        String sql = "UPDATE user_account SET password = " + new_pass.getText() +
                "WHERE account_id = " + String.valueOf(cur.getAccount_id()) + ';';

        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Change Successfully");
        alert.setHeaderText("You successfully changed your password");
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        old_pass.setPromptText("Enter old password");


    }
}
