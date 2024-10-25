package Application.backend.Controller;

import Application.backend.Connection.DatabaseConnection;


import Application.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;


import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


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
    public static  Connection connectDB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image bookFile = new Image(Login.class.getResourceAsStream("/Img/book1.png"));
        book.setImage(bookFile);

    }

    private void Validatelogin() {
        connectDB = DatabaseConnection.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + UsernameField.getText() + "' AND password = '" + passwordField.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            System.out.println(statement);
            System.out.println(queryResult);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    LoginMessage.setText("You have successfully logged in!");
                    LoginMessage.setTextFill(Color.GREEN);
                } else {
                   LoginMessage.setText("Invalid username or password");
                    LoginMessage.setTextFill(Color.RED);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void loginButtoninAction(javafx.event.ActionEvent actionEvent) {
        if (!UsernameField.getText().isBlank() && !passwordField.getText().isBlank()) {
            Validatelogin();
        }
        else if (!UsernameField.getText().isBlank() && passwordField.getText().isBlank())
        {
            LoginMessage.setText("Please enter your password");
            LoginMessage.setTextFill(Color.RED);

        }
        else if (UsernameField.getText().isBlank() && !passwordField.getText().isBlank())
        {
            LoginMessage.setText("Please enter your username");
            LoginMessage.setTextFill(Color.RED);

        }
        else {
            LoginMessage.setText("Please enter your username and password");
            LoginMessage.setTextFill(Color.RED);

        }
    }


    public void createSignupForm(javafx.event.ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Application/signup.fxml")));
            Stage SignupStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            SignupStage.setTitle("Sign up");
            SignupStage.setScene(new Scene(root));
            SignupStage.show();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Creating SignupForm error ");
            System.out.println();
            e.printStackTrace();
            e.getCause();
        }
    }


}
