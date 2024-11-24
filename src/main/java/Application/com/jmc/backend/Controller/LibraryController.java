package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LibraryController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private VBox cardLayout;
    private List<Book> books;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        books = new ArrayList<>(currentlyReading());
        try {
            for (Book book : books) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Application/bookCardHBox.fxml"));
                HBox cardBox = fxmlLoader.load();
                bookCardHBoxController bookcardController = fxmlLoader.getController();
                bookcardController.setData(book);
                cardLayout.getChildren().add(cardBox);
            }
        }catch (Exception e) {
            e.printStackTrace();

        }
    }
    private List<Book> currentlyReading(){
        List<Book> BorrowedBooks = new ArrayList<>();
        Book book1 = new Book("ass","ass");
        Book book2 = new Book("asd","asd");
        Book book3 = new Book("asd1","asd");
        Book book4 = new Book("asd2","asd");
        book1.setTitle("RDPD");
        book1.setAuthor("KIM");
        book1.setImageSrc("/Img/content.jpg");
        book2.setTitle("RDPD");
        book2.setAuthor("KIM");
        book2.setImageSrc("/Img/content.jpg");
        book3.setTitle("RDPD");
        book3.setAuthor("KIM");
        book3.setImageSrc("/Img/content.jpg");
        book4.setTitle("RDPD");
        book4.setAuthor("KIM");
        book4.setImageSrc("/Img/content.jpg");
        BorrowedBooks.add(book1);
        BorrowedBooks.add(book2);
        BorrowedBooks.add(book3);
        BorrowedBooks.add(book4);
        return BorrowedBooks;
    }
}
