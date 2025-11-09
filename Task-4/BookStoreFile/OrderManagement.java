import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class OrderManagement {

    public LinkedList<Order> listOrder; 
    public LinkedList<BookRequest> listRequests;

    /* Конструктор */
    public OrderManagement() {
        listOrder = new LinkedList<>();
        listRequests = new LinkedList<>();
    }

    /* Создание заказа */
    public Order createOrderOnBook(Book book, String customerName,
            String customerEmail) {

        Order order = new Order(book, customerName, customerEmail);

        if (book.getAvailability()) {
            System.out.println("Создан заказ.");
        } else {
            BookRequest request = new BookRequest(book); /* Создаём запрос на книгу */
            listRequests.add(request);
            order.getListRequest().add(request);
            order.setOrderStatus(OrderStatus.WAITING);
            System.out.println("Книги нет на складе. Создан запрос на книгу, заказ в ожидании.");
        }

        listOrder.add(order);
        order.orderInfo();
        return order;
    }


    /* Отмена заказа */
    public void cancelOrderOnBook(int ID) {
        Order orderToCancel = null;

        /* Проверка, что заказ с таким ID есть */
        for (Order order : listOrder) {
            if (order.getOrderID() == ID) {
                orderToCancel = order;
                break;
            }
        }

        if (orderToCancel == null) {
            System.out.println("Заказ с таким ID не найден.");
            return;
        }

        if (orderToCancel.getOrderStatus() == OrderStatus.NEW || 
                orderToCancel.getOrderStatus() == OrderStatus.WAITING) {
            orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
            System.out.println("Заказ отменён.");
        } else {
            System.out.println("Ошибка! заказ уже завершён или был отменён.");
        }
    }

    /* Метод для поступления книги на склад и закрытия запросов */
    public void receiveBookOnWarehouse(Book book) {
        book.setAvailability(true);

        for (BookRequest request : listRequests) {
            if (request.getBook().equals(book) && !request.isFulfilled()) {
                request.fulfillRequest();
            }
        }

        for (Order order : listOrder) {
            if (order.getBook().equals(book) && order.getOrderStatus() == OrderStatus.WAITING) {
                order.setOrderStatus(OrderStatus.NEW); 
                System.out.println("Заказ ID " + order.getOrderID() + " готов к выполнению.");
            }
        }
    }

    /* Ручное добавление запроса на книгу */
    public void addBookRequest(Book book) {
        BookRequest request = new BookRequest(book);
        listRequests.add(request);
        book.incrementRequests();
        System.out.println("Создан запрос на книгу: " + book.getNameBook());
    }

    /* Получить все запросы на конкретную книгу */
    public LinkedList<BookRequest> getRequestsForBook(Book book) {
        LinkedList<BookRequest> result = new LinkedList<>();
        for (BookRequest r : listRequests) {
            if (r.getBook().equals(book)) {
                result.add(r);
            }
        }
        return result;
    }

    /* Получить прибыль */
    public int getRevenue(LocalDate start, LocalDate end) {                 /* Передаём начальную дату и конечную */
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
    public int getCompletedOrdersCount(LocalDate start, LocalDate end) {    /* Передаём начальную дату и конечную */
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
    public List<Order> getCompletedOrders(LocalDate start, LocalDate end) {
    List<Order> completedOrders = new LinkedList<>();

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
}
