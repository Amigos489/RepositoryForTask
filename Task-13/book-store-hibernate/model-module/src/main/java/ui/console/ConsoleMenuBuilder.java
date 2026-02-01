package ui.console;

import ui.Builder;

import java.util.List;

public class ConsoleMenuBuilder implements Builder {

    private String nameMenu;
    private List<ConsoleMenuItem> menuItems;

    @Override
    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    @Override
    public void setMenuItems(List<ConsoleMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public void addMenuItem(ConsoleMenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    @Override
    public ConsoleMenu createMenu() {
        return new ConsoleMenu(nameMenu, menuItems);
    }
}
