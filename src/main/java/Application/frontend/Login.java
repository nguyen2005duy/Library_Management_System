package Application.frontend;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Model.getInstance().getFactoryViews().showClientView();

    }

    public static void main(String[] args) {
        launch();//

    }
}