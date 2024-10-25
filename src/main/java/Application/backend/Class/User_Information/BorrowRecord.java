package Application.backend.Class.User_Information;

import java.sql.Date;

public class BorrowRecord {
    private final String document_id;
    private final String member_id;
    private final Date borrowDate;
    private final Date returnDate;
    private int userRating;

    public BorrowRecord(String document_id, String member_id, Date borrowDate, Date returnDate) {
        this.document_id = document_id;
        this.member_id = member_id;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.userRating = 0;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getDocument_id() {
        return document_id;
    }


    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }


    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }
}

