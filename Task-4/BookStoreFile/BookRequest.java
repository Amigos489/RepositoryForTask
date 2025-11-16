import java.time.LocalDate;

/* Класс для работы с запросами */
public class BookRequest {

    private static int bookRequestIdCounter = 1; 
    
    private int bookRequestId;
    private Book book;            
    private int requestCount;     
    private LocalDate requestDate; 
    private boolean fulfilled; 

    // Конструктор
    public BookRequest(Book book) {
        this.bookRequestId = bookRequestIdCounter++;
        this.book = book;
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

    /* Вывод информации о запросе */
    public void printRequestInfo() {
        System.out.println("ID: " + getBookRequestIdCounter());
        System.out.println("Книга: " + book.getNameBook());
        System.out.println("Автор: " + book.getAuthorBook());
        System.out.println("Количество запросов: " + requestCount);
        System.out.println("Дата запроса: " + requestDate);
        System.out.println("Выполнено: " + (fulfilled ? "Да" : "Нет"));
    }

    /* Геттеры и сеттеры */
    public Book getBook() {
        return this.book;
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

    public int getBookRequestIdCounter() {
        return this.bookRequestId;
    }

    public int getId() {
        return this.bookRequestId;
    }
    
}
