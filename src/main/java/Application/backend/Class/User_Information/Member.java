package Application.backend.Class.User_Information;

import java.util.ArrayList;
import java.util.List;

public class Member extends User{
    private String member_id;
    private List<BorrowRecord> BorrowedHistory;
    private List<Integer> borrowed_documents;
    private List<String> favourite_genres;
    public Member(String username, String password, String firstName,
                  String lastName, String email, String role) {
        super(username, password, firstName, lastName, email, role);
        this.member_id = "M_"+account_id;
        borrowed_documents = new ArrayList<>();
        BorrowedHistory = new ArrayList<>();
        favourite_genres = new ArrayList<>();
    }
}
