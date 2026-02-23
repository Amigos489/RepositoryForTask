package senla.course.exception;

public class ListBookEmptyException extends RuntimeException {
    public ListBookEmptyException() {
        super("Not founds books.");
    }
}
