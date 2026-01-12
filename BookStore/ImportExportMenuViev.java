public class ImportExportMenuViev extends MenuView {
    
    ImportExportMenuController importExportMenuController;

    ImportExportMenuViev(ImportExportMenuController importExportMenuController) {
        this.importExportMenuController = importExportMenuController;
    }

    @Override
    public void showMenu() {
        System.out.println("1. Импорт данных.");
        System.out.println("2. Экспорт данных.");
        System.out.print("Выбор: ");
        StatusExportImportMenu statusExportImportMenu  = importExportMenuController.start();
        switch (statusExportImportMenu) {
            case OPEN_EXPORT: {
                System.out.println("Экспортирование данных.");
                showExport();
                break;
            }
            case OPEN_IMPORT: {
                System.out.println("Импортирование данных.");
                showImport();
                break;
            }
            case UNCORRECT_CHOICE: {
                    System.out.println("Некорректный выбор.");
                    break;
                }
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
            case CORRECTLY: {
                System.out.println("Сущность импортирована.");
                break;
            }
            case FAIL: {
                super.printErrorMessage("Не удалось импортировать сущность.");
                break;
            }

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
            case CORRECTLY: {
                System.out.println("Сущность экспортирована.");
                break;
            }
            case FAIL: {
                super.printErrorMessage("Не удалось экспортировать сущность.");
                break;
            }
        }
    }
}
