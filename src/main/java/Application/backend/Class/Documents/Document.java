package Application.backend.Class.Documents;


import java.sql.Date;

public class Document {
    private final String book_id;
    private String title;
    private String author;
    private String published_date;
    private int pages;
    private String[] categories;
    private boolean available;
    private String borrow_user_id;
    private java.sql.Date borrowed_date;
    private java.sql.Date required_date;

    public Document(String book_id, String title, String author, String[] categories,
                    String published_date, int pages, String borrow_user_id, Date borrowed_date, Date required_date) {
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

    public Document(String book_id, String title, String author,
                    String[] categories, String published_date, int pages) {
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
        available = true;
        borrow_user_id = null;
        borrowed_date = null;
        required_date = null;
    }

    /**
     * danh dau quyen sach nay da co nguoi muon.
     *
     * @param borrow_user_id id nguoi muon.
     * @param borrowed_date  ngay muon.
     * @param required_date  ngay tra.
     */
    public void check_in(String borrow_user_id, Date borrowed_date, Date required_date) {
        available = false;
        this.borrow_user_id = borrow_user_id;
        this.borrowed_date = borrowed_date;
        this.required_date = required_date;
    }


}
