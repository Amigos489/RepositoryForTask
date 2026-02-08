package senla.course.ui;

import senla.course.ui.console.ConsoleMenu;
import senla.course.ui.console.ConsoleMenuItem;

import java.util.List;

public interface Builder {
     void setNameMenu(String nameMenu);
     void setMenuItems(List<MenuItem> menuItems);
     void addMenuItem(MenuItem menuItem);
     ConsoleMenu createMenu();
}
