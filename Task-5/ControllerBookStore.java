import java.util.List;
import java.util.Scanner;

public class ControllerBookStore {
    private ViewBookStore vbs;
    private ModelBookStore mbs;

    /* Конструктор */
    ControllerBookStore(ViewBookStore vbs, ModelBookStore mbs) {
        this.vbs = vbs;
        this.mbs = mbs;
    }

    public void startMenu() {

        while (true) {

            switch (vbs.displayStartMenu()) {

                case 0: {

                    startClientMenu(vbs.displayClientMenu());
                    break;

                }

                case 1: {
                    
                    startLibrarianMenu(vbs.displayLibrarianMenu());
                    break;
                
                }

                case 2: {
                    
                    vbs.messageExitProgram();
                    return;
                
                }

                default: {

                    vbs.messageIncorrectValue();
                    break;

                }
            }
        }
    }

    public void startClientMenu (int choice) {

        switch (choice) {
            case 0: {

                List<Book> books = mbs.getWarehouse().getListBooks();
                vbs.printAllBooks(books);
                break;

            }

            case 1: { 

                String name = vbs.inputBookNameForInfo(); 
                Book book = mbs.getWarehouse().findBookByName(name); 
    
                if (book != null) {
                    book.printInfo(); // вывод информации о книге
                } else {
                    vbs.printMessage("Книга с таким названием не найдена.");
                }
                break;

            }

            case 2: {

                String bookName = vbs.inputBookName(); // имя книги
                Book book = mbs.getWarehouse().getListBooks().stream()
                            .filter(b -> b.getNameBook().equalsIgnoreCase(bookName))
                            .findFirst().orElse(null);

                if (book == null) {
                    vbs.printMessage("Книга не найдена на складе.");
                    break;
                }

                String customerName = vbs.inputCustomerName();
                String customerEmail = vbs.inputCustomerEmail();

                mbs.getOrderManagement().createOrderOnBook(book, customerName, customerEmail);
                break;

            }

            case 3: {

                int orderID = vbs.inputOrderID();
                vbs.printMessage("Отмена заказа...");
                mbs.getOrderManagement().cancelOrderOnBook(orderID);
                break;

            }

            default:
                vbs.messageIncorrectValue();
                break;
        }


    }

    public void startLibrarianMenu (int choice) {
        
        switch (choice) {
            case 0: {

                Book newBook = vbs.inputNewBook();
                StatusAddBook status = mbs.getWarehouse().addToWarehouse(newBook);

                switch (status) {

                    case SUCCESSFULY: {
                        vbs.printMessage("Книга успешно добавлена на склад.");
                        break;

                    }

                    case ONLYPART: {
                        vbs.printMessage("Добавлено только часть копий книги. Места на складе не хватило.");
                        break;

                    }

                    case FAIL: {

                        vbs.printMessage("Не удалось добавить книгу. Склад полон.");
                        break;

                    }
                }
                break;
            }


            case 1: {

                String name = vbs.inputBookName(); 
                boolean result = mbs.getWarehouse().writeFromWarehouse(name); 
                
                if (result) {
                    vbs.printMessage("Книга успешно списана со склада.");
                } else {
                    vbs.printMessage("Книга не найдена на складе или не удалось списать.");
                }
                break;

            }

            case 2: {

                List<Book> books = mbs.getWarehouse().getStaleBooks();
                vbs.printAllBooks(books);
                break;

            }
        
            default:
                vbs.messageIncorrectValue();
                break;
        }

    }

}