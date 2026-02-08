package senla.course.exception;

public class QueryInvalidResult extends RuntimeException {
    public QueryInvalidResult(String message) {
        super(message);
    }
}
