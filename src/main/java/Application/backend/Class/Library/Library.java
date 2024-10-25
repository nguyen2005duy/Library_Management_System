package Application.backend.Class.Library;
import Application.backend.Class.User_Information.User;
import Application.backend.Documents.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
public class Library {
    private static final Library instance = new Library();
    public static Connection connectDB;
    private List<Document> documentsList;
    private List<User> usersList;
    public Library()
    {
        documentsList = new ArrayList<>();
        usersList = new ArrayList<>();
    }

    public static Library getInstance()
    {
        return instance;
    }

    public void loadDocuments(){

    }

    public void loadUsers()
    {

    }

    public static void add_document(Document document)
    {

    }
}
