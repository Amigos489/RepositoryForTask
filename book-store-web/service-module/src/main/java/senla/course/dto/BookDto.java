package senla.course.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookDto {
    private int bookId;
    private String nameBook;
    private String authorBook;
    private LocalDate datePublication;
    private LocalDate dateAddWarehouse;
    private BigDecimal price;
    private boolean availability;

    BookDto() {}

    public BookDto(int bookId, String nameBook, String authorBook, LocalDate datePublication, LocalDate dateAddWarehouse, BigDecimal price, boolean availability) {
        this.bookId = bookId;
        this.nameBook = nameBook;
        this.authorBook = authorBook;
        this.datePublication = datePublication;
        this.dateAddWarehouse = dateAddWarehouse;
        this.price = price;
        this.availability = availability;
    }

    public int getId() {
        return this.bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getNameBook() {
        return this.nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAuthorBook() {
        return this.authorBook;
    }

    public void setAuthorBook(String authorBook) {
        this.authorBook = authorBook;
    }

    public LocalDate getDatePublication() {
        return this.datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public LocalDate getDateAddWarehouse() {
        return this.dateAddWarehouse;
    }

    public void setDateAddWarehouse(LocalDate dateAddWarehouse) {
        this.dateAddWarehouse = dateAddWarehouse;
    }

    public BigDecimal getPrice() { return this.price; }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean getAvailability() {
        return this.availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
