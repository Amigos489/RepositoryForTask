package senla.course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import senla.course.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {
    @JsonProperty("id")
    private int orderId;
    private LocalDate dateComplection;
    private int bookId;
    private String emailUser;
    private BigDecimal orderPrice;
    private OrderStatus statusOrder;

    public OrderDto() {}

    //Для Waiting
    public OrderDto(int bookId, String emailUser, OrderStatus statusOrder) {
        this.bookId = bookId;
        this.emailUser = emailUser;
        this.statusOrder = statusOrder;
    }

    public OrderDto(int bookId, String emailUser) {
        this.dateComplection = LocalDate.now().plusDays(7);
        this.bookId = bookId;
        this.emailUser = emailUser;
        this.statusOrder = OrderStatus.NEW;
    }

    public OrderDto(int orderId, LocalDate dateComplection, int bookId, String emailUser, BigDecimal orderPrice, OrderStatus statusOrder) {
        this.orderId = orderId;
        this.dateComplection = dateComplection;
        this.bookId = bookId;
        this.emailUser = emailUser;
        this.orderPrice = orderPrice;
        this.statusOrder = statusOrder;
    }

    public OrderDto(int bookId) {
        this.bookId = bookId;
    }

    public LocalDate getDateComplection() {
        return this.dateComplection;
    }

    public void setDateComplection(LocalDate dateComplection) {
        this.dateComplection = dateComplection;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public BigDecimal getOrderPrice() {
        return this.orderPrice;
    }

    public OrderStatus getStatusOrder() {
        return this.statusOrder;
    }

    public void setStatusOrder(OrderStatus statusOrder) {
        this.statusOrder = statusOrder;
    }

    public int getId() {
        return this.orderId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setId(int orderId) {
        this.orderId = orderId;
    }
}
