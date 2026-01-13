public class MainClass {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        DI di = DI.getInstance();
        AppConfig config = new AppConfig();
        Configurator.configure(config);

        di.registerBean(Warehouse.class, new Warehouse(0, 300, config.getCntMountStale()));
        di.registerBean(OrderManagement.class, new OrderManagement(config.isPossibilityMarkComplected()));
        di.registerBean(JsonStorage.class, new JsonStorage("storeData.json"));

        ServiceStoreBook serviceStoreBook = new ServiceStoreBook();
        di.registerBean(ServiceStoreBook.class, serviceStoreBook);
        di.injectDependencies(serviceStoreBook);
        serviceStoreBook.loadAll();

        ClientMenuController clientMenuController = new ClientMenuController();
        di.registerBean(ClientMenuController.class, clientMenuController);
        di.injectDependencies(clientMenuController);
        ClientMenuView clientMenuView = new ClientMenuView();
        di.registerBean(ClientMenuView.class, clientMenuView);
        di.injectDependencies(clientMenuView);

        LibrarianMenuController librarianMenuController = new LibrarianMenuController();
        di.registerBean(LibrarianMenuController.class, librarianMenuController);
        di.injectDependencies(librarianMenuController);
        LibrarianMenuView librarianMenuView = new LibrarianMenuView();
        di.registerBean(LibrarianMenuView.class, librarianMenuView);
        di.injectDependencies(librarianMenuView);

        ImportExportMenuController importExportMenuController = new ImportExportMenuController();
        di.registerBean(ImportExportMenuController.class, importExportMenuController);
        di.injectDependencies(importExportMenuController);

        ImportExportMenuViev importExportMenuViev = new ImportExportMenuViev();
        di.registerBean(ImportExportMenuViev.class, importExportMenuViev);
        di.injectDependencies(importExportMenuViev);

        di.registerBean(MainMenuController.class, new MainMenuController());

        MainMenuViev mainMenuViev = new MainMenuViev();
        di.registerBean(MainMenuViev.class, mainMenuViev);
        di.injectDependencies(mainMenuViev);

        mainMenuViev.showMenu();
        serviceStoreBook.saveAll();

    }
    
}
