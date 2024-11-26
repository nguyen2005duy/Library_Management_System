package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.GoogleBooksAPI;
import Application.com.jmc.backend.Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class bookCardHBoxController implements Initializable {

    @FXML
    private Label exprired_date;

    @FXML
    private Label time_remaining;

    @FXML
    private Label author;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label rating;

    @FXML
    private Label title;

    @FXML
    private Button continue_button;

    @FXML
    private Button learn_more;

    @FXML
    void loadBook(MouseEvent event) {
        //openBookDetails();
        Model.getInstance().setSelectedBook(book);
          Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set("Book");
    }

    private Book book;

    private void openBookDetails() {
        try {
            // Load the Book.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Book.fxml"));
            AnchorPane bookDetailRoot = loader.load();

            // Get the BookController and pass the book data
            BookController bookController = loader.getController();
            bookController.setBookData(book);

            // Create a new Stage (or switch scene)
            Stage stage = new Stage();
            stage.setScene(new Scene(bookDetailRoot));
            stage.setTitle(book.getTitle());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    public void setData(Book book) {
        try {
            this.book = book;
            Image image = new Image(GoogleBooksAPI.get_Book_Image(book.getBook_id()));
            bookImage.setImage(image);
            title.setText("Title: " + book.getTitle());
            author.setText("Author: " + book.getAuthor());
        } catch (IOException e) {
            System.out.println("Error loading book image at bookCardHBoxController");
        }
    }


}

