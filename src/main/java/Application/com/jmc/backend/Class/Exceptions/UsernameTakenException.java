package Application.com.jmc.backend.Class.Exceptions;

public class UsernameTakenException extends RuntimeException {
    /**
     * da co nguoi dung trung ten.
     */
    public UsernameTakenException() {
        super("Username is already taken!!");
    }

}
