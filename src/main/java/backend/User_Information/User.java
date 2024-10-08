package backend.User_Information;

import java.util.List;

public abstract class User {
    private String user_id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private List<Integer> borrowed_documents;
    private String role;
}
