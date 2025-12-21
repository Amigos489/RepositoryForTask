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

    public Book createBook(String nameBook, String authorBook, LocalDate dateOfPublication, int numberOfCopies, int numberPages, int price, LocalDate dateAddedToWarehouse) {
        return new Book(nameBook, authorBook, dateOfPublication, numberOfCopies, numberPages, price, dateAddedToWarehouse);
    }

    /* Создание заказа на книгу */
    public StatusCreateOrder createOrder(Book book, String emailClient) {
        return orderManagement.createOrderOnBook(book, emailClient);
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

    public StatusImportExport exportEssenceInFileCSV(int choice) {

        switch (choice) {
            case 1: {
                return ExportCSV.exportInFile("Books.csv", getAllBooks());
            }
            case 2: {
                return ExportCSV.exportInFile("Orders.csv", getOrderManagement().getAllOrders());
            }
            case 3: {
                return ExportCSV.exportInFile("BookRequests.csv", getBookRequest());
            }
            default:
                break;
        }
        return StatusImportExport.FAIL;

    }

    public StatusImportExport importEssenceFromFileCSV(int choice) {
        try {
            switch (choice) {
                case 1: {
                    BookImportCSV bookImport = new BookImportCSV();
                    ArrayList<Book> importedBooks = bookImport.importFromFile("Books.csv");
                    mergeBooks(importedBooks);
                    return StatusImportExport.CORRECTLY;
                }
                case 2: {
                    OrderImportCSV orderImport = new OrderImportCSV(getAllBooks());
                    ArrayList<Order> importedOrders = orderImport.importFromFile("Orders.csv");
                    mergeOrders(importedOrders);
                    return StatusImportExport.CORRECTLY;
                }
                case 3: {
                    BookRequestImportCSV bookImport = new BookRequestImportCSV();
                    ArrayList<BookRequest> importedBookRequests = bookImport.importFromFile("BookRequests.csv");
                    mergeBookRequest(importedBookRequests);
                    return StatusImportExport.CORRECTLY;
                }
                default:
                    return StatusImportExport.FAIL;
            }
        } catch(IOException e) {
            return StatusImportExport.FAIL;
        }
    }

    public void mergeOrders(ArrayList<Order> imported) {
        for (Order o : imported) {
            boolean found = false;
            for (int i = 0; i < orderManagement.getAllOrders().size(); i++) {
                if (orderManagement.getAllOrders().get(i).getOrderID() == o.getOrderID()) {
                    orderManagement.getAllOrders().set(i, o);
                    found = true;
                    break;
                }
            }
            if (!found) {
                orderManagement.getAllOrders().add(o);
            }
        }
    }

    public void mergeBooks(ArrayList<Book> imported) {
        for (Book b : imported) {
            boolean found = false;
            for (int i = 0; i < warehouse.getListBooks().size(); i++) {
                if (warehouse.getListBooks().get(i).getBookId() == b.getBookId()) {
                    warehouse.getListBooks().set(i, b);
                    found = true;
                    break;
                }
            }
            if (!found) {
                warehouse.addToWarehouse(b);
            }
        }

    }

    public void mergeBookRequest(ArrayList<BookRequest> imported) {
        for (BookRequest o : imported) {
            boolean found = false;
            for (int i = 0; i < orderManagement.getAllBookRequests().size(); i++) {
                if (orderManagement.getAllBookRequests().get(i).getId() == o.getId()) {
                    orderManagement.getAllBookRequests().set(i, o);
                    found = true;
                    break;
                }
            }
            if (!found) {
                orderManagement.getAllBookRequests().add(o);
            }
        }
    }

}