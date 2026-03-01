package senla.course.ui.console;

import senla.course.ui.Builder;
import senla.course.ui.MenuItem;

import java.util.List;

public class ConsoleMenuBuilder implements Builder {

    private String nameMenu;
    private List<MenuItem> menuItems;

    @Override
    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    @Override
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    @Override
    public ConsoleMenu createMenu() {
        return new ConsoleMenu(nameMenu, menuItems);
    }
}
