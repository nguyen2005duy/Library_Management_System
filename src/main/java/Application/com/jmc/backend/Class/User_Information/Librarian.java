package Application.com.jmc.backend.Class.User_Information;

public class Librarian extends User {
    private String Employment_date;
    private String employee_id;

    public Librarian(int accountId ,String username, String password, String firstName, String lastName, String email, String role) {
        super(username, password, firstName, lastName, email, role);
        super.setAccount_id(accountId);
        employee_id = "L_" +accountId;
    }
}
