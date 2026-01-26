package controller;

import di.Inject;
import model.Book;
import model.Order;
import model.ServiceStoreBook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import status.StatusClientMenu;
import status.StatusCreateOrder;
import java.util.ArrayList;

public class ClientMenuController extends MenuController {

    private static final Logger logger = LogManager.getLogger(ClientMenuController.class);

    @Inject
    private ServiceStoreBook serviceStoreBook;

    public ClientMenuController() {
    }

    public StatusClientMenu start() {

        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            switch (result.getInputChoice()) {
                case 1:
                    return StatusClientMenu.PRINT_ALL_BOOKS;
                case 2:
                    return StatusClientMenu.PRINT_BOOK_INFO;
                case 3:
                    return StatusClientMenu.PRINT_ALL_ORDER;
                case 4:
                    return StatusClientMenu.CREATE_ORDER_ON_BOOK;
                case 5:
                    return StatusClientMenu.CANCEL_ORDER;
                case 6:
                    return StatusClientMenu.BACK_MAIN_MENU;
            }
        }
        return StatusClientMenu.UNCORRECT_CHOICE;
    }

    public ArrayList<Book> sortedCriterionBook() throws IllegalArgumentException {

        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<Book> books = serviceStoreBook.sortedCriterionBook(result.getInputChoice());
            return books;
        } else {
            logger.error("Некорректное значение при сортировке книг.");
            throw new IllegalArgumentException();
        }
    }

    public Book getBookInfo() {

        InputValidation result = super.getUserLine();
        if (!result.getIsError()) {
            Book book = serviceStoreBook.getWarehouse().findBookByName(result.getInputLine());
            return book;
        } else {
            logger.error("Некорректное значение при выводе информации о книге.");
            return null;
        }
    }

    public ArrayList<Order> sortedCriterionOrder() throws IllegalArgumentException {

        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<Order> orders = serviceStoreBook.sortedCriterionOrder(result.getInputChoice());
            return orders;
        } else {
            logger.error("Некорректное значение при сортировке заказов.");
            throw new IllegalArgumentException();
        }
    }

    public StatusCreateOrder createOrderOnBook(String nameBook, String userEmail) {

        Book book = serviceStoreBook.getWarehouse().findBookByName(nameBook);
        if (book == null) {
            logger.error("Некорректное значение при создании заказа.");
            return StatusCreateOrder.EXPECTATION;
        }
        return serviceStoreBook.createOrder(book, userEmail);
    }

    public boolean cancelOrderOnBook(int id) {
        return serviceStoreBook.cancelOrderByID(id);
    }
}
