package model;

import enums.StatusOperationBook;
import enums.StatusOperationCsv;
import enums.StatusOperationOrder;
import enums.StatusOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ServiceStoreBook {

    private ModelStoreBook modelStoreBook;

    public ServiceStoreBook(ModelStoreBook modelStoreBook) {
        this.modelStoreBook = modelStoreBook;
    }

    /* Операции с книгами */

    public StatusOperationBook addBookToWarehouse(int bookId) {
        return modelStoreBook.addBookToWarehouse(bookId);
    }

    public StatusOperationBook WriteBookFromWarehouse(int bookId) {
        return modelStoreBook.writeBookFromWarehouse(bookId);
    }

    public StatusOrder createOrder(int bookId, String emailUser) {
        return modelStoreBook.createOrder(bookId, emailUser);
    }

    public StatusOperationOrder closeOrder(int orderId) {
        return modelStoreBook.closeOrder(orderId);
    }

    public StatusOperationOrder complectedOrder(int orderId) {
        return modelStoreBook.complectedOrder(orderId);
    }

    public List<Book> getSortedListAllBook(int choiceUser) {
        return modelStoreBook.getSortedAllBook(choiceUser);
    }

    public List<Book> getSortedListStaleBook(int choiceUser) {
        return modelStoreBook.getSortedStaleBook(choiceUser);
    }

    public List<Order> getSortedListAllOrder(int choiceUser) {
        return modelStoreBook.getSortedOrder(choiceUser);
    }

    public List<Order> getSortedListComplectedOrder(int choiceUser, LocalDate startDate, LocalDate endStart) {
        return modelStoreBook.getSortedComplectedOrder(choiceUser, startDate, endStart);
    }

    public List<Request> getSortedListAllRequest(int choiceUser) {
        return modelStoreBook.getSortedRequest(choiceUser);
    }

    public Book getBookInfo(int bookId) {
        return modelStoreBook.getInfoBook(bookId);
    }

    public Order getOrderInfo(int orderId) {
        return modelStoreBook.getInfoOrder(orderId);
    }

    public int getCountComplectedOrder(LocalDate startDate, LocalDate endDate) {
        return modelStoreBook.getCountComplectedOrder(startDate, endDate);
    }

    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {
        return modelStoreBook.getProfit(startDate, endDate);
    }

    public StatusOperationCsv exportCsv(int choiceUser) {
        StatusOperationCsv result;
        switch (choiceUser) {
            case 1:
                System.out.println("Книга");
                result = modelStoreBook.exportCsvBook();
                break;
            case 2:
                result = modelStoreBook.exportCsvOrder();
                break;
            case 3:
                result = modelStoreBook.exportCsvRequest();
                break;
            default:
                result = StatusOperationCsv.INCORRECT_CHOICE;
        }
        return result;
    }

    public StatusOperationCsv importCsv(int choiceUser) {
        StatusOperationCsv result;
        switch (choiceUser) {
            case 1:
                result = modelStoreBook.importCsvBook();
                break;
            case 2:
                result = modelStoreBook.importCsvOrder();
                break;
            case 3:
                result = modelStoreBook.importCsvRequest();
                break;
            default:
                result = StatusOperationCsv.INCORRECT_CHOICE;
        }
        return result;
    }
}
