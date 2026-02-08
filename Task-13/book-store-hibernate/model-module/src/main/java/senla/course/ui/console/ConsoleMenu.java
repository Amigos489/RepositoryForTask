package senla.course.ui.console;

import senla.course.ui.Menu;
import senla.course.ui.MenuItem;

import java.util.List;

public class ConsoleMenu implements Menu {

    private String nameMenu;
    private final List<MenuItem> menuItems;

    public ConsoleMenu(String nameMenu, List<MenuItem> menuItems) {
        this.nameMenu = nameMenu;
        this.menuItems = menuItems;
    }

    @Override
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    /* Геттеры и сеттеры */

    @Override
    public String getNameMenu() {
        return nameMenu;
    }

    @Override
    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
