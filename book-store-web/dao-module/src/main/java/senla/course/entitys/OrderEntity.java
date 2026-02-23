package senla.course.entitys;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "order_")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private LocalDate dateComplection;
    private String emailUser;
    private String statusOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private BookEntity book;

    @Transient
    private BigDecimal orderPrice;

    @Transient
    private Integer bookId;

    public OrderEntity() {}

    public OrderEntity(LocalDate dateComplection, String emailUser, String statusOrder, Integer bookId) {
        this.dateComplection = dateComplection;
        this.emailUser = emailUser;
        this.statusOrder = statusOrder;
        this.bookId = bookId;
    }

    public OrderEntity(LocalDate dateComplection, String emailUser, String statusOrder, BookEntity book) {
        this.dateComplection = dateComplection;
        this.emailUser = emailUser;
        this.statusOrder = statusOrder;
        this.book = book;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDateComplection() {
        return dateComplection;
    }

    public void setDateComplection(LocalDate dateComplection) {
        this.dateComplection = dateComplection;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
