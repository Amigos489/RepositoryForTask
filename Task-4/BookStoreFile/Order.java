import java.time.LocalDate;
import java.util.ArrayList;;

/* Класс заказа */
public class Order {

    private static int nextOrderID = 1;   

    private int orderID;
    private int priceOrder;
    private String customerEmail;
    private OrderStatus orderStatus;
    private LocalDate dateOfExecution;
    private Book book;
    private ArrayList<BookRequest> listRequest;      

    /* Конструктор */
    public Order(Book book, String customerEmail) {
        this.orderID = nextOrderID++;
        this.book = book;
        this.priceOrder = book.getPrice();
        this.customerEmail = customerEmail;
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
        System.out.println("Заказчик: " + customerEmail);
        System.out.println("Книга:");
        book.printInfo();
    }

    /* Геттеры и сеттеры*/
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int ID) {
        this.orderID = ID;
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

    public ArrayList<BookRequest> getListRequest() {
        return listRequest;
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
