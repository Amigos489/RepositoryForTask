import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class ViewMenuLibrarian {

    private Scanner input = new Scanner(System.in);
    private int choiceUser;

    public String displayInputPhone() {
        System.out.print("Введите телефон: ");
        return input.nextLine();
    }

    public String displayInputPassword() {
        System.out.print("Введите пароль: ");
        return input.nextLine();
    }

    public int displayLibrarianMenu() {
        System.out.println("Меню библиотекаря:");
        System.out.println("1. Добавить книгу на склад.");
        System.out.println("2. Списать книгу со склада");
        System.out.println("3. Просмотр всех заказов.");
        System.out.println("4. Просмотр выполненных заказов.");
        System.out.println("5. Просмотр запросов на книгу.");
        System.out.println("6. Просмотр залежавшихся книг.");
        System.out.println("7. Получить сумму заработанных средств");
        System.out.println("8. Узнать количество выполненных заказов.");
        System.out.println("9. Вернуться в главное меню");
        choiceUser = input.nextInt();
        input.nextLine();
        return choiceUser;
    }

    public Book displayAddBook() {
        System.out.println("Введите название книги: ");
        String name = input.nextLine();
        System.out.println("Введите автора книги: ");
        String author = input.nextLine();
        System.out.println("Введите дату издания книги (ГГГГ-ММ-ДД): ");
        LocalDate dateOfPublication = LocalDate.parse(input.nextLine());
        System.out.println("Введите количество экземпляров книги: ");
        int numberOfCopies = input.nextInt();
        input.nextLine();
        System.out.println("Введите количество страниц книги: ");
        int numberPages = input.nextInt();
        input.nextLine(); 
        System.out.println("Введите цену книги: ");
        int price = input.nextInt();
        input.nextLine(); 
        LocalDate dateAddedToWarehouse = LocalDate.now();
        return new Book(name, author, dateOfPublication, numberOfCopies, numberPages, price, dateAddedToWarehouse);
    }

    public void printStatusAddBook(StatusAddBook status) {
        switch (status) {
            case SUCCESSFULY: {
                System.out.println("Книга успешно добавлена на склад.");
                break;
            }
            case ONLYPART: {
                System.out.println("Книга добавлена на склад частично.");
                break;
            }
            case FAIL: {
                System.out.println("Не удалось добавить книгу на склад. недостаточно места.");
                break;
            }
        }
    }


    public String printInputNameBook() {
        String nameBook;
        System.out.print("Введите название книги: ");
        nameBook = input.nextLine();
        return nameBook;
    }

    public void printStatusWriteFromWarehouse(boolean status) {
        if (status) {
            System.out.println("Книга успешно списана со склада.");
        } else {
            System.out.println("Не удалось списать книгу со склада.");
        }
    }

    public void printAllOrders(ArrayList<Order> orders) {
        System.out.println("Заказы:");
        for (Order order : orders) {
            System.out.println("\n-------------------");
            order.orderInfo();
            System.out.println("-------------------");
        }
    }


    public LocalDate printInputStartDate() {
        System.out.print("Введите начальную дату (ГГГГ-ММ-ДД): ");
        String startDate = input.nextLine();
        return LocalDate.parse(startDate);
    }

    public LocalDate printInputEndDate() {
        System.out.print("Введите конечную дату (ГГГГ-ММ-ДД): ");
        String endDate = input.nextLine();
        return LocalDate.parse(endDate);
    }


    /* Метод вывода сообщения */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void printBookRequests(ArrayList<BookRequest> requests) {
        System.out.println("Запросы на книгу:");
        for (BookRequest request : requests) {
            System.out.println("\n-------------------");
            request.printRequestInfo();
            System.out.println("\n-------------------");
        }
    }


    public void printBooksAssortment(ArrayList<Book> books) {
        System.out.println("Ассортимент книг:");
        for (Book book : books) {
            System.out.println("\n-------------------");
            book.printInfo();
            System.out.println("-------------------");
        }
        
    }

    public int displayBookRequest() {
        System.out.println("Выберите критерий сортировки запросов на книгу:");
        System.out.println("1. по алфавиту");
        System.out.println("2. по количеству запросов.");
        choiceUser = input.nextInt();
        input.nextLine(); 
        return choiceUser;
    }


    public int displayCompletedOrder() {
        System.out.println("Выберите критерий сортировки заказов на книгу:");
        System.out.println("1. по дате исполнения.");
        System.out.println("2. по цене");
        choiceUser = input.nextInt();
        input.nextLine(); 
        return choiceUser;
    }

    public int displayStaleBook() {
        System.out.println("Выберите критерий сортировки залежавшихся книг:");
        System.out.println("1. по дате поступления.");
        System.out.println("2. по цене");
        choiceUser = input.nextInt();
        input.nextLine(); 
        return choiceUser;
    }

}