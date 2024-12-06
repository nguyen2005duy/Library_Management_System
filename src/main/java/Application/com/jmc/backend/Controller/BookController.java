package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Helpers.GoogleBooksAPI;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Controller.Client.FavouriteController;
import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.ClientMenuOptions;
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

import org.controlsfx.control.Rating;

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
    @FXML
    private Rating ratingBox;
    @FXML
    private Label QRPath;


    private Book book;
    private Image image;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ratingBox.setRating(0);
        Book currentViewing = Model.getInstance().getSelectedBook();
        Member cur = (Member) Library.current_user;
        favourite_button.setText("Add to favourites");
        for (Book a : cur.getfavourite_books()) {
            if (Objects.equals(a.getBook_id(), currentViewing.getBook_id())) {
               /* favourite_button.setDisable(true);
                favourite_button.setText("Added to Favourites");*/
                favourite_button.setText("Remove from favourites");
            }
        }
        if (!currentViewing.isAvailable() && Integer.parseInt(currentViewing.getBorrowUserId()) == Library.current_user.getAccount_id()) {
            read_button.setDisable(true);
            read_button.setText("Borrowing");
        } else if (!currentViewing.isAvailable()) {
            read_button.setDisable(true);
            read_button.setText("Not available");
        }
    }

    public void setBookData(Book book) {
        this.book = book;
        bookTitle.setText(book.getTitle());
        author.setText("Author: " + book.getAuthor());
        Rating.setText(book.getRating());
        Page.setText(book.getPages());
        description.setText(book.getDescription());
        //de set rating
        double rating = Library.getUserRating(this.book.getBook_id());
        Rating.setText(Library.getBookRating(this.book.getBook_id()));
        if (rating == -1) {
            rating = 0;
        }
        ratingBox.setRating(rating);

        ratingBox.ratingProperty().addListener((observable, oldValue, newValue) -> {
            double updatedRating = newValue.doubleValue();
            Library.addUserRating(this.book.getBook_id(), updatedRating);

            Rating.setText(Library.getBookRating(this.book.getBook_id()));
        });

        try {
            Image image = new Image(GoogleBooksAPI.get_Book_Image(book.getBook_id()));
            bookImage.setImage(image);
            this.image = image;
        } catch (IOException e) {
            System.out.println("Couldnt find book image in book controller");
        }
    }

    @FXML
    public void toQR() {
        Model.getInstance().setSelectedBook(book);
        Model.getInstance().setSelectedImage(image);
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.QR);
    }

    @FXML
    void add_to_favourite(MouseEvent event) {
        if (favourite_button.getText().equalsIgnoreCase("Add to favourites")) {
            Library.addUserFavourite(Model.getInstance().getSelectedBook().getBook_id(), Library.current_user.getAccount_id());
            favourite_button.setText("Remove from favourites");
        } else {
            Library.removeFromFavourites(book);
            favourite_button.setText("Add to favourites");
        }
    }


    @FXML
    void add_to_library(MouseEvent event) {
        Member cur = (Member) Library.current_user;
        Library.borrow_books(Model.getInstance().getSelectedBook().getBook_id(), String.valueOf(cur.getAccount_id()));
        read_button.setDisable(true);
        read_button.setText("Borrowing");
    }

}
