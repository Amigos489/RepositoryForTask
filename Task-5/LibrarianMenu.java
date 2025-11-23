/* Класс главного меню */

public class LibrarianMenu implements Menu {

    @Override
    public void showMenu() {

        System.out.println("Меню библиотекаря:");
        System.out.println("1. Добавить книгу на склад.");
        System.out.println("2. Списать книгу со склада.");
        System.out.println("3. Просмотр всех заказов.");
        System.out.println("4. Просмотр выполненных заказов.");
        System.out.println("5. Просмотр запросов");
        System.out.println("6. Просмотр залежавшихся книг.");
        System.out.println("7. Получить сумму заработанных средств.");
        System.out.println("8. Узнать количество выполненных заказов.");
        System.out.println("9. Вернуться в главное меню.");
        System.out.print("Выбор: ");

    }

    public void showSortedCriterionBookRequest() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по алфавиту.");
        System.out.println("2. по количеству запросов.");
        System.out.print("Выбор: ");

    }

    public void showSortedCriterionComplectedOrder() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по дате исполнения.");
        System.out.println("2. по цене");
        System.out.print("Выбор: ");

    }

    public void showSortedCriterionStaleBook() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по дате поступления.");
        System.out.println("2. по цене.");
        System.out.print("Выбор: ");

    }

}