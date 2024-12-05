package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Helpers.GoogleBooksAPI;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import Application.com.jmc.backend.Model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SearchResultAdminController implements Initializable {

    BookSearchModel bookSearchModel;

    @FXML
    private TableColumn<BookSearchModel, ?> action;

    @FXML
    private TableColumn<BookSearchModel, ?> author;

    @FXML
    private TableColumn<BookSearchModel, ?> available;

    @FXML
    private TableColumn<BookSearchModel, ?> book_id;

    @FXML
    private TableView<BookSearchModel> book_search;

    @FXML
    private TextField user_id;

    @FXML
    private Label number_of_results;

    @FXML
    private Label results;

    @FXML
    private TableColumn<BookSearchModel, ?> title;

    @FXML
    private TextField searchBar;

    ObservableList<BookSearchModel> BookSearchModelObservableList= FXCollections.observableArrayList();

    @FXML
    void rowclicked(MouseEvent event) {
        if (bookSearchModel != null) {
            try {
                bookSearchModel.getButton().setVisible(false);
                BookSearchModel book = book_search.getSelectionModel().getSelectedItem();
                bookSearchModel = book;
                book.getButton().setVisible(true);
                book.getButton().setOnAction(actionEvent -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to add this book to this member?");
                    if (alert.showAndWait().get() == ButtonType.OK){
                        try {
                            BookSearchModelObservableList.remove(book);
                            Library.borrow_books(book.getBook_id(), user_id.getText());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            BookSearchModel book = book_search.getSelectionModel().getSelectedItem();
            bookSearchModel = book;
            book.getButton().setVisible(true);
            book.getButton().setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to add this book to this member?");
                if (alert.showAndWait().get() == ButtonType.OK){
                    try {
                        BookSearchModelObservableList.remove(book);
                        Library.borrow_books(book.getBook_id(),user_id.getText());
                    } catch (NullPointerException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        System.out.println(Model.getInstance().getSearchString());

        String BookViewQuery = "SELECT book_id, book_title, book_author, available from book";  // Fixed typo in the SQL query
        List<String> list1 = GoogleBooksAPI.getIdList(Model.getInstance().getSearchString());

        try {
            for (String id : list1) {
                try{
                Book book = GoogleBooksAPI.getDocumentDetails(id);
                BookSearchModelObservableList.add(new BookSearchModel(book.getBook_id(), book.getTitle(), book.getAuthor(), book.isAvailable() ? 1: 0));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
            title.setCellValueFactory(new PropertyValueFactory<>("book_title"));
            author.setCellValueFactory(new PropertyValueFactory<>("book_author"));
            available.setCellValueFactory(new PropertyValueFactory<>("available"));// Corrected to match the property
            action.setCellValueFactory(new PropertyValueFactory<>("button"));
            book_search.setItems(BookSearchModelObservableList);





            // Create the FilteredList and bind to the search bar text property
            FilteredList<BookSearchModel> filteredList = new FilteredList<>(BookSearchModelObservableList, b -> true);

            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(member -> {
                    if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                        return true;  // Return all data if the search bar is empty
                    }

                    String searchKeyword = newValue.toLowerCase();  // Convert search keyword to lowercase for case-insensitive matching

                    // Check if any field (account_id, lastname, or email) contains the search keyword
                    if (member.getBook_id().toLowerCase().contains(searchKeyword)) {
                        return true;  // Match found in account_id
                    } else if (String.valueOf(member.getBorrowed_user_id()).contains(searchKeyword)) {
                        return true;
                    }else if (member.getBorrowed_date().toLowerCase().contains(searchKeyword)) {
                        return true;  // Match found in lastname
                    } else if (member.getRequired_date().toLowerCase().contains(searchKeyword)) {
                        return true;  // Match found in email
                    } else if (String.valueOf(member.getAvailable()).contains(searchKeyword)) {
                        return true;
                    }

                    return false;  // No match found
                });
            });

            // Bind the TableView's sorted data with the filtered data
            SortedList<BookSearchModel> sortedData = new SortedList<>(filteredList);
            sortedData.comparatorProperty().bind(book_search.comparatorProperty());
            book_search.setItems(sortedData);

        } catch (Exception e) {
            Logger.getLogger(SearchResultAdminController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}
