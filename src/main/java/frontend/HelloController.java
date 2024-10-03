package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public void handleHelloButtonAction() {
        // Create a new thread that prints "Hello" in the backend
        Thread backendThread = new Thread(() -> {
            System.out.println("Hello from the backend!");
        });

        // Start the thread
        backendThread.start();
    }
}