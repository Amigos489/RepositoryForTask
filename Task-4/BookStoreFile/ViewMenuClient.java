import java.util.ArrayList;
import java.util.Scanner;

public class ViewMenuClient {

    private Scanner input = new Scanner(System.in);
    private int choiceUser;


    /* Метод для ввода email */
    public String displayInputEmail() {
        System.out.print("Введите email: ");
        return input.nextLine();
    }

    /* Метод для ввода пароля */
    public String displayInputPassword() {
        System.out.print("Введите пароль: ");
        return input.nextLine();
    }

    /* Метод для отображения меню клиента */
    public int displayClientMenu() {
        System.out.println("Меню клиента:");
        System.out.println("1. Просмотр всего ассортимента книг."); /* + сортировка */
        System.out.println("2. Просмотр описания книги.");
        System.out.println("3. Просмотр своих заказов."); /* + сортировка */
        System.out.println("4. Создать заказ на книгу.");
        System.out.println("5. Отменить заказ.");
        System.out.println("6. Вернуться в главное меню");
        choiceUser = input.nextInt();
        input.nextLine(); 
        return choiceUser;
    }

    /* Метод отображения книг с сортировкой */
    public int displayBooksAssortment() {
        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. алфавиту.");
        System.out.println("2. дате издания.");
        System.out.println("3. цене.");
        System.out.println("4. наличию на складе");
        choiceUser = input.nextInt();
        input.nextLine(); 
        return choiceUser;
    }

    /* Метод отображения заказов с сортировкой */
    public int displayOrderClient() {
        System.out.println("Выберите критерий сортировки");
        System.out.println("1. по дате исполнения.");
        System.out.println("2. по цене");
        System.out.println("3. по статусу");
        choiceUser = input.nextInt();
        input.nextLine(); 
        return choiceUser;

    }

    public void printBooksAssortment(ArrayList<Book> books) {
        System.out.println("Ассортимент книг:");
        for (Book book : books) {
            System.out.println("\n-------------------");
            book.printInfo();
            System.out.println("-------------------");
        }
        
    }

    public String printInputNameBook() {

        String nameBook;

        System.out.print("Введите название книги: ");
        nameBook = input.nextLine();
        return nameBook;

    }

    public int printInputIdOrder() {

        int idOrder;

        System.out.print("Введите ID заказа для отмены:");
        idOrder = input.nextInt();
        input.nextLine();
        return idOrder;
    }

    public void printOrderClient(ArrayList<Order> orders) {
        System.out.println("Список ваших заказов:");
        for (Order order : orders) {
            System.out.println("\n-------------------");
            order.orderInfo();
            System.out.println("-------------------");
        }
    }

    /* Метод вывода сообщения */
    public void displayMessage(String message) {
        System.out.println(message);
    }


}