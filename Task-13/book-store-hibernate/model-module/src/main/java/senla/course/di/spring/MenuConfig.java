package senla.course.di.spring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import senla.course.actions.BackToMainMenuAction;
import senla.course.actions.GetProfit;
import senla.course.actions.OpenMenuAction;
import senla.course.actions.bookoperation.AddBookToWarehouseAction;
import senla.course.actions.bookoperation.GetInfoBookAction;
import senla.course.actions.bookoperation.WriteBookFromWarehouseAction;
import senla.course.actions.bookoperation.list.GetListAllBookAction;
import senla.course.actions.bookoperation.list.GetListStaleBookAction;
import senla.course.actions.importexportoperation.ExportOperationDataCsvAction;
import senla.course.actions.importexportoperation.ImportOperationDataCsvAction;
import senla.course.actions.orderoperation.*;
import senla.course.actions.orderoperation.list.GetListAllOrderAction;
import senla.course.actions.orderoperation.list.GetListComplectedOrderAction;
import senla.course.actions.requestoperation.GetListAllRequestAction;
import senla.course.controller.Controller;
import senla.course.ui.MenuController;
import senla.course.ui.MenuItem;
import senla.course.ui.Navigator;
import senla.course.ui.console.*;

import java.util.ArrayList;

@Configuration
public class MenuConfig {

    @Bean
    public Navigator navigator() {
        return new ConsoleNavigator();
    }

    @Bean
    public MenuController menuController(Navigator navigator) {
        return new ConsoleMenuController(navigator);
    }

    @Bean
    public ConsoleMenu bookMenu(Controller controller, Navigator navigator) {

        String[] criteriaSortAllBook = { "По названию.", "По дате публикации.", "По цене.", "По наличию." };
        GetListAllBookAction getListAllBookAction = new GetListAllBookAction(controller, criteriaSortAllBook);

        String[] criteriaSortStaleBook = { "По дате добавления на склад.", "По цене." };
        GetListStaleBookAction getListStaleBookAction = new GetListStaleBookAction(controller, criteriaSortStaleBook);

        ConsoleMenuBuilder builderBookMenu = new ConsoleMenuBuilder();
        builderBookMenu.setNameMenu("Управление книгами");
        builderBookMenu.setMenuItems(new ArrayList<MenuItem>());
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Добавить книгу на склад.", new AddBookToWarehouseAction(controller, 3, "добавить на склад", "добавлена на склад", "при добавлении книги на склад")));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Списать книгу со склада.", new WriteBookFromWarehouseAction(controller, 3, "списать со склада", "списана со склада", "при списании книги со склада")));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Просмотреть описание книги.", new GetInfoBookAction(controller, "Укажите id книги для получения информации: ", "Не удалось найти книгу с указанным id.")));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список книг.", getListAllBookAction));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список залежавшихся книг.", getListStaleBookAction));
        builderBookMenu.addMenuItem(new ConsoleMenuItem("Вернуться в главное меню.", new BackToMainMenuAction(navigator)));
        return builderBookMenu.createMenu();
    }

    @Bean
    public ConsoleMenu orderMenu(Controller controller, Navigator navigator) {

        String[] criteriaSortAllOrder = { "По дате исполнения.", "По цене.", "По статусу." };
        GetListAllOrderAction getListAllOrderAction = new GetListAllOrderAction(controller, criteriaSortAllOrder);

        String[] criteriaSortComplectedOrder = { "По дате исполнения.", "По цене." };
        GetListComplectedOrderAction getListComplectedOrderAction = new GetListComplectedOrderAction(controller, criteriaSortComplectedOrder);

        ConsoleMenuBuilder builderOrderMenu = new ConsoleMenuBuilder();
        builderOrderMenu.setNameMenu("Управление заказами");
        builderOrderMenu.setMenuItems(new ArrayList<MenuItem>());
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Cоздать заказ.", new CreateOrderAction(controller)));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Отменить заказ.", new CloseOrderAction(controller, 3, "отменить", "отменён", "отменить")));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Отметить заказ выполненным.", new ComplectedOrderAction(controller, 3, "выполнить", "выполнен", "выполнить")));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Просмотреть описание заказа.", new GetInfoOrderAction(controller, "Чтобы просмотреть детали заказа, укажите id заказа: ", "Не удалось найти заказ.")));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список заказов.", getListAllOrderAction));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список выполненных заказов за период времени.", getListComplectedOrderAction));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Узнать количество выполненных заказов за период времени.", new GetCountComplectedOrderAction(controller)));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Получить прибыль за период времени.", new GetProfit(controller)));
        builderOrderMenu.addMenuItem(new ConsoleMenuItem("Вернуться в главное меню.", new BackToMainMenuAction(navigator)));
        return builderOrderMenu.createMenu();
    }

    @Bean
    public ConsoleMenu requestMenu(Controller controller, Navigator navigator) {
        String[] criteriaSortAllRequest = { "По количеству запросов.", "По алфавиту." };
        GetListAllRequestAction getListAllRequestAction = new GetListAllRequestAction(controller, criteriaSortAllRequest);

        ConsoleMenuBuilder builderRequestMenu = new ConsoleMenuBuilder();
        builderRequestMenu.setNameMenu("Управление запросами");
        builderRequestMenu.setMenuItems(new ArrayList<MenuItem>());
        builderRequestMenu.addMenuItem(new ConsoleMenuItem("Просмотреть список запросов", getListAllRequestAction));
        builderRequestMenu.addMenuItem(new ConsoleMenuItem("Вернуться в главное меню.", new BackToMainMenuAction(navigator)));
        return builderRequestMenu.createMenu();
    }

    @Bean
    public ConsoleMenu exportAndImportMenu(Controller controller, Navigator navigator) {
        String[] entitys = { "Книги", "Заказы", "Запросы" };

        ConsoleMenuBuilder builderExportAndImportMenu = new ConsoleMenuBuilder();
        builderExportAndImportMenu.setNameMenu("Импорт и экспорт данных в формат csv.");
        builderExportAndImportMenu.setMenuItems(new ArrayList<MenuItem>());
        builderExportAndImportMenu.addMenuItem(new ConsoleMenuItem("Импорт данных из csv.", new ImportOperationDataCsvAction(controller, entitys, "импортировать")));
        builderExportAndImportMenu.addMenuItem(new ConsoleMenuItem("Экспорт данных в csv.", new ExportOperationDataCsvAction(controller, entitys, "экспортировать")));
        builderExportAndImportMenu.addMenuItem(new ConsoleMenuItem("Вернуться в главное меню.", new BackToMainMenuAction(navigator)));
        return builderExportAndImportMenu.createMenu();
    }

    @Bean
    public ConsoleMenu mainMenu(@Qualifier("bookMenu") ConsoleMenu bookMenu,
                                @Qualifier("orderMenu") ConsoleMenu orderMenu,
                                @Qualifier("requestMenu") ConsoleMenu requestMenu,
                                @Qualifier("exportAndImportMenu") ConsoleMenu exportAndImportMenu, Navigator navigator, MenuController menuController) {
        ConsoleMenuBuilder builderMainMenu = new ConsoleMenuBuilder();
        builderMainMenu.setNameMenu("Главное меню");
        builderMainMenu.setMenuItems(new ArrayList<MenuItem>());
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Управление книгами.", bookMenu, new OpenMenuAction(navigator, bookMenu)));
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Управление заказами.", bookMenu, new OpenMenuAction(navigator, orderMenu)));
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Управление запросами.", bookMenu, new OpenMenuAction(navigator, requestMenu)));
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Импорт и экспорт данных.", exportAndImportMenu, new OpenMenuAction(navigator, exportAndImportMenu)));
        builderMainMenu.addMenuItem(new ConsoleMenuItem("Выход из программы.", bookMenu, new ExitAppAction(menuController)));
        return builderMainMenu.createMenu();
    }
}
