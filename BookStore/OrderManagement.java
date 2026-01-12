import java.util.ArrayList;
import java.time.LocalDate;

public class OrderManagement {

    private ArrayList<Order> listOrder; 
    private ArrayList<BookRequest> listRequests;
    private boolean possibilityMarkComplected;

    /* Пустой конструктор */
    public OrderManagement() { }

    /* Конструктор */
    public OrderManagement(boolean possibilityMarkComplected) {
        listOrder = new ArrayList<>();
        listRequests = new ArrayList<>();
        this.possibilityMarkComplected = possibilityMarkComplected;
    }

    /* Создание заказа */
    public StatusCreateOrder createOrderOnBook(Book book, String customerEmail) {

        Order order = new Order(book, customerEmail);
        if (book.getAvailability()) {
            listOrder.add(order);
            return StatusCreateOrder.SUCCESSFULLY;
        } else {
            BookRequest request = new BookRequest(book); /* Создаём запрос на книгу */
            listRequests.add(request);
            order.getListRequest().add(request);
            order.setOrderStatus(OrderStatus.WAITING);
            listOrder.add(order);
            return StatusCreateOrder.EXPECTATION;
        }
    }


    /* Отмена заказа */
    public boolean cancelOrderOnBook(int ID) {
        Order orderToCancel = null;

        /* Проверка, что заказ с таким ID есть */
        for (Order order : listOrder) {
            if (order.getOrderID() == ID) {
                orderToCancel = order;
                break;
            }
        }

        if (orderToCancel == null) {
            return false;
        }

        if (orderToCancel.getOrderStatus() == OrderStatus.NEW || 
                orderToCancel.getOrderStatus() == OrderStatus.WAITING) {
            orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
            return true;
        } else {
            return false;
        }
    }

    /* Метод для поступления книги на склад и закрытия запросов */
    public void receiveBookOnWarehouse(Book book) {
        book.setAvailability(true);

        if (possibilityMarkComplected) {
            for (BookRequest request : listRequests) {
                if (request.getBookName().equals(book.getNameBook()) && !request.isFulfilled()) {
                    request.fulfillRequest();
                }
            }
        }

        for (Order order : listOrder) {
            if (order.getBook().equals(book) && order.getOrderStatus() == OrderStatus.WAITING) {
                order.setOrderStatus(OrderStatus.NEW); 
            }
        }
    }

    /* Ручное добавление запроса на книгу */
    public void addBookRequest(Book book) {
        BookRequest request = new BookRequest(book);
        listRequests.add(request);
        book.incrementRequests();
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
    public int getRevenue(LocalDate start, LocalDate end) {  
        int sum = 0;

        for (Order order : listOrder) {
            if (order.getOrderStatus() == OrderStatus.COMPLETED) {
                LocalDate date = order.getDateOfExecution();
                if ((date.isEqual(start) || date.isAfter(start)) &&
                    (date.isEqual(end) || date.isBefore(end))) {
                    sum += order.getPriceOrder();
                }
            }
        }

        return sum;
    }

    /* Посмотреть кол - во выполненные заказы*/
    public int getCompletedOrdersCount(LocalDate start, LocalDate end) {
        int count = 0;

        for (Order order : listOrder) {
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

        for (Order order : listOrder) {
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
