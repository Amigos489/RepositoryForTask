import actions.GetProfit;
import actions.bookoperation.AddBookToWarehouseAction;
import actions.bookoperation.GetInfoBookAction;
import actions.bookoperation.list.GetListAllBookAction;
import actions.bookoperation.WriteBookFromWarehouseAction;
import actions.BackToMainMenuAction;
import actions.OpenMenuAction;
import actions.bookoperation.list.GetListStaleBookAction;
import actions.importexportoperation.ExportOperationDataCsvAction;
import actions.importexportoperation.ImportOperationDataCsvAction;
import actions.orderoperation.*;
import actions.orderoperation.list.GetListAllOrderAction;
import actions.orderoperation.list.GetListComplectedOrderAction;
import actions.requestoperation.GetListAllRequestAction;
import configuration.Configurator;
import controller.Controller;
import dao.BookDao;
import dao.OrderDao;
import dao.RequestDao;
import json.JsonDataModel;
import mapping.BookMapping;
import mapping.OrderMapping;
import mapping.RequestMapping;
import model.*;
import org.hibernate.Session;
import ui.MenuController;
import ui.console.*;
import java.util.ArrayList;

import util.HibernateUtil;

public class Main {

    public static void main(String[] args) {

        JsonDataModel jsonDataModel = new JsonDataModel("book-store.json");

        Session session = HibernateUtil.getCurrentSession();
        BookDao bookDao = new BookDao(session);
        OrderDao orderDao = new OrderDao(session);
        RequestDao requestDao = new RequestDao(session);

        BookMapping bookMapping = new BookMapping();
        OrderMapping orderMapping = new OrderMapping();
        RequestMapping requestMapping = new RequestMapping();

        Warehouse warehouse = new Warehouse(bookDao, bookMapping);
        OrderManagement orderManagement = new OrderManagement(orderDao, orderMapping, requestDao, requestMapping);

        Configurator configurator = new Configurator("config.properties");
        warehouse = (Warehouse) configurator.configurationObject(warehouse);
        warehouse.initDataFromDataBase();

        orderManagement = (OrderManagement) configurator.configurationObject(orderManagement);
        orderManagement.initDataFromDataBase();

        ModelStoreBook modelStoreBook = new ModelStoreBook(warehouse, orderManagement, jsonDataModel);
        ServiceStoreBook serviceStoreBook = new ServiceStoreBook(modelStoreBook);

        Controller controller = new Controller(serviceStoreBook);
        ConsoleNavigator navigator = new ConsoleNavigator();
        MenuController menuController = new ConsoleMenuController(navigator);

        String[] criteriaSortAllBook = { "По названию.", "По дате публикации.", "По цене.", "По наличию." };
        GetListAllBookAction getListAllBookAction = new GetListAllBookAction(controller, criteriaSortAllBook);

        String[] criteriaSortStaleBook = { "По дате добавления на склад.", "По цене." };
        GetListStaleBookAction getListStaleBookAction = new GetListStaleBookAction(controller, criteriaSortStaleBook);

        String[] criteriaSortAllOrder = { "По дате исполнения.", "По цене.", "По статусу." };
        GetListAllOrderAction getListAllOrderAction = new GetListAllOrderAction(controller, criteriaSortAllOrder);

        String[] criteriaSortComplectedOrder = { "По дате исполнения.", "По цене." };
        GetListComplectedOrderAction getListComplectedOrderAction = new GetListComplectedOrderAction(controller, criteriaSortComplectedOrder);

        String[] criteriaSortAllRequest = { "По количеству запросов.", "По алфавиту." };
        GetListAllRequestAction getListAllRequestAction = new GetListAllRequestAction(controller, criteriaSortAllRequest);

        String[] entitys = { "Книги", "Заказы", "Запросы" };

        ConsoleMenuBuilder builderBookMenu = new ConsoleMenuBuilder();
        builderBookMenu.setNameMenu("Управление книгами");
        builderBookMenu.setMenuItems(new ArrayList<ConsoleMenuItem>());
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Добавить книгу на склад.", new AddBookToWarehouseAction(controller, 3, "добавить на склад", "добавлена на склад", "при добавлении книги на склад")));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Списать книгу со склада.", new WriteBookFromWarehouseAction(controller, 3, "списать со склада", "списана со склада", "при списании книги со склада")));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Просмотреть описание книги.", new GetInfoBookAction(controller, "Укажите id книги для получения информации: ", "Не удалось найти книгу с указанным id.")));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список книг.", getListAllBookAction));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список залежавшихся книг.", getListStaleBookAction));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Вернуться в главное меню.", new BackToMainMenuAction(navigator)));
        ConsoleMenu bookMenu = builderBookMenu.createMenu();

        ConsoleMenuBuilder builderOrderMenu = new ConsoleMenuBuilder();
        builderOrderMenu.setNameMenu("Управление заказами");
        builderOrderMenu.setMenuItems(new ArrayList<ConsoleMenuItem>());
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Cоздать заказ.", new CreateOrderAction(controller)));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Отменить заказ.", new CloseOrderAction(controller, 3, "отменить", "отменён", "отменить")));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Отметить заказ выполненным.", new ComplectedOrderAction(controller, 3, "выполнить", "выполнен", "выполнить")));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Просмотреть описание заказа.", new GetInfoOrderAction(controller, "Чтобы просмотреть детали заказа, укажите id заказа: ", "Не удалось найти заказ.")));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список заказов.", getListAllOrderAction));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список выполненных заказов за период времени.", getListComplectedOrderAction));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Узнать количество выполненных заказов за период времени.", new GetCountComplectedOrderAction(controller)));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Получить прибыль за период времени.", new GetProfit(controller)));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Вернуться в главное меню.", new BackToMainMenuAction(navigator)));
        ConsoleMenu orderMenu = builderOrderMenu.createMenu();

        ConsoleMenuBuilder builderRequestMenu = new ConsoleMenuBuilder();
        builderRequestMenu.setNameMenu("Управление запросами");
        builderRequestMenu.setMenuItems(new ArrayList<ConsoleMenuItem>());
        builderRequestMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список запросов", getListAllRequestAction));
        builderRequestMenu.addMenuItem(new ConsoleMenuItem("Вернуться в главное меню.", new BackToMainMenuAction(navigator)));
        ConsoleMenu requestMenu = builderRequestMenu.createMenu();

        ConsoleMenuBuilder builderExportAndImportMenu = new ConsoleMenuBuilder();
        builderExportAndImportMenu.setNameMenu("Импорт и экспорт данных в формат csv.");
        builderExportAndImportMenu.setMenuItems(new ArrayList<ConsoleMenuItem>());
        builderExportAndImportMenu.addMenuItem(new ConsoleMenuItem("Импорт данных из csv.", new ImportOperationDataCsvAction(controller, entitys, "импортировать")));
        builderExportAndImportMenu.addMenuItem(new ConsoleMenuItem("Экспорт данных в csv.", new ExportOperationDataCsvAction(controller, entitys, "экспортировать")));
        builderExportAndImportMenu.addMenuItem(new ConsoleMenuItem("Вернуться в главное меню.", new BackToMainMenuAction(navigator)));
        ConsoleMenu exportAndImportMenu = builderExportAndImportMenu.createMenu();

        ConsoleMenuBuilder builderMainMenu = new ConsoleMenuBuilder();
        builderMainMenu.setNameMenu("Главное меню");
        builderMainMenu.setMenuItems(new ArrayList<ConsoleMenuItem>());
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Управление книгами.", bookMenu, new OpenMenuAction(navigator, bookMenu)));
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Управление заказами.", bookMenu, new OpenMenuAction(navigator, orderMenu)));
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Управление запросами.", bookMenu, new OpenMenuAction(navigator, requestMenu)));
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Импорт и экспорт данных.", exportAndImportMenu, new OpenMenuAction(navigator, exportAndImportMenu)));
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Выход из программы.", bookMenu, new ExitAppAction(menuController)));
        ConsoleMenu mainMenu = builderMainMenu.createMenu();

        navigator.setCurrentMenu(mainMenu);
        navigator.setMainMenu(mainMenu);
        menuController.run();
        modelStoreBook.saveDate();
    }
}
