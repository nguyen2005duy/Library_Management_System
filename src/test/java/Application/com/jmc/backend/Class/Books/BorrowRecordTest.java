package Application.com.jmc.backend.Class.Books;

import org.junit.jupiter.api.Test;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BorrowRecordTest {

    @Test
    void testToStringWithUserRating() {
        Date borrowDate = Date.valueOf("2024-11-01");
        Date returnDate = Date.valueOf("2024-11-30");
        BorrowRecord record = new BorrowRecord(1, "3", borrowDate, returnDate, 4.5);

        String expected = "BorrowRecord{" +
                "account_id=1, book_id='3', borrowDate=" + borrowDate +
                ", returnDate=" + returnDate + ", userRating=4.5}";

        assertEquals(expected, record.toString());
    }

    @Test
    void testToStringWithoutUserRating() {
        Date borrowDate = Date.valueOf("2024-11-01");
        Date returnDate = Date.valueOf("2024-11-30");
        BorrowRecord record = new BorrowRecord(1, "3", borrowDate, returnDate);

        String expected = "BorrowRecord{" +
                "account_id=1, book_id='3', borrowDate=" + borrowDate +
                ", returnDate=" + returnDate + ", userRating=null}";

        assertEquals(expected, record.toString());
    }
}