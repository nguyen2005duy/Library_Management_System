package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class DatabaseConnection {
    public static Connection databaseLink;
    public static String databasePassword = null;

    public static Connection getConnection() {
        String databaseUser = "root";
        while (databasePassword == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type in your database password");
            databasePassword = scanner.next();
        }
        String databaseName = "library";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }

}
