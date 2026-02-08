import senla.course.di.spring.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import senla.course.model.ModelStoreBook;
import senla.course.model.OrderManagement;
import senla.course.model.Warehouse;
import senla.course.ui.Menu;
import senla.course.ui.MenuController;
import senla.course.ui.console.*;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Warehouse warehouse = context.getBean(Warehouse.class);
        OrderManagement orderManagement = context.getBean(OrderManagement.class);

        warehouse.initDataFromDataBase();
        orderManagement.initDataFromDataBase();

        ModelStoreBook modelStoreBook = context.getBean(ModelStoreBook.class);

        ConsoleNavigator navigator = context.getBean(ConsoleNavigator.class);
        MenuController menuController = context.getBean(MenuController.class);

        Menu mainMenu = context.getBean("mainMenu", Menu.class);

        navigator.setCurrentMenu(mainMenu);
        navigator.setMainMenu(mainMenu);
        menuController.run();
        modelStoreBook.saveDate();
    }
}
