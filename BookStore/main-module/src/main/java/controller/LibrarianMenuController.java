package controller;


import di.Inject;
import model.Book;
import model.BookRequest;
import model.Order;
import model.ServiceStoreBook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import status.StatusAddBook;
import status.StatusLibrarianMenu;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class LibrarianMenuController extends MenuController {

    private static final Logger logger = LogManager.getLogger(LibrarianMenuController.class);

    @Inject
    private ServiceStoreBook serviceStoreBook;

    public LibrarianMenuController() {
    }

    public StatusLibrarianMenu start() {

        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            switch (result.getInputChoice()) {
                case 1:
                    return StatusLibrarianMenu.ADD_BOOK_IN_WAREHOUSE;
                case 2:
                    return StatusLibrarianMenu.REMOVE_BOOK_FROM_WAREHOUSE;
                case 3:
                    return StatusLibrarianMenu.PRINT_ALL_ORDERS;
                case 4:
                    return StatusLibrarianMenu.PRINT_ALL_COMPLECTED_ORDERS;
                case 5:
                    return StatusLibrarianMenu.PRINT_ALL_REQUESTS;
                case 6:
                    return StatusLibrarianMenu.PRINT_STALE_BOOKS;
                case 7:
                    return StatusLibrarianMenu.PRINT_PROFIT;
                case 8:
                    return StatusLibrarianMenu.PRINT_CNT_COMPLECTED_ORDERS;
                case 9:
                    return StatusLibrarianMenu.BACK_MAIN_MENU;
                default:
                    break;
            }
        }
        return StatusLibrarianMenu.UNCORRECT_CHOICE;
    }

    public StatusAddBook addBook(String nameBook, String authorBook, LocalDate dateOfPublication, int numberOfCopies, int numberPages, int price, LocalDate dateAddedToWarehouse) {
        Book book = serviceStoreBook.createBook(nameBook, authorBook, dateOfPublication, numberOfCopies, numberPages, price, dateAddedToWarehouse);
        return serviceStoreBook.addBookToWarehouse(book);
    }

    public boolean removeBookFromWarehouse() {
        InputValidation resultNameBook = super.getUserLine();
        if (!resultNameBook.getIsError()) {
            String nameBook = resultNameBook.getInputLine();
            return serviceStoreBook.getWarehouse().writeFromWarehouse(nameBook);
        } else {
            return false;
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

    public ArrayList<Order> sortedCriterionComplectedOrder(LocalDate startDate, LocalDate endDate) throws IllegalArgumentException {
        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<Order> complectedOrders = serviceStoreBook.sortedCriterionComplectedOrder(result.getInputChoice(), startDate, endDate);
            return complectedOrders;
        } else {
            logger.error("Некорректное значение при сортировке выполненных заказов.");
            throw new IllegalArgumentException();
        }
    }

    public ArrayList<BookRequest> sortedCriterionBookRequest() throws IllegalArgumentException {
        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<BookRequest> requests = serviceStoreBook.sortedCriterionBookRequest(result.getInputChoice());
            return requests;
        } else {
            logger.error("Некорректное значение при сортировке запросов на книгу.");
            throw new IllegalArgumentException();
        }
    }

    public StatusAddBook addBookOnWarehouse() {
        Book newBook = new Book();
        return serviceStoreBook.addBookToWarehouse(newBook);
    }

    public Book createNewBook() {
        return null;
    }

    public ArrayList<Book> sortedCriterionStaleBook() throws IllegalArgumentException {
        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<Book> staleBooks = serviceStoreBook.sortedCriterionStaleBook(result.getInputChoice());
            return staleBooks;
        } else {
            logger.error("Некорректное значение при сортировке залежавшихся книг.");
            throw new IllegalArgumentException();
        }
    }

    /* Метод для получения даты, введённой пользователем */
    public InputValidation getInputDate() throws DateTimeParseException {
        String dateString = input.nextLine();
        try {
            LocalDate date = LocalDate.parse(dateString);
            return new InputValidation(date, false);
        } catch (DateTimeParseException e) {
            logger.error("Некорректное значение для даты: ", dateString);
            throw e;
        }
    }

    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {
        return serviceStoreBook.getOrderManagement().getRevenue(startDate, endDate);
    }

    public int getCntComplectedOrder(LocalDate startDate, LocalDate endDate) {
        return serviceStoreBook.getOrderManagement().getCompletedOrdersCount(startDate, endDate);
    }
}
