package Application.com.jmc.backend.Controller;
import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.GoogleBooksAPI;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DiscoverController implements Initializable {

    @FXML
    private ImageView book1;

    @FXML
    private ImageView book2;

    @FXML
    private ImageView book3;

    @FXML
    private ImageView book4;

    @FXML
    private TextField search_bar;

    @FXML
    private Button search_button;

    @FXML
    private MenuButton search_menu;

    public void setImageViewImage() throws IOException {
        // Create an Image object from a URL
        Member current_user = (Member) Library.current_user;
        List<Book> borrowed_books = current_user.getBorrowed_documents();
        System.out.println(borrowed_books);
        String imageLink1 = GoogleBooksAPI.get_Book_Image(borrowed_books.get(0).getBook_id());
        String imageLink2 = GoogleBooksAPI.get_Book_Image(borrowed_books.get(0).getBook_id());
        String imageLink3 = GoogleBooksAPI.get_Book_Image(borrowed_books.get(0).getBook_id());
        String imageLink4 = GoogleBooksAPI.get_Book_Image(borrowed_books.get(0).getBook_id());

        Image image1 = new Image(imageLink1);
        Image image2 = new Image(imageLink2);
        Image image3 = new Image(imageLink3);
        Image image4 = new Image(imageLink4);
        book1.setImage(image1);
        book1.setFitWidth(180);
        book1.setFitHeight(200);
        book1.setPreserveRatio(true);
        book2.setImage(image2);
        book2.setFitWidth(180);
        book2.setFitHeight(200);
        book2.setPreserveRatio(true);
        book3.setImage(image3);
        book3.setFitWidth(180);
        book3.setFitHeight(200);
        book3.setPreserveRatio(true);
        book4.setImage(image4);
        book4.setFitWidth(180);
        book4.setFitHeight(200);
        book4.setPreserveRatio(true);
    }
    @Override
    public void initialize (URL url, ResourceBundle rb) {
      /*  try {
           *//* Library.printRecords();
            System.out.println(Library.bookLists);
            Member current_member = (Member) Library.current_user;
            List<Book> list = current_member.getBorrowed_documents();
            list.forEach(Book::check_out);
            Library.printRecords();
            setImageViewImage();*//*
        } catch (IOException e) {
            System.out.println("Loi set image");
        }*/
    }

}
