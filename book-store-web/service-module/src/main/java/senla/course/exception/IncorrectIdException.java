package senla.course.exception;

public class IncorrectIdException extends RuntimeException {
    public IncorrectIdException(Integer id) {
        super("Incorrect id: " + id);
    }
}
