
import java.time.LocalDate;

/* Класс книги */
public class Book {
    private String nameBook;
    private String authorBook;
    private LocalDate dateOfPublication;
    private LocalDate dateAddedToWarehouse;
    private int numberOfCopies; 
    private int numberPages;
    private int price;
    private int numberOfRequests;
    private boolean availability;

    /* Конструктор */
    public Book(String nameBook, String authorBook, LocalDate dateOfPublication,
            int numberOfCopies, int numberPages, int price, LocalDate dateAddedToWarehouse) {

        this.nameBook = nameBook;
        this.authorBook = authorBook;
        this.dateOfPublication = dateOfPublication;
        this.numberOfCopies = numberOfCopies;
        this.availability = numberOfCopies > 0;         /* Определяем наличие книги, в зависимости от кол-ва экземпляров*/
        this.numberPages = numberPages;
        this.price = price;
        this.dateAddedToWarehouse = dateAddedToWarehouse;
        this.numberOfRequests = 0;                      /* Изначально кол-во запросов равно нулю */  

    }

    /* Проверка залежавшейся книги*/
    public boolean isStale() {
        if (dateAddedToWarehouse.isBefore(LocalDate.now().minusMonths(6)) 
                && numberOfCopies > 0) {
            return true;
        } else {
            return false;
        }

    }

    /* Вывод информации */
    public void printInfo() {

        System.out.println("Название: " + nameBook);
        System.out.println("Автор: " + authorBook);
        System.out.println("Дата публикации: " + dateOfPublication);
        System.out.println("Страниц: " + numberPages);
        System.out.println("Цена: " + price);
        System.out.println("Количество: " + numberOfCopies);
        System.out.println("В наличии: " + availability);
        System.out.println("Дата поступления на склад: " + dateAddedToWarehouse);
        System.out.println("Количество запросов на книгу: " + numberOfRequests);

    }


    /* Геттеры и сеттеры */
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

    public void setPrice(int price) {

        /* Проверка на корректность значения цены */
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Ошибка! цена не может быть <= 0");
        }
    }

    public LocalDate getDateAddedToWarehouse() {
        return dateAddedToWarehouse;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

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
            System.out.println("Ошибка! кол-во экземпляров не может быть <= 0");
        }
    }



}