package frontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;


import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javafx.scene.image.Image;


public class LoginController implements Initializable {

    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button LoginButton;
    @FXML
    private Label LoginMessage;
    @FXML
    private ImageView book;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image bookFile = new Image(Login.class.getResourceAsStream("book.png"));
        book.setImage(bookFile);

    }

    private void Validatelogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + UsernameField.getText() + "' AND password = '" + passwordField.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);


            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    LoginMessage.setText("You have successfully logged in!");
                } else {
                    LoginMessage.setText("Invalid username or password");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void loginButtoninAction(javafx.event.ActionEvent actionEvent) {
        if (!UsernameField.getText().isBlank() || !passwordField.getText().isBlank()) {
            Validatelogin();
        } else {
            LoginMessage.setText("Please enter your username and password");
        }
    }
}