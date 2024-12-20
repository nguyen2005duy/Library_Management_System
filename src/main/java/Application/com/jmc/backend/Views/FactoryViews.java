package Application.com.jmc.backend.Views;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Controller.*;

import Application.com.jmc.backend.Controller.Admin.AdminController;
import Application.com.jmc.backend.Controller.Admin.SearchResultAdminController;
import Application.com.jmc.backend.Controller.Client.ClientController;
import Application.com.jmc.backend.Controller.Client.FavouriteController;
import Application.com.jmc.backend.Controller.Client.SearchResultsController;
import Application.com.jmc.backend.Model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private AnchorPane AdminProfileView;
    private AnchorPane AdminSearchView;
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AccountType accountType;
    private SearchResultsController searchResultsController;
    private SearchResultAdminController adminSearchResultsController;
    private FavouriteController favouriteController;

    public FactoryViews() {
        this.accountType = AccountType.Librarian;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AnchorPane getAdminSearchView(){
        if(AdminSearchView == null){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/search_results_admin.fxml"));
                AdminSearchView = loader.load();
                adminSearchResultsController = loader.getController();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        adminSearchResultsController.refreshSearchResultAdminController();
        return AdminSearchView;
    }

    public AnchorPane getMembersView() {
        if (MembersView == null) {
            try {
                MembersView = new FXMLLoader(getClass().getResource("/Application/Members.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return MembersView;
    }

    public AnchorPane getCheckoutView() {
        if (Check_outView == null) {
            try {
                Check_outView = new FXMLLoader(getClass().getResource("/Application/check_out_books.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Check_outView;
    }

    public AnchorPane getDashboardView() {
        if (DashboardView == null) {
            try {
                DashboardView = new FXMLLoader(getClass().getResource("/Application/dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DashboardView;
    }


    public AnchorPane getSearchView() {
        if (SearchView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/search_results.fxml"));
                SearchView = loader.load(); // Load the FXML
                 searchResultsController = loader.getController(); // Get the controller
                if (searchResultsController == null) {
                    System.out.println("Error: SearchResultsController is null!");
                } else {
                    searchResultsController.refreshSearchResults(); // Refresh results
                    System.out.println("SearchResultsController loaded and refreshed successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                // Retrieve and refresh the controller for the cached view

                if (searchResultsController != null) {
                    searchResultsController.refreshSearchResults();
                    System.out.println("SearchResultsController refreshed successfully.");
                } else {
                    System.out.println("Error: Controller not found for cached SearchView!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SearchView;
    }


    public AnchorPane getSearchCategoryView() {
        if (SearchView == null) {
            try {
                // Load FXML and retrieve controller for the first time
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/search_results.fxml"));
                SearchView = loader.load(); // Load FXML
                searchResultsController = loader.getController(); // Store controller reference
                if (searchResultsController != null) {
                    searchResultsController.refreshSearchResultsByCategories(Model.getInstance().getSelectedCategory());
                } else {
                    System.out.println("Error: SearchResultsController is null!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (searchResultsController != null) {
            // Use the cached controller to refresh results
            searchResultsController.refreshSearchResultsByCategories(Model.getInstance().getSelectedCategory());
        } else {
            System.out.println("Error: SearchView is cached, but controller is null!");
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
            Task<AnchorPane> task = new Task<AnchorPane>() {
                @Override
                protected AnchorPane call() throws Exception {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Library.fxml"));
                    return loader.load();
                }
            };
            task.setOnSucceeded(event -> {
                LibraryView = task.getValue();
            });
            new Thread(task).start();

        }
        return LibraryView;
    }

    public AnchorPane getFavouriteView() {
        if (FavouriteView == null) {
            Task<AnchorPane> loadFavouriteViewTask = new Task<AnchorPane>() {
                @Override
                protected AnchorPane call() throws Exception {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/favourite.fxml"));
                    AnchorPane loadedView = loader.load();  // This is where the view is loaded
                     favouriteController= loader.getController();
                    favouriteController.refreshFavouriteBooks();

                    return loadedView;
                }
            };

            loadFavouriteViewTask.setOnSucceeded(event -> {
                FavouriteView = loadFavouriteViewTask.getValue();
            });

            new Thread(loadFavouriteViewTask).start();
        }

        favouriteController.refreshFavouriteBooks();
        return FavouriteView;
    }


    public AnchorPane getTrendingView() {
        if (TrendingView == null) {
            Task<AnchorPane> task = new Task<AnchorPane>() {
                @Override
                protected AnchorPane call() throws Exception {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/trending.fxml"));
                    return loader.load();
                }
            };
            task.setOnSucceeded(event -> {
                // Get the loaded view from the background thread and assign it to TrendingView
                TrendingView = task.getValue();
            });
            new Thread(task).start();

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

    public AnchorPane getAdminProfileView() {
        if (AdminProfileView == null) {
            try {
                AdminProfileView = new FXMLLoader(getClass().getResource("/Application/profile_admin.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AdminProfileView;
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

    public AnchorPane getQRView(Book book, Image image) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/QRCode.fxml"));
            BookView = loader.load();
            QRCodeController controller = loader.getController();
            controller.setBookData(book, image);
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

    public void preLoadLibrary() {
        // Start loading the views asynchronously
        loadViewAsync(() -> LibraryView = getLibraryView());
        loadViewAsync(() -> FavouriteView = getFavouriteView());
        loadViewAsync(() -> TrendingView = getTrendingView());
        loadViewAsync(() -> DiscoverView = getDiscoverView());
    }

    private void loadViewAsync(Runnable loadView) {
        // Load the view
        new Thread(loadView::run).start();
    }

    public boolean checkIfLoadedLibrary() {
        System.out.println(LibraryView!=null);
        System.out.println(FavouriteView!=null);
        System.out.println(TrendingView!=null);
        System.out.println(DiscoverView!=null);

        return LibraryView != null
                && FavouriteView != null
                && TrendingView != null
                && DiscoverView != null;
    }



    public void showClientView() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    public void showLoadingView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/loading.fxml"));
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
