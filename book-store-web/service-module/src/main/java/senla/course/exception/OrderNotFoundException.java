package senla.course.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Integer id) {
        super("Order with id: " + id + " not found.");
    }
}
