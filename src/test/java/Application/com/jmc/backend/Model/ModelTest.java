package Application.com.jmc.backend.Model;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Views.FactoryViews;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
    }

    @Test
    void testSingletonInstance() {
        Model instance1 = Model.getInstance();
        Model instance2 = Model.getInstance();
        assertSame(instance1, instance2, "Hai the hien khong phai la mot doi tuong duy nhat");
    }

    @Test
    void testSetAndGetSearchString() {
        Model model = Model.getInstance();
        String searchQuery = "Harry Potter";
        model.setSearchString(searchQuery);
        assertEquals(searchQuery, model.getSearchString(), "Chuoi tim kiem khong dung");
    }

    @Test
    void testSetAndGetSelectedBook() {
        String book_id = "123";
        String borrow_user_id = "user456";
        Book book = new Book(book_id, borrow_user_id);
        Model model = Model.getInstance();
        model.setSelectedBook(book);
        assertEquals(book, model.getSelectedBook());
    }

    @Test
    void testSetAndGetSelectedImage() {
        Model model = Model.getInstance();
        assertNull(model.getSelectedImage());
        Image image = new Image("https://example.com/image.jpg");
        model.setSelectedImage(image);
        assertEquals(image, model.getSelectedImage());
    }

    @Test
    void testGetFactoryViews() {
        Model model = Model.getInstance();
        FactoryViews factoryViews = model.getFactoryViews();
        assertNotNull(factoryViews, "FactoryViews khong duoc la null");
    }
}