package Application.backend.Documents;


import java.sql.Date;

public class Document {
    private final String book_id;
    private String title;
    private String author;
    private String published_date;
    private int pages;
    private String []  categories;
    private boolean available;
    private String borrow_user_id;
    private java.sql.Date borrowed_date;
    private java.sql.Date required_date;

    public Document(String book_id, String title, String author, String [] categories,
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
                    String[] categories  , String published_date, int pages) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.categories = categories ;
        this.published_date = published_date;
        this.pages = pages;
        this.available = true;
    }

    public void check_out()
    {
        available = false;
        borrow_user_id = null;
        borrowed_date = null;
        required_date = null;
    }

    public void check_in(String borrow_user_id, Date borrowed_date, Date required_date)
    {
        available = true;
        this.borrow_user_id = borrow_user_id;
        this.borrowed_date = borrowed_date;
        this.required_date = required_date;
    }
    

}
