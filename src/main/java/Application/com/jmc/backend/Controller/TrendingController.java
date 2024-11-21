package Application.com.jmc.backend.Controller;
import Application.com.jmc.backend.Class.Books.Book;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TrendingController implements Initializable {
    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane bookContainer;
    private List<Book> books;


    @Override
    public void initialize (URL url, ResourceBundle rb) {
        List<Book> books = new ArrayList<>(currentlyReading());
        try {
            for (int i = 0; i < books.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Application/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(books.get(i));
                cardLayout.getChildren().add(cardBox);
            }
        }
        catch (IOException e) {
            System.out.println("CSS");

        }
    }



    //Lay borrowed_documents tu Member
    private List<Book> currentlyReading(){
        List<Book> BorrowedBooks = new ArrayList<>();
        Book book1 = new Book("ass","ass");
        Book book2 = new Book("asd","asd");
        Book book3 = new Book("asd1","asd");
        BorrowedBooks.add(book1);
        BorrowedBooks.add(book2);
        BorrowedBooks.add(book3);
        return BorrowedBooks;
    }
}
