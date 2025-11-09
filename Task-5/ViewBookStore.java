import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ViewBookStore {

    private Scanner input = new Scanner(System.in);
    private MessagePrinter mp = (msg) -> System.out.println(msg);

    /* Главное меню */
    public int displayStartMenu() {
        mp.printMessage("Главное меню:");
        mp.printMessage("Выберите профиль: 0 - Клиент, 1 - Библиотекарь, 2 - выход из программы");
        mp.printMessage("Выбор: ");
        int choice = input.nextInt();
        input.nextLine(); // очищаем буфер после nextInt()
        return choice;
    }

    /* Меню клиента */
    public int displayClientMenu() {
        mp.printMessage("Выбран профиль клиент");
        mp.printMessage("Выберите действие: ");
        mp.printMessage("0 - просмотреть ассортимент");
        mp.printMessage("1 - информация о книге"); 
        mp.printMessage("2 - сделать заказ");
        mp.printMessage("3 - отменить заказ");
        mp.printMessage("Выбор: ");
        int choice = input.nextInt();
        input.nextLine(); // очищаем буфер после nextInt()
        return choice; 
    }

    /* Меню библиотекаря */
    public int displayLibrarianMenu() {
        mp.printMessage("Выбран профиль библиотекарь.");
        mp.printMessage("Выберите действие: ");
        mp.printMessage("0 - добавить книгу на склад");
        mp.printMessage("1 - списать книгу со склада");
        mp.printMessage("2 - получить список залежавшихся книг");
        mp.printMessage("Выбор: ");
        int choice = input.nextInt();
        input.nextLine(); // очищаем буфер после nextInt()
        return choice;
    }

    /* Ввод названия книги для списания или информации */
    public String inputBookName() {
        System.out.print("Введите название книги: ");
        return input.nextLine().trim();
    }

    public String inputBookNameForInfo() {
        System.out.print("Введите название книги для просмотра: ");
        return input.nextLine().trim();
    }

    /* Ввод новой книги */
    public Book inputNewBook() {
        System.out.print("Введите название книги: ");
        String name = input.nextLine();

        System.out.print("Введите автора книги: ");
        String author = input.nextLine();

        System.out.print("Введите количество страниц: ");
        int pages = input.nextInt();

        System.out.print("Введите количество копий: ");
        int copies = input.nextInt();

        System.out.print("Введите цену: ");
        int price = input.nextInt();
        input.nextLine(); // очищаем буфер после nextInt()

        LocalDate today = LocalDate.now();
        LocalDate publicationDate = today.minusYears(1);
        LocalDate dateAdded = today;

        return new Book(name, author, publicationDate, copies, pages, price, dateAdded);
    }

    /* Ввод имени клиента */
    public String inputCustomerName() {
        System.out.print("Введите ваше имя: ");
        return input.nextLine().trim();
    }

    /* Ввод email клиента */
    public String inputCustomerEmail() {
        System.out.print("Введите вашу почту: ");
        return input.nextLine().trim();
    }

    /* Ввод ID заказа для отмены */
    public int inputOrderID() {
        System.out.print("Введите ID заказа для отмены: ");
        int id = input.nextInt();
        input.nextLine(); // очищаем буфер после nextInt()
        return id;
    }

    /* Универсальный вывод сообщений */
    public void printMessage(String msg) {
        mp.printMessage(msg);
    }

    public void printAllBooks(List<Book> books) {
        if (books.isEmpty()) {
            mp.printMessage("На складе нет книг.");
            return;
        }
        for (Book book : books) {
            book.printInfo();
            mp.printMessage("---------------------------");
        }
    }

    public void messageExitProgram() {
        mp.printMessage("Выход из программы.");
    }

    public void messageIncorrectValue() {
        mp.printMessage("Ошибка! Некорректное значение.");
    }
}
