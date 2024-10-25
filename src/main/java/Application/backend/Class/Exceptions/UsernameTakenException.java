package Application.backend.Class.Exceptions;

public class UsernameTakenException extends RuntimeException{
    public UsernameTakenException()
    {
       super("Username is already taken!!");
    }

}
