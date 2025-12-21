import java.util.ArrayList;

public class ClientMenuController extends MenuController {

    private ServiceStoreBook serviceStoreBook;

    ClientMenuController(ServiceStoreBook serviceStoreBook) {
        this.serviceStoreBook = serviceStoreBook;
    }

    public StatusClientMenu start() {

        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            switch (result.getInputChoice()) {
                case 1: {
                    return StatusClientMenu.PRINT_ALL_BOOKS;
                }
                case 2: {
                    return StatusClientMenu.PRINT_BOOK_INFO;
                }
                case 3: {
                    return StatusClientMenu.PRINT_ALL_ORDER;
                }
                case 4: {
                    return StatusClientMenu.CREATE_ORDER_ON_BOOK;
                }
                case 5: {
                    return StatusClientMenu.CANCEL_ORDER;
                }
                case 6: {
                    return StatusClientMenu.BACK_MAIN_MENU;
                }
            }
        }
        return StatusClientMenu.UNCORRECT_CHOICE;
    }

    public ArrayList<Book> sortedCriterionBook() {

        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            ArrayList<Book> books = serviceStoreBook.sortedCriterionBook(result.getInputChoice());
            return books;
        } else {
            return null;
        }
    }

    public Book getBookInfo() {

        InputValidation result = super.getUserLine();
        if (!result.getIsError()) {
            Book book = serviceStoreBook.getWarehouse().findBookByName(result.getInputLine());
            return book;
        } else {
            return null;
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

    public StatusCreateOrder createOrderOnBook(String nameBook, String userEmail) {

        Book book = serviceStoreBook.getWarehouse().findBookByName(nameBook);
        if (book == null) {
            return StatusCreateOrder.EXPECTATION;
        }
        return serviceStoreBook.createOrder(book, userEmail);
    }

    public boolean cancelOrderOnBook(int ID) {
        return serviceStoreBook.cancelOrderByID(ID);
    }
    
}
