package Application.com.jmc.backend.Controller;
import Application.com.jmc.backend.Class.Books.Book;
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
        List<Book> BorrowedBooks = new ArrayList<>();
        Book book1 = new Book("ass","ass");
        Book book2 = new Book("asd","asd");
        Book book3 = new Book("asd1","asd");
        book1.setTitle("RDPD");
        book1.setAuthor("KIM");
        book1.setImageSrc("/Img/content.jpg");
        book2.setTitle("RDPD");
        book2.setAuthor("KIM");
        book2.setImageSrc("/Img/content.jpg");
        book3.setTitle("RDPD");
        book3.setAuthor("KIM");
        book3.setImageSrc("/Img/content.jpg");
        BorrowedBooks.add(book1);
        BorrowedBooks.add(book2);
        BorrowedBooks.add(book3);
        return BorrowedBooks;
    }

    private List<Book> RecommendedBooks(){
        List<Book> RecommendedBooks = new ArrayList<>();
        Book book1 = new Book("ass","ass");
        Book book2 = new Book("asd","asd");
        Book book3 = new Book("asd1","asd");
        book1.setTitle("RDPD");
        book1.setAuthor("KIM");
        book1.setImageSrc("/Img/content.jpg");
        book2.setTitle("RDPD");
        book2.setAuthor("KIM");
        book2.setImageSrc("/Img/content.jpg");
        book3.setTitle("RDPD");
        book3.setAuthor("KIM");
        book3.setImageSrc("/Img/content.jpg");
        RecommendedBooks.add(book1);
        RecommendedBooks.add(book2);
        RecommendedBooks.add(book3);
        return RecommendedBooks;
    }
}
