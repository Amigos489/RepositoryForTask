import java.time.LocalDate;
import java.util.ArrayList;

public class ModelStoreBook {

    private Warehouse warehouse;
    private OrderManagement orderManagement;
    private ArrayList<Client> clients;
    private ArrayList<Librarian> librarians;


    public ModelStoreBook() {
        this.warehouse = new Warehouse(0, 200);
        this.clients = new ArrayList<>();
        this.librarians = new ArrayList<>();
        this.orderManagement = new OrderManagement();

        clients.add(new Client("Dmitry Kostin Sergeevich", "Dima1234", 19, "dima@gmail.com"));
        clients.add(new Client("Ivan Petrov Alekseevich", "Ivan5678", 25, "ivan@mail.ru"));
        clients.add(new Client("Anna Smirnova Olegovna", "Anna4321", 30, "anna@gmail.com"));

        librarians.add(new Librarian("Ekaterina Volkova Igorevna", "Kate8765", 28, "555-1234"));
        librarians.add(new Librarian("Sergey Ivanov Viktorovich", "Sergey3456", 35, "555-5678"));

        warehouse.addToWarehouse(new Book("War and Peace", "Leo Tolstoy", LocalDate.parse("1869-01-01"), 35, 1200, 900, LocalDate.parse("2023-01-15")));
        warehouse.addToWarehouse(new Book("Crime and Punishment", "Fyodor Dostoevsky", LocalDate.parse("1866-01-01"), 20, 800, 700, LocalDate.parse("2023-03-10")));
        warehouse.addToWarehouse(new Book("The Master and Margarita", "Mikhail Bulgakov", LocalDate.parse("1967-01-01"), 15, 600, 800, LocalDate.parse("2023-02-20")));

    }

    public Client findClientByEmail(String email) {
        for (Client client : clients) {
            if (client.getEmail().equals(email)) {
                return client;
            }
        }
        return null;
    }

    public boolean checkPassword(Client client, String password) {
        return client.getPassword().equals(password);
    }

    public Librarian findLibrarianByPhone(String phone) {
        for (Librarian librarian : librarians) {
            if (librarian.getPhone().equals(phone)) {
                return librarian;
            }
        }
        return null;
    }

    public boolean checkPasswordLibrarian(Librarian librarian, String password) {
        return librarian.getPassword().equals(password);
    }

    public ArrayList<Book> getAllBooks() {
        return warehouse.getListBooks();
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }


    /* Создание заказа на книгу */
    public int createOrder(String emailClient, Book book) {
        Client client = findClientByEmail(emailClient);

        Order newOrder = orderManagement.createOrderOnBook(book, emailClient);
        client.getOrders().add(newOrder);
        return newOrder.getOrderID();

    }

    /* Отмена заказа на книгу */
    public boolean cancelOrderByID(int orderID) {
        return orderManagement.cancelOrderOnBook(orderID);
    }

    public StatusAddBook addBookToWarehouse(Book book) {
        return warehouse.addToWarehouse(book);
    }

    public OrderManagement getOrderManagement() {
        return this.orderManagement;
    }

    public ArrayList<BookRequest> getBookRequest() {
        return orderManagement.getAllBookRequests();
    }

    public ArrayList<Client> getClients() {
        return this.clients;
    }

    public ArrayList<Librarian> getLibrian() {
        return this.librarians;
    }

    public void mergeClients(ArrayList<Client> imported) {
        for (Client c : imported) {
            boolean found = false;
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getId() == c.getId()) {
                    clients.set(i, c);
                    found = true;
                    break;
                }
            }
            if (!found) {
                clients.add(c);
            } 
        }
    }

    public void mergeLibrarian(ArrayList<Librarian> imported) {
        for (Librarian librarian : imported) {
            boolean found = false;
            for (int i = 0; i < librarians.size(); i++) {
                if (librarians.get(i).getId() == librarian.getId()) {
                    librarians.set(i, librarian);
                    found = true;
                    break;
                }
            }
            if (!found) {
                librarians.add(librarian);
            }
        }
    
    }

    public void mergeOrders(ArrayList<Order> imported) {
        for (Order o : imported) {
            boolean found = false;
            for (int i = 0; i < orderManagement.getAllOrders().size(); i++) {
                if (orderManagement.getAllOrders().get(i).getOrderID() == o.getOrderID()) {
                    orderManagement.getAllOrders().set(i, o);
                    found = true;
                    break;
                }
            }
            if (!found) {
                orderManagement.getAllOrders().add(o);
            }
        }
    }

    public void mergeBooks(ArrayList<Book> imported) {
        for (Book b : imported) {
            boolean found = false;
            for (int i = 0; i < warehouse.getListBooks().size(); i++) {
                if (warehouse.getListBooks().get(i).getId() == b.getId()) {
                    warehouse.getListBooks().set(i, b);
                    found = true;
                    break;
                }
            }
            if (!found) {
                warehouse.addToWarehouse(b);
            }
        }

    }



}