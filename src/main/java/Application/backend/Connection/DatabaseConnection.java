<<<<<<<< HEAD:src/main/java/backend/Connection/DatabaseConnection.java
package backend.Connection;
========
package Application;
>>>>>>>> b628945d9ee2db00728341637ff7208c24a388ab:src/main/java/Application/DatabaseConnection.java

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
