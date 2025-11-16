import java.util.ArrayList;

/* Класс контроллера главного меню*/
public class ControllerMenu {

    private ViewMenu viewMenu;
    private ModelStoreBook modelStoreBook;
    private int choiceUser;
    private boolean running;

    /* Конструктор */
    public ControllerMenu(ViewMenu viewMenu) {
        this.viewMenu = viewMenu;
        this.modelStoreBook = new ModelStoreBook();
    }

    /* Метод старта программы */
    public void startMenu() {

        running = true;

        while(running) {

            viewMenu.displayMenu();
            choiceUser = viewMenu.getChoice();

            switch(choiceUser) {

                case 1: {
                    viewMenu.displayMessage("Вход как клиент.");
                    ControllerMenuClient controllerMenuClient = new ControllerMenuClient(new ViewMenuClient(), modelStoreBook);
                    controllerMenuClient.startMenuClient();
                    break;
                }
                case 2: {
                    viewMenu.displayMessage("Вход как библиотекарь.");
                    ControllerMenuLibrarian controllerMenuLibrarian = new ControllerMenuLibrarian(new ViewMenuLibrarian(), modelStoreBook);
                    controllerMenuLibrarian.startMenuLibrarian();
                    break;
                }
                case 3: {
                    viewMenu.displayMessage("Импорт и экспорт данных.");
                    System.out.println("Импорт/Экспорт сущностей.");
                    choiceUser = viewMenu.displayImportExport();
                    switch (choiceUser) {
                        case 1:
                            choiceUser = viewMenu.displayExport();
                            switch (choiceUser) {
                                case 1: {
                                    exportClient();
                                    break;
                                }
                                case 2: {
                                    exportLibrian();
                                    break;
                                }
                                case 3: {
                                    exportBook();
                                    break;
                                }
                                case 4: {
                                    exportOrder();
                                    break;
                                }
                                default: {
                                    System.out.println("Некорректный выбор.");
                                    break;
                                }
                            }
                            break;
                        case 2: {
                            choiceUser = viewMenu.displayImport();
                            switch (choiceUser) {
                                case 1: {
                                    importClients("clients.csv");
                                    break;
                                }
                                case 2: {
                                    importLibrarian("librians.csv");
                                    break;
                                }
                                case 3: {
                                    importBook("books.csv");
                                    break;
                                }
                                case 4: {
                                    importOrder("orders.csv");
                                    break;
                                }
                                default:{
                                    System.out.println("Некорректный выбор.");
                                    break;
                                }
                            }
                            break;
                        }
                    
                        default:
                            System.out.println("Некорректный выбор.");
                            break;
                    }
                    break;

                }
                case 4: {
                    viewMenu.displayMessage("Выход из программы.");
                    running = false;
                    break;
                }
                default: {
                    viewMenu.displayMessage("Некорректный выбор.");
                    break;
                }

            }
        }
        
    }


    public void exportClient() {
        ExportCSV.exportClients(modelStoreBook.getClients(), "clients.csv");
    }

    public void exportLibrian() {
        ExportCSV.exportLibrians(modelStoreBook.getLibrian(), "librians.csv");
    }

    public void exportBook() {
        ExportCSV.exportBooks(modelStoreBook.getAllBooks(), "books.csv");
    }

    public void exportOrder() {
        ExportCSV.exportOrders(modelStoreBook.getOrderManagement().getAllOrders(), "orders.csv");
    }

    public void importClients(String filePath) {
        ArrayList<Client> imported = ImportCSV.importClient(filePath);
        modelStoreBook.mergeClients(imported);
        viewMenu.displayMessage("Импорт клиентов завершён!");
    }

    public void importLibrarian(String filePath) {
        ArrayList<Librarian> imported = ImportCSV.importLibrarian(filePath);
        modelStoreBook.mergeLibrarian(imported);
        viewMenu.displayMessage("Импорт библиотекарей завершён!");
    }

    public void importOrder(String filePath) {
        ArrayList<Order> imported = ImportCSV.importOrders(filePath, modelStoreBook);
        modelStoreBook.mergeOrders(imported);
        viewMenu.displayMessage("Импорт заказов завершён!");
    }

    public void importBook(String filePath) {
        ArrayList<Book> imported = ImportCSV.importBooks(filePath);
        modelStoreBook.mergeBooks(imported);
        viewMenu.displayMessage("Импорт книг завершён!");
    }
    
}