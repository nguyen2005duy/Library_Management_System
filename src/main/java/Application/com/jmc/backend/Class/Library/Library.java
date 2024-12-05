package Application.com.jmc.backend.Class.Library;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Books.BorrowRecord;
import Application.com.jmc.backend.Class.Exceptions.UsernameTakenException;
import Application.com.jmc.backend.Class.Helpers.GoogleBooksAPI;
import Application.com.jmc.backend.Class.User_Information.Librarian;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Class.User_Information.User;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import Application.com.jmc.backend.Controller.Client.FavouriteController;
import Application.com.jmc.backend.Controller.Client.LibraryController;
import Application.com.jmc.backend.Controller.Client.TrendingController;

import java.io.IOException;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Library {
    public static Connection connectDB;
    public static List<Book> bookLists;
    public static HashMap<Integer, User> usersList;
    public static List<BorrowRecord> recordsLists;
    public static User current_user;
    public static HashMap<String, Integer> popularCategories;
    public static String[] recommendCategories = {
            "Mystery",
            "History",
            "Science Fiction",
            "Fantasy",
            "Biography & Autobiography",
            "Computers & Technology",
            "Self-Help",
            "Fantasy & Science Fiction"
    };
    public static List<Book> recommendedBooks;

    static {
        bookLists = new ArrayList<>();
        usersList = new HashMap<>();
        connectDB = DatabaseConnection.connection;
        recordsLists = new ArrayList<>();
        recommendedBooks = new ArrayList<>();
        popularCategories = new HashMap<>();
    }

    /**
     * khởi tạo thư viện.
     */
    public static void init_Library() {
        System.out.println("Initializing user data....");

        Thread loadBooks = new Thread(Library::loadBooks);
        Thread loadUsers = new Thread(Library::loadUsers);
        Thread loadRecords = new Thread(Library::load_record);

        loadBooks.start();
        loadUsers.start();
        loadRecords.start();
        try {
            loadBooks.join();
            loadUsers.join();
            loadRecords.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(usersList);
        Thread loadUsersBookLists = new Thread(Library::loadUserBookLists);
        loadUsersBookLists.start();
        try {
            loadUsersBookLists.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void init_Library_Admin() {
        Thread loadBooks = new Thread(Library::loadBooks);
        Thread loadUsers = new Thread(Library::loadUsers);
        Thread loadRecords = new Thread(Library::load_record);

        loadBooks.start();
        loadUsers.start();
        loadRecords.start();
        try {
            loadBooks.join();
            loadUsers.join();
            loadRecords.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void init_current_user(String username, String password) {
        System.out.println("Initializing current user...");
        System.out.println(username + " " + password);
        usersList.forEach((k, v) -> {
            System.out.println(v.getUsername() + " " + v.getPassword());
            if (v.getUsername().equals(username) && v.getPassword().equals(password)) {
                current_user = v;
            }
        });
    }

    /**
     * Lấy danh sách các thể loại yêu thích của người dùng hiện tại.
     *
     * @return Chuỗi các thể loại yêu thích (tối đa 4 thể loại), cách nhau bởi dấu cách.
     * @throws IOException in ra lỗi.
     */
    public static String[] get_user_favourite() throws IOException {
        Member member = (Member) Library.current_user;

        Map<String, Integer> categoryCount = new HashMap<>();

        for (BorrowRecord record : member.getBorrowedHistory()) {
            String[] categories = GoogleBooksAPI.getCategories(record.getBook_id());
            if (categories != null) {
                // Thêm từng thể loại vào Map và tăng tần suất
                for (String category : categories) {
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedCategories = new ArrayList<>(categoryCount.entrySet());
        sortedCategories.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        List<String> topcCategory = new ArrayList<>();
        for (int i = 0; i < Math.min(4, sortedCategories.size()); i++) {
            topcCategory.add(sortedCategories.get(i).getKey());
        }
        return topcCategory.toArray(new String[0]);
    }

    public static String[] get_popular_categories() throws IOException {
        // Ensure the popularCategories map is properly initialized before using it
        Map<String, Integer> categoryCount = popularCategories;

        // Check if the categories have been initialized or not
        int pos = 0;
        while (categoryCount.size() < 8 && pos < recommendCategories.length) {
            categoryCount.put(recommendCategories[pos], 1);  // Assign a default value, if necessary
            pos++;
        }

        // Sort the categories by value (descending order)
        List<Map.Entry<String, Integer>> sortedCategories = new ArrayList<>(categoryCount.entrySet());
        sortedCategories.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Create a list to store the top categories
        List<String> topCategories = new ArrayList<>();

        // Add the top 8 categories (or fewer, depending on the data)
        for (int i = 0; i < Math.min(8, sortedCategories.size()); i++) {
            topCategories.add(sortedCategories.get(i).getKey());
        }

        // Return the top categories as an array
        return topCategories.toArray(new String[0]);
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
            java.sql.ResultSet set = stmt.executeQuery("SELECT book_id FROM book");
            while (set.next()) {
                columnData.add(set.getString("book_id"));
            }
        } catch (SQLException e) {
            e.getCause();
            e.printStackTrace();
        }
        return columnData.toArray(new String[0]);
    }

    /**
     * Tải danh sách sách từ danh sách ID sách.
     */
    public static void loadBooks() {
        String[] booksID = getBooksID();
        for (String s : booksID) {
            try {
                Book book = GoogleBooksAPI.getDocumentDetails(s);
                String[] categories = book.getCategories();
                for (String c : categories) {
                    int fre = popularCategories.getOrDefault(c, 0);
                    popularCategories.put(c, fre + 1);
                }
                bookLists.add(book);

            } catch (IOException e) {
                System.out.println("Load documents error");
            }
        }
    }


    /**
     * Gán danh sách sách đã mượn cho từng thành viên.
     */
    public static void loadUserBookLists() {
        bookLists.forEach(v -> {
            if (v.getBorrowUserId() != null) {
                if (usersList.get(Integer.parseInt(v.getBorrowUserId())) instanceof Member mem) {
                    mem.add_book(v);
                }
            }
        });
    }

    /**
     * In thông tin chi tiết sách
     */
    public static void printBookDetails() {
        bookLists.forEach(System.out::println);
    }

    /**
     * Load users khi chạy login, dùng đa lượng.
     */
    public static void loadUsers() {
        try {
            Statement stmt = connectDB.createStatement();
            java.sql.ResultSet set = stmt.executeQuery("SELECT account_id, firstname, lastname, username, password, email, role FROM user_account");

            while (set.next()) {
                int account_id = Integer.parseInt(set.getString("account_id"));
                String firstname = set.getString("firstname");
                String lastname = set.getString("lastname");
                String username = set.getString("username");
                String password = set.getString("password");
                String email = set.getString("email");
                String role = set.getString("role");

                if (role.equalsIgnoreCase("Member")) {
                    Statement memberStmt = connectDB.createStatement();
                    java.sql.ResultSet memberSet = memberStmt.executeQuery("SELECT member_id, isPremiumMember FROM member_account WHERE account_id = " + account_id);

                    if (memberSet.next()) {
                        String member_id = memberSet.getString("member_id");
                        boolean isPremiumMember = memberSet.getString("isPremiumMember").equals("1");

                        User user = new Member(account_id, username, password, firstname, lastname, email, role, member_id, isPremiumMember);
                        usersList.put(user.getAccount_id(), user);
                    } else {
                        System.out.println("No member details found for account ID: " + account_id);
                    }
                } else {
                    User user = new Librarian(account_id,username,password,firstname,lastname,email,role);
                    usersList.put(user.getAccount_id(),user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * In thông tin chi tiết người dùng.
     */
    public static void printUsers() {
        usersList.forEach((k, v) -> {
            System.out.println(v);
        });
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
            java.sql.ResultSet set = stmt.executeQuery("SELECT available, borrowed_user_id, borrowed_date, required_date FROM book WHERE book_id = '" + id + "'");

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


    /**
     * thêm sách vào database.
     *
     * @param book thông tin cuốn sách.;;
     */
    public static void add_book(Book book) {
        bookLists.add(book);
        Member cur = (Member) Library.current_user;
        cur.getBorrowed_documents().add(book);
        String insertFields = "INSERT INTO book(book_id,available,borrowed_user_id,borrowed_date,required_date) VALUES (";
        String insertValues = "'" + book.getBook_id() + "'," +
                (book.isAvailable() ? 1 : 0) + "," +
                book.getBorrowUserId() + ",'" +
                book.getBorrowedDate() + "','" +
                book.getRequiredDate() + "')";
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
        usersList.put(member.getAccount_id(), member);
    }

    /**
     * them nguoi dung vao co so du lieu.
     *
     * @param user nguoi dung can them.
     * @throws UsernameTakenException loi khi thay ten nguoi dung trung nhau.
     */
    public static void add_user(User user) throws UsernameTakenException {
        usersList.forEach((k, user1) -> {
            if (user.getUsername().equals(user1.getUsername())) {
                throw new UsernameTakenException();
            }
        });
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
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();


        String sql = "DELETE FROM user_account WHERE account_id = " + String.valueOf(id);

        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.getCause();
        }

        User userToRemove = usersList.remove(id);
        return userToRemove != null;
    }

    /**
     * Tìm tài liệu theo tên.
     *
     * @param name Tên tài liệu cần tìm.
     * @return Chuỗi kết quả tìm kiếm tài liệu hoặc thông báo lỗi.
     */
    public static String find_document(String name) {
        try {
            return GoogleBooksAPI.searchMultiBooks(name);
        } catch (IOException e) {
            return "Errors while finding books";
        }
    }

    /**
     * Mượn sách từ danh sách và cập nhật cơ sở dữ liệu.
     *
     * @param id      id của cuốn sách.
     * @param user_id id của người dùng.
     */
    public static void borrow_books(String id, String user_id) {
        for (Book book : bookLists) {
            if (id.equals(book.getBook_id())) {
                if (book.isAvailable()) {
                    book.check_in(user_id);
                    String updateQuery = "UPDATE book SET available = 0, " +
                            "borrowed_user_id = " + book.getBorrowUserId() + ", " +
                            "borrowed_date = '" + book.getBorrowedDate() + "', " +
                            "required_date = '" + book.getRequiredDate() + "' " +
                            "WHERE book_id = '" + book.getBook_id() + "'";
                    Member mem = (Member) usersList.get(Integer.parseInt(user_id));
                    Book book1 = null;
                    try {
                        book1 = GoogleBooksAPI.getDocumentDetails(id);
                    } catch (IOException ignored) {

                    }
                    assert book1 != null;
                    Book bookCurr = new Book(book1, user_id);
                    mem.add_borrowed_documents(bookCurr);
                    LibraryController.books.add(bookCurr);
                    TrendingController.books.add(bookCurr);
                    try {
                        Statement stmt = connectDB.createStatement();
                        stmt.executeUpdate(updateQuery);
                    } catch (SQLException e) {
                        System.out.println("Error updating book in database.");
                        e.printStackTrace();
                    }
                    bookLists.add(book);
                    // Cần Update phần này để khi ktra thấy available chuyển book available thành ko
                }
                return;
            }
        }
        Book book1 = null;
        try {
            book1 = GoogleBooksAPI.getDocumentDetails(id);
        } catch (IOException ignored) {

        }
        assert book1 != null;
        Book book = new Book(book1, user_id);
        LibraryController.books.add(book);
        TrendingController.books.add(book);

        add_book(book);
    }

    /**
     * Khi có user rating, thêm record vào bảng borrow_record.
     *
     * @param book       Cuốn sách người dùng mượn.
     * @param userRating Đánh giá của người dùng.
     */
    public static void add_record(Book book, double userRating) {
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        String returnDate = sqlDate.toString();
        String insertFields = "INSERT INTO borrow_record(book_id, account_id,rating) VALUES ('";
        String insertValues = book.getBook_id() + "','" + book.getBorrowUserId() +
                 userRating + "')";
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
     * Khi có user rating, thêm record vào bảng borrow_record.
     *
     * @param book Cuốn sách người dùng mượn.
     */
    public static void add_record(Book book) {
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        String returnDate = sqlDate.toString();
        String insertFields = "INSERT INTO borrow_record(book_id, account_id) VALUES ('";
        String insertValues = book.getBook_id() + "','" + book.getBorrowUserId() + "')";
        String insertToRecord = insertFields + insertValues;
        Library.recordsLists.add(new BorrowRecord(Integer.parseInt(book.getBorrowUserId()), book.getBook_id()));
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(insertToRecord);
        } catch (SQLException e) {
            System.out.println("Loi khi them record vao database.");////
            e.getCause();
        }
    }

    /**
     * Tải các bản ghi mượn từ cơ sở dữ liệu và thêm vào recordsLists.
     */
    public static void load_record() {
        try {
            Statement stmt = connectDB.createStatement();
            java.sql.ResultSet set = stmt.executeQuery("SELECT book_id,account_id,user_rating  FROM borrow_record");
            while (set.next()) {
                int account_id = Integer.parseInt(set.getString("account_id"));
                String bookId = set.getString("book_id");
                String user_ratingString = set.getString("user_rating");
                if (user_ratingString == null) {
                    recordsLists.add(new BorrowRecord(account_id, bookId));
                } else {
                    recordsLists.add(new BorrowRecord(account_id, bookId, Double.parseDouble(user_ratingString)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * in ra các bản ghi trên terminal.
     */
    public static void printRecords() {
        System.out.println(recordsLists);
    }

    /**
     * Converts a string in "yyyy-MM-dd" format to java.sql.Date.
     *
     * @param dateString the date string to convert
     * @return java.sql.Date object, or null if parsing fails
     */
    public static Date convertStringToSQLDate(String dateString) {
        try {
            // Parse the String into a java.util.Date using "yyyy-MM-dd" format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(dateString);

            // Convert java.util.Date to java.sql.Date
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
            return null; // Return null if parsing fails
        }

    }

    public static List<Book> searchFor(String queueFor) {
        List<String> IdsList = GoogleBooksAPI.getIdList(queueFor);
        List<Book> searchResults = new ArrayList<>();
        for (String id : IdsList) {
            try {
                searchResults.add(GoogleBooksAPI.getDocumentDetails(id));
            } catch (IOException e) {
                System.out.println("Loi ham searchFor trong Library");
            }
        }
        return searchResults;
    }

    public static List<Book> searchForCategory(String category) {
        List<String> IdsList = GoogleBooksAPI.getCategoryIdList(category);
        List<Application.com.jmc.backend.Class.Books.Book> searchResults = new ArrayList<>();
        for (String id : IdsList) {
            try {
                searchResults.add(GoogleBooksAPI.getDocumentDetails(id));
            } catch (IOException e) {
                System.out.println("Loi ham searchFor trong Library");
            }
        }
        return searchResults;
    }

    public static void add_user_favourite(String book_id, int account_id) {
        System.out.println(book_id);
        System.out.println(account_id);
        Member cur = (Member) Library.current_user;
        List<Book> favourite = cur.getfavourite_books();

        String insertFields = "INSERT INTO favourite_books(account_id,book_id) VALUES (";
        String insertValues = "'" + account_id + "','" +
                book_id + "')";
        String insertToFavourites = insertFields + insertValues;
        Member mem = (Member) usersList.get(account_id);

        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(insertToFavourites);
            Book currBook = GoogleBooksAPI.getDocumentDetails(book_id);
            mem.addFavouriteBooks(currBook);
            FavouriteController.books.add(currBook);
        } catch (SQLException e) {
            System.out.println("Loi khi them favourite vao database.");////
            System.out.println(e.getCause());
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load_current_user_favourite() {
        Member cur = (Member) Library.current_user;
        try {
            Statement stmt = connectDB.createStatement();
            java.sql.ResultSet
                    set = stmt.executeQuery("SELECT book_id FROM favourite_books WHERE account_id='"
                    + cur.getAccount_id() + "'");
            while (set.next()) {
                try {
                    cur.addFavouriteBooks(GoogleBooksAPI.getDocumentDetails(set.getString("book_id")));
                } catch (IOException e) {
                    System.out.println("Loi khi add favourite vao current user");
                }
            }
        } catch (SQLException e) {
            System.out.println("load user favourite khong chay");
        }
    }

    public static String getBookRating(String bookId) {
        double totalRating = 0;
        int ratingCount = 0;

        for (BorrowRecord record : recordsLists) {
            if (record.getBook_id().equals(bookId)) {
                Double userRating = record.getUserRating();
                if (userRating != null && userRating >= 0 && userRating <= 5) {
                    totalRating += userRating;
                    ratingCount++;
                }
            }
        }

        if (ratingCount > 0) {
            double averageRating = totalRating / ratingCount;
            return String.format("%.2f", averageRating);
        }

        return "Not rated";
    }

    public static void loadRecommendedBooks() {
        try {
            String[] fav = get_user_favourite();
            if (fav.length != 0) {
                for (int i = 0; i < recommendCategories.length; i++) {
                    recommendCategories[i] = fav[i];
                }
            }
            for (int i = 0; i < 4; i++) {
                Random random = new Random();
                int bookAmount = random.nextInt() % 2 + 1;
                Library.recommendedBooks.addAll(GoogleBooksAPI.searchBooksByCategory(recommendCategories[i], bookAmount));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Loi khi load recommned Books");
        }

    }

    public static double getUserRating(String book_id) {
        int currentMemberId = current_user.getAccount_id();
        for (BorrowRecord record : recordsLists) {
            if (record.getBook_id().equals(book_id) && currentMemberId == record.getAccount_id()) {
                return record.getUserRating();
            }
        }
        return -1;
    }

    public static BorrowRecord getRatingRecord(String book_id) {
        int currentMemberId = current_user.getAccount_id();
        for (BorrowRecord record : recordsLists) {
            if (record.getBook_id().equals(book_id) && currentMemberId == record.getAccount_id()) {
                return record;
            }
        }
        return null;
    }

    public static void addUserRatingDataBase(String book_id, double rating) {
        int currentMemberId = current_user.getAccount_id();

        if (getUserRating(book_id) == -1) {
            String insertFields = "INSERT INTO borrow_record (book_id, account_id, user_rating) VALUES ";
            String insertValues = "('" + book_id + "', " + currentMemberId + ", " + rating + ")";
            String insertToAddUserRating = insertFields + insertValues;

            try {
                Statement stmt = connectDB.createStatement();
                stmt.executeUpdate(insertToAddUserRating);
            } catch (SQLException e) {
                System.out.println("Error adding rating to database.");
                e.printStackTrace();
            }
        } else {
            String updateRating = "UPDATE borrow_record SET user_rating = " + rating +
                    " WHERE book_id = '" + book_id + "' AND account_id = " + currentMemberId;

            try {
                Statement stmt = connectDB.createStatement();
                stmt.executeUpdate(updateRating);
            } catch (SQLException e) {
                System.out.println("Error updating rating in database.");
                e.printStackTrace();
            }
        }
    }

    public static void addUserRating(String book_id, double rating) {
        addUserRatingDataBase(book_id, rating);
        BorrowRecord record = getRatingRecord(book_id);
        if (record == null) {
            recordsLists.add(new BorrowRecord(Library.current_user.getAccount_id(), book_id, rating));
        } else {
            recordsLists.remove(record);
            recordsLists.add(new BorrowRecord(Library.current_user.getAccount_id(), book_id, rating));
        }
    }

    public static void returnBook(String book_id) {
        Book bookToReturn = null;
        for (Book book : bookLists) {
            if (book.getBook_id().equals(book_id)) {
                bookToReturn = book;
            }
        }
        assert bookToReturn != null;
        String updateQuery = "DELETE FROM book WHERE book_id = '" + bookToReturn.getBook_id() +"'";
        bookLists.remove(bookToReturn);
        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            System.out.println("Error updating book in database.");
            e.printStackTrace();
        }
    }

    public static void returnAllBookThroughUser(int user_id) {

        Library.bookLists.removeIf(book -> {
            return book.getBorrowUserId().equals(String.valueOf(user_id));
        });
        String updateQuery = "UPDATE book SET available = 1, " +
                "borrowed_user_id = " + null + ", " +
                "borrowed_date = '" + null + "', " +
                "required_date = '" + null + "' " +
                "WHERE borrowed_user_id = '" + user_id + "'";

        try {
            Statement stmt = connectDB.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            System.out.println("Error updating book in database.");
            e.printStackTrace();
        }
    }
}

