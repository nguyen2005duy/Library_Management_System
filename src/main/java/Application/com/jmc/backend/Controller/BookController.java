package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.GoogleBooksAPI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookController implements Initializable {

    @FXML
    private Label Page;

    @FXML
    private Label Rating;

    @FXML
    private Label author;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;

    @FXML
    private TextArea description;

    @FXML
    private Button favourite_button;

    @FXML
    private Button read_button;

    @FXML
    private Label status;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setBookData(Book book) {
        bookTitle.setText(book.getTitle());
        author.setText("Author: " + book.getAuthor());
        Rating.setText(book.getRating());
        Page.setText(book.getPages());
        description.setText(book.getDescription());

        // Load the book image (if available)
        try {
            Image image = new Image(GoogleBooksAPI.get_Book_Image(book.getBook_id()));
            bookImage.setImage(image);
        } catch (IOException e) {
            System.out.println("Couldnt find book image in book controller");
        }
    }
}
