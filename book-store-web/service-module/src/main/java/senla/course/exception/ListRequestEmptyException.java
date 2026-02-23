package senla.course.exception;

public class ListRequestEmptyException extends RuntimeException {
    public ListRequestEmptyException() {
        super("Not founds request.");
    }
}
