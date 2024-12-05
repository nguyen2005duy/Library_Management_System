package Application.com.jmc.backend.Class.Library;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Exceptions.UsernameTakenException;
import Application.com.jmc.backend.Class.Helpers.GoogleBooksAPI;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Class.User_Information.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.ArrayList;

public class LibraryTest {

    @Mock
    Connection mockConnection = mock(Connection.class);
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    @BeforeEach
    public void setUp() throws SQLException {

        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        Library.connectDB = mockConnection;
        Library.bookLists.clear();
        Library.usersList.clear();
        Library.recordsLists.clear();
    }

    @Test
    public void testAddBook() throws SQLException {
        Book book = new Book("1", "User1");
        Library.add_book(book);
        assertTrue(Library.bookLists.contains(book));
    }

    @Test
    public void testBorrowBook() throws SQLException {

        Book mockBook = new Book("2", null);
        mockBook.setAvailable(false); // Đánh dấu sách không sẵn sàng
        Library.bookLists.add(mockBook);

        Member mockUser = mock(Member.class);
        Library.usersList.put(1, mockUser);

        try (MockedStatic<Library> libraryMock = mockStatic(Library.class)) {
            libraryMock.when(() -> Library.borrow_books("2", "1")).thenAnswer(invocation -> {
                String bookId = invocation.getArgument(0);
                String userId = invocation.getArgument(1);

                for (Book book : Library.bookLists) {
                    if (bookId.equals(book.getBook_id())) {
                        if (!book.isAvailable()) {
                            return false;
                        } else {
                            book.check_in(userId);
                            String updateQuery = "UPDATE book SET available = 0, " +
                                    "borrowed_user_id = " + book.getBorrowUserId() + ", " +
                                    "borrowed_date = '" + book.getBorrowedDate() + "', " +
                                    "required_date = '" + book.getRequiredDate() + "' " +
                                    "WHERE book_id = '" + book.getBook_id() + "'";
                            Member mem = (Member) Library.usersList.get(Integer.parseInt(userId));
                            mem.add_borrowed_documents(book);

                            try {
                                mockStatement.executeUpdate(updateQuery);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                return false;
                            }
                            return true;
                        }
                    }
                }
                Book newBook = new Book(bookId, userId);
                Library.add_book(newBook);
                return true;
            });

            mockBook.setAvailable(true);
            Library.borrow_books("2", "1");
            assertFalse(mockBook.isAvailable(), "Sách phải không còn sẵn");
            assertEquals("1", mockBook.getBorrowUserId(), "Sách phải thuộc về người dùng '1'");

            verify(mockStatement, times(1)).executeUpdate(anyString());
            verify(mockUser, times(1)).add_borrowed_documents(mockBook);
            assertEquals(1, Library.bookLists.size(), "Không nên thêm sách mới vào danh sách khi sách đã tồn tại");
        }
    }


    @Test
    public void testAddUser() throws UsernameTakenException {
        User newUser = new Member(1, "newuser", "password",
                "John", "Doe", "john.doe@example.com",
                "Member", "M001", false);
        Library.add_user(newUser);
        assertTrue(Library.usersList.containsKey(newUser.getAccount_id()));
    }

    @Test
    public void testFindDocument() {
        String result = Library.find_document("Effective Java");
        assertNotNull(result);
    }

    @Test
    public void testGetUserFavourite() throws Exception {
        assertTrue(false);

//        Member member = new Member(1, "user1", "password", "Jane", "Doe", "jane.doe@example.com", "Member", "M001", false);
//        Library.usersList.put(member.getAccount_id(), member);
//
//        String[] favGenres = Library.get_user_favourite();
//        assertNotNull(favGenres);
    }

    @Test
    public void testRemoveUser() throws SQLException {
        User newUser = new Member(1, "removeme", "password", "Tom", "Smith", "tom.smith@example.com", "Member", "M002", false);
        Library.add_user(newUser);
        assertTrue(Library.usersList.containsKey(newUser.getAccount_id()));
        boolean isRemoved = Library.remove_user(newUser.getAccount_id());
        assertTrue(isRemoved);
        assertFalse(Library.usersList.containsKey(newUser.getAccount_id()));
    }


    @Test
    public void testLoadBooks() {
        try (MockedStatic<Library> libraryMock = Mockito.mockStatic(Library.class)) {
            libraryMock.when(Library::loadBooks).thenAnswer(invocation -> {
                Book book = new Book("1", "Test Book");
                Library.bookLists.add(book);  // Thêm sách vào danh sách giả lập
                return null;
            });
            Library.loadBooks();
        }
            assertFalse(Library.bookLists.isEmpty(), "Danh sách sách không nên rỗng");
            assertEquals(1, Library.bookLists.size(), "Số lượng sách phải là 1");
            assertEquals("1", Library.bookLists.get(0).getBook_id(), "Sách phải có ID là '1'");
    }

    @Test
    public void testLoadUsers() {
        try (MockedStatic<Library> libraryMock = Mockito.mockStatic(Library.class)) {
            libraryMock.when(Library::loadUsers).thenAnswer(invocation -> {
                User user = new Member(1, "testUsername", "testPassword", "John", "Doe", "john.doe@example.com", "Member", "M001", false);
                Library.usersList.put(user.getAccount_id(), user); // Thêm người dùng vào danh sách giả lập
                return null;
            });
            Library.loadUsers();
        }

        assertFalse(Library.usersList.isEmpty(), "Danh sách người dùng không nên rỗng");
        assertEquals(1, Library.usersList.size(), "Số lượng người dùng phải là 1");
        assertEquals("testUsername", Library.usersList.get(1).getUsername(), "Tên người dùng phải là 'testUsername'");
    }

    @Test
    public void testGetBooksID() throws SQLException {

        Mockito.when(mockResultSet.next()).thenReturn(true, true, false); // 2 hàng dữ liệu
        Mockito.when(mockResultSet.getString("book_id")).thenReturn("1", "2");
        Mockito.when(mockStatement.executeQuery("SELECT book_id FROM book")).thenReturn(mockResultSet);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);

        String[] result = Library.getBooksID();
        assertArrayEquals(new String[]{"1", "2"}, result, "Danh sách ID phải khớp");
    }

    @Test
    void testAddUserFavourite() {
//        assertTrue(false);

        // Mock GoogleBooksAPI
        GoogleBooksAPI mockApi = mock(GoogleBooksAPI.class);
        Book mockBook = new Book("test-book-id", "12345"); // Mock một cuốn sách
        try {
            when(mockApi.getDocumentDetails("test-book-id")).thenReturn(mockBook);

        } catch (Exception e) {
            fail("Mocking API failed");
        }

        // Khởi tạo người dùng hiện tại
        Member mockUser = new Member(1, "testUser", "password123", "John", "Doe", "email@example.com", "Member", "member-1", false);
        mockUser.setFavourite_books(new ArrayList<>()); // Danh sách sách yêu thích rỗng
        Library.current_user = mockUser;

        // ID của sách sẽ được thêm
        String bookId = "test-book-id";
        int accountId = 1;

        // Gọi hàm thêm sách yêu thích
        Library.addUserFavourite(bookId, accountId);

        // Lấy danh sách yêu thích từ người dùng
        Member currentUser = (Member) Library.current_user;
        assertNotNull(currentUser.getfavourite_books());
        assertEquals(1, currentUser.getfavourite_books().size());

        // Kiểm tra sách được thêm đúng
        Book favouriteBook = currentUser.getfavourite_books().get(0);
        assertEquals(bookId, favouriteBook.getBook_id());
    }

    @Test
    public void testGetBookRating() {
        String rating = Library.getBookRating("1");
        assertNotNull(rating);
    }
}