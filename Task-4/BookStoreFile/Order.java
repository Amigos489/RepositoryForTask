import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/* Класс заказа */
public class Order {

    private static int nextOrderID = 1;                         /* Позволяет задавать уникальный ID для всех заказов*/

    private int orderID;
    private int priceOrder;
    private String customerName;
    private String customerEmail;
    private OrderStatus orderStatus;
    private LocalDate dateOfExecution;
    private Book book;
    private List<BookRequest> listRequest;                      /* Список запросов на книгу*/

    /* Конструктор */
    public Order(Book book, String customerName, String customerEmail) {
        this.orderID = nextOrderID++;
        this.book = book;
        this.priceOrder = book.getPrice();
        this.customerName = customerName;
        this.customerEmail = customerEmail;                      /* Цена заказа = цена книги */
        this.orderStatus = OrderStatus.NEW;
        this.dateOfExecution = LocalDate.now().plusDays(7);     /* дата исполнения = текущая дата + 7 дней */
        this.listRequest = new ArrayList<>();
    }

    /* Вывод информации о заказе */
    public void orderInfo() {
        System.out.println("ID заказа: " + orderID);
        System.out.println("Статус: " + orderStatus);
        System.out.println("Дата исполнения: " + dateOfExecution);
        System.out.println("Цена заказа: " + priceOrder);
        System.out.println("Заказчик: " + customerName + " (" + customerEmail + ")");
        System.out.println("Книга:");
        book.printInfo();
    }

    /* Геттеры и сеттеры*/
    public int getOrderID() {
        return orderID;
    }

    public int getPriceOrder() {
        return priceOrder;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDate getDateOfExecution() {
        return dateOfExecution;
    }

    public Book getBook() {
        return book;
    }

    public List<BookRequest> getListRequest() {
        return listRequest;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    public void setPriceOrder(int priceOrder) {
        if (priceOrder >= 0) {
            this.priceOrder = priceOrder;
        } else {
            System.out.println("Ошибка! Цена заказа не может быть <= 0");
        }
    }

    public void setOrderStatus(OrderStatus orderStatus) {

        this.orderStatus = orderStatus;

    }

    public void setDateOfExecution(LocalDate dateOfExecution) {

        this.dateOfExecution = dateOfExecution;

    }
}
