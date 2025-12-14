/* Класс главного меню */

public class MainMenu implements Menu {

    @Override
    public void showMenu() {

        System.out.println("Главное меню:");
        System.out.println("Выберете способ входа:");
        System.out.println("1. Клиент");
        System.out.println("2. Библиотекарь");
        System.out.println("3. Выход из программы");
        System.out.print("Выбор: ");

    }

}