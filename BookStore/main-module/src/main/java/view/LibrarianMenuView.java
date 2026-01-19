package view;

import controller.LibrarianMenuController;
import di.Inject;
import model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import status.StatusAddBook;
import status.StatusLibrarianMenu;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class LibrarianMenuView extends MenuView {

    private static final Logger logger = LogManager.getLogger(LibrarianMenuView.class);

    @Inject
    private LibrarianMenuController librarianMenuController; /* Контроллер меню библиотекаря */

    public LibrarianMenuView() {
    }

    @Override
    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1. Добавить книгу на склад.");
            System.out.println("2. Списать книгу со склада.");
            System.out.println("3. Просмотр всех заказов.");
            System.out.println("4. Просмотр выполненных заказов.");
            System.out.println("5. Просмотр запросов.");
            System.out.println("6. Просмотр залежавшихся книг.");
            System.out.println("7. Получить сумму заработанных средств.");
            System.out.println("8. Узнать количество выполненных заказов.");
            System.out.println("9. Вернуться в главное меню.");
            System.out.print("Выбор: ");

            /* Вызов контроллера меню библиотекаря */
            StatusLibrarianMenu statusLibrarianMenu = librarianMenuController.start();
            switch (statusLibrarianMenu) {
                case ADD_BOOK_IN_WAREHOUSE:
                    logger.info("Выбран пункт добавление книги на склад.");
                    System.out.println("Добавление книги на склад.");
                    printCreateBookInWarehouse();
                    break;
                case REMOVE_BOOK_FROM_WAREHOUSE:
                    logger.info("Выбран пункт списание книги со склада.");
                    System.out.println("Списание книги со склада.");
                    printRemoveBookFromWarehouse();
                    break;
                case PRINT_ALL_ORDERS:
                    logger.info("Выбран пункт вывод списка заказов");
                    System.out.println("Вывод списка заказов.");
                    showSortedCriterionOrder();
                    break;
                case PRINT_ALL_COMPLECTED_ORDERS:
                    logger.info("Выбран пункт вывод списка выполненных заказов за указанный период.");
                    System.out.println("Вывод списка выполненных заказов за указанный период.");
                    printComplectedOrder();
                    break;
                case PRINT_ALL_REQUESTS:
                    logger.info("Выбран пункт вывод списка запросов на книги.");
                    System.out.println("Вывод списка запросов на книги.");
                    showSortedCriterionBookRequest();
                    break;
                case PRINT_STALE_BOOKS:
                    logger.info("Выбран пункт вывод списка залежавшихся книг.");
                    System.out.println("Вывод списка залежавшихся книг.");
                    showSortedCriterionStaleBook();
                    break;
                case PRINT_PROFIT:
                    logger.info("Выбран пункт получение прибыли за период.");
                    System.out.println("Получение прибыли за период.");
                    printProfit();
                    break;
                case PRINT_CNT_COMPLECTED_ORDERS:
                    logger.info("Выбран пункт вывод количества выполненных заказов.");
                    System.out.println("Вывод количества выполненных заказов.");
                    printCntComplectedOrder();
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

    public void printCreateBookInWarehouse() {
        try {
            System.out.print("Укажите название книги: ");
            String nameBook = (librarianMenuController.getUserLine()).getInputLine();
            System.out.print("Укажите автора книги: ");
            String authorBook = (librarianMenuController.getUserLine()).getInputLine();
            System.out.print("Укажите дату публикации: ");
            LocalDate dateOfPublication = (librarianMenuController.getInputDate()).getDate();
            System.out.print("Укажите количество копий: ");
            int numberOfCopies = (librarianMenuController.getChoiceUser()).getInputChoice();
            System.out.print("Укажите количество страниц: ");
            int numberPages = (librarianMenuController.getChoiceUser()).getInputChoice();
            System.out.print("Укажите цену: ");
            int price = (librarianMenuController.getChoiceUser()).getInputChoice();
            LocalDate dateAddedToWarehouse = LocalDate.now();
            StatusAddBook statusAddBook = librarianMenuController.addBook(nameBook, authorBook, dateOfPublication, numberOfCopies, numberPages, price, dateAddedToWarehouse);
            switch (statusAddBook) {
                case SUCCESSFULY:
                    System.out.println("Книга добавлена на склад.");
                    break;
                case ONLYPART:
                    System.out.println("Добавлена только часть экземпляров книги.");
                    break;
                case FAIL:
                    super.printErrorMessage("Не удалось добавить книгу на склад.");
                    break;
            }
            logger.info("Команда обработана.");
        } catch (DateTimeParseException e) {
        }
    }

    public void printRemoveBookFromWarehouse() {
        System.out.print("Укажите название книги для списания: ");
        if (librarianMenuController.removeBookFromWarehouse()) {
            System.out.println("Книга списана со клада.");
        } else {
            super.printErrorMessage("Не удалось списать книгу со склада.");
        }
        logger.info("Команда обработана.");
    }

    public void printComplectedOrder() {
        try {
            printInputData("начальную");
            LocalDate startDate = (librarianMenuController.getInputDate()).getDate();
            printInputData("конечную");
            LocalDate endDate = (librarianMenuController.getInputDate()).getDate();
            OrderView.printOrders(showSortedCriterionComplectedOrder(startDate, endDate));
            logger.info("Команда обработана.");
        } catch (DateTimeParseException e) {
            logger.error("Введена некорректная дата.");
            super.printErrorMessage("Введена некорректная дата.");
        }
    }

    public void showSortedCriterionOrder() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по дате исполнения.");
        System.out.println("2. по цене");
        System.out.println("3. по статусу");
        System.out.print("Выбор: ");
        try {
            OrderView.printOrders(librarianMenuController.sortedCriterionOrder());
            logger.info("Команда обработана.");
        } catch (IllegalArgumentException e) {
        }
    }

    public void showSortedCriterionBookRequest() {
        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по алфавиту.");
        System.out.println("2. по количеству запросов.");
        System.out.print("Выбор: ");
        try {
            BookRequestView.printBookRequests(librarianMenuController.sortedCriterionBookRequest());
            logger.info("Команда обработана.");
        } catch (IllegalArgumentException e) {
        }
    }

    /* Проблема метода, не только выводит но и возвращает массив */
    public ArrayList<Order> showSortedCriterionComplectedOrder(LocalDate startDate, LocalDate endDate) {
        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по дате исполнения.");
        System.out.println("2. по цене");
        System.out.print("Выбор: ");
        try {
            return librarianMenuController.sortedCriterionComplectedOrder(startDate, endDate);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void showSortedCriterionStaleBook() {
        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по дате поступления.");
        System.out.println("2. по цене.");
        System.out.print("Выбор: ");
        try {
            BookView.printBooks(librarianMenuController.sortedCriterionStaleBook());
            logger.info("Команда обработана.");
        } catch (IllegalArgumentException e) {
        }
    }

    public void printProfit() {
        printInputData("начальную");
        try {
            LocalDate startDate = (librarianMenuController.getInputDate()).getDate();
            printInputData("конечную");
            LocalDate endDate = (librarianMenuController.getInputDate()).getDate();
            BigDecimal profit = librarianMenuController.getProfit(startDate, endDate);
            System.out.println("Прибыль с " + startDate + " по " + endDate + " составляет: " + profit);
            logger.info("Команда обработана.");
        } catch (DateTimeParseException e) {
            logger.error("Введена некорректная дата.");
            super.printErrorMessage("Введена некорректная дата.");
        }
    }

    public void printCntComplectedOrder() {
        try {
            printInputData("начальную");
            LocalDate startDate = (librarianMenuController.getInputDate()).getDate();

            printInputData("конечную");
            LocalDate endDate = (librarianMenuController.getInputDate()).getDate();

            int cntComplectedOrder = librarianMenuController.getCntComplectedOrder(startDate, endDate);
            System.out.println("Количество выполненных заказов с " + startDate + " по " + endDate + " составляет: " + cntComplectedOrder);
            logger.info("Команда обработана.");
        } catch (DateTimeParseException e) {
            logger.error("Введена некорректная дата.");
            super.printErrorMessage("Введена некорректная дата.");
        }
    }

    public void printInputData(String border) {
        System.out.print("Введите " + border + " дату (гггг-мм-чч): ");
    }
}
