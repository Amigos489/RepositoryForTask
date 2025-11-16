import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/* Класс контроллера меню библиотекаря */
public class ControllerMenuLibrarian {

    private ViewMenuLibrarian viewMenuLibrarian;
    private ModelStoreBook modelStoreBook;
    private String phoneLibrarian;
    private String passwordLibrarian;
    private boolean running;
    public int choiceUser;

    /* Конструктор */
    public ControllerMenuLibrarian(ViewMenuLibrarian viewMenuLibrarian, ModelStoreBook modelStoreBook) {
        this.viewMenuLibrarian = viewMenuLibrarian;
        this.modelStoreBook = modelStoreBook;
    }

    public void startMenuLibrarian() {

        Librarian librarian = null;
        running = true;

        while(librarian == null) {

            phoneLibrarian = viewMenuLibrarian.displayInputPhone();
            librarian = modelStoreBook.findLibrarianByPhone(phoneLibrarian);
            //Пока просто проверка на null
            if (librarian != null) {
                System.out.println("Добрый день, " + librarian.getFullName());
                passwordLibrarian = viewMenuLibrarian.displayInputPassword();
                if (modelStoreBook.checkPasswordLibrarian(librarian, passwordLibrarian)) {
                    System.out.println("Успешный вход.");
                } else {
                    System.out.println("Неверный пароль. повторите снова.");
                    librarian = null;
                }
            } else {
                System.out.println("Библиотекарь c таким телефоном не найден. повторите снова.");
            }

        }

        while (running) {

            choiceUser = viewMenuLibrarian.displayLibrarianMenu();

            switch (choiceUser) {

                case 1: {
                    System.out.println("Добавление книги на склад.");
                    Book book = viewMenuLibrarian.displayAddBook();
                    viewMenuLibrarian.printStatusAddBook(modelStoreBook.getWarehouse().addToWarehouse(book));
                    break;
                }
                case 2: {
                    System.out.println("Списание книги со склада.");
                    String nameBook = viewMenuLibrarian.printInputNameBook();
                    viewMenuLibrarian.printStatusWriteFromWarehouse(modelStoreBook.getWarehouse().writeFromWarehouse(nameBook));
                    break;
                }
                case 3: {
                    System.out.println("Просмотр всех заказов.");
                    ArrayList<Order> allOrders = modelStoreBook.getOrderManagement().getAllOrders();
                    viewMenuLibrarian.printAllOrders(allOrders);
                    break;
                }
                case 4: {
                    System.out.println("Просмотр выполненных заказов.");
                    LocalDate startDate = viewMenuLibrarian.printInputStartDate();
                    LocalDate endDate = viewMenuLibrarian.printInputEndDate();
                    choiceUser = viewMenuLibrarian.displayCompletedOrder();
                    getCompletedOrders(choiceUser, startDate, endDate);
                    break;
                }
                case 5: {
                    System.out.println("Просмотр запросов на книгу.");
                    choiceUser = viewMenuLibrarian.displayBookRequest();
                    String nameBook = viewMenuLibrarian.printInputNameBook();
                    Book book = modelStoreBook.getWarehouse().findBookByName(nameBook);
                    if (book != null) {
                        ArrayList<BookRequest> requests = modelStoreBook.getOrderManagement().getRequestsForBook(book);
                        if (requests.isEmpty()) {
                            viewMenuLibrarian.displayMessage("Не найдено запросов на книгу.");
                            break;
                        } else {
                            viewMenuLibrarian.printBookRequests(requests);
                        }
                    } else {
                        viewMenuLibrarian.displayMessage("Не найдена книга с таким названием");
                    }
                    break;
                }
                case 6: {
                    System.out.println("Просмотр залежавшихся книг.");
                    choiceUser = viewMenuLibrarian.displayStaleBook();
                    getStaleBook(choiceUser);
                    break;
                }
                case 7: {
                    System.out.println("Получить сумму заработанных средств.");
                    LocalDate startDate = viewMenuLibrarian.printInputStartDate();
                    LocalDate endDate = viewMenuLibrarian.printInputEndDate();
                    int profit = modelStoreBook.getOrderManagement().getRevenue(startDate, endDate);
                    viewMenuLibrarian.displayMessage("Сумма заработанных средств с " + startDate + " по " + endDate + ": " + profit);
                    break;
                }
                case 8: {
                    System.out.println("Количество выполненных заказов за период");
                    LocalDate startDate = viewMenuLibrarian.printInputStartDate();
                    LocalDate endDate = viewMenuLibrarian.printInputEndDate();
                    System.out.println("Количество выполненных заказов за период: " + 
                            modelStoreBook.getOrderManagement().getCompletedOrdersCount(startDate, endDate));
                    break;
                }

                case 9: {
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

    public void getBookRequest(int choiceUser) {

        String nameBook = viewMenuLibrarian.printInputNameBook();
        Book book = modelStoreBook.getWarehouse().findBookByName(nameBook);

        ArrayList<BookRequest> requests = modelStoreBook.getOrderManagement().getRequestsForBook(book);

        switch (choiceUser) {
            case 1:
                System.out.println("Сортировка по алфавиту.");
                Collections.sort(requests, new SortedBookRequestByNameBook());
                viewMenuLibrarian.printBookRequests(requests);
                break;
            case 2:
                System.out.println("Сортировка по количеству запросов.");
                Collections.sort(requests, new SortedBookRequestByCount());
                viewMenuLibrarian.printBookRequests(requests);
                break;
            default:
                System.out.println("Некорректный выбор.");
                break;
        }

    }

    public void getCompletedOrders(int choiceUser, LocalDate startDate, LocalDate endDate) {

        ArrayList<Order> allCompletedOrder = modelStoreBook.getOrderManagement()
                .getCompletedOrders(startDate, endDate);

        switch (choiceUser) {
            case 1: {
                System.out.println("Сортировка по дате исполнения.");
                Collections.sort(allCompletedOrder, new SortedOrderByDateOfExecution());
                viewMenuLibrarian.printAllOrders(allCompletedOrder);
                break;
            }
            case 2: {
                System.out.println("Сортировка по цене.");
                Collections.sort(allCompletedOrder, new SortedOrderByPrice());
                viewMenuLibrarian.printAllOrders(allCompletedOrder);
                break;
            }
        }

    }

    public void getStaleBook(int choiceUser) {

        ArrayList<Book> staleBooks = modelStoreBook.getWarehouse().getStaleBooks();
        switch (choiceUser) {
            case 1: {
                System.out.println("Сортировка по дате поступления.");
                Collections.sort(staleBooks, new SortedBookByDateOfPublication());
                viewMenuLibrarian.printBooksAssortment(staleBooks);
                break;
            }
            case 2: {
                System.out.println("Сортировка по цене.");
                Collections.sort(staleBooks, new SortedBookByPrice());
                viewMenuLibrarian.printBooksAssortment(staleBooks);
                break;
            }
            default:
                viewMenuLibrarian.displayMessage("Некорректный выбор.");
                break;
        }

    }
}