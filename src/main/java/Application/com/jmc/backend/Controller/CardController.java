package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    @FXML
    private Label authorName;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private HBox box;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }



    public void setData(Book book){
        //ImageView lay anh
        Image image = new Image(getClass().getResourceAsStream(book.getImageSrc()));
        bookImage.setImage(image);
        bookName.setText(book.getTitle());
        authorName.setText(book.getAuthor());
        box.setStyle("-fx-background-color: #" + "000000" + ";"
        + "-fx-background-radius: 15;"
        + "-fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0),10,0,0,10);");
    }

}
