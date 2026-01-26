package view;

import controller.ImportExportMenuController;
import di.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import status.StatusExportImportMenu;
import status.StatusImportExport;

public class ImportExportMenuViev extends MenuView {

    private static final Logger logger = LogManager.getLogger(ImportExportMenuViev.class);

    @Inject
    ImportExportMenuController importExportMenuController;

    public ImportExportMenuViev() {
    }

    public ImportExportMenuViev(ImportExportMenuController importExportMenuController) {
        this.importExportMenuController = importExportMenuController;
    }

    @Override
    public void showMenu() {
        System.out.println("ВНИМАНИЕ! на данный момент могут возникать ошибки при импорте/экспорте файлов csv.");
        System.out.println("1. Импорт данных.");
        System.out.println("2. Экспорт данных.");
        System.out.print("Выбор: ");
        StatusExportImportMenu statusExportImportMenu = importExportMenuController.start();
        switch (statusExportImportMenu) {
            case OPEN_EXPORT:
                logger.info("Выбран пункт экспортирование данных.");
                System.out.println("Экспортирование данных.");
                showExport();
                break;
            case OPEN_IMPORT:
                logger.info("Выбран пункт импортирование данных.");
                System.out.println("Импортирование данных.");
                showImport();
                break;
            case UNCORRECT_CHOICE:
                logger.info("Выбран некорректный пункт.");
                System.out.println("Некорректный выбор.");
                break;
        }
    }

    public void showImport() {
        System.out.println("Выберите сущность для импорта (Импортируйте строго в этом порядке).");
        System.out.println("1. Книга.");
        System.out.println("2. Заказ.");
        System.out.println("3. Запрос на книгу.");
        System.out.print("Выбор: ");
        StatusImportExport statusImportExport = importExportMenuController.importEssence();
        switch (statusImportExport) {
            case CORRECTLY:
                System.out.println("Сущность импортирована.");
                logger.info("Команда обработана.");
                break;
            case FAIL:
                super.printErrorMessage("Не удалось импортировать сущность.");
                logger.error("Не удалось импортировать сущность.");
                break;
        }
    }

    public void showExport() {
        System.out.println("Выберите сущность для экспорта.");
        System.out.println("1. Книга.");
        System.out.println("2. Заказ.");
        System.out.println("3. Запрос на книгу.");
        System.out.print("Выбор: ");
        StatusImportExport statusImportExport = importExportMenuController.exportEssence();
        switch (statusImportExport) {
            case CORRECTLY:
                System.out.println("Сущность экспортирована.");
                logger.info("Команда обработана.");
                break;
            case FAIL:
                super.printErrorMessage("Не удалось экспортировать сущность.");
                logger.error("Не удалось экспортировать сущность.");
                break;
        }
    }
}
