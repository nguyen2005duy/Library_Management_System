package Application.com.jmc.backend.Controller.Client;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Controller.BookCardController;
import Application.com.jmc.backend.Controller.CardController;
import Application.com.jmc.backend.Controller.bookCardHBoxController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrendingController implements Initializable {
    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane bookContainer;

    // Change from List to ObservableList
    public static ObservableList<Book>  books;
    public static ObservableList<Book> recommendedBooks;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize ObservableLists
        books = FXCollections.observableArrayList(currentlyReading());
        recommendedBooks = FXCollections.observableArrayList(RecommendedBooks());

        // Listen for changes in the books list
        books.addListener((ListChangeListener<Book>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Book addedBook : change.getAddedSubList()) {
                        addBookToLayout(addedBook, cardLayout);
                    }
                }
                if (change.wasRemoved()) {
                    for (Book removedBook : change.getRemoved()) {
                        HBox targetBox = null;

                        for (javafx.scene.Node node : cardLayout.getChildren()) {
                            if (node instanceof HBox) {
                                FXMLLoader loader = (FXMLLoader) node.getProperties().get("loader");
                                bookCardHBoxController controller = loader.getController();
                                if (controller.getBook().equals(removedBook)) {
                                    targetBox = (HBox) node;
                                    break;
                                }
                            }
                        }

                        if (targetBox != null) {
                            cardLayout.getChildren().remove(targetBox);
                        }
                    }
                }
            }
        });

        // Initial layout update for books
        for (Book book : books) {
            addBookToLayout(book, cardLayout);
        }

        // Similarly handle recommendedBooks if necessary (following the same pattern)
        int column = 0;
        int row = 1;
        for (Book recommendedBook : recommendedBooks) {
            try {
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
            } catch (IOException e) {
                System.out.println("Error loading book card: " + e.getMessage());
            }
        }
    }

    private void addBookToLayout(Book book, HBox container) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Application/card.fxml"));
            HBox cardBox = fxmlLoader.load();
            CardController cardController = fxmlLoader.getController();
            cardController.setData(book);
            container.getChildren().add(cardBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private ObservableList<Book> currentlyReading() {
        Member currentMember = (Member) Library.current_user;
        return FXCollections.observableArrayList(currentMember.getBorrowedDocuments());
    }

    private ObservableList<Book> RecommendedBooks() {
        return FXCollections.observableArrayList(Library.recommendedBooks);
    }
}
