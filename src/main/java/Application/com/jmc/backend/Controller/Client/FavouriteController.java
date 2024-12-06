package Application.com.jmc.backend.Controller.Client;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Controller.BookCardController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
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
    public static ObservableList<Book> books;



    @Override
    public void initialize (URL url, ResourceBundle rb) {
        books = FXCollections.observableArrayList(favouriteBooks());
        books.addListener((ListChangeListener<Book>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                } else if ( change.wasRemoved())
                {
                    bookContainer.getChildren().clear();
                    refreshFavouriteBooks();  // Call the refresh method on any change

                }
            }
        });
    }
    public void refreshFavouriteBooks () {
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
        System.out.println("favouritecontroller");
        System.out.println(cur.getFavourite_books());
        return cur.getfavourite_books();
    }
}
