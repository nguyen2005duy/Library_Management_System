package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchResultsController implements Initializable {

    @FXML
    private GridPane bookContainer;

    @FXML
    private Label number_of_results;

    @FXML
    private Label results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         refreshSearchResults();
    }
    public void refreshSearchResults () {
        results.setText("Results for '" + Model.getInstance().getSearchString() + "'");
        List<Book> searchResults = new ArrayList<>(getSearchResults(Model.getInstance().getSearchString()));
        int column = 0;
        int row = 1;
        if (!searchResults.isEmpty()) {
            number_of_results.setText(String.valueOf(searchResults.size()));
        } else {
            number_of_results.setText("Nothing to show");
        }
        try {
            for (Book res : searchResults) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Application/BookCard.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookCardController bookCardController = fxmlLoader.getController();
                bookCardController.setData(res);

                if (column == 4) {
                    column = 0;
                    row++;
                }

                bookContainer.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));
            }
        } catch (IOException e) {
            System.out.println("CSS");

        }
    }
    //load kq tu gg api
    private List<Book> getSearchResults(String queueFor) {
        return Library.searchFor(queueFor);
    }
}
