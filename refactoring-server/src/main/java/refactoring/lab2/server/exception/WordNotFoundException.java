package refactoring.lab2.server.exception;

public class WordNotFoundException extends RuntimeException {

    public WordNotFoundException(String message) {
        super(message);
    }

    public WordNotFoundException(String message, Throwable reason) {
        super(message, reason);
    }

}
