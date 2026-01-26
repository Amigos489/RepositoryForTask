import java.sql.Connection;

public class MainClass {

    static final String NAME_JSON_FILE = "storeData.json";
    static final int CURRENT_CAPACITY_WAREHOUSE = 0;
    static final int MAX_CAPACITY_WAREHOUSE = 300;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        DI di = DI.getInstance();
        AppConfig config = new AppConfig();
        Configurator.configure(config);

        ConnectionDataBase connectionDataBase = ConnectionDataBase.getInstance();
        di.registerBean(ConnectionDataBase.class, connectionDataBase);
        connectionDataBase.openConnection(config.getURL(), config.getUser(),config.getPassword());
        di.registerBean(Connection.class, connectionDataBase.getConnection());

        BooksDAO booksDAO = new BooksDAO();
        di.registerBean(BooksDAO.class, booksDAO);
        di.injectDependencies(booksDAO);

        OrdersDAO ordersDAO = new OrdersDAO();
        di.registerBean(OrdersDAO.class, ordersDAO);
        di.injectDependencies(ordersDAO);

        BookRequestDAO bookRequestDAO = new BookRequestDAO();
        di.registerBean(BookRequestDAO.class, bookRequestDAO);
        di.injectDependencies(bookRequestDAO);

        Warehouse warehouse = new Warehouse();
        di.registerBean(Warehouse.class, warehouse);
        di.injectDependencies(warehouse);
        warehouse.initFromDAO();

        OrderManagement orderManagement = new OrderManagement();
        di.registerBean(OrderManagement.class, orderManagement);
        di.injectDependencies(orderManagement);
        orderManagement.initFromDAO();
        orderManagement.setPriceAllOrders();

        di.registerBean(JsonStorage.class, new JsonStorage(NAME_JSON_FILE));

        ServiceStoreBook serviceStoreBook = new ServiceStoreBook();
        di.registerBean(ServiceStoreBook.class, serviceStoreBook);
        di.injectDependencies(serviceStoreBook);
        //serviceStoreBook.loadAll();

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
        connectionDataBase.closeConnection();

    }
    
}
