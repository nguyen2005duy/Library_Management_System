package Application.com.jmc.backend.Controller.Admin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookSearchModel {
    private final StringProperty book_id;
    private final IntegerProperty borrowed_user_id;
    private final StringProperty borrowed_date;
    private final StringProperty required_date;
    private final IntegerProperty available;  // Corrected spelling

    public BookSearchModel(String book_id, Integer available, Integer borrowed_user_id, String borrowed_date, String required_date) {
        this.book_id = new SimpleStringProperty(book_id);
        this.borrowed_user_id = new SimpleIntegerProperty(borrowed_user_id);
        this.borrowed_date = new SimpleStringProperty(borrowed_date);
        this.required_date = new SimpleStringProperty(required_date);
        this.available = new SimpleIntegerProperty(available);  // Corrected spelling
    }

    public String getBook_id() {
        return book_id.get();
    }

    public int getBorrowed_user_id() {
        return borrowed_user_id.get();
    }

    public String getBorrowed_date() {
        return borrowed_date.get();
    }

    public String getRequired_date() {  // Getter for required_date
        return required_date.get();
    }

    public int getAvailable() {  // Corrected getter
        return available.get();
    }

    // Property getters
    public StringProperty book_idProperty() {
        return book_id;
    }

    public IntegerProperty borrowed_user_idProperty() {
        return borrowed_user_id;
    }

    public StringProperty borrow_dateProperty() {
        return borrowed_date;
    }

    public StringProperty required_dateProperty() {  // Getter for required_dateProperty
        return required_date;
    }

    public IntegerProperty availableProperty() {  // Corrected getter
        return available;
    }
}
