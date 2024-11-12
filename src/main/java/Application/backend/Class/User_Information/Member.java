package Application.backend.Class.User_Information;

import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    private String member_id;
    private List<BorrowRecord> BorrowedHistory;
    private List<Integer> borrowed_documents;
    private List<String> favourite_genres;
    private boolean isPremiumMember;
    public Member(String username, String password, String firstName,
                  String lastName, String email, String role) {
        super(username, password, firstName, lastName, email, role);
        this.member_id = "M_" + account_id;
        borrowed_documents = new ArrayList<>();
        BorrowedHistory = new ArrayList<>();
        favourite_genres = new ArrayList<>();
    }

    public void generateMemberType(){
        member_id = "M_"+account_id;
        BorrowedHistory = new ArrayList<>();
        borrowed_documents = new ArrayList<>();
        favourite_genres = new ArrayList<>();
        isPremiumMember = false;
    }

    public String getMember_id() {
        return member_id;
    }

    public boolean isPremiumMember() {
        return isPremiumMember;
    }
}
