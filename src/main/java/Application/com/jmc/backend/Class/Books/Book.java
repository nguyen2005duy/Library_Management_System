package Application.com.jmc.backend.Class.Books;


import Application.com.jmc.backend.Class.Library.Library;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;

public class Book {
    private final String book_id;
    private String title;
    private String author;
    private String published_date;
    private String pages;
    private String[] categories;
    private boolean available;
    private String borrow_user_id;
    private java.sql.Date borrowed_date;
    private java.sql.Date required_date;
    private String imageSrc;

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public Book(String book_id, String borrow_user_id) {
        this.book_id = book_id;
        this.borrow_user_id = borrow_user_id;
        this.borrowed_date = new Date(System.currentTimeMillis());
        LocalDate borrowedLocalDate = borrowed_date.toLocalDate();
        LocalDate requiredLocalDate = borrowedLocalDate.plusDays(30);
        this.required_date = java.sql.Date.valueOf(requiredLocalDate);
    }

    public Book(String book_id, String title, String author, String[] categories,
                String published_date, String pages, String borrow_user_id, Date borrowed_date, Date required_date) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.categories = categories;
        this.published_date = published_date;
        this.pages = pages;
        this.available = false;
        this.borrow_user_id = borrow_user_id;
        this.borrowed_date = borrowed_date;
        this.required_date = required_date;
    }

    public Book(String book_id, String title, String author, String pages, String[] categories, String published_date) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.categories = categories;
        this.published_date = published_date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getBorrow_user_id() {
        return borrow_user_id;
    }

    public void setBorrow_user_id(String borrow_user_id) {
        this.borrow_user_id = borrow_user_id;
    }

    public Date getBorrowed_date() {
        return borrowed_date;
    }

    public void setBorrowed_date(Date borrowed_date) {
        this.borrowed_date = borrowed_date;
    }

    public void setBorrowed_Date(String borrowed_date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = formatter.parse(borrowed_date);
            Date sqlDate = new Date(utilDate.getTime());
            setBorrowed_date(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getRequired_date() {
        return required_date;
    }

    public void setRequired_date(Date required_date) {
        this.required_date = required_date;
    }

    public void setRequired_date(String required_date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = formatter.parse(required_date);
            Date sqlDate = new Date(utilDate.getTime());
            setBorrowed_date(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public Book(String book_id, String title, String author,
                String[] categories, String published_date, String pages) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.categories = categories;
        this.published_date = published_date;
        this.pages = pages;
        this.available = true;
    }

    /**
     * danh dau quyen sach nay chua co nguoi muon.
     */
    public void check_out() {
        Library.add_record(this);
        Date currentTime  = new Date(System.currentTimeMillis());
        LocalDate currentDate = borrowed_date.toLocalDate();
        Date currentDateSQL = java.sql.Date.valueOf(currentDate);
        Library.recordsLists.add(new BorrowRecord(Integer.parseInt(borrow_user_id),book_id,borrowed_date,currentDateSQL));
        String updateQuery = "UPDATE book SET available = 1, " +
                "borrowed_user_id = NULL, " + // Use NULL without quotes
                "borrowed_date = NULL, " +   // Use NULL without quotes
                "required_date = NULL " +   // Use NULL without quotes
                "WHERE book_id = '" + this.book_id + "'";
        try {
            Statement stmt = Library.connectDB.createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            System.out.println("Error updating book in database.");
            e.printStackTrace();
        }
        // Cần Update phần này để khi ktra thấy available chuyển book available thành ko
        available = true;
        borrow_user_id = null;
        borrowed_date = null;
        required_date = null;
    }

    /**
     * danh dau quyen sach nay da co nguoi muon.
     *
     * @param borrow_user_id id nguoi muon.
     */
    public void check_in(String borrow_user_id) {
        available = false;
        this.borrow_user_id = borrow_user_id;
        this.borrowed_date = new Date(System.currentTimeMillis());
        LocalDate borrowedLocalDate = borrowed_date.toLocalDate();
        LocalDate requiredLocalDate = borrowedLocalDate.plusDays(30);
        this.required_date = java.sql.Date.valueOf(requiredLocalDate);
    }

    @Override
    public String toString() {
        return "Book{" +  "borrowed_id'" + borrow_user_id + '\'' +
                "title='" + title + '\'' +
                ", published_date='" + published_date + '\'' +
                ", pages=" + pages +
                ", categories=" + Arrays.toString(categories) +
                ", book_id='" + book_id + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Book other = (Book) obj;
        return book_id.equals(other.book_id);
    }
}
