package Application.com.jmc.backend.Controller.Client;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Model.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable
{
    public BorderPane client_parent;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        Model.getInstance().getFactoryViews().getClientSelectedMenuItem().addListener((observationValue, oldVal, newVal) -> {
            switch (newVal){
                case "Trending": client_parent.setCenter(Model.getInstance().getFactoryViews().getTrendingView()); break;
                case "Library" : client_parent.setCenter(Model.getInstance().getFactoryViews().getLibraryView()); break;
                case "Search":
                    client_parent.setCenter(Model.getInstance().getFactoryViews().getSearchView()); break;
                case "Favourite" : client_parent.setCenter(Model.getInstance().getFactoryViews().getFavouriteView()); break;
                case "Profile" : client_parent.setCenter(Model.getInstance().getFactoryViews().getProfileView()); break;
                case "Book" :
                    Book selectedBook = Model.getInstance().getSelectedBook(); // Adjust as needed
                    client_parent.setCenter(Model.getInstance().getFactoryViews().getBookView(selectedBook));
                   break;
                default: client_parent.setCenter(Model.getInstance().getFactoryViews().getDiscoverView()); break;
            }
        });
    }

}
