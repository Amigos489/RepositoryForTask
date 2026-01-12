import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderManagement {

    private ArrayList<Order> listOrder;
    private ArrayList<BookRequest> listRequests;
    private boolean possibilityMarkComplected;
    @Inject
    private OrdersDAO ordersDAO;
    @Inject
    private BookRequestDAO bookRequestDAO;
    @Inject
    private BooksDAO bookDAO;

    /* Конструктор по умолчанию */
    public OrderManagement() { 
        this.possibilityMarkComplected = true;
    }

    public void initFromDAO() {
        this.listOrder = new ArrayList<>(ordersDAO.getAll());
        this.listRequests = new ArrayList<>(bookRequestDAO.getAll());
    }

    /* Конструктор */
    public OrderManagement(boolean possibilityMarkComplected) {
        listOrder = (ArrayList<Order>)ordersDAO.getAll();
        listRequests = (ArrayList<BookRequest>)bookRequestDAO.getAll();
        this.possibilityMarkComplected = possibilityMarkComplected;
    }

    /* Создание заказа */
    public StatusCreateOrder createOrderOnBook(Book book, String customerEmail) {
        try {
            ordersDAO.accomplishmentTransaction(OperationTransaction.START);
            Order order = new Order(book, customerEmail);
            order.setPriceOrder(book.getPrice());
            if (book.getAvailability()) {
                ordersDAO.save(order);                                      //Сохраняем в бд
                listOrder.add(order);
                ordersDAO.accomplishmentTransaction(OperationTransaction.COMMIT);
                ordersDAO.accomplishmentTransaction(OperationTransaction.END);
                return StatusCreateOrder.SUCCESSFULLY;
            } else {
                BookRequest request = new BookRequest(book); /* Создаём запрос на книгу */
                bookRequestDAO.save(request);                               //Сохраняем в бд
                listRequests.add(request);
                order.setOrderStatus(OrderStatus.WAITING);
                ordersDAO.save(order);                                      //Сохраняем в бд
                listOrder.add(order);
                ordersDAO.accomplishmentTransaction(OperationTransaction.COMMIT);
                ordersDAO.accomplishmentTransaction(OperationTransaction.END);
                return StatusCreateOrder.EXPECTATION;
            }
        } catch (SQLException e) {
            ordersDAO.accomplishmentTransaction(OperationTransaction.ROLLBACK);
            ordersDAO.accomplishmentTransaction(OperationTransaction.END);
            return StatusCreateOrder.EXPECTATION;
        }
    }

    public void setPriceAllOrders() {
        listOrder = (ArrayList<Order>) ordersDAO.getAll();
        for (Order order : listOrder) {
            order.setPriceOrder((bookDAO.getByID(order.getBookID())).getPrice());
        }
    }


    /* Отмена заказа */
    public boolean cancelOrderOnBook(int ID) {
        Order order = ordersDAO.getByID(ID);        
        if (order == null) {
            return false;
        }

        if (order.getOrderStatus() == OrderStatus.NEW || 
                order.getOrderStatus() == OrderStatus.WAITING) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            ordersDAO.update(order);
            return true;
        } else {
            return false;
        }
    }

    /* Метод для поступления книги на склад и закрытия запросов */
    public void receiveBookOnWarehouse(Book book) {
        try {
            bookDAO.accomplishmentTransaction(OperationTransaction.START);
            book.setAvailability(true);
            bookDAO.update(book);

            List<BookRequest> requests = bookRequestDAO.getAll();
            if (possibilityMarkComplected) {
                for (BookRequest request : requests) {
                    if (request.getBookId() == book.getBookID() && !request.isFulfilled()) {
                        request.fulfillRequest();
                        bookRequestDAO.update(request);
                    }
                }
            }

            List<Order> orders = ordersDAO.getAll();
            for (Order order : orders) {
                if (order.getBookID() == book.getBookID() && order.getOrderStatus() == OrderStatus.WAITING) {
                    order.setOrderStatus(OrderStatus.NEW); 
                    ordersDAO.update(order);
                }
            }
            bookDAO.accomplishmentTransaction(OperationTransaction.COMMIT);
            bookDAO.accomplishmentTransaction(OperationTransaction.END);
        } catch (SQLException e) {
            e.printStackTrace();
            bookDAO.accomplishmentTransaction(OperationTransaction.ROLLBACK);
            bookDAO.accomplishmentTransaction(OperationTransaction.END);
        }
    }

    /* Ручное добавление запроса на книгу */
    public void addBookRequest(Book book) {
        try {
            BookRequest request = new BookRequest(book);
            bookRequestDAO.save(request);
            listRequests.add(request);
            book.incrementRequests();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Получить все запросы на конкретную книгу */
    public ArrayList<BookRequest> getRequestsForBook(Book book) {
        ArrayList<BookRequest> result = new ArrayList<>();
        for (BookRequest r : listRequests) {
            if (r.getBookName().equals(book.getNameBook())) {
                result.add(r);
            }
        }
        return result;
    }

    /* Получить прибыль */
    public BigDecimal getRevenue(LocalDate start, LocalDate end) {  
        BigDecimal sum = BigDecimal.ZERO;

        List<Order> orders = ordersDAO.getAll();
        for (Order order : orders) {
            if (order.getOrderStatus() == OrderStatus.COMPLETED) {
                LocalDate date = order.getDateOfExecution();
                if ((date.isEqual(start) || date.isAfter(start)) &&
                    (date.isEqual(end) || date.isBefore(end))) {
                    sum = sum.add(order.getPriceOrder());
                }
            }
        }

        return sum;
    }

    /* Посмотреть кол - во выполненных заказов*/
    public int getCompletedOrdersCount(LocalDate start, LocalDate end) {
        int count = 0;

        List<Order> orders = ordersDAO.getAll();
        for (Order order : orders) {
            if (order.getOrderStatus() == OrderStatus.COMPLETED) {
                LocalDate date = order.getDateOfExecution();
                if ((date.isEqual(start) || date.isAfter(start)) &&
                    (date.isEqual(end) || date.isBefore(end))) {
                    count++;
                }
            }
        }

        return count;
    }

    /* Получить список выполненных заказов */
    public ArrayList<Order> getCompletedOrders(LocalDate start, LocalDate end) {
    ArrayList<Order> completedOrders = new ArrayList<>();

        List<Order> orders = ordersDAO.getAll();
        for (Order order : orders) {
            if (order.getOrderStatus() == OrderStatus.COMPLETED) {
                LocalDate date = order.getDateOfExecution();
                if ((date.isEqual(start) || date.isAfter(start)) &&
                    (date.isEqual(end) || date.isBefore(end))) {
                    completedOrders.add(order);
                }
            }
        }

        return completedOrders;
    }

    /* Геттеры */
    public ArrayList<Order> getAllOrders() {
        return this.listOrder;
    }

    public ArrayList<BookRequest> getAllBookRequests() {
        return this.listRequests;
    }

    /* Сеттеры */
    public void setAllOrders(ArrayList<Order> orders) {
        this.listOrder = orders;
    }

    public void setBookRequests(ArrayList<BookRequest> listRequests) {
        this.listRequests = listRequests;
    }

    
}
