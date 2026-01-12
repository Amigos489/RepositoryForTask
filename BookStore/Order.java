import java.math.BigDecimal;
import java.time.LocalDate;

/* Класс заказа */
public class Order implements Exportable{

    private int orderID;
    private BigDecimal priceOrder;
    private String customerEmail;
    private OrderStatus orderStatus;
    private LocalDate dateOfExecution;
    private Book book;
    private int bookID;
    
    public Order() {}

    /* Конструктор */
    public Order(Book book, String customerEmail) {
        this.book = book;
        this.bookID = book.getBookID();
        this.priceOrder = book.getPrice();
        this.customerEmail = customerEmail;
        this.orderStatus = OrderStatus.NEW;
        this.dateOfExecution = LocalDate.now().plusDays(7);     /* дата исполнения = текущая дата + 7 дней */
    }

    public Order(String customerEmail, LocalDate dateOfExecution, Integer bookID, OrderStatus orderStatus) {
        this.customerEmail = customerEmail;
        this.dateOfExecution = dateOfExecution;
        this.bookID = bookID;
        this.orderStatus = orderStatus;
    }

    /* Геттеры */
    public int getOrderID() {
        return orderID;
    }

    public BigDecimal getPriceOrder() {
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

    public int getBookID() {
        return bookID;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    /* Сеттеры */

    public void setOrderStatus(OrderStatus orderStatus) {

        this.orderStatus = orderStatus;

    }

    public void setDateOfExecution(LocalDate dateOfExecution) {

        this.dateOfExecution = dateOfExecution;

    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setPriceOrder(BigDecimal price) {
        this.priceOrder = price;
    }

    

    @Override
    public String generateStringHeader() {
        return "orderID,customerEmail,priceOrder,orderStatus,dateOfExecution,bookId,bookTitle";
    }

    @Override
    public String generateStringInfo() {
        return (this.getOrderID() + "," + "\"" + this.getCustomerEmail() + "\"," + this.getPriceOrder() + "," + this.getOrderStatus() + "," +
            this.getDateOfExecution() + "," + this.getBookID() + "," + "\"" + this.getBook().getNameBook() + "\"");
    }
}
