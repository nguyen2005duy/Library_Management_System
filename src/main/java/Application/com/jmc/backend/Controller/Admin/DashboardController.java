package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.AdminMenuOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {
    @FXML
    private TableView<BookSearchModel> TableViewDue;

    @FXML
    private TableView<BookSearchModel> TableViewRecent;

    @FXML
    private TableColumn<BookSearchModel, ?> author;

    @FXML
    private TableColumn<BookSearchModel, ?> avaiable;

    @FXML
    private TableColumn<BookSearchModel, ?> book_id;

    @FXML
    private TableColumn<BookSearchModel, ?> book_id_due;

    @FXML
    private TableColumn<BookSearchModel, ?> borrow_date;

    @FXML
    private TableColumn<BookSearchModel, ?> due_date;

    @FXML
    private TableColumn<BookSearchModel, ?> fine;

    @FXML
    private TableColumn<BookSearchModel, ?> member_id;

    @FXML
    private TableColumn<BookSearchModel, ?> member_id_due;

    @FXML
    private TableColumn<BookSearchModel, ?> return_date;

    @FXML
    private TableColumn<BookSearchModel, ?> title;

    @FXML
    private TableColumn<BookSearchModel, ?> title_due;

    @FXML
    private Label user_numbers;

    @FXML
    private TextField bookSearchBar;

    @FXML
    void search(MouseEvent event) {
        Model.getInstance().setSearchString(bookSearchBar.getText());
        Model.getInstance().getFactoryViews().getAdminSelectedMenuItem().set(AdminMenuOptions.SEARCH);
        System.out.println(Model.getInstance().getSearchString());

    }

    @FXML
    private Button search_button;

    @FXML
    void loadBook(MouseEvent event) {
        Model.getInstance().getFactoryViews().getAdminSelectedMenuItem().set(AdminMenuOptions.CHECK_OUT);
    }

    ObservableList<BookSearchModel> BookSearchModelObservableList= FXCollections.observableArrayList();
    ObservableList<BookSearchModel> DueBookSearchModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user_numbers.setText(String.valueOf(Library.usersList.size()));
        user_numbers.setText(String.valueOf(MembersController.MemberSearchModelObservableList.size()));
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String BookViewQuery = "SELECT book_id, available, borrowed_user_id, borrowed_date, required_date from book";  // Fixed typo in the SQL query
        String DueBookQuery = "SELECT book_id,title, borrowed_user_id, DATEDIFF(required_date, NOW()) as due_date, (2 * DATEDIFF(required_date, NOW())) as fine from book";
        //Book
        try {
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(BookViewQuery);

            while (rs.next()) {
                String query_book_id = rs.getString("book_id");
                Integer query_borrower_id = rs.getInt("borrowed_user_id");
                String query_borrow_date = rs.getString("borrowed_date");
                String query_return_date = rs.getString("required_date");
                Integer query_available = rs.getInt("available");  // Corrected spelling of 'available'

                BookSearchModelObservableList.add(new BookSearchModel(query_book_id, query_available, query_borrower_id, query_borrow_date, query_return_date));
            }

            book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
            member_id.setCellValueFactory(new PropertyValueFactory<>("borrowed_user_id"));
            borrow_date.setCellValueFactory(new PropertyValueFactory<>("borrowed_date"));
            return_date.setCellValueFactory(new PropertyValueFactory<>("required_date"));
            avaiable.setCellValueFactory(new PropertyValueFactory<>("available"));  // Corrected to match the property

            TableViewRecent.setItems(BookSearchModelObservableList);
        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }


        //Due_book
        try{
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(DueBookQuery);

            while (rs.next()) {
                String query_book_id = rs.getString("book_id");
                String query_book_title = rs.getString("title");
                Integer query_borrower_id = rs.getInt("borrowed_user_id");
                Integer query_due_date = rs.getInt("due_date");
                Integer query_fine = rs.getInt("fine");  // Corrected spelling of 'available'

                DueBookSearchModelObservableList.add(new BookSearchModel(query_book_id, query_book_title, query_borrower_id, query_due_date, query_fine));
            }

            book_id_due.setCellValueFactory(new PropertyValueFactory<>("book_id"));
            title_due.setCellValueFactory(new PropertyValueFactory<>("title"));
            member_id_due.setCellValueFactory(new PropertyValueFactory<>("borrowed_user_id"));
            due_date.setCellValueFactory(new PropertyValueFactory<>("due_date"));
            fine.setCellValueFactory(new PropertyValueFactory<>("fine"));  // Corrected to match the property

            TableViewDue.setItems(DueBookSearchModelObservableList);
        }catch(SQLException e){
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}
