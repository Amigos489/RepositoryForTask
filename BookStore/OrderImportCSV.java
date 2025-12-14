import java.time.LocalDate;
import java.util.ArrayList;

public class OrderImportCSV extends ImportCSV<Order> {

    private ArrayList<Book> books;

    OrderImportCSV(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public Order conversionInEssence(String line) {

        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        int orderID = Integer.parseInt(parts[0]);
        String customerEmail = parts[1].replace("\"", "");
        int priceOrder = Integer.parseInt(parts[2]);
        OrderStatus status = OrderStatus.valueOf(parts[3]);
        LocalDate dateOfExecution = LocalDate.parse(parts[4]);
        int bookId = Integer.parseInt(parts[5]);

        // Ищем книгу в списке книг, а не заказов
        Book book = null;
        for (Book b : books) {
            if (b.getBookId() == bookId) {
                book = b;
                break;
            }
        }

        if (book != null) {
            Order order = new Order(book, customerEmail);
            order.setOrderID(orderID);
            order.setOrderStatus(status);
            order.setPriceOrder(priceOrder);
            order.setDateOfExecution(dateOfExecution);
            return order;
        }
        return null;
    }
}