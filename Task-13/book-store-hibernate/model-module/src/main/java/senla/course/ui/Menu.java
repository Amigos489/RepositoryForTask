package senla.course.ui;

import senla.course.ui.console.ConsoleMenuItem;

import java.util.List;

public interface Menu {
    void addMenuItem(MenuItem menuItem);
    String getNameMenu();
    void setNameMenu(String nameMenu);
    List<MenuItem> getMenuItems();
}

