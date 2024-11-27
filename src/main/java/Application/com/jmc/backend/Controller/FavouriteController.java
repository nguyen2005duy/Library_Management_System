package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FavouriteController  implements Initializable {

    @FXML
    private GridPane bookContainer;

    @FXML
    private Label message;




    @Override
    public void initialize (URL url, ResourceBundle rb) {
        List <Book> favorite_books = new ArrayList<>(favouriteBooks());
        int column = 0;
        int row = 1;
        if (favorite_books.isEmpty()) {
            message.setText("(Nothing to show)");
        }
        else {
            try {
                for (Book recommendedBook : favorite_books) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Application/BookCard.fxml"));
                    VBox bookBox = fxmlLoader.load();
                    BookCardController bookCardController = fxmlLoader.getController();
                    bookCardController.setData(recommendedBook);

                    if (column == 4) {
                        column = 0;
                        row++;
                    }

                    bookContainer.add(bookBox, column++, row);
                    GridPane.setMargin(bookBox, new Insets(10));
                }
            } catch (IOException e) {
                System.out.println("CSS");

            }
        }
    }

    //tra ve list favou
    private List<Book> favouriteBooks(){
        Member cur =(Member)Library.current_user;
        return cur.getfavourite_books();
    }
}
