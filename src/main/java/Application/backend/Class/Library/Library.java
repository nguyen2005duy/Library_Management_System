package Application.backend.Class.Library;
import Application.backend.Class.Exceptions.UsernameTakenException;
import Application.backend.Class.User_Information.User;
import Application.backend.Connection.DatabaseConnection;
import Application.backend.Documents.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Library {
    public static Connection connectDB;
    public static List<Document> documentsList;
    public static List<User> usersList;
    static
    {
        documentsList = new ArrayList<>();
        usersList = new ArrayList<>();
        connectDB = DatabaseConnection.getConnection();
    }
    public static void loadDocuments(){

    }

    public static void loadUsers()
    {

    }

    public static void add_document(Document document)
    {

    }
    public static void  add_user(User user) throws UsernameTakenException {
        for (User user1: usersList)
        {
            if(user.getUsername().equals(user1.getUsername()))
            {
                throw new UsernameTakenException();
            }
        }
        usersList.add(user);
        String insertFields = "INSERT INTO user_account(firstname, lastname, username, password, email, role) VALUES ('";
        String insertValues = user.getFirstName() + "','" + user.getLastName() + "','" + user.getUsername() + "','" +
                user.getPassword() + "','" + user.getEmail() +"', 'Member')";
        String insertToRegister = insertFields + insertValues;
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(insertToRegister);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
