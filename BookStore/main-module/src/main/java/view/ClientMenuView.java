package view;

import controller.ClientMenuController;
import di.Inject;
import model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import status.StatusClientMenu;
import status.StatusCreateOrder;

public class ClientMenuView extends MenuView {

    private static final Logger logger = LogManager.getLogger(ClientMenuView.class);

    @Inject
    private ClientMenuController clientMenuController; /* Контроллер меню клиента */

    public ClientMenuView() {
    }

    @Override
    public void showMenu() {

        boolean running = true;

        while (running) {
            System.out.println("1. Просмотр всего ассортимента книг.");
            System.out.println("2. Просмотр описания книги.");
            System.out.println("3. Просмотр заказов.");
            System.out.println("4. Создать заказ на книгу.");
            System.out.println("5. Отменить заказ.");
            System.out.println("6. Вернуться в главное меню");
            System.out.print("Выбор: ");
            StatusClientMenu statusClientMenu = clientMenuController.start();
            switch (statusClientMenu) {
                case PRINT_ALL_BOOKS:
                    logger.info("Выбран пункт вывод всего ассортимента книг.");
                    System.out.println("Вывод всего ассортимента книг");
                    showSortedCriterionBook();
                    break;
                case PRINT_BOOK_INFO:
                    logger.info("Выбран пункт вывод информации о книге.");
                    System.out.println("Вывод информации о книге.");
                    printBookInfo();
                    break;
                case PRINT_ALL_ORDER:
                    logger.info("Выбран пункт вывод всех заказов.");
                    System.out.println("Вывод всех заказов");
                    showSortedCriterionOrder();
                    break;
                case CREATE_ORDER_ON_BOOK:
                    logger.info("Выбран пункт создание заказа на книгу.");
                    System.out.println("Создать заказ на книгу");
                    printCreateOrder();
                    break;
                case CANCEL_ORDER:
                    logger.info("Выбран пункт закрытие заказа на книгу.");
                    System.out.println("Закрыть заказ на книгу");
                    printCancelOrder();
                    break;
                case BACK_MAIN_MENU:
                    logger.info("Выбран пункт выход в главное меню.");
                    System.out.println("Выход в главное меню.");
                    running = false;
                    break;
                case UNCORRECT_CHOICE:
                    logger.info("Выбран некорректный пункт.");
                    System.out.println("Некорректный выбор.");
                    break;
            }
        }
    }

    /* Отображение вариантов сортировки для книг */
    public void showSortedCriterionBook() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. алфавиту.");
        System.out.println("2. дате издания.");
        System.out.println("3. цене.");
        System.out.println("4. наличию на складе");
        System.out.print("Выбор: ");
        try {
            BookView.printBooks(clientMenuController.sortedCriterionBook());
            logger.info("Команда обработана.");
        } catch (IllegalArgumentException e) {
        }
    }

    public void printBookInfo() {
        System.out.print("Введите название книги: ");
        Book book = clientMenuController.getBookInfo();
        if (book == null) {
            super.printErrorMessage("книги с таким название не найдено.");
        } else {
            BookView.printBookInfo(book);
            logger.info("Команда обработана.");
        }
    }

    /* Отображение вариантов сортировки для заказов*/
    public void showSortedCriterionOrder() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по дате исполнения.");
        System.out.println("2. по цене");
        System.out.println("3. по статусу");
        System.out.print("Выбор: ");
        try {
            OrderView.printOrders(clientMenuController.sortedCriterionOrder());
            logger.info("Команда обработана.");
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    public void printCreateOrder() {
        System.out.print("Введите название книги: ");
        String nameBook = (clientMenuController.getUserLine()).getInputLine();
        System.out.print("Укажите свою почту: ");
        String userEmail = (clientMenuController.getUserLine()).getInputLine();

        StatusCreateOrder statusCreateOrder = clientMenuController.createOrderOnBook(nameBook, userEmail);
        if (statusCreateOrder == StatusCreateOrder.SUCCESSFULLY) {
            System.out.println("Заказ успешно создан.");
        } else {
            super.printErrorMessage("Книга отсутствует на складе");
        }
        logger.info("Команда обработана.");
    }

    public void printCancelOrder() {
        System.out.print("Введите ID заказа для отмены: ");
        int ID = (clientMenuController.getChoiceUser()).getInputChoice();
        if (clientMenuController.cancelOrderOnBook(ID)) {
            System.out.println("Заказ с ID " + ID + " отменён.");
        } else {
            System.out.println("Не удалось отменить заказ.");
        }
        logger.info("Команда обработана.");
    }
}
