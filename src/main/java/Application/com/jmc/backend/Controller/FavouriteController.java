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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FavouriteController  implements Initializable {

    @FXML
    private GridPane bookContainer;
    private List<Book> books;



    @Override
    public void initialize (URL url, ResourceBundle rb) {
        books = new ArrayList<>(favouriteBooks());
        int column = 0;
        int row = 1;
        try {
            for (Book recommendedBook : books) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Application/BookCard.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookCardController bookCardController = fxmlLoader.getController();
                bookCardController.setData(recommendedBook);

                if (column == 4){
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

    //tra ve list favou
    private List<Book> favouriteBooks(){
        Member cur =(Member)Library.current_user;

        List<Book> RecommendedBooks = cur.getfavourite_books();
        Book book1 = new Book("ass","ass");
        Book book2 = new Book("asd","asd");
        Book book3 = new Book("asd1","asd");
        Book book4 = new Book("asd2","asd");
        Book book5 = new Book("asd3","asd");
        Book book6 = new Book("asd4","asd");
        Book book7 = new Book("asd5","asd");
        Book book8 = new Book("asd6","asd");
        Book book9 = new Book("asd7","asd");

        book1.setTitle("RDPD");
        book1.setAuthor("KIM");
        book1.setImageSrc("/Img/content.jpg");
        book2.setTitle("RDPD");
        book2.setAuthor("KIM");
        book2.setImageSrc("/Img/content.jpg");
        book3.setTitle("RDPD");
        book3.setAuthor("KIM");
        book3.setImageSrc("/Img/content.jpg");
        book4.setTitle("RDPD");
        book4.setAuthor("KIM");
        book4.setImageSrc("/Img/content.jpg");
        book5.setTitle("RDPD");
        book5.setAuthor("KIM");
        book5.setImageSrc("/Img/content.jpg");
        book6.setTitle("RDPD");
        book6.setAuthor("KIM");
        book6.setImageSrc("/Img/content.jpg");
        book7.setTitle("RDPD");
        book7.setAuthor("KIM");
        book7.setImageSrc("/Img/content.jpg");
        book8.setTitle("RDPD");
        book8.setAuthor("KIM");
        book8.setImageSrc("/Img/content.jpg");
        book9.setTitle("RDPD");
        book8.setAuthor("KIM");
        book9.setImageSrc("/Img/content.jpg");





        RecommendedBooks.add(book1);
        RecommendedBooks.add(book2);
        RecommendedBooks.add(book3);
        RecommendedBooks.add(book4);
        RecommendedBooks.add(book5);
        RecommendedBooks.add(book6);
        RecommendedBooks.add(book7);
        RecommendedBooks.add(book8);
        RecommendedBooks.add(book9);


        return RecommendedBooks;
    }
}
