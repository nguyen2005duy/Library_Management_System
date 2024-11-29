package Application.com.jmc.backend.Controller.Admin;

import Application.com.jmc.backend.Connection.DatabaseConnection;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
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

        try{
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(memberViewQuery);

            while (rs.next()) {
                Integer query_member_id = rs.getInt("account_id");
                StringProperty query_member_name = rs.getString("lastname");
                StringProperty query_member_email = rs.getString("email");

                MemberSearchModelObservableList.add(new MemberSearchModel(query_member_id,query_member_name, query_member_email));
            }

            Member_ID.setCellValueFactory(new PropertyValueFactory<>("account_id"));
            Member_name.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            Member_email.setCellValueFactory(new PropertyValueFactory<>("email"));


            TableViewMember.setItems(MemberSearchModelObservableList);

        }catch (Exception e){
            Logger.getLogger(MembersController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}
