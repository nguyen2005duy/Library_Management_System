package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Check_out_BookController implements Initializable {

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

    ObservableList<BookSearchModel> BookSearchModelObservableList= FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String BookViewQuery = "SELECT book_id, available, borrowed_user_id, borrowed_date, required_date from book";  // Fixed typo in the SQL query

        try {
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(BookViewQuery);

            while (rs.next()) {
                String query_book_id = rs.getString("book_id");
                Integer query_borrower_id = rs.getInt("borrowed_user_id");
                String query_borrow_date = rs.getString("borrowed_date");
                String query_return_date = rs.getString("required_date");
                Integer query_available = rs.getInt("available");

                BookSearchModelObservableList.add(new BookSearchModel(query_book_id, query_available, query_borrower_id, query_borrow_date,query_return_date));
            }

            Book_ID.setCellValueFactory(new PropertyValueFactory<>("book_id"));
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

}
