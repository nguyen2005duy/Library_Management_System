package Application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/application/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        Image icon = new Image(Login.class.getResourceAsStream("Img/book1.png"));
        stage.getIcons().add(icon);
        stage.setTitle("LoginPage!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();//
    }
}