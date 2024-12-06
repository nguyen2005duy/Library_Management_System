package Application.com.jmc.backend.Class.Books;

import org.junit.jupiter.api.*;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.sql.Date;

class BookTest {

    @Mock
    private Book book = mock(Book.class);

    @AfterEach
    void afterAll() {
        System.out.println(book.toString());
    }

    @Test
    void testConstructorWithBorrower() {
        book = new Book("1", "1");
        System.out.println(book);

        assertEquals("1", book.getBook_id());
        assertEquals("1", book.getBorrowUserId());
        assertNotNull(book.getBorrowedDate());
        assertNotNull(book.getRequiredDate());
        assertTrue(book.getRequiredDate().after(book.getBorrowedDate()));
    }

    @Test
    void testConstructorWithFullDetails() {
        String[] categories = {"Fantasy", "Adventure"};
        Date borrowedDate = new Date(System.currentTimeMillis());
        Date requiredDate = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000);

        book = new Book(
                "2",
                "Harry Potter",
                "J.K. Rowling",
                categories,
                "1997-06-26",
                "223",
                "2",
                borrowedDate,
                requiredDate
        );

        assertEquals("2", book.getBook_id());
        assertEquals("Harry Potter", book.getTitle());
        assertEquals("J.K. Rowling", book.getAuthor());
        assertEquals("1997-06-26", book.getPublishedDate());
        assertEquals("223", book.getPages());
        assertEquals("2", book.getBorrowUserId());
        assertArrayEquals(categories, book.getCategories());
        assertEquals(borrowedDate, book.getBorrowedDate());
        assertEquals(requiredDate, book.getRequiredDate());
        assertFalse(book.isAvailable());
    }

    @Test
    void testCheckIn() {
        book = new Book("4", "4");
        book.check_in("4");

        assertFalse(book.isAvailable());
        assertEquals("4", book.getBorrowUserId());
        assertNotNull(book.getBorrowedDate());
        assertNotNull(book.getRequiredDate());
    }

    @Test
    void testCheckOut() {
        book = new Book("5", "5");
        book.check_out();

        assertTrue(book.isAvailable());
        assertNull(book.getBorrowUserId());
        assertNull(book.getBorrowedDate());
        assertNull(book.getRequiredDate());
    }

    @Test
    void testToString() {
        String[] categories = {"Mystery", "Thriller"};

        book = new Book(
                "7",
                "The Da Vinci Code",
                "Dan Brown",
                "454",
                categories,
                "2003-04-03"
        );

        String expected = "Book{" +
                "book_id='7', " +
                "borrow_user_id='null', " +
                "borrowed_date=null, " +
                "required_date=null" +
                '}';

        assertEquals(expected, book.toString());
    }

    @Test
    void testMock() {
        Book mockBook = mock(Book.class);
        mockBook = new Book("1", "1");

        assertEquals("1", mockBook.getBook_id());
        assertEquals("1", mockBook.getBorrowUserId());
        assertNotNull(mockBook.getBorrowedDate());
        assertNotNull(mockBook.getRequiredDate());
        assertTrue(mockBook.getRequiredDate().after(mockBook.getBorrowedDate()));
    }
}