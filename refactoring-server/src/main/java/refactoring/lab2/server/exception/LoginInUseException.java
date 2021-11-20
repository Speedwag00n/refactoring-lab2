package refactoring.lab2.server.exception;

public class LoginInUseException extends RuntimeException {

    public LoginInUseException(String message) {
        super(message);
    }

    public LoginInUseException(String message, Throwable reason) {
        super(message, reason);
    }

}
