package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class bookCardHBoxController implements Initializable {

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
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set("Book");
    }

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

