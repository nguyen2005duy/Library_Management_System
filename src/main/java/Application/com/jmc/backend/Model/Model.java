package Application.com.jmc.backend.Model;

import Application.com.jmc.backend.Class.Books.Book;
import Application.com.jmc.backend.Views.FactoryViews;

public class Model {
    private static Model model;
    private String search;
    private final FactoryViews factoryViews;
    private Book selectedBook;

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSearchString(String search) {
        this.search = search;
    }

    public String getSearchString() {
        return search;
    }

    public void setSelectedBook(Book book) {
        this.selectedBook = book;
    }

    public Model() {
        this.factoryViews = new FactoryViews();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public FactoryViews getFactoryViews() {
        return factoryViews;
    }
}
