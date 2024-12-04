package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Helpers.QRCodeGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class QRCodeController implements Initializable {
    @FXML
    private Label author;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;
    @FXML
    private ImageView QRCode;
    private Book book;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setBookData(Book book, Image image) {
        this.book = book;
        bookTitle.setText(book.getTitle());
        author.setText("Author: " + book.getAuthor());
        QRCodeGenerator.generateQR(book.getBook_id());
        String path = "file:src/main/resources/QRCodes/" + book.getBook_id() + "_qrCode";
        Image qrImage = new Image(path);
        QRCode.setImage(qrImage);
        bookImage.setImage(image);

    }

}
