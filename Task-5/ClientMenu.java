/* Класс меню клиента */

public class ClientMenu implements Menu {

    @Override
    public void showMenu() {

        System.out.println("Меню клиента:");
        System.out.println("1. Просмотр всего ассортимента книг.");
        System.out.println("2. Просмотр описания книги.");
        System.out.println("3. Просмотр заказов.");
        System.out.println("4. Создать заказ на книгу.");
        System.out.println("5. Отменить заказ.");
        System.out.println("6. Вернуться в главное меню");
        System.out.print("Выбор: ");

    }

    public void showSortedCriterionBook() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. алфавиту.");
        System.out.println("2. дате издания.");
        System.out.println("3. цене.");
        System.out.println("4. наличию на складе");
        System.out.print("Выбор: ");

    }

    public void showSortedCriterionOrder() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по дате исполнения.");
        System.out.println("2. по цене");
        System.out.println("3. по статусу");
        System.out.print("Выбор: ");

    }

}