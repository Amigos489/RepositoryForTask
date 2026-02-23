package senla.course.exception;

public class ActiveRequestOnBookException extends RuntimeException {
    public ActiveRequestOnBookException(Integer id) {
        super("Exist active request on book with id: " + id);
    }
}
