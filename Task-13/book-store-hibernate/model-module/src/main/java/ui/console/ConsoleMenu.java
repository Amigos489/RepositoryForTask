package ui.console;

import ui.Menu;
import ui.MenuItem;
import java.util.List;

public class ConsoleMenu implements Menu {

    private String nameMenu;
    private final List<ConsoleMenuItem> menuItems;

    public ConsoleMenu(String nameMenu, List<ConsoleMenuItem> menuItems) {
        this.nameMenu = nameMenu;
        this.menuItems = menuItems;
    }

    public void addMenuItem(ConsoleMenuItem menuItem) {
        menuItems.add(menuItem);
    }

    /* Геттеры и сеттеры */

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    public List<ConsoleMenuItem> getMenuItems() {
        return menuItems;
    }
}
