package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Helpers.GoogleBooksAPI;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.ClientMenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookCardController implements Initializable {
    private Book book;

    @FXML
    private Label author;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label rating;

    @FXML
    private Label title;
    @FXML
    private void openBookDetails() {
        Model.getInstance().setSelectedBook(book);
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.BOOK);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Trending
    public void setData(Book book) {
        try {
            this.book = book;
            Image image = new Image(GoogleBooksAPI.get_Book_Image(book.getBook_id()));
            bookImage.setImage(image);
            title.setText("Title: " + book.getTitle());
            author.setText("Author: " +     book.getAuthor());
            rating.setText(Library.getBookRating(this.book.getBook_id()));
        } catch (IOException e) {
            System.out.println("Error loading book image at bookCardHBoxController");
        }
    }

}
