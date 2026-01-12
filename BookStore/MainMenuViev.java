public class MainMenuViev extends MenuView {

    private MainMenuController mainMenuController; /* Контроллер главного меню */
    private ClientMenuView clientMenuView;
    private LibrarianMenuView librarianMenuViev;
    private ImportExportMenuViev importExportMenuViev;
    

    public MainMenuViev(MainMenuController mainMenuController, ClientMenuView clientMenuView, LibrarianMenuView librarianMenuView, ImportExportMenuViev importExportMenuViev) {
        this.mainMenuController = mainMenuController;
        this.clientMenuView = clientMenuView;
        this.librarianMenuViev = librarianMenuView;
        this.importExportMenuViev = importExportMenuViev;
    }

    @Override
    public void showMenu() {

        boolean running = true;

        while (running) {
            System.out.println("Главное меню:");
            System.out.println("Выберете способ входа:");
            System.out.println("1. Клиент");
            System.out.println("2. Библиотекарь");
            System.out.println("3. Импорт/Экспорт данных.");
            System.out.println("4. Выход из программы");
            System.out.print("Выбор: ");

            /* Вызов контроллера главного меню */
            StatusMainMenu statusMainMenu = mainMenuController.start();

            switch (statusMainMenu) {
                case CREATE_CLIENT_MENU: {
                    System.out.println("Вход как клиент.");
                    clientMenuView.showMenu();
                    break;
                }
                case CREATE_LIBRARIAN_MENU: {
                    System.out.println("Вход как библиотекарь.");
                    librarianMenuViev.showMenu();
                    break;
                }
                case IMPORT_EXPORT: {
                    System.out.println("Импорт/Экспорт данных.");
                    importExportMenuViev.showMenu();
                    break;
                }
                case EXIT: {
                    System.out.println("Выход.");
                    running = false;
                    break;
                }
                case UNCORRECT_CHOICE:
                    System.out.println("Некорректный выбор.");
                    break;
            }
        }

    }
    
}
