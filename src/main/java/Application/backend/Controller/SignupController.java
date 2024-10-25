package Application.backend.Controller;

import Application.Login;
import Application.backend.Connection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private ImageView book;
    @FXML
    private Button SignupButton;
    @FXML
    private Label registerMessage;
    @FXML
    private TextField SignupUsernameField;
    @FXML
    private PasswordField SignupPasswordField;
    @FXML
    private PasswordField ConfirmPasswordField;
    @FXML
    private Label ConfirmPasswordMessage;
    @FXML
    private TextField SignupEmailField;
    @FXML
    private TextField FirstnameField;
    @FXML
    private TextField LastnameField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image bookFile = new Image(Login.class.getResourceAsStream("Img/book1.png"));
        book.setImage(bookFile);

    }


    public void SignupEnable(javafx.event.ActionEvent actionEvent) {
        if(SignupButton.isDisable()) {
            SignupButton.setDisable(false);
        }
        else
        {
            SignupButton.setDisable(true);
        }
    }

    public void RegisterUser() {
        Connection connectDB = DatabaseConnection.getConnection();
        DatabaseConnection connectNow = new DatabaseConnection();
        String username = SignupUsernameField.getText();
        String password = SignupPasswordField.getText();
        String email = SignupEmailField.getText();
        String FirstName = FirstnameField.getText();
        String LastName = LastnameField.getText();

        String insertFields = "INSERT INTO user_account(firstname, lastname, username, password, email) VALUES ('";
        String insertValues = FirstName + "','" + LastName + "','" + username + "','" + password + "','" + email + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(insertToRegister);
            registerMessage.setText("Registered Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void registerButtonOnAction(javafx.event.ActionEvent actionEvent) {
        if (SignupPasswordField.getText().equals(ConfirmPasswordField.getText())) {
            RegisterUser();
            ConfirmPasswordMessage.setText("");

        } else {
            ConfirmPasswordMessage.setText("Password does not match!");
        }
    }

    public void SwitchToSignin(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Application/Login.fxml"));
            Stage SignupStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            SignupStage.setScene(new Scene(root));
            SignupStage.show();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Switching to Signin error");
            System.out.println();
            e.printStackTrace();
            e.getCause();
        }
    }
}
