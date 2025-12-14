import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuLibrarianController implements Controller {

    private LibrarianMenu librarianMenu; 
    private Scanner input = new Scanner(System.in);
    private ServiceStoreBook serviceStoreBook; 
    
    /* Конструктор */ 
    MenuLibrarianController(ServiceStoreBook serviceStoreBook, LibrarianMenu librarianMenu) { 
        this.serviceStoreBook = serviceStoreBook;
        this.librarianMenu = librarianMenu; 
    }

    @Override
    public void startMenu() {

        boolean running = true;

        while (running) {
        
            librarianMenu.showMenu();

            switch (getChoiceUser()) {
                case 1: {
                    librarianMenu.printMessage("Добавить книгу на склад.");
                    Book book = addNewBook();
                    addBookOnWarehouse(book);
                    break;
                }
                case 2: {
                    librarianMenu.printMessage("Списать книгу со склада");
                    writeBookFromWarehouse();
                    break;
                }
                case 3: {
                    librarianMenu.printMessage("Просмотр всех заказов.");
                    librarianMenu.showSortedCriterionBookRequest();
                    ArrayList<Order> orders = serviceStoreBook.getOrderManagement().getAllOrders();
                    OrderView.printOrders(orders);
                    break;
                }
                case 4: {
                    librarianMenu.printMessage("Просмотр выполненных заказов.");
                    LocalDate start = getDate();
                    LocalDate end = getDate();
                    librarianMenu.showSortedCriterionComplectedOrder();
                    ArrayList<Order> orders = serviceStoreBook.sortedCriterionComplectedOrder(getChoiceUser(), start, end);
                    OrderView.printOrders(orders);
                    break;
                }
                case 5: {
                    librarianMenu.printMessage("Просмотр запросов");
                    ArrayList<BookRequest> bookRequests = serviceStoreBook.getOrderManagement().getAllBookRequests();
                    BookRequestView.printBookRequests(bookRequests);
                    break;
                }
                case 6: {
                    librarianMenu.printMessage("Просмотр залежавшихся книг.");
                    librarianMenu.showSortedCriterionStaleBook();
                    ArrayList<Book> books = serviceStoreBook.sortedCriterionStaleBook(getChoiceUser());
                    BookView.printBooks(books);
                    break;
                }
                case 7: {
                    librarianMenu.printMessage("Получить сумму заработанных средств.");
                    LocalDate start = getDate();
                    LocalDate end = getDate();
                    int revenue = serviceStoreBook.getOrderManagement().getRevenue(start, end);
                    librarianMenu.printMessage("Прибыль с " + start + " до " + end + " составляет: " + revenue);
                    break;
                }
                case 8: {
                    librarianMenu.printMessage("Узнать количество выполненных заказов.");
                    LocalDate start = getDate();
                    LocalDate end = getDate();
                    int ordersCnt = serviceStoreBook.getOrderManagement().getCompletedOrdersCount(start, end);
                    librarianMenu.printMessage("Количество выполненных заказов с " + start + " до " + end + " составляет: " + ordersCnt);
                    break;
                }
                case 9: {
                    librarianMenu.printMessage("Вернуться в главное меню");
                    running = false;
                    break;
                }
                default: {
                    librarianMenu.printMessage("Ошибка! некорректный ввод."); 
                    break;
                }
            }
        }
    }

    public int getChoiceUser() {
        while (true) {
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                input.nextLine();
                return choice;
            } else {
                input.nextLine();
                librarianMenu.printMessage("Некорректный ввод. Введите число.");
            }
        }
    }

    public String getLineUser() {
        return input.nextLine();
    }

    /* Добавление книги */
    public Book addNewBook() {
        librarianMenu.printMessage("Введите название книги: ");
        String name = getLineUser();
        librarianMenu.printMessage("Введите автора книги: ");
        String author = getLineUser();
        librarianMenu.printMessage("Введите дату издания книги (ГГГГ-ММ-ДД): ");
        LocalDate dateOfPublication = LocalDate.parse(getLineUser());
        librarianMenu.printMessage("Введите количество экземпляров книги: ");
        int numberOfCopies = getChoiceUser();
        librarianMenu.printMessage("Введите количество страниц книги: ");
        int numberPages = getChoiceUser(); 
        librarianMenu.printMessage("Введите цену книги: ");
        int price = getChoiceUser(); 
        LocalDate dateAddedToWarehouse = LocalDate.now();
        return new Book(name, author, dateOfPublication, numberOfCopies, numberPages, price, dateAddedToWarehouse);
    }

    /* Добавление книги на склад */
    public void addBookOnWarehouse(Book book) {
        StatusAddBook result = serviceStoreBook.getWarehouse().addToWarehouse(book);
        switch (result) {
            case SUCCESSFULY: {
                librarianMenu.printMessage("Книга успешно добавлена на склад.");
                break;
            }
            case ONLYPART: {
                librarianMenu.printMessage("Книга добавлена на склад частично.");
                break;
            }
            case FAIL: {
                librarianMenu.printMessage("Не удалось добавить книгу на склад. недостаточно места.");
                break;
            }
        }
    }

    public void writeBookFromWarehouse() {

        String nameBook = getLineUser();
        boolean result = serviceStoreBook.getWarehouse().writeFromWarehouse(nameBook);
        if (result) {
            librarianMenu.printMessage("Книга списана со склада.");
        } else {
            librarianMenu.printMessage("Ошибка! не удалось списать книгу со склада");
        }

    }

    public LocalDate getDate() {
        librarianMenu.printMessage("Введите нужную дату (ГГГГ-ММ-ДД): ");
        String startDate = input.nextLine();
        return LocalDate.parse(startDate);
    }


}