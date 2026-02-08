package entitys;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    private String nameBook;
    private String authorBook;
    private LocalDate datePublication;
    private LocalDate dateAddWarehouse;
    private BigDecimal price;
    private boolean availability;

    @OneToMany()
    @JoinColumn(name = "bookId")
    private List<OrderEntity> orders;

    @OneToMany()
    @JoinColumn(name = "bookId")
    private List<RequestEntity> requests;

    public BookEntity() {}

    public BookEntity(String nameBook, String authorBook, LocalDate datePublication, LocalDate dateAddWarehouse, BigDecimal price, boolean availability) {
        this.nameBook = nameBook;
        this.authorBook = authorBook;
        this.datePublication = datePublication;
        this.dateAddWarehouse = dateAddWarehouse;
        this.price = price;
        this.availability = availability;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(String authorBook) {
        this.authorBook = authorBook;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public LocalDate getDateAddWarehouse() {
        return dateAddWarehouse;
    }

    public void setDateAddWarehouse(LocalDate dateAddWarehouse) {
        this.dateAddWarehouse = dateAddWarehouse;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return  "Информация" + "BookEntity{" +
                "bookId=" + bookId +
                ", nameBook='" + nameBook + '\'' +
                ", authorBook='" + authorBook + '\'' +
                ", datePublication=" + datePublication +
                ", dateAddWarehouse=" + dateAddWarehouse +
                ", price=" + price +
                ", availability=" + availability +
                ", orders=" + orders +
                ", requests=" + requests +
                '}';
    }
}
