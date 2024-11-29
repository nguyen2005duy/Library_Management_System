package Application.com.jmc.backend.Controller.Admin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class MemberSearchModel {
    private final StringProperty email;  // Use StringProperty for JavaFX binding
    private final StringProperty lastname;
    private final IntegerProperty account_id;

    // Constructor
    public MemberSearchModel(int account_id, String lastname, String email) {
        this.account_id = new SimpleIntegerProperty(account_id);
        this.lastname = new SimpleStringProperty(lastname);
        this.email = new SimpleStringProperty(email);
    }

    // Getters for properties
    public String getEmail() {
        return email.get();  // Return the value of the email property
    }

    public String getLastname() {  // Corrected method name
        return lastname.get();
    }

    public int getAccount_id() {
        return account_id.get();
    }

    // Property getters for use with PropertyValueFactory
    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty lastnameProperty() {  // Corrected method name
        return lastname;
    }

    public IntegerProperty account_idProperty() {
        return account_id;
    }
}
