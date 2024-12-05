package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.AdminMenuOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Check_out_BookController implements Initializable {

    BookSearchModel bookSearchModel;

    @FXML
    private TableColumn<BookSearchModel, ?> Action;

    @FXML
    private TableColumn<BookSearchModel, String> Book_ID;

    @FXML
    private TableView<BookSearchModel> TableViewBook;

    @FXML
    private TableColumn<BookSearchModel, String> borrowed_date;

    @FXML
    private TableColumn<BookSearchModel, String> returned_date;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<BookSearchModel, Integer> status;

    @FXML
    private TableColumn<BookSearchModel, Integer> Member_id;

    @FXML
    private TextField bookSearchBar;

    @FXML
    private TableColumn<BookSearchModel, ?> author;

    @FXML
    private TableColumn<BookSearchModel, ?> title;

    @FXML
    private Button search_button;

    @FXML
    void search(MouseEvent event) {
        Model.getInstance().setSearchString(bookSearchBar.getText());
        Model.getInstance().getFactoryViews().getAdminSelectedMenuItem().set(AdminMenuOptions.SEARCH);
        System.out.println(Model.getInstance().getSearchString());
    }

    ObservableList<BookSearchModel> BookSearchModelObservableList= FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String BookViewQuery = "SELECT book_id, book_title, book_author, available, borrowed_user_id, borrowed_date, required_date from book";  // Fixed typo in the SQL query

        try {
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(BookViewQuery);

            while (rs.next()) {
                String query_book_id = rs.getString("book_id");
                String query_title = rs.getString("book_title");
                String query_author = rs.getString("book_author");
                Integer query_borrower_id = rs.getInt("borrowed_user_id");
                String query_borrow_date = rs.getString("borrowed_date");
                String query_return_date = rs.getString("required_date");
                Integer query_available = rs.getInt("available");  // Corrected spelling of 'available'

                BookSearchModelObservableList.add(new BookSearchModel(query_book_id,query_title,query_author, query_available, query_borrower_id, query_borrow_date, query_return_date));
            }

            Book_ID.setCellValueFactory(new PropertyValueFactory<>("book_id"));
            title.setCellValueFactory(new PropertyValueFactory<>("book_title"));
            author.setCellValueFactory(new PropertyValueFactory<>("book_author"));
            Member_id.setCellValueFactory(new PropertyValueFactory<>("borrowed_user_id"));
            borrowed_date.setCellValueFactory(new PropertyValueFactory<>("borrowed_date"));
            returned_date.setCellValueFactory(new PropertyValueFactory<>("required_date"));
            status.setCellValueFactory(new PropertyValueFactory<>("available"));  // Corrected to match the property
            Action.setCellValueFactory(new PropertyValueFactory<>("button"));
            TableViewBook.setItems(BookSearchModelObservableList);





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
                    } else if(member.getBook_title().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }else if (member.getBook_author().toLowerCase().contains(searchKeyword)) {
                        return true;
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
            sortedData.comparatorProperty().bind(TableViewBook.comparatorProperty());
            TableViewBook.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(Check_out_BookController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }


    @FXML
    void rowclicked(MouseEvent event) {
        if (bookSearchModel != null) {
            try {
                bookSearchModel.getButton().setVisible(false);
                BookSearchModel book = TableViewBook.getSelectionModel().getSelectedItem();
                bookSearchModel = book;
                book.getButton().setVisible(true);
                book.getButton().setOnAction(actionEvent -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to return this book?");
                    if (alert.showAndWait().get() == ButtonType.OK){
                        try {
                            BookSearchModelObservableList.remove(book);
                            Library.returnBook(book.getBook_id());
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
            BookSearchModel book = TableViewBook.getSelectionModel().getSelectedItem();
            bookSearchModel = book;
            book.getButton().setVisible(true);
            book.getButton().setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to return this book?");
                if (alert.showAndWait().get() == ButtonType.OK){
                    try {
                        BookSearchModelObservableList.remove(book);
                        Library.returnBook(book.getBook_id());

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }



}
