package Application.com.jmc.backend.Views;

import Application.com.jmc.backend.Class.Books.Book;
<<<<<<< HEAD
import Application.com.jmc.backend.Controller.Admin.AdminController;
import Application.com.jmc.backend.Controller.BookController;
import Application.com.jmc.backend.Controller.Client.ClientController;
=======
import Application.com.jmc.backend.Class.Library.Library;
import Application.com.jmc.backend.Class.User_Information.Member;
import Application.com.jmc.backend.Controller.*;
>>>>>>> e3a0fa03e55dc15e116a1f2cfac6ec9dba03a2f7

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FactoryViews {
    //Customer views
    private final SimpleStringProperty clientSelectedMenuItem;
    private AnchorPane DiscoverView;
    private AnchorPane TrendingView;
    private AnchorPane LibraryView;
    private AnchorPane FavouriteView;
    private AnchorPane BookView;
    private AnchorPane ProfileView;
    private AnchorPane SearchView;
<<<<<<< HEAD

    private SimpleStringProperty AdminSelectedMenuItem;
    private AnchorPane MembersView;
=======
    private int LibrarySize;
    private int FavouriteSize;
    private int TrendingSize;


>>>>>>> e3a0fa03e55dc15e116a1f2cfac6ec9dba03a2f7
    public FactoryViews() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
        this.AdminSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

<<<<<<< HEAD
    public StringProperty getAdminSelectedMenuItem() {
        return AdminSelectedMenuItem;
    }

    public AnchorPane getMembersView() {
        if (MembersView == null) {
            try{
                MembersView = new FXMLLoader(getClass().getResource("/Application/Members.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return MembersView;
    }

    public AnchorPane getSearchView(){
        if (SearchView == null) {
            try{
                SearchView = new FXMLLoader(getClass().getResource("/Application/search_results.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
=======
    public void setFavouriteSize(int favouriteSize) {
        FavouriteSize = favouriteSize;
    }

    public void setLibrarySize(int librarySize) {
        LibrarySize = librarySize;
    }

    public void setTrendingSize(int trendingSize) {
        TrendingSize = trendingSize;
    }

    public AnchorPane getSearchView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/search_results.fxml"));
            SearchView = loader.load();
            SearchResultsController controller = loader.getController();
            controller.refreshSearchResults();
        } catch (Exception e) {
            e.printStackTrace();
>>>>>>> e3a0fa03e55dc15e116a1f2cfac6ec9dba03a2f7
        }
        return SearchView;
    }

    public AnchorPane getLibraryView() {
        if (LibraryView == null||LibrarySize!=
                ((Member) Library.current_user).getBorrowed_documents().size()) {
            try {
                LibrarySize= ((Member) Library.current_user).getBorrowed_documents().size();
                LibraryView = new FXMLLoader(getClass().getResource("/Application/Library.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }
        return LibraryView;
    }



    public AnchorPane getFavouriteView() {
        if (FavouriteView == null || FavouriteSize !=
                ((Member) Library.current_user).getfavourite_books().size()) {
            try {
                FavouriteSize =  ((Member) Library.current_user).getfavourite_books().size();
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
        if (TrendingView == null || LibrarySize !=
                ((Member) Library.current_user).getBorrowed_documents().size()) {
            try {
                LibrarySize= ((Member) Library.current_user).getBorrowed_documents().size();
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


    public void showClientView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    public void showAdminView (){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Admin.fxml"));
        AdminController AdminController = new AdminController();
        loader.setController(AdminController);
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
}
