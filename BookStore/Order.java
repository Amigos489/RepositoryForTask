import java.time.LocalDate;
import java.util.ArrayList;;

/* Класс заказа */
public class Order implements Exportable{

    private static int nextOrderID = 1;   

    private int orderID;
    private int priceOrder;
    private String customerEmail;
    private OrderStatus orderStatus;
    private LocalDate dateOfExecution;
    private Book book;
    private ArrayList<BookRequest> listRequest; 
    
    public Order() {}

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

    /* Геттеры */
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

    public ArrayList<BookRequest> getListRequest() {
        return listRequest;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    /* Сеттеры */

    public void setID(int ID) {
        if(ID > 0) {
            this.orderID = ID;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setPriceOrder(int priceOrder) {
        if (priceOrder > 0) {
            this.priceOrder = priceOrder;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setOrderStatus(OrderStatus orderStatus) {

        this.orderStatus = orderStatus;

    }

    public void setDateOfExecution(LocalDate dateOfExecution) {

        this.dateOfExecution = dateOfExecution;

    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @Override
    public String generateStringHeader() {
        return "orderID,customerEmail,priceOrder,orderStatus,dateOfExecution,bookId,bookTitle";
    }

    @Override
    public String generateStringInfo() {
        return (this.getOrderID() + "," + "\"" + this.getCustomerEmail() + "\"," + this.getPriceOrder() + "," + this.getOrderStatus() + "," +
            this.getDateOfExecution() + "," + this.getBook().getBookId() + "," + "\"" + this.getBook().getNameBook() + "\"");
    }
}
