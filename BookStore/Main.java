public class Main {

    public static void main(String[] args) {

        AppConfig config = new AppConfig();
        Configurator.configure(config);

        int cntMountStale = config.getCntMountStale();
        boolean possibilityMarkComplected = config.isPossibilityMarkComplected();


        JsonStorage jsonStorage = new JsonStorage("storeData.json");


        Warehouse warehouse = new Warehouse(0, 300, cntMountStale);
        OrderManagement orderManagement = new OrderManagement(possibilityMarkComplected);
        ServiceStoreBook serviceStoreBook = new ServiceStoreBook(warehouse, orderManagement, jsonStorage);
        serviceStoreBook.loadAll();

        ClientMenuController clientMenuController = new ClientMenuController(serviceStoreBook);
        ClientMenuView clientMenuView = new ClientMenuView(clientMenuController);

        LibrarianMenuController librarianMenuController = new LibrarianMenuController(serviceStoreBook);
        LibrarianMenuView librarianMenuView = new LibrarianMenuView(librarianMenuController);

        ImportExportMenuController importExportMenuController = new ImportExportMenuController(serviceStoreBook);
        ImportExportMenuViev importExportMenuViev = new ImportExportMenuViev(importExportMenuController);
        
        MainMenuController mainMenuController = new MainMenuController();
        MainMenuViev mainMenuViev = new MainMenuViev(mainMenuController, clientMenuView, librarianMenuView, importExportMenuViev);
        mainMenuViev.showMenu();

        serviceStoreBook.saveAll();
    }
    
}
