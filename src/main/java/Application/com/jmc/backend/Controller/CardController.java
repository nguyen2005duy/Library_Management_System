package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.GoogleBooksAPI;
import Application.com.jmc.backend.Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    private Book book;

    @FXML
    private Label authorName;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private HBox box;

    private String[] colors = {"C5705D","D0B8A8","F8EDE3", "DFD3C3" };

    public static int num = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    //Favourite && Trending
    public void setData(Book book){
        //ImageView lay anh
        try {
            this.book = book;
            Image image = new Image(GoogleBooksAPI.get_Book_Image(book.getBook_id()));
            bookImage.setImage(image);
            bookName.setText(book.getTitle());
            authorName.setText(book.getAuthor());
            box.setStyle("-fx-background-color: #" + colors[num] + ";"
                    + "-fx-background-radius: 15;"
                    + "-fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0),10,0,0,10);");
            if (num == 3) num = 0;
            else num ++;
        } catch (IOException e) {
            System.out.println("Loi load image trong CardController");
        }
    }

    @FXML
    void loadBook(MouseEvent event) {
        //openBookDetails()
        Model.getInstance().setSelectedBook(book);
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set("Book");
    }

}
