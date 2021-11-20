package exception;

public class ComposedWordIsNotValid extends RuntimeException {

    public ComposedWordIsNotValid(String message) {
        super(message);
    }

    public ComposedWordIsNotValid(String message, Throwable cause) {
        super(message, cause);
    }

}
