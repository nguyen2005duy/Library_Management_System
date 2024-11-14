package Application.backend.Class.Library;

import Application.backend.Class.Exceptions.UsernameTakenException;
import Application.backend.Class.User_Information.Member;
import Application.backend.Class.User_Information.User;
import Application.backend.Connection.DatabaseConnection;
import Application.backend.Class.Books.*;

import javax.swing.*;
import java.io.IOException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {
    public static Connection connectDB;
    public static List<Book> bookLists;
    public static List<User> usersList;
    public static List<BorrowRecord> recordsLists;

    static {
        bookLists = new ArrayList<>();
        usersList = new ArrayList<>();
        connectDB = DatabaseConnection.connection;
        recordsLists = new ArrayList<>();
    }

    /**
     * Lay arrays cua book_id de phuc vu cho function looadBooks.
     *
     * @return bookIdarrays.
     */
    public static String[] getBooksID() {
        ArrayList<String> columnData = new ArrayList<>();
        try {
            Statement stmt = connectDB.createStatement();
            java.sql.ResultSet set = stmt.executeQuery("SELECT book_id FROM document");
            while (set.next()) {
                columnData.add(set.getString("book_id"));
            }
        } catch (SQLException e) {
            e.getCause();
            e.printStackTrace();
        }
        return columnData.toArray(new String[0]);
    }

    public static void loadBooks() {
        String[] booksID = getBooksID();
        for (String s : booksID) {
            try {
                bookLists.add(GoogleBooksAPI.getDocumentDetails(s));
            } catch (IOException e) {
                System.out.println("Load documents error");
            }
        }
    }

    public static void printBookDetails() {
        bookLists.forEach(System.out::println);
    }

    /**
     * Load users khi chay login, dung da luong
     */
    public static void loadUsers() {
        try {
            Statement stmt = connectDB.createStatement();
            java.sql.ResultSet set = stmt.executeQuery("SELECT account_id,firstname,lastname,username,password,email,role FROM user_account");

            if (set.next()) {
                int account_id = Integer.parseInt(set.getString("account_id"));
                String firstname = set.getString("firstname");
                String lastname = set.getString("lastname");
                String username = set.getString("username");
                String password = set.getString("password");
                String email = set.getString("email");
                String role = set.getString("role");
                if (role.equalsIgnoreCase("Member")) {
                    set = stmt.executeQuery("SELECT member_id,isPremiumMember FROM member_account");
                    User user;
                    if (set.next()) {
                        String member_id = set.getString("member_id");
                        boolean isPremiumMember = set.getString("isPremiumMember").equals("1");
                        user = new Member(username, password, firstname, lastname, email, role, member_id, isPremiumMember);
                        usersList.add(user);
                    } else {
                        System.out.println("Errors while loading member info");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * de check xem load user chua.
     */
    public static void printUsers() {
        usersList.forEach(System.out::println);
    }

    /**
     * De lay cac du lieu tu database.
     *
     * @param id id_book.
     * @return arrays cac du lieu tu database.
     */
    public static String[] loadBookBorrowedId(String id) {
        ArrayList<String> columnData = new ArrayList<>();
        try {
            Statement stmt = connectDB.createStatement();
            java.sql.ResultSet set = stmt.executeQuery("SELECT available, borrowed_user_id, borrowed_date, required_date FROM document WHERE book_id = '" + id + "'");

            if (set.next()) {
                columnData.add(set.getString("available"));
                if (set.getString("available").equals("0")) {
                    columnData.add(set.getString("borrowed_user_id"));
                    columnData.add(set.getString("borrowed_date"));
                    columnData.add(set.getString("required_date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnData.toArray(new String[0]);
    }


    public static void add_document(Book book) {

        String insertFields = "INSERT INTO book(book_id,available,borrowed_user_id,borrowed_date,required_date) VALUES (";
        String insertValues = "'" + book.getBook_id() + "'," +
                (book.isAvailable() ? 1 : 0) + "," +
                book.getBorrow_user_id() + ",'" +
                book.getBorrowed_date() + "','" +
                book.getRequired_date() + "')";
        String insertToDownloadBooks = insertFields + insertValues;
        System.out.println(insertToDownloadBooks);
        System.out.println(book.getBook_id());
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(insertToDownloadBooks);
        } catch (SQLException e) {
            System.out.println("Loi khi them book vao database.");////
            e.getCause();
            return;
        }
        bookLists.add(book);
    }


    /**
     * them nguoi dung vao usersList va co so du lieu.
     *
     * @param user nguoi dung can them.
     */

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
        usersList.add(member);
    }

    /**
     * them nguoi dung vao co so du lieu.
     *
     * @param user nguoi dung can them.
     * @throws UsernameTakenException loi khi thay ten nguoi dung trung nhau.
     */
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
    }

    /**
     * xoa nguoi dung vao usersList va co so du lieu.
     *
     * @param id ma id cua nguoi dung.
     * @return dung khi da xoa nguoi dung thanh cong, sai khi khong tim thay nguoi dung.
     * @throws SQLException loi khi thao tac co so du lieu.
     */
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


    public static boolean borrow_books(String id, String user_id) {
        for (Book book : bookLists) {
            if (id.equals(book.getBook_id())) {
                if (!book.isAvailable()) {
                    return false;
                } else {
                    // Cần Update phần này để khi ktra thấy available chuyển book available thành ko
                }
            }
        }
        Book book = new Book(id, user_id);
        add_document(book);
        return true;
    }

    /**
     * Khi co user rating, them record
     *
     * @param book
     * @param userRating
     */
    public static void add_record(Book book, double userRating) {
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        String returnDate = sqlDate.toString();
        String insertFields = "INSERT INTO borrow_record(book_id, account_id, borrow_date, return_date, user_rating) VALUES ('";
        String insertValues = book.getBook_id() + "','" + book.getBorrow_user_id() +
                "','" + book.getBorrowed_date() + "','" + returnDate + "','" + userRating + "')";
        String insertToRecord = insertFields + insertValues;
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(insertToRecord);
        } catch (SQLException e) {
            System.out.println("Loi khi them record vao database.");////
            e.getCause();
        }
    }

    /**
     * Khi khong co user_rating.
     *
     * @param book book.
     */
    public static void add_record(Book book) {
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        String returnDate = sqlDate.toString();
        String insertFields = "INSERT INTO borrow_record(book_id, account_id, borrow_date, return_date) VALUES ('";
        String insertValues = book.getBook_id() + "','" + book.getBorrow_user_id() +
                "','" + book.getBorrowed_date() + "','" + returnDate + "')";
        String insertToRecord = insertFields + insertValues;
        Library.recordsLists.add(new BorrowRecord(Integer.parseInt(book.getBorrow_user_id()), book.getBook_id()
                , book.getBorrowed_date(), sqlDate));
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(insertToRecord);
        } catch (SQLException e) {
            System.out.println("Loi khi them record vao database.");////
            e.getCause();
        }
    }

}
