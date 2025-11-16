import java.util.Scanner;

public class ViewMenu {

    private Scanner input = new Scanner(System.in);

    /* Метод отображения главного меню */
    public void displayMenu() {

        System.out.println("Главное меню:");
        System.out.println("Выберете способ входа:");
        System.out.println("1. Клиент\n2. Библиотекарь\n3. Импорт и экспорт данных\n4. Выход из программы");

    }

    /* Метод ввода */
    public int getChoice() {
        int choice = input.nextInt();
        input.nextLine(); // Очистка буфера
        return choice;
    }

    /* Метод вывода сообщения */
    public void displayMessage(String message) {
        System.out.println(message);
    }


    public int displayImportExport() {
        System.out.println("Выберите действие.");
        System.out.println("1. Экспорт данных.");
        System.out.println("2. Импорт данных.");
        return getChoice();
    }

    public int displayImport() {
        System.out.println("Выберите действие.");
        System.out.println("1. Импорт клиентов.");
        System.out.println("2. Импорт библиотекарей.");
        System.out.println("3. Импорт книг.");
        System.out.println("4. Импорт заказов.");
        return getChoice();
    }

    public int displayExport() {
        System.out.println("Выберите действие.");
        System.out.println("1. Экспорт клиентов.");
        System.out.println("2. Экспорт библиотекарей.");
        System.out.println("3. Экспорт книг.");
        System.out.println("4. Экспорт заказов.");
        return getChoice();
    }
    
}