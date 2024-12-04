package Application.com.jmc.backend.Controller.Client;

import Application.com.jmc.backend.Controller.LoadingTask;
import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.AccountType;
import Application.com.jmc.backend.Views.FactoryViews;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.application.Platform;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {
    @FXML
    public Rectangle recMain;
    @FXML
    public Rectangle recSub;
    @FXML
    public Label ldProgress;

    private final FactoryViews factoryViews = Model.getInstance().getFactoryViews();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadingTask task = new LoadingTask();

        // Update progress bar and label
        task.progressProperty().addListener((ob, oldValue, newValue) -> {
            String formattedNum = String.format("%.0f", newValue.doubleValue() * 100);
            ldProgress.setText(formattedNum + "%");
            recSub.setWidth(recMain.getWidth() * newValue.doubleValue());
        });

        // When the task completes successfully
        task.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                if (factoryViews.getAccountType() == AccountType.Member) {
                    factoryViews.showClientView();  // Show the Client View after loading
                    closeCurrentStage();
                } else {
                    factoryViews.showAdminView();
                    closeCurrentStage();
                }
            });
        });

        // Handle task failure (optional)
        task.setOnFailed(event ->

        {
            System.err.println("Loading task failed.");
            Throwable exception = task.getException();
            if (exception != null) exception.printStackTrace();
        });

        new Thread(task).start();
    }

    private void closeCurrentStage() {
        // Assuming the current stage is retrieved through the label or any other component
        ldProgress.getScene().getWindow().hide();
    }
}
