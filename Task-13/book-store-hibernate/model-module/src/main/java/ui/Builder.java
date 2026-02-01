package ui;

import ui.console.ConsoleMenu;
import ui.console.ConsoleMenuItem;

import java.util.List;

public interface Builder {
     void setNameMenu(String nameMenu);
     void setMenuItems(List<ConsoleMenuItem> menuItems);
     void addMenuItem(ConsoleMenuItem menuItem);
     ConsoleMenu createMenu();
}
