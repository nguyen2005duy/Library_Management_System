package Application.com.jmc.backend.Controller;
import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.awt.*;
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
    private List<Book> recommededBooks;


    @Override
    public void initialize (URL url, ResourceBundle rb) {
        books = new ArrayList<>(currentlyReading());
        recommededBooks = new ArrayList<>(RecommendedBooks());
        int column = 0;
        int row = 1;
        try {
            for (Book book : books) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Application/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(book);
                cardLayout.getChildren().add(cardBox);
            }

            for (Book recommendedBook : recommededBooks) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Application/BookCard.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookCardController bookCardController = fxmlLoader.getController();
                bookCardController.setData(recommendedBook);

                if (column == 6){
                    column = 0;
                    row++;
                }

                bookContainer.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));
            }
        }
        catch (IOException e) {
            System.out.println("CSS");

        }
    }



    //Lay borrowed_documents tu Member
    private List<Book> currentlyReading(){
        Member current_member = (Member) Library.current_user;
        return current_member.getBorrowed_documents();
    }

    private List<Book> RecommendedBooks(){
        Member current_member = (Member) Library.current_user;
        return current_member.getBorrowed_documents();
    }
}
