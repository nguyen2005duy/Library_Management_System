package Application.com.jmc.backend.Class.Books;


import Application.com.jmc.backend.Class.Library.Library;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Book {
    private final String book_id;
    private String title;
    private String author;
    private String publishedDate;
    private String pages;
    private String[] categories;
    private boolean available;
    private String borrowUserId;
    private java.sql.Date borrowedDate;
    private java.sql.Date requiredDate;
    private String description;
    private String rating;

    /**
     * Constructor cho việc thêm sách khi mượn , nếu sách chưa được mượn, lấy từ api sau đó thêm ngày
     * mượn, cùng với id
     * @param GoogleApiBooks Sách đuọc lấy xuống từ googleapi
     * @param borrowUserId id của người mượn
     */
    public Book (Book GoogleApiBooks, String borrowUserId) {
        this.title = GoogleApiBooks.title;
        this.author = GoogleApiBooks.author;
        this.categories = GoogleApiBooks.categories;
        this.publishedDate = GoogleApiBooks.publishedDate;
        this.pages = GoogleApiBooks.pages;
        this.available = false;
        this.book_id = GoogleApiBooks.book_id;
        this.borrowUserId = borrowUserId;
        this.borrowedDate = new Date(System.currentTimeMillis());
        this.description = GoogleApiBooks.description;
        LocalDate borrowedLocalDate = borrowedDate.toLocalDate();
        LocalDate requiredLocalDate = borrowedLocalDate.plusDays(30);
        this.requiredDate = java.sql.Date.valueOf(requiredLocalDate);
        System.out.println(requiredDate);
        this.rating = "Not rated";
    }

    /**
     * Constuctor vói book id và id nguòi mượn, nhằm việc tạo book khi cho
     * @param book_id id sách
     * @param borrowUserId id người
     */
    public Book(String book_id, String borrowUserId) {
        this.book_id = book_id;
        this.borrowUserId = borrowUserId;
        this.borrowedDate = new Date(System.currentTimeMillis());
        LocalDate borrowedLocalDate = borrowedDate.toLocalDate();
        LocalDate requiredLocalDate = borrowedLocalDate.plusDays(30);
        this.requiredDate = java.sql.Date.valueOf(requiredLocalDate);
        System.out.println(requiredDate);
        this.rating = "Not rated";

    }

    /**
     * Constructor bình thường với việc tạo các thuộc tính cho sách
     * @param book_id id sách
     * @param title tên sách
     * @param author tác giả
     * @param categories thể loại
     * @param publishedDate ngày viết
     * @param pages số trang
     * @param borrowUserId id người mượn
     * @param borrowedDate ngày mượn
     * @param requiredDate ngày cần trả
     */
    public Book(String book_id, String title, String author, String[] categories,
                String publishedDate, String pages, String borrowUserId, Date borrowedDate, Date requiredDate) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.categories = categories;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.available = false;
        this.borrowUserId = borrowUserId;
        this.borrowedDate = borrowedDate;
        this.requiredDate = requiredDate;
        this.rating = "Not rated";

    }

    /**
     * Constructor cho những quyển sách ko có nguòi mượn
     * @param book_id id người mượn
     * @param title tên sách
     * @param author tác giả
     * @param pages số trang
     * @param categories thể loại
     * @param publishedDate ngày xuất bản
     */
    public Book(String book_id, String title, String author, String pages, String[] categories, String publishedDate) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.categories = categories;
        this.publishedDate = publishedDate;
         this.rating = "Not rated";

    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
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

    public String getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(String borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public void setBorrowed_Date(String borrowed_date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = formatter.parse(borrowed_date);
            Date sqlDate = new Date(utilDate.getTime());
            setBorrowedDate(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public void setRequired_date(String required_date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = formatter.parse(required_date);
            Date sqlDate = new Date(utilDate.getTime());
            setRequiredDate(sqlDate);
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


    /**
     *  Đánh dấu quyển sách đã được trả
     */
    public void check_out() {
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
        borrowUserId = null;
        borrowedDate = null;
        requiredDate = null;
    }

    /**
     * Đánh dấu quyển sách đã có người mượn.
     *
     * @param borrow_user_id id nguoi muon.
     */
    public void check_in(String borrow_user_id) {
        available = false;
        this.borrowUserId = borrow_user_id;
        this.borrowedDate = new Date(System.currentTimeMillis());
        LocalDate borrowedLocalDate = borrowedDate.toLocalDate();
        LocalDate requiredLocalDate = borrowedLocalDate.plusDays(30);
        this.requiredDate = java.sql.Date.valueOf(requiredLocalDate);
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id='" + book_id + '\'' +
                ", borrow_user_id='" + borrowUserId + '\'' +
                ", borrowed_date=" + borrowedDate +
                ", required_date=" + requiredDate +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Book other = (Book) obj;
        return book_id.equals(other.book_id);
    }
}
