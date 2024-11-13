package Application.backend.Class.Library;

import Application.backend.Class.Exceptions.UsernameTakenException;
import Application.backend.Class.User_Information.Member;
import Application.backend.Class.User_Information.User;
import Application.backend.Connection.DatabaseConnection;
import Application.backend.Class.Documents.*;

import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    public static Connection connectDB;
    public static List<Document> documentsList;
    public static List<User> usersList;
    public static List<Member> membersList;

    static {
        documentsList = new ArrayList<>();
        usersList = new ArrayList<>();
        membersList = new ArrayList<>();
        connectDB = DatabaseConnection.getConnection();
    }

    public static void loadDocuments() {

    }

    public static void loadUsers() {

    }

    public static void add_document(Document document) {

    }

    public static void add_member(User user) {
        Member member = (Member) user;
        member.generateMemberType();
        String insertFields = "INSERT INTO member_account(member_id, isPremiumMember, account_id) VALUES ('";
        String insertValues = member.getMember_id() + "','" + 0 +
                "','" + member.getAccount_id() + "')";
        String insertToRegister = insertFields + insertValues;
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(insertToRegister);
        } catch (SQLException e) {
            e.printStackTrace();////
            e.getCause();
        }
        membersList.add(member);
    }

    public static void add_user(User user) throws UsernameTakenException {
        for (User user1 : usersList) {
            if (user.getUsername().equals(user1.getUsername())) {
                throw new UsernameTakenException();
            }
        }
        String insertFields = "INSERT INTO user_account(firstname, lastname, username, password, email, role) VALUES ('";
        String insertValues = user.getFirstName() + "','" + user.getLastName() + "','" + user.getUsername() + "','" +
                user.getPassword() + "','" + user.getEmail() + "', 'Member')";
        String insertToRegister = insertFields + insertValues;
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(insertToRegister);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        int lastInsertId = 0;
        String sql = "SELECT LAST_INSERT_ID()";

        try (Statement statement = connectDB.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                lastInsertId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.getCause();
        }
        user.setAccount_id(lastInsertId);
        System.out.println(user.getAccount_id());
        add_member(user);
        usersList.add(user);
    }

    public static boolean remove_user(int id) throws SQLException {
        User userToRemove = null;
        for (User user1 : usersList) {
            if (id == user1.getAccount_id()) {
                userToRemove = user1;
                break;
            }
        }
        if (userToRemove == null) {
            return false;
        }
        return usersList.remove(userToRemove);
    }

    public static String find_document(String name) {
        try {
            return GoogleBooksAPI.searchMultiBooks(name);
        } catch (IOException e) {
            return "Errors while finding books";
        }
    }
}
