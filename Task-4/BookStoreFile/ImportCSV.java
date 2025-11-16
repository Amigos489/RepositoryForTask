import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;

public class ImportCSV {

    public static ArrayList<Book> importBooks(String filePath) {
        ArrayList<Book> imported = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); 

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                int id = Integer.parseInt(parts[0]);
                String nameBook = parts[1].replace("\"", "");
                String authorBook = parts[2].replace("\"", "");
                LocalDate dateOfPublication = LocalDate.parse(parts[3]);
                LocalDate dateAddedToWarehouse = LocalDate.parse(parts[4]);
                int numberOfCopies = Integer.parseInt(parts[5]);
                int numberPages = Integer.parseInt(parts[6]);
                int price = Integer.parseInt(parts[7]);
                int numberOfRequests = Integer.parseInt(parts[8]);
                boolean availability = Boolean.parseBoolean(parts[9]);

                Book book = new Book(nameBook, authorBook, dateOfPublication,
                        numberOfCopies, numberPages, price, dateAddedToWarehouse);
                book.setId(id);
                book.setAvailability(availability);
                for (int i = 0; i < numberOfRequests; i++) book.incrementRequests();

                imported.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imported;
    }

    // ------------------ Импорт клиентов ------------------
    public static ArrayList<Client> importClients(String filePath) {
        ArrayList<Client> imported = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // пропускаем заголовок

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                int id = Integer.parseInt(parts[0]);
                String fullName = parts[1].replace("\"", "");
                String password = parts[2].replace("\"", "");
                int age = Integer.parseInt(parts[3]);
                String email = parts[4].replace("\"", "");

                Client client = new Client(fullName, password, age, email);
                client.setId(id);

                imported.add(client);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imported;
    }

    public static ArrayList<Librarian> importLibrarians(String filePath) {
        ArrayList<Librarian> imported = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // заголовок

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                int id = Integer.parseInt(parts[0]);
                String fullName = parts[1].replace("\"", "");
                String password = parts[2].replace("\"", "");
                int age = Integer.parseInt(parts[3]);
                String phone = parts[4].replace("\"", "");

                Librarian librarian = new Librarian(fullName, password, age, phone);
                librarian.setId(id);
                imported.add(librarian);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imported;
    }

    public static ArrayList<Order> importOrders(String filePath, ArrayList<Book> listBooks) {
        ArrayList<Order> imported = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // пропускаем заголовок

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                int orderID = Integer.parseInt(parts[0]);
                String customerEmail = parts[1].replace("\"", "");
                int priceOrder = Integer.parseInt(parts[2]);
                OrderStatus status = OrderStatus.valueOf(parts[3]);
                LocalDate dateOfExecution = LocalDate.parse(parts[4]);
                int bookId = Integer.parseInt(parts[5]);

                // Ищем книгу в списке книг, а не заказов
                Book book = null;
                for (Book b : listBooks) {
                    if (b.getId() == bookId) {
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
                    imported.add(order);
                } else {
                    System.out.println("Не найдена книга с ID " + bookId + " для заказа " + orderID);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imported;
    }

}
