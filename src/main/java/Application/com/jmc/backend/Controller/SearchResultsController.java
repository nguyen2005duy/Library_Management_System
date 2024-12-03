package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Model.Model;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchResultsController implements Initializable {

    @FXML
    private GridPane bookContainer;

    @FXML
    private Label number_of_results;

    @FXML
    private Label results;

    private Thread currentSearchThread = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize any setup if needed
    }

    public void refreshSearchResults() {
        String searchQuery = Model.getInstance().getSearchString();

        // Clear existing results
        results.setText("Results for '" + searchQuery + "'");
        bookContainer.getChildren().clear(); // Clear any previous results
        number_of_results.setText("Loading...");

        // Stop previous search thread if it's running
        if (currentSearchThread != null && currentSearchThread.isAlive()) {
            currentSearchThread.interrupt();
        }

        // Start a new search thread
        currentSearchThread = new Thread(() -> {
            List<Book> searchResults = getSearchResults(searchQuery);

            Platform.runLater(() -> {
                if (!searchResults.isEmpty()) {
                    number_of_results.setText(String.valueOf(searchResults.size()) + " results found");
                } else {
                    number_of_results.setText("Nothing to show");
                }

                int column = 0;
                int row = 1;

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
                    e.printStackTrace();
                }
            });
        });

        currentSearchThread.start(); // Start the thread
    }

    // Load results from Google API or library system
    private List<Book> getSearchResults(String query) {
        return Library.searchFor(query);
    }
}
