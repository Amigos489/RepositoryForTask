package senla.course.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request implements GettingInfo, ReferringBook {
    @JsonProperty("id")
    private int requestId;
    private int bookId;
    private String nameBook;
    private int countRequest;
    private boolean isClosed;

    public Request() {}

    public Request(int requestId, int bookId, String nameBook) {
        this.requestId = requestId;
        this.bookId = bookId;
        this.nameBook = nameBook;
        this.countRequest = 1;
        this.isClosed = false;
    }

    public Request(int requestId, int bookId, String nameBook, int countRequest, boolean isClosed) {
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

    @Override
    public String getInfo() {
        return "id : " + this.getId() + '\n' +
                "id книги: " + this.getBookId() + '\n' +
                "Название книги: " + this.getNameBook() + '\n' +
                "Количество запросов: " + this.getCountRequest() + '\n' +
                "Закрыт: " + this.isClosed + '\n';
    }

    @Override
    public String getStringParameter() {
        return "requestId,bookId,nameBook,countRequest,isClosed";
    }

    @Override
    public String getStringInfo() {
        return getId() + "," + getBookId() + ',' + getNameBook() + ',' + getCountRequest() + ',' + getIsClosed();
    }

    @Override
    public int getId() {
        return this.requestId;
    }

    @Override
    public int getBookId() {
        return this.bookId;
    }
}
