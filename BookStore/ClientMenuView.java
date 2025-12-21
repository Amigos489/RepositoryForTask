public class ClientMenuView extends MenuView {

    private ClientMenuController clientMenuController; /* Контроллер меню клиента */

    public ClientMenuView(ClientMenuController clientMenuController) {
        this.clientMenuController = clientMenuController;
    }

    @Override
    public void showMenu() {

        boolean running = true;

        while(running) {
            System.out.println("1. Просмотр всего ассортимента книг.");
            System.out.println("2. Просмотр описания книги.");
            System.out.println("3. Просмотр заказов.");
            System.out.println("4. Создать заказ на книгу.");
            System.out.println("5. Отменить заказ.");
            System.out.println("6. Вернуться в главное меню");
            System.out.print("Выбор: ");
            StatusClientMenu statusClientMenu = clientMenuController.start();
            switch (statusClientMenu) {
                case PRINT_ALL_BOOKS: {
                    System.out.println("Вывод всего ассортимента книг");
                    showSortedCriterionBook();
                    break;
                }
                case PRINT_BOOK_INFO: {
                    System.out.println("Вывод информации о книге.");
                    printBookInfo();
                    break;
                }
                case PRINT_ALL_ORDER: {
                    System.out.println("Вывод всех заказов");
                    showSortedCriterionOrder();
                    break;
                }
                case CREATE_ORDER_ON_BOOK: {
                    System.out.println("Создать заказ на книгу");
                    printCreateOrder();
                    break;
                }
                case CANCEL_ORDER: {
                    System.out.println("Закрыть заказ на книгу");
                    printCancelOrder();
                    break;
                }
                case BACK_MAIN_MENU: {
                    System.out.println("Выход в главное меню.");
                    running = false;
                    break;
                }
                case UNCORRECT_CHOICE: {
                    System.out.println("Некорректный выбор.");
                    break;
                }
            }
        }
    }
    
    /* Отображение вариантов сортировки для книг */
    public void showSortedCriterionBook() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. алфавиту.");
        System.out.println("2. дате издания.");
        System.out.println("3. цене.");
        System.out.println("4. наличию на складе");
        System.out.print("Выбор: ");
        BookView.printBooks(clientMenuController.sortedCriterionBook());
    }

    public void printBookInfo() {
        System.out.print("Введите название книги: ");
        Book book = clientMenuController.getBookInfo();
        if (book == null) {
            super.printErrorMessage("книги с таким название не найдено.");
        } else {
            BookView.printBookInfo(book);
        }
    }

    /* Отображение вариантов сортировки для заказов*/
    public void showSortedCriterionOrder() {

        System.out.println("Выберите критерий сортировки:");
        System.out.println("1. по дате исполнения.");
        System.out.println("2. по цене");
        System.out.println("3. по статусу");
        System.out.print("Выбор: ");
        OrderView.printOrders(clientMenuController.sortedCriterionOrder());
    }

    public void printCreateOrder() {
        System.out.print("Введите название книги: ");
        String nameBook = (clientMenuController.getUserLine()).getInputLine();
        System.out.print("Укажите свою почту: ");
        String userEmail = (clientMenuController.getUserLine()).getInputLine();

        StatusCreateOrder statusCreateOrder = clientMenuController.createOrderOnBook(nameBook, userEmail);
        if (statusCreateOrder == StatusCreateOrder.SUCCESSFULLY) {
            System.out.println("Заказ успешно создан.");
        } else {
            super.printErrorMessage("Книга отсутствует на складе. Создан запрос на книгу.");
        }
    }

    public void printCancelOrder() {
        System.out.print("Введите ID заказа для отмены: ");
        int ID = (clientMenuController.getChoiceUser()).getInputChoice();
        if (clientMenuController.cancelOrderOnBook(ID)) {
            System.out.println("Заказ с ID " + ID + " отменён.");
        } else {
            System.out.println("Не удалось отменить заказ.");
        }
    }
}
