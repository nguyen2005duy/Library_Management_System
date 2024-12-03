package Application.com.jmc.backend.Controller.Admin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class BookSearchModel {
    private final StringProperty book_id;
    private  StringProperty book_title;
    private  IntegerProperty borrowed_user_id;
    private  StringProperty borrowed_date;
    private  StringProperty required_date;
    private  IntegerProperty available;
    private IntegerProperty due_date;
    private IntegerProperty fine;
    private Button button;// Corrected spelling

    public BookSearchModel(String book_id, String book_title,Integer borrowed_user_id, String borrowed_date, String required_date, Integer available, Integer due_date, Integer fine) {
        this.book_id = new SimpleStringProperty(book_id);
        this.book_title = new SimpleStringProperty(book_title);
        this.borrowed_user_id = new SimpleIntegerProperty(borrowed_user_id);
        this.due_date = new SimpleIntegerProperty(due_date);
        this.fine = new SimpleIntegerProperty(fine);
        this.required_date = new SimpleStringProperty(required_date);
        this.available = new SimpleIntegerProperty(available);
        this.borrowed_date = new SimpleStringProperty(borrowed_date);

    }

    public BookSearchModel(String book_id, String book_title, Integer borrower_user_id, Integer due_date, Integer fine) {
        this.book_id = new SimpleStringProperty(book_id);
        this.book_title = new SimpleStringProperty(book_title);
        this.borrowed_user_id = new SimpleIntegerProperty(borrower_user_id);
        this.due_date = new SimpleIntegerProperty(due_date);
        this.fine = new SimpleIntegerProperty(fine);// Corrected spelling
    }

    public BookSearchModel(String book_id, Integer available, Integer borrowed_user_id, String borrowed_date, String required_date ) {
        this.book_id = new SimpleStringProperty(book_id);
        this.borrowed_user_id = new SimpleIntegerProperty(borrowed_user_id);
        this.borrowed_date = new SimpleStringProperty(borrowed_date);
        this.required_date = new SimpleStringProperty(required_date);
        this.available = new SimpleIntegerProperty(available);
        this.button = new Button("Return");
        this.button.getStylesheets().add(getClass().getResource("/Styles/button.css").toExternalForm());

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

    public StringProperty borrowed_dateProperty() {
        return borrowed_date;
    }

    public int getDue_date() {
        return due_date.get();
    }

    public IntegerProperty due_dateProperty() {
        return due_date;
    }

    public int getFine() {
        return fine.get();
    }

    public IntegerProperty fineProperty() {
        return fine;
    }

    public String getBook_title() {
        return book_title.get();
    }

    public StringProperty book_titleProperty() {
        return book_title;
    }

    public Button getButton() {
        return button;
    }


}
