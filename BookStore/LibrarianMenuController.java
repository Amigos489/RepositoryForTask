import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class LibrarianMenuController extends MenuController {

    private ServiceStoreBook serviceStoreBook;

    LibrarianMenuController(ServiceStoreBook serviceStoreBook) {
        this.serviceStoreBook = serviceStoreBook;
    }

    public StatusLibrarianMenu start() {

        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            switch (result.getInputChoice()) {
                case 1: {
                    return StatusLibrarianMenu.ADD_BOOK_IN_WAREHOUSE;
                }
                case 2: {
                    return StatusLibrarianMenu.REMOVE_BOOK_FROM_WAREHOUSE;
                }
                case 3: {
                    return StatusLibrarianMenu.PRINT_ALL_ORDERS;
                }
                case 4: {
                    return StatusLibrarianMenu.PRINT_ALL_COMPLECTED_ORDERS;
                }
                case 5: {
                    return StatusLibrarianMenu.PRINT_ALL_REQUESTS;
                }
                case 6: {
                    return StatusLibrarianMenu.PRINT_STALE_BOOKS;
                }
                case 7: {
                    return StatusLibrarianMenu.PRINT_PROFIT;
                }
                case 8: {
                    return StatusLibrarianMenu.PRINT_CNT_COMPLECTED_ORDERS;
                }
                case 9: {
                    return StatusLibrarianMenu.BACK_MAIN_MENU;
                }
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

    public ArrayList<Order> sortedCriterionOrder() {

        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<Order> orders = serviceStoreBook.sortedCriterionOrder(result.getInputChoice());
            return orders;
        } else {
            return null;
        }
    }

    public ArrayList<Order> sortedCriterionComplectedOrder(LocalDate startDate, LocalDate endDate) {
        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<Order> complectedOrders = serviceStoreBook.sortedCriterionComplectedOrder(result.getInputChoice(), startDate, endDate);
            return complectedOrders;
        } else {
            return null;
        }
    }

    public ArrayList<BookRequest> sortedCriterionBookRequest() {
        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<BookRequest> requests = serviceStoreBook.sortedCriterionBookRequest(result.getInputChoice());
            return requests;
        } else {
            return null;
        }
    }

    public StatusAddBook addBookOnWarehouse() {
        Book newBook = new Book();
        return serviceStoreBook.addBookToWarehouse(newBook);
    }

    public Book createNewBook() {
        return null;
    }

    public ArrayList<Book> sortedCriterionStaleBook() {
        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<Book> staleBooks = serviceStoreBook.sortedCriterionStaleBook(result.getInputChoice());
            return staleBooks;
        } else {
            return null;
        }
    }

    /* Мето для получения даты, введённой пользователем */
    public InputValidation getInputDate() {
        String dateString = input.nextLine();
        try {
            LocalDate date = LocalDate.parse(dateString);
            return new InputValidation(date, false);
        } catch (DateTimeParseException e) {
            return new InputValidation(dateString, true);
        }
    }

    public int getProfit(LocalDate startDate, LocalDate endDate) {
        return serviceStoreBook.getOrderManagement().getRevenue(startDate, endDate);
    }

    public int getCntComplectedOrder(LocalDate startDate, LocalDate endDate) {
        return serviceStoreBook.getOrderManagement().getCompletedOrdersCount(startDate, endDate);
    }




    
}
