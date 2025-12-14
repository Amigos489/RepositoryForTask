public class Main {

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse(0, 200);
        ServiceStoreBook serviceStoreBook = new ServiceStoreBook(warehouse);
        MenuClientController menuClientController = new MenuClientController(serviceStoreBook, new ClientMenu());
        MenuLibrarianController menuLibrarianController = new MenuLibrarianController(serviceStoreBook, new LibrarianMenu());
        MainMenu mainMenu = new MainMenu();
        MenuController menuController = new MenuController(mainMenu, menuClientController, menuLibrarianController);
        menuController.startMenu();
        
    }

}