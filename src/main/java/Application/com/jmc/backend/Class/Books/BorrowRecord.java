package Application.com.jmc.backend.Class.Books;

import java.sql.Date;

public class BorrowRecord {
    private final String book_id;
    private final int account_id;
    private Double userRating;

    public BorrowRecord(int account_id, String book_id) {
        this.account_id = account_id;
        this.book_id = book_id;
    }

    public BorrowRecord(int account_id, String book_id, Double userRating) {
        this.account_id = account_id;
        this.book_id = book_id;
        this.userRating = userRating;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public int getAccount_id() {
        return account_id;
    }

    public String getBook_id() {
        return book_id;
    }
}

