package controller;

import enums.StatusOperationBook;
import enums.StatusOperationCsv;
import enums.StatusOperationOrder;
import enums.StatusOrder;
import model.Book;
import model.Order;
import model.Request;
import model.ServiceStoreBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Controller {

    private ServiceStoreBook serviceStoreBook;

    public Controller() {}

    public Controller(ServiceStoreBook serviceStoreBook) {
        this.serviceStoreBook = serviceStoreBook;
    }

    /* Операции с книгами */

    public StatusOperationBook addBookToWarehouse(int bookId) {
        return serviceStoreBook.addBookToWarehouse(bookId);
    }

    public StatusOperationBook writeBookToWarehouse(int bookId) {
        return serviceStoreBook.WriteBookFromWarehouse(bookId);
    }

    public StatusOrder createOrder(int bookId, String emailUser) {
        return serviceStoreBook.createOrder(bookId, emailUser);
    }

    /* Операции с заказами */

    public StatusOperationOrder closeOrder(int orderId) {
        return serviceStoreBook.closeOrder(orderId);
    }

    public StatusOperationOrder complectedOrder(int orderId) {
        return serviceStoreBook.complectedOrder(orderId);
    }

    /* Сортировка сущностей */

    public List<Book> getSortedListAllBook(int choiceUser) {
        return serviceStoreBook.getSortedListAllBook(choiceUser);
    }

    public List<Book> getSortedListStaleBook(int choiceUser) {
        return serviceStoreBook.getSortedListStaleBook(choiceUser);
    }

    public List<Order> getSortedListAllOrder(int choiceUser) {
        return serviceStoreBook.getSortedListAllOrder(choiceUser);
    }

    public List<Order> getSortedListComplectedOrder(int choiceUser, LocalDate startDate, LocalDate endDate) {
        return serviceStoreBook.getSortedListComplectedOrder(choiceUser, startDate, endDate);
    }

    public List<Request> getSortedListAllRequest(int choiceUser) {
        return serviceStoreBook.getSortedListAllRequest(choiceUser);
    }

    public Book getInfoBook(int bookId) {
        return serviceStoreBook.getBookInfo(bookId);
    }

    public Order getInfoOrder(int orderId) {
        return serviceStoreBook.getOrderInfo(orderId);
    }

    public int getCountComplectedOrder(LocalDate startDate, LocalDate endDate) {
        return serviceStoreBook.getCountComplectedOrder(startDate, endDate);
    }

    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {
        return serviceStoreBook.getProfit(startDate, endDate);
    }

    public StatusOperationCsv exportCsv(int choiceUser) {
        return serviceStoreBook.exportCsv(choiceUser);
    }

    public StatusOperationCsv importCsv(int choiceUser) {
        return serviceStoreBook.importCsv(choiceUser);
    }
}
