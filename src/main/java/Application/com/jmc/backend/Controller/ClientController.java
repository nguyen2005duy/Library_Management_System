package Application.com.jmc.backend.Controller;

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
                case "Category" : client_parent.setCenter(Model.getInstance().getFactoryViews().getCategoryView()); break;
                case "Book" :client_parent.setCenter(Model.getInstance().getFactoryViews().getBookView()); break;
                default: client_parent.setCenter(Model.getInstance().getFactoryViews().getDiscoverView()); break;
            }
        });
    }

}
