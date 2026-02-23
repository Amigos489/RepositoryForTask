package senla.course.entitys;


import jakarta.persistence.*;

@Entity
@Table(name = "request")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;
    private int countRequest;
    private boolean isClosed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private BookEntity book;

    @Transient
    private String nameBook;

    @Transient
    private Integer bookId;

    public RequestEntity(Integer bookId, Integer countRequest, Boolean isClosed) {
        this.bookId = bookId;
        this.countRequest = countRequest;
        this.isClosed = isClosed;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public RequestEntity() {}

    public RequestEntity(int countRequest, boolean isClosed, BookEntity book, Integer bookId) {
        this.countRequest = countRequest;
        this.isClosed = isClosed;
        this.book = book;
        this.bookId = bookId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public int getCountRequest() {
        return countRequest;
    }

    public void setCountRequest(int countRequest) {
        this.countRequest = countRequest;
    }

    public boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean closed) {
        isClosed = closed;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public void incrementCountRequest() {
        this.countRequest+=1;
    }
}
