package Application.com.jmc.backend.Controller.Client;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Controller.bookCardHBoxController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LibraryController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private VBox cardLayout;
    public static ObservableList<Book> books;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        books = FXCollections.observableArrayList(currentlyReading());
        books.addListener((ListChangeListener<Book>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Book addedBook : change.getAddedSubList()) {
                        addBookToLayout(addedBook);
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
        for (Book book : books) {
            addBookToLayout(book);
        }
    }

    private void addBookToLayout(Book book) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Application/bookCardHBox.fxml"));
            HBox cardBox = fxmlLoader.load();
            bookCardHBoxController bookcardController = fxmlLoader.getController();
            bookcardController.setData(book);
            cardLayout.getChildren().add(cardBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void refreshCurrentlyReading() {
        books.setAll(currentlyReading());
    }
    private List<Book> currentlyReading(){
        Member current_member = (Member) Library.current_user;
        System.out.println(current_member.getBorrowedDocuments());
        return current_member.getBorrowedDocuments();
    }
}
