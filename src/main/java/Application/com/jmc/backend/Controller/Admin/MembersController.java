package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import javafx.beans.property.StringProperty;
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


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MembersController implements Initializable {

    @FXML
    private TableColumn<MemberSearchModel, ?> Action;
    @FXML
    private TableColumn<MemberSearchModel, Integer> Member_ID;

    @FXML
    private TableColumn<MemberSearchModel, String> Member_email;

    @FXML
    private TableColumn<MemberSearchModel, String> Member_name;

    @FXML
    private TableView<MemberSearchModel> TableViewMember;

    @FXML
    private TextField searchBar;

    ObservableList<MemberSearchModel> MemberSearchModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String memberViewQuery = "SELECT account_id, lastname, email FROM user_account";

        try {
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(memberViewQuery);

            while (rs.next()) {
                Integer query_member_id = rs.getInt("account_id");
                String query_member_name = rs.getString("lastname");
                String query_member_email = rs.getString("email");

                MemberSearchModelObservableList.add(new MemberSearchModel(query_member_id, query_member_name, query_member_email));
            }

            Member_ID.setCellValueFactory(new PropertyValueFactory<>("account_id"));
            Member_name.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            Member_email.setCellValueFactory(new PropertyValueFactory<>("email"));

            TableViewMember.setItems(MemberSearchModelObservableList);

            // Create the FilteredList and bind to the search bar text property
            FilteredList<MemberSearchModel> filteredList = new FilteredList<>(MemberSearchModelObservableList, b -> true);

            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(member -> {
                    if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                        return true;  // Return all data if the search bar is empty
                    }

                    String searchKeyword = newValue.toLowerCase();  // Convert search keyword to lowercase for case-insensitive matching

                    // Check if any field (account_id, lastname, or email) contains the search keyword
                    if (String.valueOf(member.getAccount_id()).contains(searchKeyword)) {
                        return true;  // Match found in account_id
                    } else if (member.getLastname().toLowerCase().contains(searchKeyword)) {
                        return true;  // Match found in lastname
                    } else if (member.getEmail().toLowerCase().contains(searchKeyword)) {
                        return true;  // Match found in email
                    }

                    return false;  // No match found
                });
            });

            // Bind the TableView's sorted data with the filtered data
            SortedList<MemberSearchModel> sortedData = new SortedList<>(filteredList);
            sortedData.comparatorProperty().bind(TableViewMember.comparatorProperty());
            TableViewMember.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(MembersController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}
