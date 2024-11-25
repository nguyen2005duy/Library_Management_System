package Application.com.jmc.backend.Class.Books;

import java.sql.Date;

public class BorrowRecord {
    private final String book_id;
    private final int account_id;
    private final Date borrowDate;
    private final Date returnDate;
    private Double userRating;

    public BorrowRecord(int account_id, String book_id, Date borrowDate, Date returnDate, Double userRating) {
        this.account_id = account_id;
        this.book_id = book_id;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.userRating = userRating;
    }

    public BorrowRecord(int account_id, String book_id, Date borrowDate, Date returnDate) {
        this.account_id = account_id;
        this.book_id = book_id;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getAccount_id() {
        return account_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "account_id=" + account_id +
                ", book_id='" + book_id + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", userRating=" + userRating +
                '}';
    }
}

