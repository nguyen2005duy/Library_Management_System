package Application.com.jmc.backend.Controller.Client;

import Application.com.jmc.backend.Class.Helpers.GoogleBooksAPI;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.ClientMenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DiscoverController implements Initializable {

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private Text text3;

    @FXML
    private Text text4;

    @FXML
    private TextField search_bar;

    @FXML
    private Button search_button;

    @FXML
    private MenuButton search_menu;

    @FXML
    private Label label;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private ImageView image;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;


    @FXML
    void search(MouseEvent event) {
        Model.getInstance().setSearchString(search_bar.getText());
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.SEARCH);
    }

    @FXML
    private void openGenre(MouseEvent event) throws IOException {
        // Determine which genre (or label) was clicked
        Label clickedLabel = (Label) event.getSource(); // Cast the clicked item to a Label
        String selectedCategory = clickedLabel.getText();  // Get the text of the label (the category)

        // Set the selected category in the Model
        Model.getInstance().setSelectedCategory(selectedCategory);
        // Use the same menu item to search by category
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().set(ClientMenuOptions.SEARCHBYCATEGORY);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String[] popularCategories = Library.get_popular_categories();

            Label[] labels = {label, label1, label2, label3, label4, label5, label6, label7};
            ImageView[] images = {image, image2, image3, image4, image5, image6, image7, image8};

            for (int i = 0; i < labels.length; i++) {
                labels[i].setText(popularCategories[i]);
                Image image = GoogleBooksAPI.getCategoryCover(popularCategories[i]);

                images[i].setImage(image);
            }
        } catch (IOException e) {
            System.out.println("Couldn't get popularCategories in Discover");
        }
    }

}
