package Application.Views;

import Application.backend.Controller.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FactoryViews {
    //Customer views
    private AnchorPane appView;

    public FactoryViews() {}

    public void showLoginView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Login.fxml"));
        createStage(loader);

    }

    public void showAppView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/App.fxml"));
        AppController appController = new AppController();
        loader.setController(appController);

        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("The Books");
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }

}
