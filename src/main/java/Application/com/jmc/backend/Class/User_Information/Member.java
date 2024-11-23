package Application.com.jmc.backend.Class.User_Information;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Books.BorrowRecord;

import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    private String member_id;
    private List<BorrowRecord> BorrowedHistory;
    private List<Book> borrowed_documents;
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

    public Member(int account_id, String username, String password, String firstName, String lastName, String email, String role, String member_id, boolean isPremiumMember) {
        super(username, password, firstName, lastName, email, role);
        this.account_id = account_id;
        this.member_id = member_id;
        this.isPremiumMember = isPremiumMember;
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

    public List<Book> getBorrowed_documents() {
        return borrowed_documents;
    }

    public String getMember_id() {
        return member_id;
    }

    public boolean isPremiumMember() {
        return isPremiumMember;
    }
    public void add_book(Book book) {
        borrowed_documents.add(book);
    }

    public void setBorrowed_documents(List<Book> borrowed_documents) {
        this.borrowed_documents = borrowed_documents;
    }

    public List<BorrowRecord> getBorrowedHistory() {
        return BorrowedHistory;
    }

    public void setBorrowedHistory(List<BorrowRecord> borrowedHistory) {
        BorrowedHistory = borrowedHistory;
    }
}
