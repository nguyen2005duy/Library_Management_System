package backend;

import frontend.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.sql.*;
import java.util.Scanner;

public class Main extends Application {
    static Connection connectDB = DatabaseConnection.getConnection();
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        primaryStage.setTitle("Hello Button Example");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    public static void addUser() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adding user to database");

        System.out.println("Username:");
        String username = scanner.next();

        System.out.println("Password:");
        String password = scanner.next();

        System.out.println("First Name:");
        String firstName = scanner.next();

        System.out.println("Last Name:");
        String lastName = scanner.next();

        System.out.println("Librarian (yes/no):");
        String role = scanner.next().equalsIgnoreCase("yes") ? "Librarian" : "Member";

        String sql = "INSERT INTO user_account (firstname, lastname, username, password, role) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connectDB.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, role);
            int affectedRows = pstmt.executeUpdate(); // Use executeUpdate() for INSERT
            if (affectedRows > 0) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("Adding user failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        try {
            addUser();
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        launch(args);
    }

}
