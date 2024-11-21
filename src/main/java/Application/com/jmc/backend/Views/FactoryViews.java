package Application.com.jmc.backend.Views;

import Application.com.jmc.backend.Controller.ClientController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FactoryViews {
    //Customer views
    private final SimpleStringProperty clientSelectedMenuItem;
    private AnchorPane DiscoverView;
    private AnchorPane TrendingView;

    public FactoryViews() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getTrendingView(){
        if (TrendingView == null) {
            try {
                TrendingView = new FXMLLoader(getClass().getResource("/Application/trending.fxml")).load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TrendingView;
    }

    public AnchorPane getDiscoverView() {
        if (DiscoverView == null) {
            try {
                DiscoverView = new FXMLLoader(getClass().getResource("/Application/discover.fxml")).load();
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DiscoverView;
    }
    public void showLoginView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Login.fxml"));
        createStage(loader);

    }

    public void showClientView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;

        try{
            scene = new Scene(loader.load());

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("cc");
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("The Books");
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }

    public void showTrendingView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/trending.fxml"));
        createStage(loader);
    }
}
