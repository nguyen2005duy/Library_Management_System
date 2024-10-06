package frontend;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection databaseLink;

    public static Connection getConnection() {
        String databaseUser = "root";
        String databasePassword = "c0981439876";
        String databaseName = "user_accounts";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }

}
