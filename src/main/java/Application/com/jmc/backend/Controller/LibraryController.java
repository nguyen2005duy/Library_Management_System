package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private ObservableList<Book> books;
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
                    // Optionally handle removed books if necessary
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
        return current_member.getBorrowed_documents();
    }
}
