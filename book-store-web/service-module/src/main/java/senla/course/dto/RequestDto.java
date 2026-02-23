package senla.course.dto;

public class RequestDto {

    private int requestId;
    private int bookId;
    private String nameBook;
    private int countRequest;
    private boolean isClosed;

    public RequestDto() {}

    public RequestDto(int bookId) {
        this.bookId = bookId;
        this.countRequest = 1;
        this.isClosed = false;
    }

    public RequestDto(int requestId, int bookId, String nameBook) {
        this.requestId = requestId;
        this.bookId = bookId;
        this.nameBook = nameBook;
        this.countRequest = 1;
        this.isClosed = false;
    }

    public RequestDto(int requestId, int bookId, String nameBook, int countRequest, boolean isClosed) {
        this.requestId = requestId;
        this.bookId = bookId;
        this.nameBook = nameBook;
        this.countRequest = countRequest;
        this.isClosed = isClosed;
    }

    public String getNameBook() {
        return this.nameBook;
    }

    public int getCountRequest() {
        return this.countRequest;
    }

    public void setCountRequest(int countRequest) {
        this.countRequest = countRequest;
    }

    public void incCountRequest() {
        this.countRequest++;
    }

    public boolean getIsClosed() {
        return this.isClosed;
    }

    public void setIsClosed(boolean isClosed) { this.isClosed = isClosed; }

    public int getId() {
        return this.requestId;
    }

    public int getBookId() {
        return this.bookId;
    }
}
