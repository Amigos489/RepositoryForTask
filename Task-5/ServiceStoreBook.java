import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class ServiceStoreBook {


    private Warehouse warehouse;
    private OrderManagement orderManagement;
    private JsonStorage jsonStorage;

    public ServiceStoreBook(Warehouse warehouse, OrderManagement orderManagement, JsonStorage jsonStorage) {
        this.warehouse = warehouse;
        this.orderManagement = orderManagement;
        this.jsonStorage = jsonStorage;

    }

    public void saveAll() {
        StoreData data = new StoreData();
        data.setBooks(warehouse.getListBooks());
        data.setOrders(orderManagement.getAllOrders());
        data.setRequests(orderManagement.getAllBookRequests());

        try {
            jsonStorage.save(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Загрузка всего состояния
    public void loadAll() {
        try {
            StoreData data = jsonStorage.load(StoreData.class);
            if (data != null) {
                warehouse.setListBooks(data.getBooks());
                orderManagement.setAllOrders(data.getOrders());
                orderManagement.setBookRequests(data.getRequests());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Сортировка книг */
    public ArrayList<Book> sortedCriterionBook(int choice) {

        ArrayList<Book> books = warehouse.getListBooks();

        switch (choice) {
            case 1: {
                Collections.sort(books, new SortedBookByName());
                break;
            }
            case 2: {
                Collections.sort(books, new SortedBookByDateOfPublication());
                break;
            }
            case 3: {
                Collections.sort(books, new SortedBookByPrice());
                break;
            }
            case 4: {
                Collections.sort(books, new SortedBookByAvailability());
                break;
            }
            default: {
                break;
            }
        }
        return books;

    }

    /* Сортировка заказов */
    public ArrayList<Order> sortedCriterionOrder(int choice) {

        ArrayList<Order> orders = orderManagement.getAllOrders();

        switch (choice) {
            case 1: {
                Collections.sort(orders, new SortedOrderByDateOfExecution());
                break;
            }
            case 2: {
                Collections.sort(orders, new SortedOrderByPrice());
                break;
            }
            case 3: {
                Collections.sort(orders, new SortedOrderByOrderStatus());
                break;
            }
            default: {
                break;
            }
        }
        return orders;
    }

    public ArrayList<BookRequest> sortedCriterionBookRequest(int choice) {

        ArrayList<BookRequest> requests = orderManagement.getAllBookRequests();

        switch (choice) {
            case 1:
                Collections.sort(requests, new SortedBookRequestByNameBook());
                break;
            case 2:
                Collections.sort(requests, new SortedBookRequestByCount());
                break;
            default:
                System.out.println("Некорректный выбор.");
                break;
        }
        return requests;

    }

    public ArrayList<Book> sortedCriterionStaleBook(int choice) {

        ArrayList<Book> staleBooks = getWarehouse().getStaleBooks();
        switch (choice) {
            case 1: {
                Collections.sort(staleBooks, new SortedBookByDateOfPublication());
                break;
            }
            case 2: {
                Collections.sort(staleBooks, new SortedBookByPrice());
                break;
            }
            default:
                break;
        }
        return staleBooks;
    }

    public ArrayList<Order> sortedCriterionComplectedOrder(int choice, LocalDate start, LocalDate end) {

        ArrayList<Order> orders = orderManagement.getCompletedOrders(start, end);

        switch (choice) {
            case 1: {
                Collections.sort(orders, new SortedOrderByDateOfExecution());
                break;
            }
            case 2: {
                Collections.sort(orders, new SortedOrderByPrice());
                break;
            }
        }
        return orders;
    }

    /* Создание заказа на книгу */
    public void createOrder(Book book, String emailClient) {
        orderManagement.createOrderOnBook(book, emailClient);
    }

    /* Отмена заказа на книгу */
    public boolean cancelOrderByID(int orderID) {
        return orderManagement.cancelOrderOnBook(orderID);
    }

    /* Добавление книги на склад */
    public StatusAddBook addBookToWarehouse(Book book) {
        return warehouse.addToWarehouse(book);
    }

    /* Геттеры */
    public OrderManagement getOrderManagement() {
        return this.orderManagement;
    }

    public ArrayList<BookRequest> getBookRequest() {
        return orderManagement.getAllBookRequests();
    }

    public ArrayList<Book> getAllBooks() {
        return warehouse.getListBooks();
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

}