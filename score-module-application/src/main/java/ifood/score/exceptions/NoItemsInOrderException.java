package ifood.score.exceptions;

public class NoItemsInOrderException extends RuntimeException {

    public NoItemsInOrderException(String message) {
        super(message);
    }
}
