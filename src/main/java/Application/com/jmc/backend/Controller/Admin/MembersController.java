package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Connection.DatabaseConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MembersController implements Initializable {


    MemberSearchModel memberSearchModel;
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
    private Button add_button;

    @FXML
    private TextField searchBar;

    @FXML
    void rowclicked(MouseEvent event) {
        if (memberSearchModel != null) {
            try {
                memberSearchModel.getDelete().setVisible(false);
                MemberSearchModel mem = TableViewMember.getSelectionModel().getSelectedItem();
                memberSearchModel = mem;
                mem.getDelete().setVisible(true);
                mem.getDelete().setOnAction(actionEvent -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to delete this member?");
                    if (alert.showAndWait().get() == ButtonType.OK){
                        try {
                            MemberSearchModelObservableList.remove(mem);
                            Library.remove_user(mem.getAccount_id());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            MemberSearchModel mem = TableViewMember.getSelectionModel().getSelectedItem();
            memberSearchModel = mem;
            mem.getDelete().setVisible(true);
            mem.getDelete().setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this member?");
                if (alert.showAndWait().get() == ButtonType.OK){
                    try {
                        MemberSearchModelObservableList.remove(mem);
                        Library.remove_user(mem.getAccount_id());

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }



    @FXML
    void add_member(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/add_member.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Member");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            System.out.println("can't load new window");
        }
    }

      static ObservableList<MemberSearchModel> MemberSearchModelObservableList = FXCollections.observableArrayList();



    public static ObservableList<MemberSearchModel> getMemberSearchModelObservableList() {
        return MemberSearchModelObservableList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String memberViewQuery = "SELECT account_id, lastname, email FROM user_account WHERE role = 'Member'" ;

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
            Action.setCellValueFactory(new PropertyValueFactory<>("delete"));


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
