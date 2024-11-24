package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class BookCardController implements Initializable {

    @FXML
    private Label author;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label rating;

    @FXML
    private Label title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Book book) {
        Image image = new Image(getClass().getResourceAsStream(book.getImageSrc()));
        bookImage.setImage(image);
        title.setText("Title: " + book.getTitle());
        author.setText("Author: " +     book.getAuthor());
    }
}
