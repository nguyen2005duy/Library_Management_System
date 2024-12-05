package Application.com.jmc.backend.Class.User_Information;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Class.Books.BorrowRecord;

import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    private String member_id;
    private List<BorrowRecord> BorrowedHistory;
    private List<Book> borrowedDocuments;
    private List<String> favourite_genres;
    private List<Book> favourite_books;
    private boolean isPremiumMember;

    public Member(String username, String password, String firstName,
                  String lastName, String email, String role) {
        super(username, password, firstName, lastName, email, role);
        this.member_id = "M_" + account_id;
        borrowedDocuments = new ArrayList<>();
        BorrowedHistory = new ArrayList<>();
        favourite_genres = new ArrayList<>();
        favourite_books = new ArrayList<>();
    }

    public Member(int account_id, String username, String password, String firstName, String lastName, String email, String role, String member_id, boolean isPremiumMember) {
        super(username, password, firstName, lastName, email, role);
        this.account_id = account_id;
        this.member_id = member_id;
        this.isPremiumMember = isPremiumMember;
        borrowedDocuments = new ArrayList<>();
        BorrowedHistory = new ArrayList<>();
        favourite_genres = new ArrayList<>();
        favourite_books = new ArrayList<>();
    }

    public List<Book> getfavourite_books() {
        return favourite_books;
    }

    public void addFavouriteBooks (Book book) {
        favourite_books.add(book);
    }
    public void generateMemberType(){
        member_id = "M_"+account_id;
        BorrowedHistory = new ArrayList<>();
        borrowedDocuments = new ArrayList<>();
        favourite_genres = new ArrayList<>();
        isPremiumMember = false;
    }

    public void add_borrowed_documents(Book book) {
        borrowedDocuments.add(book);
    }
    public List<Book> getBorrowedDocuments() {
        return borrowedDocuments;
    }

    public String getMember_id() {
        return member_id;
    }

    public boolean isPremiumMember() {
        return isPremiumMember;
    }

    public void add_book(Book book) {
        borrowedDocuments.add(book);
    }

    public void setBorrowedDocuments(List<Book> borrowedDocuments) {
        this.borrowedDocuments = borrowedDocuments;
    }

    public List<BorrowRecord> getBorrowedHistory() {
        return BorrowedHistory;
    }

    public void setBorrowedHistory(List<BorrowRecord> borrowedHistory) {
        BorrowedHistory = borrowedHistory;
    }
    public List<Book> getFavourite_books() {
        return favourite_books;
    }

    public void setFavourite_books(List<Book> favourite_books) {
        this.favourite_books = favourite_books;
    }
    public Book removeBook(String id) {
        Book bookToRemove = null;

        for (int i = 0; i< borrowedDocuments.size(); i++)  {
            if (borrowedDocuments.get(i).getBook_id().equals(id)) {
                    bookToRemove = borrowedDocuments.get(i);
            }
        }
        borrowedDocuments.remove(bookToRemove);
        return bookToRemove;
    }


}
