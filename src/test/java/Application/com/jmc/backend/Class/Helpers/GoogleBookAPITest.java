package Application.com.jmc.backend.Class.Helpers;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoogleBooksAPITest {

    @InjectMocks
    private GoogleBooksAPI googleBooksAPI; // Thực thể cần kiểm thử

    @Mock
    private Library libraryMock; // Mock đối tượng Library nếu cần

    @BeforeEach
    void setUp() {
        // Khởi tạo thư viện nếu cần thiết
        Library.init_Library();
    }

    @Test
    void testSearchMultiBooks_ShouldReturnValidResponse() throws IOException {
        String query = "Java Programming"; // Tìm kiếm sách
        String mockResponse = "{\"items\":[{\"id\":\"1\",\"volumeInfo\":{\"title\":\"Java Programming\",\"authors\":[\"John Doe\"]}}]}";

        // Mock phương thức tĩnh searchMultiBooks của GoogleBooksAPI
        try (MockedStatic<GoogleBooksAPI> mocked = mockStatic(GoogleBooksAPI.class)) {
            // Thiết lập mock để phương thức tĩnh trả về phản hồi giả
            mocked.when(() -> GoogleBooksAPI.searchMultiBooks(query)).thenReturn(mockResponse);

            // Kiểm tra phương thức
            String response = GoogleBooksAPI.searchMultiBooks(query);

            // Xác minh phản hồi không rỗng và chứa thông tin mong muốn
            assertNotNull(response);
            assertTrue(response.contains("Java Programming"));
        }
    }

    @Test
    void testGetBookInfo_ShouldPrintBookDetails() throws IOException {
        String jsonResponse = "{\"items\":[{\"id\":\"1\",\"volumeInfo\":{\"title\":\"Java Programming\",\"authors\":[\"John Doe\"]}}]}";

        // Tạo đối tượng ObjectMapper để parse JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        // Xác minh chi tiết sách
        JsonNode book = rootNode.path("items").get(0);
        String title = book.path("volumeInfo").path("title").asText();
        String author = book.path("volumeInfo").path("authors").get(0).asText();

        assertEquals("Java Programming", title);
        assertEquals("John Doe", author);
    }

    @Test
    void testGetIdList_ShouldReturnListOfIds() throws IOException {
        String query = "Java Programming";
        String mockResponse = "{\"items\":[{\"id\":\"1\"},{\"id\":\"2\"}]}";

        // Mock phương thức tĩnh searchMultiBooks
        try (MockedStatic<GoogleBooksAPI> mockedStatic = mockStatic(GoogleBooksAPI.class)) {
            // Giả lập phương thức tĩnh searchMultiBooks trả về kết quả mock
            mockedStatic.when(() -> GoogleBooksAPI.searchMultiBooks(query)).thenReturn(mockResponse);

            // Gọi phương thức tĩnh getIdList và kiểm tra kết quả
            List<String> ids = GoogleBooksAPI.getIdList(query);

            // In ra danh sách ID để kiểm tra
            System.out.println("IDs: " + ids);

            // Kiểm tra danh sách các ID
            assertNotNull(ids);
            assertEquals(2, ids.size());
            assertTrue(ids.contains("1"));
            assertTrue(ids.contains("2"));
        }
    }

    @Test
    void testGetBookDetails_ShouldReturnBookObject() throws IOException {
        String bookId = "1";
        String mockResponse = "{\"id\":\"1\",\"volumeInfo\":{\"title\":\"Java Programming\",\"authors\":[\"John Doe\"],\"description\":\"A book about Java.\"}}";

        // Mock phương thức static searchOneBook
        try (MockedStatic<GoogleBooksAPI> mockedStatic = mockStatic(GoogleBooksAPI.class)) {
            mockedStatic.when(() -> GoogleBooksAPI.searchOneBook(bookId)).thenReturn(mockResponse);

            // Kiểm tra phương thức lấy chi tiết sách
            Book book = GoogleBooksAPI.getDocumentDetails(bookId);

            assertNotNull(book);
            assertEquals("Java Programming", book.getTitle());
            assertEquals("John Doe", book.getAuthor());
            assertEquals("A book about Java.", book.getDescription());
        }
    }

//    @Test
//    void testSearchBooksByCategory_ShouldReturnBooks() {
//        String category = "Programming";
//
//        // Giả lập phương thức tìm sách theo thể loại
//        String mockResponse = "{\"items\":[{\"id\":\"1\",\"volumeInfo\":{\"title\":\"Java Programming\"}}]}";
//        when(libraryMock.searchBooksByCategory(category, 5)).thenReturn(mockResponse);
//
//        // Kiểm tra phương thức
//        List<Book> books = googleBooksAPI.searchBooksByCategory(category, 5);
//
//        assertNotNull(books);
//        assertTrue(books.size() > 0);
//        assertEquals("Java Programming", books.get(0).getTitle());
//    }
}