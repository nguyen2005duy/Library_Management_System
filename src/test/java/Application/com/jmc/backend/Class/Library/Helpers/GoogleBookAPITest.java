package Application.com.jmc.backend.Class.Library.Helpers;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleBookAPITest {

    @BeforeAll
    public static void setUp() {
        Library.init_Library();
    }

    @Test
    public void testSearchMultiBooks() throws IOException {
        String query = "Java Programming";
        String response = GoogleBooksAPI.searchMultiBooks(query);
        assertNotNull(response, "Phan hoi khong duoc null");
        assertTrue(response.contains("items"), "Phan hoi khong chua thong tin sach");
    }

    @Test
    public void testSearchBooksByCategory() throws IOException {
        String category = "Technology";
        String response = GoogleBooksAPI.searchBooksByCategory(category);
        assertNotNull(response, "Phan hoi khong duoc null");
        assertTrue(response.contains("items"), "Phan hoi khong chua thong tin sach");
    }

    @Test
    public void testGetBookInfo() throws IOException {
        String query = "Java Programming";
        String response = GoogleBooksAPI.searchMultiBooks(query);
        int pos = 0;
        String bookId = GoogleBooksAPI.get_book_ID(pos, response);
        assertNotNull(bookId, "ID sach khong duoc null");

        String bookInfo = GoogleBooksAPI.searchOneBook(bookId);
        assertNotNull(bookInfo, "Thong tin sach khong duoc null");
        assertTrue(bookInfo.contains("volumeInfo"), "Thong tin sach khong chua volumeInfo");
    }

    @Test
    public void testGetCategories() throws IOException {
        String query = "Java Programming";
        String response = GoogleBooksAPI.searchMultiBooks(query);
        int pos = 0;
        String bookId = GoogleBooksAPI.get_book_ID(pos, response);
        assertNotNull(bookId, "ID sach khong duoc null");

        String[] categories = GoogleBooksAPI.getCategories(bookId);
        assertNotNull(categories, "Categories khong duoc null");
        assertTrue(categories.length > 0, "Sach phai co it nhat mot the loai");
    }

    @Test
    public void testGetDocumentDetails() throws IOException {
        String query = "Java Programming";
        String response = GoogleBooksAPI.searchMultiBooks(query);
        int pos = 0;
        String bookId = GoogleBooksAPI.get_book_ID(pos, response);
        Book book = GoogleBooksAPI.getDocumentDetails(bookId);
        assertNotNull(book, "Chi tiet sach khong duoc null");
        assertEquals("Java Programming", book.getTitle(), "Ten sach khong dung");
    }

    @Test
    public void testGetBookImage() throws IOException {
        String query = "Java Programming";
        String response = GoogleBooksAPI.searchMultiBooks(query);
        int pos = 0;
        String bookId = GoogleBooksAPI.get_book_ID(pos, response);
        String imageUrl = GoogleBooksAPI.get_Book_Image(bookId);
        assertNotNull(imageUrl, "URL anh sach khong duoc null");
        assertFalse(imageUrl.contains("Couldn't find image"), "Khong tim thay anh sach");
    }
}