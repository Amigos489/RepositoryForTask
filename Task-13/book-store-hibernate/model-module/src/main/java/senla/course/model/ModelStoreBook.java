package senla.course.model;

import senla.course.csv.ExportEntityCsv;
import senla.course.csv.ImportBookCsv;
import senla.course.csv.ImportOrderCsv;
import senla.course.csv.ImportRequestCsv;
import senla.course.enums.StatusOperationBook;
import senla.course.enums.StatusOperationCsv;
import senla.course.enums.StatusOperationOrder;
import senla.course.enums.StatusOrder;
import senla.course.exception.EntityNotFound;
import senla.course.exceptions.InvalidValueFileCsv;
import senla.course.json.JsonDataModel;
import org.springframework.stereotype.Component;
import senla.course.sorted.book.SortedBookById;
import senla.course.sorted.order.SortedOrderById;
import senla.course.sorted.request.SortedRequestById;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class ModelStoreBook {

    private static int orderIdCouter = 1;
    private static int requestIdCouter = 1;

    private final Warehouse warehouse;
    private final OrderManagement orderManagement;
    private JsonDataModel jsonDataModel;

    public ModelStoreBook(Warehouse warehouse, OrderManagement orderManagement, JsonDataModel jsonDataModel) {
        this.warehouse = warehouse;
        this.orderManagement = orderManagement;
        this.jsonDataModel = jsonDataModel;
    }

    /* Операции с книгами */

    public StatusOperationBook addBookToWarehouse(int bookId) {
        return warehouse.addBookById(bookId);
    }

    public StatusOperationBook writeBookFromWarehouse(int bookId) {
        return warehouse.writeBookById(bookId);
    }

    public StatusOrder createOrder(int bookId, String userEmail) {
        Book book = warehouse.findBookById(bookId);
        if (book == null) {
            return StatusOrder.ERROR_CREATE_ORDER;
        }
        try {
            if (warehouse.isBookAvailable(bookId)) {
                orderManagement.createOrder(orderIdCouter, bookId, book.getPrice(),userEmail,StatusOrder.NEW, LocalDate.now().plusDays(7));
                orderIdCouter++;
                return StatusOrder.NEW;
            } else {
                orderManagement.createOrder(orderIdCouter, bookId, book.getPrice(),userEmail,StatusOrder.WAITING);
                orderIdCouter++;
                orderManagement.createRequest(requestIdCouter, bookId, book.getNameBook());
                requestIdCouter++;
                return StatusOrder.WAITING;
            }
        } catch (EntityNotFound e) {
            return StatusOrder.ERROR_CREATE_ORDER;
        }
    }

    public StatusOperationOrder closeOrder(int orderId) {
        Order order = orderManagement.findOrderById(orderId);
        if (order == null) {
            return StatusOperationOrder.ORDER_NOT_FOUND;
        }
        if (order.getStatusOrder() == StatusOrder.COMPLECTED) {
            return StatusOperationOrder.ORDER_COMPLECTED_BEFORE_CLOSE;
        }
        return orderManagement.cancelOrder(orderId);
    }

    public StatusOperationOrder complectedOrder(int orderId) {
        Order order = orderManagement.findOrderById(orderId);
        if (order == null) {
            return StatusOperationOrder.ORDER_NOT_FOUND;
        }
        if (order.getStatusOrder() == StatusOrder.CLOSED) {
            return StatusOperationOrder.ORDER_CLOSED_BEFORE_COMPLECTED;
        }
        if (orderManagement.findActiveRequestByBookId(order.getBookId()) != -1 && order.getStatusOrder() == StatusOrder.NEW) {
            return orderManagement.complectedOrder(orderId);
        }
        if (orderManagement.findActiveRequestByBookId(order.getBookId()) == -1) {
            return orderManagement.complectedOrder(orderId);
        } else {
            return StatusOperationOrder.ERROR_COMPLECTED_ORDER;
        }
    }

    public List<Book> getSortedAllBook(int choiceUser) {
        return warehouse.sortedListAllBook(choiceUser);
    }

    public List<Order> getSortedOrder(int choiceUser) {
        return orderManagement.sortedListOrder(choiceUser);
    }

    public List<Request> getSortedRequest(int choiceUser) {
        return orderManagement.sortedListRequest(choiceUser);
    }

    public List<Book> getSortedStaleBook(int choiceUser) {
        return warehouse.sortedListStaleBook(choiceUser);
    }

    public List<Order> getSortedComplectedOrder(int choiceUser, LocalDate startDate, LocalDate endDate) {
        return orderManagement.sortedListComplectedOrder(choiceUser, startDate, endDate);
    }

    public Book getInfoBook(int bookId) {
        return warehouse.findBookById(bookId);
    }

    public Order getInfoOrder(int orderId) {
        return orderManagement.findOrderById(orderId);
    }

    public int getCountComplectedOrder(LocalDate startDate, LocalDate endDate) {
        return orderManagement.getCountComplectedOrder(startDate, endDate);
    }

    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {
        return orderManagement.getProfit(startDate, endDate);
    }

    public List<Book> getAllBook() {
        return warehouse.getAllBooks();
    }

    public List<Order> getAllOrder() {
        return orderManagement.getAllOrder();
    }

    public List<Request> getAllRequest() {
        return orderManagement.getAllRequest();
    }

    public void saveDate() {
        jsonDataModel.initData(getAllBook(), getAllOrder(), getAllRequest());
        jsonDataModel.saveDataFromModel();
    }

    public StatusOperationCsv exportCsvBook() {
        ExportEntityCsv<Book> exportEntityCsv = new ExportEntityCsv<Book>("books.csv");
        return exportEntityCsv.exportCsv(warehouse.getAllBooks());
    }

    public StatusOperationCsv exportCsvOrder() {
        ExportEntityCsv<Order> exportEntityCsv = new ExportEntityCsv<Order>("orders.csv");
        return exportEntityCsv.exportCsv(orderManagement.getAllOrder());
    }

    public StatusOperationCsv exportCsvRequest() {
        ExportEntityCsv<Request> exportEntityCsv = new ExportEntityCsv<Request>("requests.csv");
        return exportEntityCsv.exportCsv(orderManagement.getAllRequest());
    }

    public StatusOperationCsv importCsvBook() {
        try {
            ImportBookCsv importBookCsv = new ImportBookCsv("books.csv");
            List<Book> importBooks = importBookCsv.importCsv();
            makkingConnectionBook(warehouse.getAllBooks(), importBooks);
            return StatusOperationCsv.ENTITY_IMPORT_CSV;
        } catch (InvalidValueFileCsv e) {
            return StatusOperationCsv.ERROR_ENTITY_IMPORT_CSV;
        }
    }

    public StatusOperationCsv importCsvOrder() {
        ImportOrderCsv importOrderCsv = new ImportOrderCsv("orders.csv");
        try {
            List<Order> importOrders = importOrderCsv.importCsv();
            makkingConnectionOrder(orderManagement.getAllOrder(), importOrders);
            return StatusOperationCsv.ENTITY_IMPORT_CSV;
        } catch (InvalidValueFileCsv e) {
            return StatusOperationCsv.ERROR_ENTITY_IMPORT_CSV;
        }
    }

    public StatusOperationCsv importCsvRequest() {
        try {
            ImportRequestCsv importRequestCsv = new ImportRequestCsv("requests.csv");
            List<Request> importRequests = importRequestCsv.importCsv();
            makkingConnectionRequest(orderManagement.getAllRequest(), importRequests);
            return StatusOperationCsv.ENTITY_IMPORT_CSV;
        } catch (InvalidValueFileCsv e) {
            return StatusOperationCsv.ERROR_ENTITY_IMPORT_CSV;
        }
    }

    public void makkingConnectionBook(List<Book> books, List<Book> importedBooks) {
        if (books == null) {
            warehouse.setBooks(importedBooks);
            return;
        }

        Collections.sort(books, new SortedBookById());
        Collections.sort(importedBooks, new SortedBookById());

        System.out.println(books.size());
        books = replacingEntitys(books, importedBooks);
        if (importedBooks.size() > books.size()) {
            List<Book> subListBooks = importedBooks.subList(books.size(), importedBooks.size());
            books.addAll(books.size(), subListBooks);
        }
    }

    public void makkingConnectionOrder(List<Order> orders, List<Order> importedOrders) {
        if (orders == null) {
            orderManagement.setOrders(importedOrders);
            return;
        }

        Collections.sort(orders, new SortedOrderById());
        Collections.sort(importedOrders, new SortedOrderById());

        orders = replacingEntitysRefferingBook(orders, importedOrders);
        orders = addNewElementsRefferingBook(orders, importedOrders);
    }

    public void makkingConnectionRequest(List<Request> requests, List<Request> importedRequets) {
        if (requests == null) {
            orderManagement.setRequests(importedRequets);
            return;
        }

        Collections.sort(requests, new SortedRequestById());
        Collections.sort(importedRequets, new SortedRequestById());

        requests = replacingEntitysRefferingBook(requests, importedRequets);
        requests = addNewElementsRefferingBook(requests, importedRequets);
    }

    public  <T extends GettingInfo> List<T> replacingEntitys(List<T> entitys, List<T> importedEntitys) {
        for (int i = 0; i < entitys.size(); ++i) {
            for (int j = i; j < importedEntitys.size(); ++j) {
                if (entitys.get(i).getId() == importedEntitys.get(j).getId()) {
                    entitys.set(i, importedEntitys.get(j));
                }
            }
        }
        return entitys;
    }

    public <T extends ReferringBook & GettingInfo> List<T> replacingEntitysRefferingBook(List<T> entitys, List<T> importedEntitys) {
        for (int i = 0; i < entitys.size(); ++i) {
            for (int j = i; j < importedEntitys.size(); ++j) {
                if (entitys.get(i).getId() == importedEntitys.get(j).getId()) {
                    if (warehouse.findBookById(importedEntitys.get(j).getBookId()) == null) {
                        System.out.println("Данная сущность связана с некорректным id книги, поэтому не будет заменена.");
                    } else {
                        entitys.set(i, importedEntitys.get(j));
                    }
                }
            }
        }
        return entitys;
    }

    public <T extends ReferringBook> List<T> addNewElementsRefferingBook(List<T> entitys, List<T> importedEntitys) {
        if (importedEntitys.size() > entitys.size()) {
            List<T> subListEntitys = importedEntitys.subList(entitys.size(), importedEntitys.size());
            Iterator<T> entityIterator = subListEntitys.iterator();
            while (entityIterator.hasNext()) {
                T nextEntity = entityIterator.next();
                if (warehouse.findBookById(nextEntity.getBookId()) == null) {
                    entityIterator.remove();
                }
            }
            entitys.addAll(entitys.size(), subListEntitys);
        }
        return entitys;
    }
}
