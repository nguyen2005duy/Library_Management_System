package Application.com.jmc.backend.Views;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Controller.BookCardController;
import Application.com.jmc.backend.Controller.BookController;
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
    private AnchorPane LibraryView;
    private AnchorPane CategoryView;
    private AnchorPane BookView;
    public FactoryViews() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getLibraryView() {
        if (LibraryView == null) {
            try {
                LibraryView = new FXMLLoader(getClass().getResource("/Application/Library.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return LibraryView;
    }

    public AnchorPane getCategoryView() {
        if (CategoryView == null) {
            try {
                CategoryView = new FXMLLoader(getClass().getResource("/Application/Category.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return CategoryView;
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

    public AnchorPane getBookView(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/book.fxml"));
            BookView = loader.load();

            BookController controller = loader.getController();
            controller.setBookData(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BookView;
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
