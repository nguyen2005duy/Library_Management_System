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
        assertEquals("1", book.getBorrow_user_id());
        assertNotNull(book.getBorrowed_date());
        assertNotNull(book.getRequired_date());
        assertTrue(book.getRequired_date().after(book.getBorrowed_date()));
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
        assertEquals("1997-06-26", book.getPublished_date());
        assertEquals("223", book.getPages());
        assertEquals("2", book.getBorrow_user_id());
        assertArrayEquals(categories, book.getCategories());
        assertEquals(borrowedDate, book.getBorrowed_date());
        assertEquals(requiredDate, book.getRequired_date());
        assertFalse(book.isAvailable());
    }

    @Test
    void testConstructorWithoutBorrower() {
        String[] categories = {"Science Fiction", "Drama"};

        book = new Book(
                "3",
                "Dune",
                "Frank Herbert",
                "412",
                categories,
                "1965-08-01"
        );

        assertEquals("3", book.getBook_id());
        assertEquals("Dune", book.getTitle());
        assertEquals("Frank Herbert", book.getAuthor());
        assertEquals("1965-08-01", book.getPublished_date());
        assertEquals("412", book.getPages());
        assertArrayEquals(categories, book.getCategories());
        assertTrue(book.isAvailable());
    }

    @Test
    void testCheckIn() {
        book = new Book("4", "4");
        book.check_in("4");

        assertFalse(book.isAvailable());
        assertEquals("4", book.getBorrow_user_id());
        assertNotNull(book.getBorrowed_date());
        assertNotNull(book.getRequired_date());
    }

    @Test
    void testCheckOut() {
        book = new Book("5", "5");
        book.check_out();

        assertTrue(book.isAvailable());
        assertNull(book.getBorrow_user_id());
        assertNull(book.getBorrowed_date());
        assertNull(book.getRequired_date());
    }

    @Test
    void testSetAndGetImageSrc() {
        book = new Book("6", "6");
        book.setImageSrc("images/book_cover.png");

        assertEquals("images/book_cover.png", book.getImageSrc());
    }

    @Test
    void testToString() {
        String[] categories = {"Mystery", "Thriller"};

        book = new Book(
                "7",
                "The Da Vinci Code",
                "Dan Brown",
                categories,
                "2003-04-03",
                "454"
        );

        String expected = "Book{borrowed_id'null', " +
                "title='The Da Vinci Code', " +
                "published_date='2003-04-03', " +
                "pages=454, " +
                "categories=[Mystery, Thriller], " +
                "book_id='7', " +
                "author='Dan Brown'}";

        assertEquals(expected, book.toString());
    }

    @Test
    void testMock() {
        Book mockBook = mock(Book.class);
        mockBook = new Book("1", "1");

        assertEquals("1", mockBook.getBook_id());
        assertEquals("1", mockBook.getBorrow_user_id());
        assertNotNull(mockBook.getBorrowed_date());
        assertNotNull(mockBook.getRequired_date());
        assertTrue(mockBook.getRequired_date().after(mockBook.getBorrowed_date()));
    }
}