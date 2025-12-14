import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;

/* Класс книги */
public class Book {

    private static int bookIdCounter = 1; 

    private String nameBook;
    private String authorBook;
    private LocalDate dateOfPublication;
    private LocalDate dateAddedToWarehouse;
    @JsonProperty("id")
    private int bookId;
    private int numberOfCopies; 
    private int numberPages;
    private int price;
    private int numberOfRequests;
    private boolean availability;

    /* Пустой конструктор */
    public Book() {}

    /* Конструктор */
    public Book(String nameBook, String authorBook, LocalDate dateOfPublication,
            int numberOfCopies, int numberPages, int price, LocalDate dateAddedToWarehouse) {

        this.nameBook = nameBook;
        this.authorBook = authorBook;
        this.dateOfPublication = dateOfPublication;
        setNumberOfCopies(numberOfCopies);
        this.availability = numberOfCopies > 0;         /* Определяем наличие книги, в зависимости от кол-ва экземпляров*/
        this.numberPages = numberPages;
        setPrice(price);
        this.dateAddedToWarehouse = dateAddedToWarehouse;
        this.numberOfRequests = 0;                      /* Изначально кол-во запросов равно нулю */
        this.bookId = bookIdCounter++; 
    }

    /* Геттеры */

    public int getBookId() {
        return this.bookId;
    }

    public String getNameBook() {
        return this.nameBook;
    }

    public String getAuthorBook() {
        return this.authorBook;
    }

    public LocalDate getDateOfPublication() {
        return this.dateOfPublication;
    }

    public int getNumberOfCopies() {
        return this.numberOfCopies;
    }

    public int getNumberPages() {
        return this.numberPages;
    }

    public int getPrice() {
        return this.price;
    }

    public boolean getAvailability() {
        return this.availability;
    }

    public LocalDate getDateAddedToWarehouse() {
        return dateAddedToWarehouse;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    /* Сеттеры */

    public void incrementRequests() {
        this.numberOfRequests++;
    }

    public void resetRequests() {
        this.numberOfRequests = 0;
    }

    public void setDateAddedToWarehouse(LocalDate date) {
        this.dateAddedToWarehouse = date;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        if (numberOfCopies >= 0) {
            this.numberOfCopies = numberOfCopies;
            this.availability = numberOfCopies > 0; /* Устанавливаем и наличие книги */
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setPrice(int price) {

        /* Проверка на корректность значения цены */
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException();
        }
    }

}