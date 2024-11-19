package Application.frontend;
import Application.backend.Class.Library.Library;
import Application.backend.Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Model.getInstance().getFactoryViews().showLoginView();

    }

    public static void main(String[] args) {
        Thread loadDocuments = new Thread(Library::loadBooks);
        Thread loadUsers = new Thread(Library::loadUsers);
        loadDocuments.start();
        loadUsers.start();
        try {
            loadDocuments.join();
            loadUsers.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        Library.printBookDetails();
        Library.printUsers();
        launch();//
    }
}