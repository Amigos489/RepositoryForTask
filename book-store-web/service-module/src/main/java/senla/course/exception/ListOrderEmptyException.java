package senla.course.exception;

public class ListOrderEmptyException extends RuntimeException {
    public ListOrderEmptyException() {
        super("Not founds orders.");
    }
}
