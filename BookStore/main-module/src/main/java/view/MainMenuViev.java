package view;

import controller.MainMenuController;
import di.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import status.StatusMainMenu;

public class MainMenuViev extends MenuView {

    private static final Logger logger = LogManager.getLogger(MainMenuViev.class);

    @Inject
    private MainMenuController mainMenuController; /* Контроллер главного меню */
    @Inject
    private ClientMenuView clientMenuView;
    @Inject
    private LibrarianMenuView librarianMenuViev;
    @Inject
    private ImportExportMenuViev importExportMenuViev;

    public MainMenuViev() {
    }

    @Override
    public void showMenu() {

        boolean running = true;


        logger.info("Начало работы программы");
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
                case CREATE_CLIENT_MENU:
                    logger.info("Выбран пункт вход как клиент");
                    System.out.println("Вход как клиент.");
                    clientMenuView.showMenu();
                    break;
                case CREATE_LIBRARIAN_MENU:
                    logger.info("Выбран пункт вход как библиотекарь");
                    System.out.println("Вход как библиотекарь.");
                    librarianMenuViev.showMenu();
                    break;
                case IMPORT_EXPORT:
                    logger.info("Выбран пункт импорт/экспорт данных.");
                    System.out.println("Импорт/Экспорт данных.");
                    importExportMenuViev.showMenu();
                    break;
                case EXIT:
                    logger.info("Выбран пункт выход из программы");
                    System.out.println("Выход.");
                    running = false;
                    break;
                case UNCORRECT_CHOICE:
                    logger.info("Выбран некорректный пункт");
                    System.out.println("Некорректный выбор.");
                    break;
            }
        }
    }
}
