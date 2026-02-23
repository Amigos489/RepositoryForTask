package senla.course.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Integer id) {
        super("Book with id: " + id + " not found.");
    }
}
