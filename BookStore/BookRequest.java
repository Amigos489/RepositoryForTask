import java.time.LocalDate;

/* Класс для работы с запросами */
public class BookRequest implements Exportable {

    private static int bookRequestIdCounter = 1; 
    
    private int id;
    private int bookId;
    private String bookName;            
    private int requestCount;     
    private LocalDate requestDate; 
    private boolean fulfilled; 

    public BookRequest() {}

    /* Конструктор */
    public BookRequest(Book book) {
        this.id = bookRequestIdCounter++;
        this.bookId = book.getBookId();
        this.bookName = book.getNameBook();
        this.requestCount = 1;
        this.requestDate = LocalDate.now();
        this.fulfilled = false;
    }

    /* Увеличить количество запросов */
    public void incrementRequest() {
        this.requestCount++;
    }

    /* Закрыть запрос */
    public void fulfillRequest() {
        this.fulfilled = true;
    }

    /* Геттеры */

    public int getId() {
        return this.id;
    }

    public String getBookName() {
        return this.bookName;
    }

    public int getRequestCount() {
        return this.requestCount;
    }

    public LocalDate getRequestDate() {
        return this.requestDate;
    }

    public boolean isFulfilled() {
        return this.fulfilled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public static void syncCounter(int importedId) {
        bookRequestIdCounter = Math.max(bookRequestIdCounter, importedId + 1);
    }


    /* Методы для работы с экспортом*/
    @Override
    public String generateStringHeader() {
        return "id,bookId,bookName,requestCount,requestDate,fulfilled";
    }

    @Override
    public String generateStringInfo() {
        return id + "," + bookId + "," + "\"" + bookName + "\"," +
       requestCount + "," + requestDate + "," + fulfilled;

    }
    
}
