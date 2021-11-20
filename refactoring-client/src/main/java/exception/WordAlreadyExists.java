package exception;

public class WordAlreadyExists extends RuntimeException {

    public WordAlreadyExists(String message) {
        super(message);
    }

    public WordAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

}
