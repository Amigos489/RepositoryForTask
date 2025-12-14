import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MenuClientController implements Controller {

    private ClientMenu clientMenu;
    private Scanner input = new Scanner(System.in);
    private ServiceStoreBook serviceStoreBook; 
    
    /* Конструктор */ 
    MenuClientController(ServiceStoreBook serviceStoreBook, ClientMenu clientMenu) { 
        this.serviceStoreBook = serviceStoreBook;
        this.clientMenu = clientMenu; 
    }

    @Override
    public void startMenu() {

        boolean running = true;

        while (running) {
        
            clientMenu.showMenu();

            switch (getChoiceUser()) {
                case 1: {
                    clientMenu.printMessage("Просмотр всего ассортимента.");
                    clientMenu.showSortedCriterionBook();
                    ArrayList<Book> books = serviceStoreBook.sortedCriterionBook(getChoiceUser());
                    BookView.printBooks(books);
                    break;
                }
                case 2: {
                    clientMenu.printMessage("Просмотр описания книги.");
                    clientMenu.printMessage("Введите название книги: ");
                    String nameBook = getLineUser();
                    Book book = serviceStoreBook.getWarehouse().findBookByName(nameBook);
                    if (book != null) {
                        BookView.printBookInfo(book);
                    } else {
                        clientMenu.printMessage("Ошибка! Не найдено книги с таким названием.");
                    }
                    break;
                }
                case 3: {
                    clientMenu.printMessage("Просмотр заказов");
                    clientMenu.showSortedCriterionOrder();
                    ArrayList<Order> orders = serviceStoreBook.sortedCriterionOrder(getChoiceUser());
                    OrderView.printOrders(orders);
                    break;
                }
                case 4: {
                    clientMenu.printMessage("Создать заказ на книгу.");
                    createOrderOnBook();
                    break;
                }
                case 5: {
                    clientMenu.printMessage("Отменить заказ.");
                    cancelOrderOnBook();
                    break;
                }
                case 6: {
                    clientMenu.printMessage("Вернуться в главное меню");
                    running = false;
                    break;
                }
                default: {
                    clientMenu.printMessage("Ошибка! некорректный ввод."); 
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
                clientMenu.printMessage("Некорректный ввод. Введите число.");
            }
        }
    }

    public String getLineUser() {
        return input.nextLine();
    }


    public void createOrderOnBook() {

        clientMenu.printMessage("Введите название книги:");
        String nameBook = getLineUser();
        Book book = serviceStoreBook.getWarehouse().findBookByName(nameBook);
            if (book != null) {
                clientMenu.printMessage("Введите email:");
                String customerEmail = getLineUser();
                StatusCreateOrder result = serviceStoreBook.getOrderManagement().createOrderOnBook(book, customerEmail);
                if (result == StatusCreateOrder.SUCCESSFULLY) {
                    clientMenu.printMessage("Заказ успешно создан.");
                } else if (result == StatusCreateOrder.EXPECTATION) {
                    clientMenu.printMessage("Книги нет в наличии. Создан запрос на книгу");
                }
            } else {
                clientMenu.printMessage("Ошибка! Не найдено книги с таким названием.");
            }

    }

    public void cancelOrderOnBook() {

        clientMenu.printMessage("Введите ID заказа для отмены:");
        int ID = getChoiceUser();

        boolean result = serviceStoreBook.getOrderManagement().cancelOrderOnBook(ID);
        if (result) {
            clientMenu.printMessage("Заказ c " + ID + " отменён.");
        } else {
            clientMenu.printMessage("Ошибка! Не удалось отменить заказ");
        }

    }

}