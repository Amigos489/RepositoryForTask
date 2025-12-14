public class Main {

    public static void main(String[] args) {
        JsonStorage jsonStorage = new JsonStorage("storeData.json");
        PropertyFile.loadPropertyFile("config.property");
        int cntMountStale = PropertyFile.getCntMountStale();
        boolean possibilityMarkComplected = PropertyFile.getPossibilityMarkComplected();
        Warehouse warehouse = new Warehouse(0, 200, cntMountStale);
        OrderManagement orderManagement = new OrderManagement(possibilityMarkComplected);
        ServiceStoreBook serviceStoreBook = new ServiceStoreBook(warehouse, orderManagement, jsonStorage);
        serviceStoreBook.loadAll();
        MenuClientController menuClientController = new MenuClientController(serviceStoreBook, new ClientMenu());
        MenuLibrarianController menuLibrarianController = new MenuLibrarianController(serviceStoreBook, new LibrarianMenu());
        MainMenu mainMenu = new MainMenu();
        MenuController menuController = new MenuController(mainMenu, menuClientController, menuLibrarianController);
        menuController.startMenu();
        serviceStoreBook.saveAll();
        
    }

}