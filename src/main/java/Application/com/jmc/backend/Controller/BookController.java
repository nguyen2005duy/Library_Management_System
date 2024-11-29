package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.GoogleBooksAPI;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
        Book currentViewing = Model.getInstance().getSelectedBook();
        Member cur = (Member) Library.current_user;
        for (Book a : cur.getfavourite_books()) {
            if (Objects.equals(a.getBook_id(), currentViewing.getBook_id())) {
                favourite_button.setDisable(true);
                favourite_button.setText("Added to Favourites");
            }
        }
        if (!currentViewing.isAvailable() && Integer.parseInt(currentViewing.getBorrow_user_id()) == Library.current_user.getAccount_id()) {
            read_button.setDisable(true);
            read_button.setText("Borrowing");
        } else if (!currentViewing.isAvailable()) {
            read_button.setDisable(true);
            read_button.setText("Not available");
        }
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


    @FXML
    void add_to_favourite(MouseEvent event) {
        Library.add_user_favourite(Model.getInstance().getSelectedBook().getBook_id(),Library.current_user.getAccount_id());
        favourite_button.setDisable(true);
        favourite_button.setText("Added to favourite");
    }

    @FXML
    void add_to_library(MouseEvent event) {
        Member cur = (Member) Library.current_user;
        cur.add_borrowed_documents(Model.getInstance().getSelectedBook());
        Library.borrow_books(Model.getInstance().getSelectedBook().getBook_id(),String.valueOf(cur.getAccount_id()));
        read_button.setDisable(true);
        read_button.setText("Borrowing");
    }

}
