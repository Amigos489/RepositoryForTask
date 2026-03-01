package senla.course.exception;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(Integer id) {
        super("Request with id: " + id + " not found.");
    }
}
