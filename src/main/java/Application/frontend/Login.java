package Application.frontend;
import Application.Views.FactoryViews;
import Application.backend.Model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Model.getInstance().getFactoryViews().showAppView();
    }

    public static void main(String[] args) {
        launch();//
    }
}