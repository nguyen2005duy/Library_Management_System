package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Helpers.GoogleBooksAPI;
import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.ClientMenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class bookCardHBoxController implements Initializable {
    private Book book;
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
          Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.BOOK);
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
            LocalDate currentDate = new Date(System.currentTimeMillis()).toLocalDate();
            LocalDate requiredLocalDate = book.getRequired_date().toLocalDate();

            exprired_date.setText("Expired Date:" + requiredLocalDate);
            long daysLeft = ChronoUnit.DAYS.between(currentDate, requiredLocalDate);
            time_remaining.setText("Time remaining:" + daysLeft +" days");
        } catch (IOException e) {
            System.out.println("Error loading book image at bookCardHBoxController");
        }
    }
    public Book getBook() {
        return book;
    }


}

