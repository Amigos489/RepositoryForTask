import java.time.LocalDate;

/* Класс для работы с запросами */
public class BookRequest {
    
    private Book book;            
    private int requestCount;     
    private LocalDate requestDate; 
    private boolean fulfilled; 

    // Конструктор
    public BookRequest(Book book) {
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
}
