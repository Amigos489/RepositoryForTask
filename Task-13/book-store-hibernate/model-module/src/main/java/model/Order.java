package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.StatusOrder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order implements GettingInfo, ReferringBook {
    @JsonProperty("id")
    private int orderId;
    private LocalDate dateComplection;
    private int bookId;
    private String emailUser;
    private BigDecimal orderPrice;
    private StatusOrder statusOrder;

    public Order() {}

    public Order(int orderId, int bookId, String emailUser, BigDecimal orderPrice) {
        this.orderId = orderId;
        this.dateComplection = LocalDate.now().plusDays(7);
        this.bookId = bookId;
        this.emailUser = emailUser;
        this.orderPrice = orderPrice;
        this.statusOrder = StatusOrder.NEW;
    }

    public Order(int orderId, int bookId, String emailUser, BigDecimal orderPrice, StatusOrder statusOrder) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.emailUser = emailUser;
        this.orderPrice = orderPrice;
        this.statusOrder = statusOrder;
    }

    public Order(int orderId, LocalDate dateComplection, int bookId, String emailUser, BigDecimal orderPrice, StatusOrder statusOrder) {
        this.orderId = orderId;
        this.dateComplection = dateComplection;
        this.bookId = bookId;
        this.emailUser = emailUser;
        this.orderPrice = orderPrice;
        this.statusOrder = statusOrder;
    }

    public Order(int bookId) {
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

    public StatusOrder getStatusOrder() {
        return this.statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    @Override
    @JsonIgnore
    public String getInfo() {
        return "id : " + this.getId() + '\n' +
                "Статус : " + this.getStatusOrder() + '\n' +
                "Дата исполнения: " + this.getDateComplection() + '\n' +
                "Цена: " + this.getOrderPrice() + '\n' +
                "id книги: " + this.getBookId() + '\n' +
                "email заказчика: " + this.getEmailUser();
    }

    @Override
    public String getStringParameter() {
        return "orderId,dateComplection,bookId,emailUser,orderPrice,statusOrder";
    }

    @Override
    public String getStringInfo() {
        return getId() + "," + getDateComplection() + ',' + getBookId() +
                ',' + getEmailUser() + ',' + getOrderPrice() + ',' + getStatusOrder();
    }

    @Override
    public int getId() {
        return this.orderId;
    }

    @Override
    public int getBookId() {
        return this.bookId;
    }
}
