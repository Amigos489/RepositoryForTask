import java.util.ArrayList;
import java.util.Collections;

/* Класс контроллера меню клиента */
public class ControllerMenuClient {

    private ViewMenuClient viewMenuClient;
    private ModelStoreBook modelStoreBook;
    private String emailClient;
    private String passwordClient;
    private String nameBook;
    private boolean running;
    public int choiceUser;

    /* Конструктор */
    public ControllerMenuClient(ViewMenuClient viewMenuClient, ModelStoreBook modelStoreBook) {
        this.viewMenuClient = viewMenuClient;
        this.modelStoreBook = modelStoreBook;
    }

    public void startMenuClient() {

        Client client = null;
        running = true;

        while(client == null) {

            emailClient = viewMenuClient.displayInputEmail();
            client = modelStoreBook.findClientByEmail(emailClient);
            //Пока просто проверка на null
            if (client != null) {
                System.out.println("Добрый день, " + client.getFullName());
                passwordClient = viewMenuClient.displayInputPassword();
                if (modelStoreBook.checkPassword(client, passwordClient)) {
                    System.out.println("Успешный вход.");
                } else {
                    System.out.println("Неверный пароль. повторите снова.");
                    client = null;
                }
            } else {
                System.out.println("Клиент c таким email не найден. повторите снова.");
            }

        }

        while (running) {

            choiceUser = viewMenuClient.displayClientMenu();

            switch (choiceUser) {

                case 1: {
                    System.out.println("Просмотр книг.");
                    choiceUser = viewMenuClient.displayBooksAssortment();
                    getBooksAssortment(choiceUser);
                    break;
                }
                case 2: {
                    System.out.println("Просмотр информации о книге.");
                    nameBook = viewMenuClient.printInputNameBook();
                    Book book = modelStoreBook.getWarehouse().findBookByName(nameBook);
                    if (book != null) {
                        System.out.println("Информация о книге: " + book.getNameBook() );
                        book.printInfo();
                    } else {
                        System.out.println("Не найдено книги с соответствующим названием.");
                    }                    
                    break;
                }
                case 3: {
                    System.out.println("Просмотр своих заказов.");
                    choiceUser = viewMenuClient.displayOrderClient();
                    getOrdersClient(choiceUser, emailClient);
                    break;

                }
                case 4: {
                    System.out.println("Создание заказа на книгу.");
                    nameBook = viewMenuClient.printInputNameBook();
                    Book book = modelStoreBook.getWarehouse().findBookByName(nameBook);
                    int ID = modelStoreBook.createOrder(emailClient, book);
                    System.out.println("Заказ создан. ID заказ: " + ID);
                    break;
                }
                case 5: {
                    System.out.println("Отмена заказа на книгу.");
                    int OrderID = viewMenuClient.printInputIdOrder();
                    if(modelStoreBook.cancelOrderByID(OrderID)) {
                        viewMenuClient.displayMessage("Заказ с ID " + OrderID + " отменен.");
                    } else {
                        viewMenuClient.displayMessage("Заказ с ID " + OrderID + " не удалось отменить.");
                    }
                    break;
                }
                case 6: {
                    System.out.println("Возврат в главное меню.");
                    running = false;
                    break;
                }
                default: {
                    System.out.println("Некорректный выбор.");
                    break;
                }

            }

        }

    }

    public void getBooksAssortment(int choiceUser) {
        ArrayList<Book> books = modelStoreBook.getAllBooks();

        switch (choiceUser) {
            case 1: {
                System.out.println("Сортировка по алфавиту.");
                Collections.sort(books, new SortedBookByName());
                viewMenuClient.printBooksAssortment(books);
                break;
            }
            case 2: {
                System.out.println("Сортировка по дате издания.");
                Collections.sort(books, new SortedBookByDateOfPublication());
                viewMenuClient.printBooksAssortment(books);
                break;
            }
            case 3: {
                System.out.println("Сортировка по цене.");
                Collections.sort(books, new SortedBookByPrice());
                viewMenuClient.printBooksAssortment(books);
                break;
            }
            case 4: {
                System.out.println("Сортировка по наличию на складе.");
                Collections.sort(books, new SortedBookByAvailability());
                viewMenuClient.printBooksAssortment(books);
                break;
            }
            default: {
                System.out.println("Некорректный выбор.");
                break;
            }
        }
    }

    public void getOrdersClient(int choiceUser, String emailClient) {
        ArrayList<Order> orders = modelStoreBook.findClientByEmail(emailClient).getOrders();

        switch (choiceUser) {
            case 1: {
                System.out.println("Сортировка по дате исполнения.");
                Collections.sort(orders, new SortedOrderByDateOfExecution());
                viewMenuClient.printOrderClient(orders);
                break;
            }
            case 2: {
                System.out.println("Сортировка по цене.");
                Collections.sort(orders, new SortedOrderByPrice());
                viewMenuClient.printOrderClient(orders);
                break;
            }
            case 3: {
                System.out.println("Сортировка по статусу.");
                Collections.sort(orders, new SortedOrderByOrderStatus());
                viewMenuClient.printOrderClient(orders);
                break;
            }
            default: {
                System.out.println("Некорректный выбор.");
                break;
            }

        }


    }
}