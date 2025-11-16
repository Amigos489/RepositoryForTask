import java.io.PrintWriter;
import java.util.ArrayList;

public class ExportCSV {

    public static void exportClients(ArrayList<Client> clients, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("id,fullName,password,age,email");
            for (Client client : clients) {
                writer.println(
                    client.getId() + "," +
                    "\"" + client.getFullName() + "\"," +
                    "\"" + client.getPassword() + "\"," +
                    client.getAge() + "," +
                    "\"" + client.getEmail() + "\""
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportLibrarians(ArrayList<Librarian> librarians, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("id,fullName,password,age,phone");
            for (Librarian librarian : librarians) {
                writer.println(
                    librarian.getId() + "," +
                    "\"" + librarian.getFullName() + "\"," +
                    "\"" + librarian.getPassword() + "\"," +
                    librarian.getAge() + "," +
                    "\"" + librarian.getPhone() + "\""
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportBooks(ArrayList<Book> books, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("bookId,nameBook,authorBook,dateOfPublication,dateAddedToWarehouse,numberOfCopies,numberPages,price,numberOfRequests,availability");
            for (Book book : books) {
                writer.println(
                    book.getId() + "," +
                    "\"" + book.getNameBook() + "\"," +
                    "\"" + book.getAuthorBook() + "\"," +
                    book.getDateOfPublication() + "," +
                    book.getDateAddedToWarehouse() + "," +
                    book.getNumberOfCopies() + "," +
                    book.getNumberPages() + "," +
                    book.getPrice() + "," +
                    book.getNumberOfRequests() + "," +
                    book.getAvailability()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportOrders(ArrayList<Order> orders, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("orderID,customerEmail,priceOrder,orderStatus,dateOfExecution,bookId,bookTitle");
            for (Order order : orders) {
                writer.println(
                    order.getOrderID() + "," +
                    "\"" + order.getCustomerEmail() + "\"," +
                    order.getPriceOrder() + "," +
                    order.getOrderStatus() + "," +
                    order.getDateOfExecution() + "," +
                    order.getBook().getId() + "," +
                    "\"" + order.getBook().getNameBook() + "\""
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
