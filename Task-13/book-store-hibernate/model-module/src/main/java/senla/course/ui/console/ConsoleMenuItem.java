package senla.course.ui.console;

import senla.course.ui.IAction;
import senla.course.ui.Menu;
import senla.course.ui.MenuItem;

public class ConsoleMenuItem implements MenuItem {

    private String title;
    private IAction action;
    private Menu nextMenu;

    public ConsoleMenuItem(String title) {
        this.title = title;
    }

    public ConsoleMenuItem(String title, Menu nextMenu) {
        this.title = title;
        this.nextMenu = nextMenu;
    }

    public ConsoleMenuItem(String title, IAction action) {
        this.title = title;
        this.action = action;
    }

    public ConsoleMenuItem(String title, Menu nextMenu, IAction action) {
        this.title = title;
        this.nextMenu = nextMenu;
        this.action = action;
    }

    @Override
    public void doAction() {
        action.execute();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }
}

