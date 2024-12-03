package Application.com.jmc.backend.Views;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Controller.*;

import Application.com.jmc.backend.Controller.Admin.AdminController;
import Application.com.jmc.backend.Controller.Admin.AdminMenuController;
import Application.com.jmc.backend.Controller.Client.ClientController;
import Application.com.jmc.backend.Controller.Client.FavouriteController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.sound.midi.MidiFileFormat;
import java.io.IOException;

public class FactoryViews {
    //Customer views
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AnchorPane DiscoverView;
    private AnchorPane TrendingView;
    private AnchorPane LibraryView;
    private AnchorPane FavouriteView;
    private AnchorPane BookView;
    private AnchorPane ProfileView;
    private AnchorPane SearchView;
    private AnchorPane MembersView;
    private AnchorPane Check_outView;
    private AnchorPane DashboardView;
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private int LibrarySize;
    private int FavouriteSize;
    private int TrendingSize;


    public FactoryViews() {
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public void setFavouriteSize(int favouriteSize) {
        FavouriteSize = favouriteSize;
    }

    public void setLibrarySize(int librarySize) {
        LibrarySize = librarySize;
    }

    public void setTrendingSize(int trendingSize) {
        TrendingSize = trendingSize;
    }

    public AnchorPane getMembersView(){
        if(MembersView == null){
            try{
                MembersView = new FXMLLoader(getClass().getResource("/Application/Members.fxml")).load();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return MembersView;
    }

    public AnchorPane getCheckoutView(){
        if(Check_outView == null){
            try{
                Check_outView = new FXMLLoader(getClass().getResource("/Application/check_out_books.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return Check_outView;
    }

    public AnchorPane getDashboardView(){
        if(DashboardView == null){
            try{
                DashboardView = new FXMLLoader(getClass().getResource("Application/dashboard.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return DashboardView;
    }


    public AnchorPane getSearchView() {
        if(SearchView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/search_results.fxml"));
                SearchView = loader.load();
                SearchResultsController controller = loader.getController();
                controller.refreshSearchResults();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SearchView;
    }

/*    public AnchorPane getLibraryView() {
        if (LibraryView == null || LibrarySize !=
                ((Member) Library.current_user).getBorrowed_documents().size()) {
            try {
                LibrarySize = ((Member) Library.current_user).getBorrowed_documents().size();
                LibraryView = new FXMLLoader(getClass().getResource("/Application/Library.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }
        return LibraryView;
    }*/

    public AnchorPane getLibraryView() {
        if (LibraryView == null) {
            try {
                LibrarySize = ((Member) Library.current_user).getBorrowed_documents().size();
                LibraryView = new FXMLLoader(getClass().getResource("/Application/Library.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return LibraryView;
    }

    public AnchorPane getFavouriteView() {
        if (FavouriteView == null ) {
            try {
                FavouriteSize = ((Member) Library.current_user).getfavourite_books().size();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/favourite.fxml"));
                FavouriteView = loader.load();
                FavouriteController controller = loader.getController();
                controller.refreshFavouriteBooks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return FavouriteView;
    }

    public AnchorPane getTrendingView() {
        if (TrendingView == null) {
            try {
                LibrarySize = ((Member) Library.current_user).getBorrowed_documents().size();
                TrendingView = new FXMLLoader(getClass().getResource("/Application/trending.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TrendingView;
    }

    public AnchorPane getProfileView() {
        if (ProfileView == null) {
            try {
                ProfileView = new FXMLLoader(getClass().getResource("/Application/profile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ProfileView;
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DiscoverView;
    }

    public void showLoginView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Login.fxml"));
        createStage(loader);

    }
    public void showDiscoverView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/discover.fxml"));
        createStage(loader);
    }


    public void showClientView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;

        try {
            scene = new Scene(loader.load());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("cc");
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("The Books");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    public void showTrendingView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/trending.fxml"));
        createStage(loader);
    }

    public void showAdminView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }
}
